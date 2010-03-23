package com.jeebook.appengine.gtd.client.service;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.jeebook.appengine.gtd.client.model.TaskFilter;
import com.jeebook.appengine.gtd.client.model.TaskOrdering;
import com.jeebook.appengine.gtd.client.model.TaskValue;

@RemoteServiceRelativePath("task")
public interface TaskService extends RemoteService {

    ArrayList<TaskValue> getMockTasks() throws NotLoggedInException;
    ArrayList<TaskValue> getTasks(TaskFilter filter, TaskOrdering order) throws NotLoggedInException;
    TaskValue saveTask(TaskValue taskValue) throws NotLoggedInException;
	
}
