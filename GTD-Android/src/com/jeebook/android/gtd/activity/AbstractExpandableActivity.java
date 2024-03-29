/*
 * Copyright (C) 2009 Android Shuffle Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jeebook.android.gtd.activity;

import com.jeebook.android.gtd.activity.config.ExpandableListConfig;

import com.jeebook.android.gtd.R;
import com.jeebook.android.gtd.model.Preferences;
import com.jeebook.android.gtd.util.AlertUtils;
import com.jeebook.android.gtd.util.BindingUtils;
import com.jeebook.android.gtd.util.MenuUtils;
import com.jeebook.android.gtd.view.SwipeListItemListener;
import com.jeebook.android.gtd.view.SwipeListItemWrapper;

import android.app.ExpandableListActivity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;

public abstract class AbstractExpandableActivity<G,C> extends ExpandableListActivity 
	implements SwipeListItemListener {
	
	private static final String cTag = "AbstractExpandableActivity";

	protected ExpandableListAdapter mAdapter;
	private ExpandableListConfig<G,C> mConfig;

	public AbstractExpandableActivity()
	{
		mConfig = createListConfig();
	}
	
	@Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(mConfig.getContentViewResId());
        setDefaultKeyMode(DEFAULT_KEYS_SHORTCUT);
        
		// Inform the list we provide context menus for items
        getExpandableListView().setOnCreateContextMenuListener(this);
        
        Cursor groupCursor = createGroupQuery();
        // Set up our adapter
        mAdapter = createExpandableListAdapter(groupCursor); 
        setListAdapter(mAdapter);
        
		// register self as swipe listener
		SwipeListItemWrapper wrapper = (SwipeListItemWrapper) findViewById(R.id.swipe_wrapper);
		wrapper.setSwipeListItemListener(this);        
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		refreshChildCount();
	}
	
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	switch (event.getKeyCode()) {
    	case KeyEvent.KEYCODE_N:
    		// go to previous view
    		int prevView = mConfig.getCurrentViewMenuId() - 1;
    		if (prevView < MenuUtils.INBOX_ID) {
    			prevView = MenuUtils.CONTEXT_ID;
    		}
    		MenuUtils.checkCommonItemsSelected(prevView, this, mConfig.getCurrentViewMenuId());
    		return true;
		case KeyEvent.KEYCODE_M:
			// go to previous view
			int nextView = mConfig.getCurrentViewMenuId() + 1;
			if (nextView > MenuUtils.CONTEXT_ID) {
				nextView = MenuUtils.INBOX_ID;
			}
			MenuUtils.checkCommonItemsSelected(nextView, this, mConfig.getCurrentViewMenuId());
			return true;
    	}
		return super.onKeyDown(keyCode, event);
	}

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item = menu.findItem(MenuUtils.SYNC_ID);
        if (item != null) {
            item.setVisible(Preferences.validateTracksSettings(this));
        }
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuUtils.addExpandableInsertMenuItems(menu, mConfig.getGroupName(this), 
        		mConfig.getChildName(this), this);
        MenuUtils.addViewMenuItems(menu, mConfig.getCurrentViewMenuId());
        MenuUtils.addPrefsHelpMenuItems(this, menu);
        MenuUtils.addSearchMenuItem(this, menu);
        
        return true;
    }
        
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case MenuUtils.INSERT_CHILD_ID:
	        	long packedPosition = getSelectedPosition();
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
	        	if (groupPosition > -1)
	        	{
		        	Cursor cursor = (Cursor) getExpandableListAdapter().getGroup(groupPosition);
		        	G group = getListConfig().readGroup(cursor, getResources());
	        		insertItem(mConfig.getChildContentUri(), group);	        		
	        	}
	        	else
	        	{
	        		insertItem(mConfig.getChildContentUri());
	        	}
	            return true;
	        case MenuUtils.INSERT_GROUP_ID:
	            insertItem(mConfig.getGroupContentUri());
	            return true;
        }
        if (MenuUtils.checkCommonItemsSelected(item, this, mConfig.getCurrentViewMenuId())) return true;
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
    	ExpandableListView.ExpandableListContextMenuInfo info;
        try {
             info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            Log.e(cTag, "bad menuInfo", e);
            return;
        }
        long packedPosition = info.packedPosition;
        int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
        boolean isChild = isChild(packedPosition); 
        Cursor cursor;
        if (isChild) {
        	cursor = (Cursor)(getExpandableListAdapter().getChild(groupPosition, childPosition));
        } else {
        	cursor = (Cursor)(getExpandableListAdapter().getGroup(groupPosition));
        }
        if (cursor == null) {
            // For some reason the requested item isn't available, do nothing
            return;
        }

        // Setup the menu header
        menu.setHeaderTitle(cursor.getString(1));

        if (isChild)
        {
        	long childId = getExpandableListAdapter().getChildId(groupPosition, childPosition);
            Uri selectedUri = ContentUris.withAppendedId(mConfig.getChildContentUri(), childId);
            MenuUtils.addSelectedAlternativeMenuItems(menu, selectedUri, false);
        	MenuUtils.addCompleteMenuItem(menu);
        }
        else
        {
        	long groupId = getExpandableListAdapter().getGroupId(groupPosition);
            Uri selectedUri = ContentUris.withAppendedId(mConfig.getGroupContentUri(), groupId);
            MenuUtils.addSelectedAlternativeMenuItems(menu, selectedUri, false);
            MenuUtils.addInsertMenuItems(menu, mConfig.getChildName(this), true, this);
        }
		// ... and ends with the delete command.
		MenuUtils.addDeleteMenuItem(menu);
    }
        
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	ExpandableListView.ExpandableListContextMenuInfo info;
        try {
             info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            Log.e(cTag, "bad menuInfo", e);
            return false;
        }

        switch (item.getItemId()) {
	        case MenuUtils.COMPLETE_ID:
	            toggleComplete(info.packedPosition, info.id);
	            return true;

	        case MenuUtils.DELETE_ID: 
                // Delete the item that the context menu is for
    			deleteItem(info.packedPosition);
                return true;
	            
	        case MenuUtils.INSERT_ID:
                int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
	        	if (groupPosition > -1)
	        	{
		        	Cursor cursor = (Cursor) getExpandableListAdapter().getGroup(groupPosition);
		        	G group = getListConfig().readGroup(cursor, getResources());
	        		insertItem(mConfig.getChildContentUri(), group);	        		
	        	}
	        	else
	        	{
	        		insertItem(mConfig.getChildContentUri());
	        	}
	            return true;
        }
        return false;
    }	 
    
    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
   		Uri url = ContentUris.withAppendedId(mConfig.getChildContentUri(), id);
		// Launch activity to view/edit the currently selected item
		startActivity(getClickIntent(url));
		return true;
	}
    	

    public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

        public MyExpandableListAdapter(Context context, Cursor cursor, int groupLayout,
                int childLayout, String[] groupFrom, int[] groupTo, String[] childrenFrom,
                int[] childrenTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childrenFrom,
                    childrenTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
        	long groupId = groupCursor.getLong(getGroupIdColumnIndex());
        	Log.d(cTag, "getChildrenCursor for groupId " + groupId);
    		return createChildQuery(groupId);
        }

    }

	public void onListItemSwiped(int position) {
		long packedPosition = getExpandableListView().getExpandableListPosition(position);
		if (isChild(packedPosition)) {
	        int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
	        int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
			long id = getExpandableListAdapter().getChildId(groupPosition, childPosition);
			toggleComplete(packedPosition, id);
		}
	}
    
    protected final void toggleComplete(long packedPosition, long id) {
        int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
    	Cursor c = (Cursor) getExpandableListAdapter().getChild(groupPosition, childPosition);
        BindingUtils.toggleTaskComplete(this, c, mConfig.getChildContentUri(), id);
    }
    

    protected Boolean isChildSelected() {
    	long packed = this.getSelectedPosition();
    	return isChild(packed);
    }
    
    protected Boolean isChild(long packedPosition) {
    	int type = ExpandableListView.getPackedPositionType(packedPosition);
    	Boolean isChild = null;
    	switch (type) {
    	case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
    		isChild = Boolean.TRUE;
    		break;
    	case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
    		isChild = Boolean.FALSE;
    	}
    	return isChild;
    }
    
    protected Uri getSelectedContentUri() {
    	Uri selectedUri = null;
    	Boolean childSelected = isChildSelected(); 
    	if (childSelected != null) {
    		selectedUri = childSelected ? mConfig.getChildContentUri() : mConfig.getGroupContentUri();
    	}
    	return selectedUri;
    }



	/**
	 * Return the intent generated when a list item is clicked.
	 * 
	 * @param url type of data selected
	 */
	protected Intent getClickIntent(Uri uri) {
		return new Intent(Intent.ACTION_EDIT, uri);
	}
	
    /**
     * Permanently delete the selected item.
     */
    protected final void deleteItem() {
    	deleteItem(getSelectedPosition());
    }
    
    protected final void deleteItem(final long packedPosition) {
    	final int type = ExpandableListView.getPackedPositionType(packedPosition);
    	final int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
    	final int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
    	switch (type) {
	    	case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
	        	Log.d(cTag, "Deleting child at position " + groupPosition + "," + childPosition);
				final long childId = getExpandableListAdapter().getChildId(groupPosition, childPosition);
		    	Log.i(cTag, "Deleting child id " + childId);
				Uri childUri = ContentUris.withAppendedId(mConfig.getChildContentUri(), childId);			
		        getContentResolver().delete(childUri, null, null);		    
		        showCancelToast(false);
		        refreshChildCount();
		        getExpandableListView().invalidate();
	    		break;
	    		
	    	case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
	        	Log.d(cTag, "Deleting parent at position " + groupPosition);
	        	// first check if there's any children...
	    		int childCount = getExpandableListAdapter().getChildrenCount(groupPosition);
	    		if (childCount > 0) {
		    		OnClickListener buttonListener = new OnClickListener() {
		    			public void onClick(DialogInterface dialog, int which) {
		    				if (which == DialogInterface.BUTTON1) {
		    					final long groupId = getExpandableListAdapter().getGroupId(groupPosition);
		    			    	Log.i(cTag, "Deleting group id " + groupId);
		    					Uri uri = ContentUris.withAppendedId(mConfig.getGroupContentUri(), groupId);			
		    			        getContentResolver().delete(uri, null, null);
		    			    	Log.i(cTag, "Deleting all child for group id " + groupId);
		    					getContentResolver().delete(mConfig.getChildContentUri(), 
		    							mConfig.getGroupIdColumnName() + " = ?", 
		    							new String[] {String.valueOf(groupId)});
		    			        showCancelToast(true);
		    				} else {
		    					Log.d(cTag, "Hit Cancel button. Do nothing.");
		    				}
		    			}
		    		};
	    			AlertUtils.showDeleteGroupWarning(this, mConfig.getGroupName(this), 
	    					mConfig.getChildName(this), childCount, buttonListener);    		
	    		} else {
			    	Log.i(cTag, "Deleting childless group at position " + groupPosition);
					final long groupId = getExpandableListAdapter().getGroupId(groupPosition);
			    	Log.i(cTag, "Deleting group id " + groupId);
					Uri groupUri = ContentUris.withAppendedId(mConfig.getGroupContentUri(), groupId);			
			        getContentResolver().delete(groupUri, null, null);
			        showCancelToast(true);
	    		}
	        	break;
    	}
    }

    private final void showCancelToast(boolean isGroup) {
    	String name = isGroup ? getListConfig().getGroupName(this)
    			: getListConfig().getChildName(this);
    	String text = getResources().getString(
    			R.string.itemDeletedToast, name );
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();        
    	
    }
    
    private final void insertItem(Uri uri, G group) {
    	Intent intent =  new Intent(Intent.ACTION_INSERT, uri);
    	Bundle extras = intent.getExtras();
    	if (extras == null) extras = new Bundle();
    	updateInsertExtras(extras, group);
    	intent.putExtras(extras);
        startActivity(intent);
    }
    
    private final void insertItem(Uri uri) {
        // Launch activity to insert a new item
    	Intent intent =  new Intent(Intent.ACTION_INSERT, uri);
        startActivity(intent);
    }

	abstract protected void updateInsertExtras(Bundle extras, G group);

	abstract protected ExpandableListConfig<G,C> createListConfig();
	
	abstract void refreshChildCount();

    abstract ExpandableListAdapter createExpandableListAdapter(Cursor cursor);
	
    /**
     * @return a cursor selecting the child items to display for a selected top level group item.
     */
    abstract Cursor createChildQuery(long groupId);
    
    /**
     * @return a cursor selecting the top levels items to display in the list.
     */
    abstract Cursor createGroupQuery();
    
    /**
     * @return index of group id column in group cursor
     */	
    abstract int getGroupIdColumnIndex();
    
    /**
     * @return index of child id column in group cursor
     */	
    abstract int getChildIdColumnIndex();
    
	// custom helper methods
	
	protected final ExpandableListConfig<G,C> getListConfig()
	{
		return mConfig;
	}
    
}
