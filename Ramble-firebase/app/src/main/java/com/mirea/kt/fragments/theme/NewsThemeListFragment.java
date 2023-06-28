package com.mirea.kt.fragments.theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mirea.kt.databinding.FragmentNewsThemeListBinding;
import com.mirea.kt.fragments.list.NewsListFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class NewsThemeListFragment extends Fragment {
    private FragmentNewsThemeListBinding binding;

    private String[] newsTabThemes;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsThemeListBinding.inflate(inflater);

        setViewPager();

        return binding.getRoot();
    }

    private void setViewPager() {
        newsTabThemes = new String[] {"Moscow", "Politics", "Community", "Incidents"};

        List<NewsListFragment> newsListFragments;

        try {
            newsListFragments = Arrays.asList(
                    new NewsListFragment(new URL("https://news.rambler.ru/rss/Moscow/")),
                    new NewsListFragment(new URL("https://news.rambler.ru/rss/politics/")),
                    new NewsListFragment(new URL("https://news.rambler.ru/rss/community/")),
                    new NewsListFragment(new URL("https://news.rambler.ru/rss/incidents/")));

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        NewsThemeListAdapter newsThemeListAdapter = new NewsThemeListAdapter(getChildFragmentManager(), getLifecycle(), newsListFragments);
        binding.newsThemeList.setAdapter(newsThemeListAdapter);

        new TabLayoutMediator(binding.newsThemeTab, binding.newsThemeList, (tab, position) -> tab.setText(newsTabThemes[position])).attach();
    }
}

