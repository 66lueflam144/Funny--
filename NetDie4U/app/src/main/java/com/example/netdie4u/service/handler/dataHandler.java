package com.example.netdie4u.service.handler;

import android.util.Log;


import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.data.Item_;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class dataHandler {

    private final XmlPullParser parser_in_datahandler;
    private static final String TAG = "Class datahandler : ";
    CHandler_FEED cHandlerFeed = new CHandler_FEED();

    public dataHandler() throws XmlPullParserException {
        Log.d(TAG, "Initializing Parse_Me...");
        parser_in_datahandler = XmlPullParserFactory.newInstance().newPullParser();
        Log.d(TAG, "XmlPullParser initialized.");
    }


    public Ghost_List just_meet_me_at(InputStream inputStream) {
        try {
            byte[] data = null;
            // 读取输入流
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                data = inputStream.readAllBytes();
            } else {
                // 对于较低版本的 Android，可以手动读取输入流
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                }
                data = byteArrayOutputStream.toByteArray();
            }

            // 检查 data 是否为 null 或空
            if (data == null || data.length == 0) {
                Log.e(TAG, "Input stream is empty or null");
                return new Ghost_List(null, ""); // 返回一个空的 Ghost_List
            }

            InputStream typeCheckStream = new ByteArrayInputStream(data);
            InputStream parseStream = new ByteArrayInputStream(data);

            fetchType type = check_IS_RSS_FEED(typeCheckStream);
            String titleShow = "";
            Item_ firstItem = null;
            List<Item_> items = new ArrayList<>();

            switch (type) {
                case RSS :
//                ghostLists = handle_RSS(inputStream);
                    items = handle_RSS(parseStream);
                    break;
                case ATOM :
                    items = handle_feed(parseStream);
                    break;
                case UNKNOWN :
                    Log.w(TAG, "Unknown feed type.");
                    break;
            }

            if (!items.isEmpty()) {
                for (Item_ item : items) {
                    if (item.isChannelItem()) {
                        titleShow = item.getTitle();
                    }
                    if (!item.isChannelItem()) {
                        firstItem = item;
                        break;
                    }
                }
            }

            if (firstItem == null) {
                Log.w(TAG, "No valid Item_ found, returning empty Ghost_List.");
            }

            return new Ghost_List(firstItem, titleShow);


        } catch (IOException e) {
            Log.e(TAG, "Error reading input stream", e);
            return new Ghost_List(null, "");
        }



    }


    public enum fetchType {
        RSS,
        ATOM,
        UNKNOWN
    }

    public fetchType check_IS_RSS_FEED(InputStream inputStream) {

        try {
            parser_in_datahandler.setInput(inputStream, null);

            int eventType = parser_in_datahandler.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if  (eventType == XmlPullParser.START_TAG) {
                    String tagName = parser_in_datahandler.getName();

                    if ("rss".equals(tagName)) {
                        return fetchType.RSS;
                    }
                    else if ("feed".equals(tagName)) {
                        return fetchType.ATOM;
                    }
                }
                eventType = parser_in_datahandler.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.w(TAG, "ERROR: " + e);
        }

        return fetchType.UNKNOWN;

    }

    public List<Item_> handle_RSS(InputStream inputStream) {
        System.out.println("YES you are in RSS");
        CHandler_KIT cHandlerRSS = new CHandler_KIT();
        return cHandlerRSS.handle_RSS(inputStream);
    }

    public List<Item_> handle_feed(InputStream inputStream) {

        System.out.println("YES you are in ATOM RSS");
        CHandler_KIT cHandlerRSS = new CHandler_KIT();
        return cHandlerRSS.handle_Atom(inputStream);


    }





}
