package com.abben.yunziyuanesr.downloadutil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.concurrent.ConcurrentHashMap;

public class DownLoadManager {

    public static final int	START          = 1;
    public static final int	PROGRESSING   = 2;
    public static final int COMPLETED      = 3;
    public static final int EXCEPTION      = 4;

    private static DownLoadManager mInstance;
    private ConcurrentHashMap<Object, DownLoadHelper> mTagToHelpers;
    private Handler mUIHandler;

    private DownLoadManager(){
        mTagToHelpers = new ConcurrentHashMap<>();
        mUIHandler = new UIHandler(mTagToHelpers);
    }

    public synchronized static DownLoadManager getInstance() {
        if(mInstance == null) {
            mInstance = new DownLoadManager();
        }
        return mInstance;
    }

    public void addHelper(final DownLoadHelper helper) {
        if(helper == null) return;

        helper.mListener = new ProgressListener() {
            @Override
            public void onStart() {
                Message message = mUIHandler.obtainMessage();
                message.obj = helper.mTag;
                message.what = START;
                message.sendToTarget();
            }

            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                Message message = mUIHandler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putLong("bytesRead", bytesRead);
                bundle.putLong("contentLength", contentLength);
                bundle.putBoolean("done", done);
                message.setData(bundle);
                message.obj = helper.mTag;
                message.what = PROGRESSING;
                message.sendToTarget();
            }

            @Override
            public void onCompleted() {
                Message message = mUIHandler.obtainMessage();
                message.obj = helper.mTag;
                message.what = COMPLETED;
                message.sendToTarget();
            }

            @Override
            public void onError(String err) {
                Message message = mUIHandler.obtainMessage();
                message.obj = helper.mTag;
                message.what = EXCEPTION;
                Bundle bundle = new Bundle();
                bundle.putString("err", err);
                message.setData(bundle);
                message.sendToTarget();
            }
        };

        mTagToHelpers.put(helper.mTag, helper);
    }

    public DownLoadHelper getHelperByTag(Object tag) {
        return tag == null? null : mTagToHelpers.get(tag);
    }

    public void cancelHelperByTag(Object tag) {
        DownLoadHelper helper = mTagToHelpers.get(tag);

        if(helper != null) {
            helper.setCancel(true);
            helper.mDownloadListeners.clear();
            mTagToHelpers.remove(tag);
        }
    }

    private static class UIHandler extends Handler {

        private ConcurrentHashMap<Object, DownLoadHelper> mTagToHelpers;

        public UIHandler(ConcurrentHashMap<Object, DownLoadHelper> tagToHelpers){
            mTagToHelpers = tagToHelpers;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String tagID = (String) msg.obj;
            DownLoadHelper helper = mTagToHelpers.get(tagID);
            if(helper == null) return;

            switch(msg.what) {
                case START: //任务开始
                    for(ProgressListener listener: helper.mDownloadListeners){
                        listener.onStart();
                    }
                    break;

                case PROGRESSING: //任务进度更新
                    Bundle data = msg.getData();
                    for(ProgressListener listener: helper.mDownloadListeners){
                        listener.update(data.getLong("bytesRead"), data.getLong("contentLength"), data.getBoolean("done"));
                    }
                    break;

                case COMPLETED:   //任务完成
                    for(ProgressListener listener: helper.mDownloadListeners){
                        listener.onCompleted();
                    }
                    helper.mDownloadListeners.clear();
                    mTagToHelpers.remove(tagID);
                    break;

                case EXCEPTION:   //任务发生异常
                    Bundle e = msg.getData();
                    for(ProgressListener listener: helper.mDownloadListeners){
                        listener.onError(e.getString("err"));
                    }
                    helper.mDownloadListeners.clear();
                    helper.setCancel(true);
                    mTagToHelpers.remove(tagID);
                    break;

                default:
                    break;
            }
        }
    }

    public void unsubscribe(){
        for (Object key : mTagToHelpers.keySet()) {
            DownLoadHelper appDownLoadHelper = mTagToHelpers.get(key);
            appDownLoadHelper.setCancel(true);
            appDownLoadHelper.mDownloadListeners.clear();
            mTagToHelpers.remove(key);
        }
    }
}
