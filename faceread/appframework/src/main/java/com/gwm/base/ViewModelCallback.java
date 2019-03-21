package com.gwm.base;

public interface ViewModelCallback<T> {
    void onRequestSuccessData(T data);

    void onError();
}
