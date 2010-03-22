package com.jeebook.android.gtd.service;

public interface Locator<T> {
	
	public T findById(long id);
	public T findByName(String name);

}
