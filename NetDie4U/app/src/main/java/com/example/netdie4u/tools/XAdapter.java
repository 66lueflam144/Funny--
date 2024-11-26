package com.example.netdie4u.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netdie4u.R;
import com.example.netdie4u.basic.XFragment;
import com.example.netdie4u.data.Feed;

import java.util.ArrayList;
import java.util.List;
public class XAdapter extends RecyclerView.Adapter<XAdapter.ViewHolder> {
    private List<XFragment> x_list;
    private List<Feed> feeds;
    private FragmentManager fragmentManager;
    private OnItemLongClickListener longClickListener;

    public XAdapter(List<XFragment> x_list, List<Feed> feeds, FragmentManager fragmentManager) {
        this.x_list = x_list;
        this.feeds = feeds;
        this.fragmentManager = fragmentManager;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        private FrameLayout fragmentContainer;
        private TextView Title_show;
        private TextView title;
        private TextView author;
        private TextView pubDate;
        private TextView link;
        private TextView description;
        private Button readmore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fragmentContainer = itemView.findViewById(R.id.recycler_view);
            Title_show = itemView.findViewById(R.id.Title_show);
            title = itemView.findViewById(R.id.title_of_artical);
            author = itemView.findViewById(R.id.author);
            pubDate = itemView.findViewById(R.id.pubDate);
            link = itemView.findViewById(R.id.link_of_a);
            description = itemView.findViewById(R.id.description);
            readmore = itemView.findViewById(R.id.readmore);
            fragmentContainer = itemView.findViewById(R.id.cardViewContainer);
        }

        public TextView getTitle_show() { return Title_show; }
        public TextView getTitle() { return title; }
        public TextView getAuthor() { return author; }
        public TextView getPubDate() { return pubDate; }
        public TextView getLink() { return link; }
        public TextView getDescription() { return description; }
        public Button getReadmore() { return readmore; }
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(Feed feed);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    public XAdapter(List<XFragment> x_list, FragmentManager fragmentManager) {
        this.x_list = x_list;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.x, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        XFragment xFragment = x_list.get(position);
        Feed feed = feeds.get(position);

        holder.getTitle_show().setText(xFragment.getTitleShow());
        holder.getTitle().setText(xFragment.getTitleOfArticle());
        holder.getAuthor().setText(xFragment.getAuthor());
        holder.getPubDate().setText(xFragment.getPubDate());
        holder.getLink().setText(xFragment.getLink());
        holder.getDescription().setText(xFragment.getDescription());

        holder.getReadmore().setOnClickListener(view -> {
            Context context = view.getContext();
            String url = xFragment.getLink();
            if (url != null && !url.isEmpty()) {
                try {
                    @ColorInt int colorPrimaryLight = ContextCompat.getColor(context, R.color.macaron_pink);
                    @ColorInt int colorPrimaryDark = ContextCompat.getColor(context, R.color.macaron_yellow);

                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder()
                            .setDefaultColorSchemeParams(new CustomTabColorSchemeParams.Builder()
                                    .setToolbarColor(colorPrimaryLight)
                                    .build())
                            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK,
                                    new CustomTabColorSchemeParams.Builder()
                                            .setToolbarColor(colorPrimaryDark)
                                            .build())
                            .setUrlBarHidingEnabled(true)
                            .setShowTitle(true);

                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(url));
                } catch (Exception e) {
                    Toast.makeText(context, "无法打开链接", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "链接为空", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null && position < feeds.size()) {
                longClickListener.onItemLongClick(feed);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return x_list.size();
    }

    public void updateList(List<XFragment> newList, List<Feed> newFeeds) {
        this.x_list = newList != null ? new ArrayList<>(newList) : new ArrayList<>();
        this.feeds = newFeeds != null ? new ArrayList<>(newFeeds) : new ArrayList<>();
        notifyDataSetChanged();
    }
}
