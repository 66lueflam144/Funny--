package com.example.netdie4u.service.network.interfaces;

public interface FetchCallBack<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}
