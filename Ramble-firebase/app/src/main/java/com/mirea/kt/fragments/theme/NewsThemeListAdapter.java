package com.mirea.kt.fragments.theme;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mirea.kt.fragments.list.NewsListFragment;

import java.util.List;

public class NewsThemeListAdapter extends FragmentStateAdapter {
    private final List<NewsListFragment> newsFragmentList;

    public NewsThemeListAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<NewsListFragment> newsFragmentList) {
        super(fragmentManager, lifecycle);
        this.newsFragmentList = newsFragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return newsFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return newsFragmentList.size();
    }
}

