package com.example.netdie4u.service.handler;


import android.util.Log;

import com.example.netdie4u.data.Item_;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CHandler_KIT {


    private static final String TAG = "C_Handler _ RSS";

    private static Item_ handleRSSStartTag(String tagName, Item_ currentItem, XmlPullParser parser, List<Item_> items) throws XmlPullParserException, IOException {
        try {
            if (tagName.equalsIgnoreCase("channel")) {
                // RSS特有的channel标签处理
                currentItem = new Item_();
                currentItem.setChannelItem(true);
                items.add(currentItem);
            } else if (tagName.equalsIgnoreCase("item")) {
                currentItem = new Item_();
                currentItem.setChannelItem(false);
                items.add(currentItem);
            } else if (currentItem != null) {
                handleCommonTags(tagName, currentItem, parser);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing RSS tag: " + tagName, e);
        }
        return currentItem;
    }

    private static Item_ handleAtomStartTag(String tagName, Item_ currentItem, XmlPullParser parser, List<Item_> items) throws XmlPullParserException, IOException {
        try {
            if (tagName.equalsIgnoreCase("feed")) {
                // Atom特有的feed标签处理
                currentItem = new Item_();
                currentItem.setChannelItem(true);
                items.add(currentItem);
            } else if (tagName.equalsIgnoreCase("entry")) {
                currentItem = new Item_();
                currentItem.setChannelItem(false);
                items.add(currentItem);
            } else if (currentItem != null) {
                handleCommonTags(tagName, currentItem, parser);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error parsing Atom tag: " + tagName, e);
        }
        return currentItem;
    }

    private static void handleCommonTags(String tagName, Item_ currentItem, XmlPullParser parser) throws XmlPullParserException, IOException {
        String text = parser.nextText();
        if (text != null && !text.trim().isEmpty()) {
            switch (tagName.toLowerCase()) {
                case "title":
                    currentItem.setTitle(text.trim());
                    break;
                case "id":
                    currentItem.setLink(text.trim());
                    break;
                case "link":
                    currentItem.setLink(text.trim());
                    break;
                case "author":
                case "dc:creator":
                    currentItem.setAuthor(text.trim());
                    break;
                case "pubdate":
                case "published":
                    currentItem.setPubDate(text.trim());
                    break;
                case "description":
                case "content":
                    String contentText = text.trim();
                    int maxLength = 50;
                    if (contentText.length() > maxLength) {
                        contentText = contentText.substring(0, maxLength) + "...";
                    }
                    currentItem.setDescription(contentText);
                    break;
            }
        }
    }



    public List<Item_> handle_RSS(InputStream inputStream) {
        List<Item_> items = new ArrayList<>();
        Item_ currentItem = null;

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                if (tagName != null) {
                    if (eventType == XmlPullParser.START_TAG) {
                        currentItem = handleRSSStartTag(tagName, currentItem, parser, items);
                    } else if (eventType == XmlPullParser.END_TAG) {
                        tagName = parser.getName();
                        // 处理结束标签
                        Log.d(TAG, "End tag: " + tagName);
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e(TAG, "Error parsing RSS: ", e);
        }

        return items;
    }

    public List<Item_> handle_Atom(InputStream inputStream) {
        List<Item_> items = new ArrayList<>();
        Item_ currentItem = null;

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                if (tagName != null) {
                    if (eventType == XmlPullParser.START_TAG) {
                        currentItem = handleAtomStartTag(tagName, currentItem, parser, items);
                    } else if (eventType == XmlPullParser.END_TAG) {
                        tagName = parser.getName();
                        // 处理结束标签
                        Log.d(TAG, "End tag: " + tagName);
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e(TAG, "Error parsing Atom: ", e);
        }

        return items;
    }




}
