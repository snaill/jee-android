package com.jeebook.appengine.gtd.client.service;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.jeebook.appengine.gtd.client.model.TaskFilter;
import com.jeebook.appengine.gtd.client.model.TaskOrdering;
import com.jeebook.appengine.gtd.client.model.TaskValue;

public interface TaskServiceAsync {

    void getMockTasks(AsyncCallback<ArrayList<TaskValue>> callback);
    void getTasks(TaskFilter filter, TaskOrdering order, AsyncCallback<ArrayList<TaskValue>> callback);
    void saveTask(TaskValue taskValue, AsyncCallback<TaskValue> callback);
	
}
