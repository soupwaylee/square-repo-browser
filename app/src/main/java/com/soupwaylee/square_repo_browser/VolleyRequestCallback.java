package com.soupwaylee.square_repo_browser;

public interface VolleyRequestCallback<T> {

    /**
     * To be called from the main thread.
     * @param result the result of the task
     */
    void updateFromDownload(T result);

    void finishDownloading();
}