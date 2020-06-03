package com.ryd.gyy.guolinstudy.Interface;

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();

    void onError();
}


