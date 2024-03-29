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

import com.jeebook.android.gtd.R;
import com.jeebook.android.gtd.activity.config.AbstractTaskListConfig;
import com.jeebook.android.gtd.activity.config.ListConfig;
import com.jeebook.android.gtd.model.Task;
import com.jeebook.android.gtd.provider.Shuffle;
import com.jeebook.android.gtd.util.MenuUtils;

import android.content.ContextWrapper;
import android.net.Uri;

public class TopTasksActivity extends AbstractTaskListActivity {

	@Override
	protected ListConfig<Task> createListConfig()
	{
		return new AbstractTaskListConfig() {

			public Uri getListContentUri() {
				// Tasks with no projects or created since last clean
				return Shuffle.Tasks.cTopTasksContentURI;
			}

		    public int getCurrentViewMenuId() {
		    	return MenuUtils.TOP_TASKS_ID;
		    }
		    
		    public String createTitle(ContextWrapper context)
		    {
		    	return context.getString(R.string.title_next_tasks);
		    }
			
		};
	}

}
