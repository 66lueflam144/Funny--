package com.example.netdie4u.service.network;

import android.util.Log;

import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.service.network.interfaces.FetchCallBack;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SheWithNet {
    private static final String TAG = "SHE IS IN NET ";
    private TheNet theNet;
    private XmlPullParser parser;
    private Ghost_List ghostList;

    public SheWithNet(XmlPullParser parser) throws XmlPullParserException {
        this.parser = parser;
    }

    public void setTheNet(TheNet theNet) {
        this.theNet = theNet;
    }


    public void fetchData(String xmlURL, FetchCallBack<Ghost_List> callBack) {
        Log.d(TAG, "Requesting data from URL: " + xmlURL);
        theNet.fetchData(xmlURL, new FetchCallBack<Ghost_List>() {
            @Override
            public void onSuccess(Ghost_List result) {

                ghostList = result;


                System.out.println("========== 数据获取结果 ==========");
                if (result != null) {
                    System.out.println("标题: " + result.getTitle_show());
                    if (result.getFirstItem() != null) {
                        System.out.println("第一条内容: ");
                        System.out.println("- 标题: " + result.getFirstItem().getTitle());
                        System.out.println("- 链接: " + result.getFirstItem().getLink());
                        System.out.println("- 作者: " + result.getFirstItem().getAuthor());
                        System.out.println("- 发布时间: " + result.getFirstItem().getPubDate());
                        System.out.println("- 描述: " + result.getFirstItem().getDescription());
                    }
                } else {
                    System.out.println("获取到的数据为空");
                }
                System.out.println("================================");

                // 将结果返回给调用者
                callBack.onSuccess(result);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Failed to fetch data: " + e.getMessage());
                callBack.onFailure(e);
            }
        });
    }

    public Ghost_List getGhostList() {
        return ghostList; // 返回存储的 Ghost_List 对象
    }
}
