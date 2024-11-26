package com.example.netdie4u.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.netdie4u.data.Ghost_List;
import com.example.netdie4u.data.Item_;
import com.example.netdie4u.databinding.FragmentGalleryBinding;
import com.google.android.material.snackbar.Snackbar;
public class GalleryFragment extends Fragment {
    private FragmentGalleryBinding binding;
    private GalleryViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        setupViews();
        observeViewModel();

        return binding.getRoot();
    }

    private void setupViews() {
        binding.buttonParse.setOnClickListener(v -> {
            String url = binding.editTextUrl.getText().toString();
            if (!url.isEmpty()) {
                viewModel.parseRssUrl(url);
            }
        });

        binding.buttonAdd.setOnClickListener(v -> {
            Ghost_List currentPreview = viewModel.getPreviewData().getValue();
            String url = binding.editTextUrl.getText().toString();
            if (currentPreview != null && !url.isEmpty()) {
                viewModel.saveFeed(url, currentPreview);
                binding.editTextUrl.setText("");
                binding.previewCardView.setVisibility(View.GONE);
                Snackbar.make(binding.getRoot(), "RSS源添加成功", Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.readmoreInParserpage.setOnClickListener(v -> {
            Ghost_List currentPreview = viewModel.getPreviewData().getValue();
            if (currentPreview != null && currentPreview.getFirstItem() != null) {
                String link = currentPreview.getFirstItem().getLink();
                // Handle link opening if needed
            }
        });
    }

    private void observeViewModel() {
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressIndicator.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.buttonParse.setEnabled(!isLoading);
        });

        viewModel.getPreviewData().observe(getViewLifecycleOwner(), this::updatePreview);

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
            }
        });

        viewModel.getNavigateToHome().observe(getViewLifecycleOwner(), shouldNavigate -> {
            if (shouldNavigate) {
                binding.editTextUrl.setText("");
                binding.previewCardView.setVisibility(View.GONE);
                requireActivity().onBackPressed();
                viewModel.navigationComplete();
            }
        });
    }

    private void updatePreview(Ghost_List ghostList) {
        if (ghostList != null && ghostList.getFirstItem() != null) {
            binding.previewCardView.setVisibility(View.VISIBLE);
            binding.textViewFeedTitle.setText(ghostList.getTitle_show());

            Item_ firstItem = ghostList.getFirstItem();
            binding.textViewItemTitle.setText(firstItem.getTitle());
            binding.textViewItemAuthor.setText(firstItem.getAuthor());
            binding.textViewItemPubDate.setText(firstItem.getPubDate());
            binding.textViewItemDescription.setText(firstItem.getDescription());

            String link = firstItem.getLink();
            if (link != null && !link.isEmpty()) {
                binding.textViewItemLink.setVisibility(View.VISIBLE);
                binding.textViewItemLink.setText(link);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
