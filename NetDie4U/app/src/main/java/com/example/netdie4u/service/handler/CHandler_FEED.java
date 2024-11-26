package com.example.netdie4u.service.handler;


import static com.example.netdie4u.service.kits.Sugar_kit.switch_Like_nintendo;

import com.example.netdie4u.data.Item_;

import com.example.netdie4u.service.kits.Sugar_kit;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CHandler_FEED {

    String title  = null;
    String link = null;
    String description = null;
    String pubDate = null;

    public CHandler_FEED() {
    }




    public List<Item_> handle_items(XmlPullParser parser_in_handle_items) {


        Item_ currentItem = null;
        List<Item_> items = new ArrayList<>();


        try {
            int eventType = parser_in_handle_items.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = parser_in_handle_items.getName();

                if (tagName != null) {
//                     items.addAll(switch_Like_nintendo(eventType, tagName, currentItem, parser_in_handle_items));
                    currentItem = switch_Like_nintendo(eventType, tagName, currentItem, parser_in_handle_items, items);
                }

                eventType = parser_in_handle_items.next();

            }
        } catch (XmlPullParserException | IOException e) {
            throw new RuntimeException("Error parsing feed items", e);
        }
        // 输出解析结果
        for (Item_ item : items) {
            if (item.isChannelItem()) {
                System.out.println("Channel/FEED Item: " + item);
            } else {
                System.out.println("Regular Item: " + item);
            }
        }

        return items;

    }

    public void bonnie() {

    }



}
