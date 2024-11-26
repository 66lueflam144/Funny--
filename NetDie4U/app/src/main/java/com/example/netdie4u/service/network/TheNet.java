package com.example.netdie4u.service.network;

import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.service.network.interfaces.FetchCallBack;
import com.example.netdie4u.service.handler.dataHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


public class TheNet {

    private static final String TAG = "Class: TheNet";
    private OkHttpClient client;
    private XmlPullParser parser_in_net;
    private dataHandler data_handler = new dataHandler();

    public TheNet(XmlPullParser parserInNet) throws XmlPullParserException {
        this.parser_in_net = parserInNet; // 初始化 XmlPullParser 对象
        // 配置 OkHttpClient 以增加超时
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 连接超时设置为 30 秒
                .readTimeout(30, TimeUnit.SECONDS)    // 读取超时设置为 30 秒
                .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时设置为 30 秒
                .build();
    }

    public void fetchData(String xmlURL, FetchCallBack<Ghost_List> callBack) {
        Log.d(TAG, "Fetching data from: " + xmlURL);

        Request request = new Request.Builder()
                .url(xmlURL)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = null;
                    try {
                        inputStream = response.body().byteStream();
                        Log.d(TAG, "YES we are really here, TheNet onResponse....");

                        setInput(inputStream); // 设置输入流

                        // 解析 XML 并获取结果
                        Ghost_List result = data_handler.just_meet_me_at(inputStream);
                        callBack.onSuccess(result);

                    } catch (XmlPullParserException e) {
                        Log.e(TAG, "XML resolving failed: " + e.getMessage());
                        callBack.onFailure(e);
                    } finally {
                        if (inputStream != null) {
                            try {
                                inputStream.close(); // 确保 InputStream 被关闭
                            } catch (IOException e) {
                                Log.e(TAG, "Failed to close InputStream: " + e.getMessage());
                            }
                        }
                        response.close(); // 关闭 Response 对象
                    }
                } else {
                    Log.e(TAG, "Error response code: " + response.code());
                    callBack.onFailure(new IOException("Unexpected code: " + response));
                }
            }


            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Network request failed: " + e.getMessage());
                callBack.onFailure(e);
            }
        });
    }

    public void setInput(InputStream inputStream) throws XmlPullParserException {
        parser_in_net.setInput(inputStream, null);
    }
}
