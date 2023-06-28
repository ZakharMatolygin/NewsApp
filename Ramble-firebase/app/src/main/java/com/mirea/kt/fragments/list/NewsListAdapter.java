package com.mirea.kt.fragments.list;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.mirea.kt.News;
import com.mirea.kt.databinding.CardNewsBinding;
import com.mirea.kt.fragments.theme.NewsThemeListFragmentDirections;

import java.util.ArrayList;
import java.util.Collection;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> implements Filterable {
    private final ArrayList<News> newsArrayList;
    private final ArrayList<News> newsArrayListFull;

    public NewsListAdapter(ArrayList<News> newsArrayList) {
        this.newsArrayListFull = newsArrayList;
        this.newsArrayList = new ArrayList<>(newsArrayListFull);
    }

    public static class NewsListViewHolder extends RecyclerView.ViewHolder {
        CardNewsBinding binding;

        public NewsListViewHolder(@NonNull CardNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(News news) {
            binding.newsPubDate.setText(news.newsPubDate);
            binding.newsTitle.setText(news.newsTitle);
            binding.newsDescription.setText(news.newsDescription);
            binding.newsAuthor.setText(news.newsAuthor);
            binding.newsCard.setOnClickListener(v -> {
                NavDirections action = NewsThemeListFragmentDirections.actionNewsThemeListFragmentToNewsFragment(news);
                Navigation.findNavController(v).navigate(action);
            });
        }
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsListViewHolder(CardNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder holder, int position) {
        holder.bind(newsArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence query) {
                ArrayList<News> filteredNewsList = new ArrayList<>();

                if (query == null || query.length() == 0)
                    filteredNewsList.addAll(newsArrayListFull);
                else {
                    for (News news : newsArrayListFull) {
                        if (news.newsTitle.toLowerCase().contains(query.toString().toLowerCase().trim()))
                            filteredNewsList.add(news);
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredNewsList;

                return results;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence query, FilterResults results) {
                newsArrayList.clear();
                newsArrayList.addAll((Collection<? extends News>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
