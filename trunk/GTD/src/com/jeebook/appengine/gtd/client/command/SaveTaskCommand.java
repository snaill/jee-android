package com.jeebook.appengine.gtd.client.command;


import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.jeebook.appengine.gtd.client.model.TaskValue;
import com.jeebook.appengine.gtd.client.service.TaskServiceAsync;

public class SaveTaskCommand implements Command {

    private TaskServiceAsync mService;
    private AsyncCallback<TaskValue> mCallback;
    private TaskValue mTaskValue;
    
    public SaveTaskCommand(
            TaskValue taskValue,
            TaskServiceAsync service,
            AsyncCallback<TaskValue> callback) {
        mTaskValue = taskValue;
        mService = service;
        mCallback = callback;
    }
    
    @Override
    public void execute() {
        mService.saveTask(mTaskValue, mCallback);
    }

    public static class Factory {
        private TaskServiceAsync service;
        
        @Inject
        public Factory(TaskServiceAsync service) {
            this.service = service;
        }
        
        public SaveTaskCommand create(TaskValue taskValue, AsyncCallback<TaskValue> callback) {
            return new SaveTaskCommand(taskValue, service, callback);
        }
    }
    
}
