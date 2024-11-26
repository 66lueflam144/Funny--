package com.example.netdie4u.basic;

import static androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior.setTag;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import com.example.netdie4u.R;

public class XFragment  extends Fragment {
    private Context currentContext;

    private final String TAG = "XFragment";
    private static final String ARG_TITLE_SHOW = "title_show";
    private static final String ARG_TITLE_OF_ARTICLE = "title_of_article";
    private static final String ARG_AUTHOR = "author";
    private static final String ARG_LINK = "link";
    private static final String ARG_PUB_DATE = "pub_date";
    private static final String ARG_DESCRIPTION = "description";

    private TextView titleShow;
    private TextView titleOfArticle;
    private TextView author;
    private TextView link;
    private TextView pubDate;
    private TextView description;
    private Button readMore;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        currentContext = context; // 将当前的 Context 缓存
    }

    @Override
    public void onDetach() {
        super.onDetach();
        currentContext = null; // 清除缓存
    }

    public Context getCurrentContext() {
        return currentContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.x, container, false);
        titleShow = view.findViewById(R.id.Title_show);
        titleOfArticle = view.findViewById(R.id.title_of_artical);
        author = view.findViewById(R.id.author);
        link = view.findViewById(R.id.link_of_a);
        pubDate = view.findViewById(R.id.pubDate);
        description = view.findViewById(R.id.description);
        readMore = view.findViewById(R.id.readmore);

        Bundle args = getArguments();
        if (args != null) {
            titleShow.setText(args.getString(ARG_TITLE_SHOW, "HH"));
            titleOfArticle.setText(args.getString(ARG_TITLE_OF_ARTICLE, ""));
            author.setText(args.getString(ARG_AUTHOR, ""));
            link.setText(args.getString(ARG_LINK, ""));
            pubDate.setText(args.getString(ARG_PUB_DATE, ""));
            description.setText(args.getString(ARG_DESCRIPTION, ""));
        }


//        readMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openLink(v);
//            }
//        });
        readMore.setOnClickListener(v -> openLink(v));



        view.setTag(TAG);
        return view;
    }

    public static XFragment newInstance(String titleShow, String titleOfArticle, String author, String link, String pubDate, String description) {
        XFragment fragment = new XFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE_SHOW, titleShow);
        args.putString(ARG_TITLE_OF_ARTICLE, titleOfArticle);
        args.putString(ARG_AUTHOR, author);
        args.putString(ARG_LINK, link);
        args.putString(ARG_PUB_DATE, pubDate);
        args.putString(ARG_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

//    public String getTitleShow() {
//        return titleShow != null ? titleShow.getText().toString() : "HH";
//    }
//
//    public String getTitleOfArticle() {
//        return titleOfArticle != null ? titleOfArticle.getText().toString() : "";
//    }
//
//    public String getAuthor() {
//        return author != null ? author.getText().toString() : "";
//    }
//
//    public String getPubDate() {
//        return pubDate != null ? pubDate.getText().toString() : "";
//    }
//
//    public String getDescription() {
//        return description != null ? description.getText().toString() : "";
//    }
//
//    public String getLink() {
//        return link != null ? link.getText().toString() : "";
//    }

    public String getTitleShow() {
        return getArguments() != null ? getArguments().getString(ARG_TITLE_SHOW, "HH") : "HH";
    }

    public String getTitleOfArticle() {
        return getArguments() != null ? getArguments().getString(ARG_TITLE_OF_ARTICLE, "") : "";
    }

    public String getAuthor() {
        return getArguments() != null ? getArguments().getString(ARG_AUTHOR, "") : "";
    }

    public String getPubDate() {
        return getArguments() != null ? getArguments().getString(ARG_PUB_DATE, "") : "";
    }

    public String getDescription() {
        return getArguments() != null ? getArguments().getString(ARG_DESCRIPTION, "") : "";
    }

    public String getLink() {
        return getArguments() != null ? getArguments().getString(ARG_LINK, "") : "";
    }



    public void openLink(View view) {
        String url = getLink();
        if (url == null || url.isEmpty()) {
            Toast.makeText(requireContext(), "链接为空", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            @ColorInt int colorPrimaryLight = ContextCompat.getColor(requireContext(), R.color.macaron_pink);
            @ColorInt int colorPrimaryDark = ContextCompat.getColor(requireContext(), R.color.macaron_yellow);

            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                    .setDefaultColorSchemeParams(new CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(colorPrimaryLight)
                            .build())
                    .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, new CustomTabColorSchemeParams.Builder()
                            .setToolbarColor(colorPrimaryDark)
                            .build())
                    .setUrlBarHidingEnabled(true)
                    .setShowTitle(true);

            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(requireContext(), Uri.parse(url));
        } catch (IllegalStateException e) {
            Toast.makeText(requireContext(), "Fragment已分离，无法打开链接", Toast.LENGTH_SHORT).show();
            e.printStackTrace(); // 打印堆栈跟踪以帮助调试
        }
}




}
