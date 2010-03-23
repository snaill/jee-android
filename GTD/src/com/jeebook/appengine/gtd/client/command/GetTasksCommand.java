package com.jeebook.appengine.gtd.client.command;

import java.util.ArrayList;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.jeebook.appengine.gtd.client.model.TaskValue;
import com.jeebook.appengine.gtd.client.service.TaskServiceAsync;

public class GetTasksCommand implements Command {

	private TaskServiceAsync mService;
	private AsyncCallback<ArrayList<TaskValue>> mCallback;
	
	public GetTasksCommand(
			TaskServiceAsync service,
			AsyncCallback<ArrayList<TaskValue>> callback) {
		mService = service;
		mCallback = callback;
	}
	
	@Override
	public void execute() {
//		mService.getMockTasks(mCallback);
	    mService.getTasks(null, null, mCallback);
	}

	public static class Factory {
		private TaskServiceAsync service;
		
		@Inject
		public Factory(TaskServiceAsync service) {
			this.service = service;
		}
		
		public GetTasksCommand create(AsyncCallback<ArrayList<TaskValue>> callback) {
			return new GetTasksCommand(service, callback);
		}
	}
	
}
