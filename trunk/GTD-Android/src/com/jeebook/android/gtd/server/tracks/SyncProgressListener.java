package com.jeebook.android.gtd.server.tracks;

import com.jeebook.android.gtd.service.Progress;

/**
 * Items that can receive updates about synchronization progress
 * 
 * @author Morten Nielsen
 */
public interface SyncProgressListener {
    void progressUpdate(Progress progress);
}
