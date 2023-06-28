package com.mirea.kt.fragments.list;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mirea.kt.News;
import com.mirea.kt.databinding.FragmentNewsListBinding;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class NewsListFragment extends Fragment {
    private FragmentNewsListBinding binding;

    private final URL url;

    private final ArrayList<News> newsArrayList = new ArrayList<>();
    private final ArrayList<String> newsTitle = new ArrayList<>();
    private final ArrayList<String> newsLink = new ArrayList<>();
    private final ArrayList<String> newsPubDate = new ArrayList<>();
    private final ArrayList<String> newsDescription = new ArrayList<>();
    private final ArrayList<String> newsAuthor = new ArrayList<>();

    public NewsListAdapter newsListAdapter;

    public NewsListFragment(URL url) {
        this.url = url;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNewsListBinding.inflate(getLayoutInflater());

        new ProcessInBackground().execute();

        return binding.getRoot();
    }

    private void setSearch() {
        binding.newsSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query != null) newsListAdapter.getFilter().filter(query);
                return true;
            }
        });
    }

    private InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(NewsListFragment.this.requireContext());
        Exception e = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("LOADING...");
            progressDialog.show();
            System.out.println("___ LOADING...");
        }

        @Override
        protected Exception doInBackground(Integer... integers) {
            try {

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF-8");

                boolean insideItem = false;

                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) insideItem = true;
                        else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) newsTitle.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("link")) {
                            if (insideItem) newsLink.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("pubDate")) {
                            if (insideItem) newsPubDate.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) newsDescription.add(xpp.nextText());
                        } else if (xpp.getName().equalsIgnoreCase("author")) {
                            if (insideItem) newsAuthor.add(xpp.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }

                    eventType = xpp.next();
                }

            } catch (IOException | XmlPullParserException e) {
                this.e = e;
            }

            return e;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(Exception e) {
            super.onPostExecute(e);

            for (int i = 0; i < newsTitle.size(); i++) {
                newsArrayList.add(new News(newsPubDate.get(i), newsTitle.get(i), newsDescription.get(i), newsAuthor.get(i), newsLink.get(i)));
            }

            newsListAdapter = new NewsListAdapter(newsArrayList);
            binding.newsList.setAdapter(newsListAdapter);
            newsListAdapter.notifyDataSetChanged();
            setSearch();
            binding.newsList.setLayoutManager(new LinearLayoutManager(getContext()));

            progressDialog.dismiss();
            System.out.println("___ DISMISSED");
        }
    }
}