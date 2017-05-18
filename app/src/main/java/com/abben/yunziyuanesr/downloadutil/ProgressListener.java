package com.abben.yunziyuanesr.downloadutil;

public interface ProgressListener {
    void onStart();
    void update(long bytesRead, long contentLength, boolean done);
    void onCompleted();
    void onError(String err);
}
