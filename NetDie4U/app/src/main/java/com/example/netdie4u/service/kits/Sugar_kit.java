package com.example.netdie4u.service.kits;


import android.util.Log;

import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.data.Item_;
import com.example.netdie4u.data.data_itself;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sugar_kit {

    public static String who(XmlPullParser parser) throws XmlPullParserException, IOException {

        System.out.println("\n++++++++Here we go with WHO++++++++++\n");
        String result = "";

        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag(); // 跳到下一个标签，避免循环
            System.out.println("\n===========Completed WHO, result: " + result);

        }
        return result;
    }


    public static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        System.out.println("\n++++++++++++++Here we go with SKIP++++++++++++\n");
        if (parser.getEventType() != XmlPullParser.START_TAG) {
//            throw new IllegalStateException();
            System.out.println("\n!!!!!!!!!!!Current event type is not START_TAG, skipping...");
            return;
        }

        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
        System.out.println("\n!!!!!!!!!!!!!Skipped unknown tag, current depth: " + depth);
    }


    public static Item_ switch_Like_nintendo(int eventType, String tagName, Item_ currentItem, XmlPullParser parser, List<Item_> items) throws XmlPullParserException, IOException {
        switch (eventType) {
            case XmlPullParser.START_TAG:
                currentItem = handleStartTag(tagName, currentItem, parser, items);
                break;

            case XmlPullParser.END_TAG:
                handleEndTag(tagName, currentItem);
                break;
        }
        return currentItem; // 返回最新的 currentItem 对象
    }



    private static Item_ handleStartTag(String tagName, Item_ currentItem, XmlPullParser parser, List<Item_> items) throws XmlPullParserException, IOException {
        try {
            if (tagName.equalsIgnoreCase("item") || tagName.equalsIgnoreCase("entry")) {
                currentItem = new Item_();
                currentItem.setChannelItem(false);
                items.add(currentItem);
            } else if (currentItem == null && (tagName.equalsIgnoreCase("title") ||
                    tagName.equalsIgnoreCase("link") ||
                    tagName.equalsIgnoreCase("pubDate"))) {
                // 处理频道级别的标签
                currentItem = new Item_();
                currentItem.setChannelItem(true);
                items.add(currentItem);

                String text = parser.nextText();
                if (text != null && !text.trim().isEmpty()) {
                    if (tagName.equalsIgnoreCase("title")) {
                        currentItem.setTitle(text.trim());
                    } else if (tagName.equalsIgnoreCase("link")) {
                        currentItem.setLink(text.trim());
                    } else if (tagName.equalsIgnoreCase("pubDate")) {
                        currentItem.setPubDate(text.trim());
                    }
                }
            } else if (currentItem != null) {
                // 处理条目级别的标签
                String text = parser.nextText();
                if (text != null && !text.trim().isEmpty()) {
                    if (tagName.equalsIgnoreCase("title")) {
                        currentItem.setTitle(text.trim());
                    } else if (tagName.equalsIgnoreCase("link")) {
                        currentItem.setLink(text.trim());
                    } else if (tagName.equalsIgnoreCase("author") ||
                            tagName.equalsIgnoreCase("dc:creator")) {
                        currentItem.setAuthor(text.trim());
                    } else if (tagName.equalsIgnoreCase("pubDate") ||
                            tagName.equalsIgnoreCase("published")) {
                        currentItem.setPubDate(text.trim());
                    } else if (tagName.equalsIgnoreCase("description") ||
                            tagName.equalsIgnoreCase("content")) {
                        currentItem.setDescription(text.trim());
                    }
                }
            }
        } catch (XmlPullParserException | IOException e) {
            Log.e("Sugar_kit", "Error parsing tag: " + tagName, e);
        }
        return currentItem;
    }


    private static void handleEndTag(String tagName, Item_ currentItem) {
        if (tagName.equalsIgnoreCase("item") || tagName.equalsIgnoreCase("entry")) {
            currentItem = null; // 清空 currentItem 引用
        }
    }

    public static data_itself transformWithValidation(Ghost_List ghostList) {
        if (ghostList == null) {
            throw new IllegalArgumentException("Ghost_List cannot be null");
        }

        Item_ item = ghostList.getFirstItem();
        if (item == null) {
            throw new IllegalArgumentException("First item in Ghost_List cannot be null");
        }

        // 进行数据验证
        String title_show = ghostList.getTitle_show();
        String title = item.getTitle();
        String author = item.getAuthor();
        String pubDate = item.getPubDate();
        String link = item.getLink();
        String description = item.getDescription();

        // 确保必要字段不为空
        if (title_show == null) title_show = "未知来源";
        if (title == null) title = "无标题";
        if (author == null) author = "未知作者";
        if (pubDate == null) pubDate = "未知日期";
        if (link == null) link = "";
        if (description == null) description = "无描述";

        return new data_itself(
                title_show,
                title,
                author,
                pubDate,
                link,
                description
        );
    }



}
