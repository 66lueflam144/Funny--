package com.example.netdie4u.ui.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.netdie4u.R;
import com.example.netdie4u.basic.XFragment;
import com.example.netdie4u.data.Feed;
import com.example.netdie4u.databinding.FragmentHomeBinding;
import com.example.netdie4u.tools.XAdapter;
import com.example.netdie4u.ui.gallery.GalleryViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private XAdapter xAdapter;
    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Initialize adapter with empty list
        // Initialize adapter with empty lists for both XFragments and Feeds
        xAdapter = new XAdapter(new ArrayList<>(), new ArrayList<>(), getChildFragmentManager());
        recyclerView.setAdapter(xAdapter);

        // Observe feeds from database
        homeViewModel.getFeeds().observe(getViewLifecycleOwner(), feeds -> {
            List<XFragment> fragments = new ArrayList<>();
            for (Feed feed : feeds) {
                fragments.add(XFragment.newInstance(
                        feed.titleShow,
                        feed.titleOfArticle,
                        feed.author,
                        feed.link,
                        feed.pubDate,
                        feed.description
                ));
            }
            xAdapter.updateList(fragments, feeds);
        });


        xAdapter.setOnItemLongClickListener(feed -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("删除确认")
                    .setMessage("确定要删除这个RSS源吗？")
                    .setPositiveButton("删除", (dialog, which) -> {
                        homeViewModel.deleteFeed(feed);
                    })
                    .setNegativeButton("取消", null)
                    .show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
