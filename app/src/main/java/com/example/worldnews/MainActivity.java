package com.example.worldnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsDetails>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private String primaryUrlForEverythingAndSelectAllOption = "https://newsapi.org/v2/everything?";
    private String primaryUrl = "https://newsapi.org/v2/top-headlines?";
    private String apikey = "4aa98b7f7d544b508d00bdb1ba382dc5";
    private String query;
    private String country;
    private String category;
    private String requestUrl = "";
    private int LOADER_ID = 1;
    private NewsAdapter mNewsAdapter;
    private TextView emptyTextView;
    private ProgressBar progressBar;
    private boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        country = sharedPreferences.getString(getString(R.string.country_key), getString(R.string.select_all_value));
        category = sharedPreferences.getString(getString(R.string.category_key), getString(R.string.general_category_value));


        emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        ListView listView = (ListView) findViewById(R.id.list_view);
        mNewsAdapter = new NewsAdapter(this, new ArrayList<NewsDetails>());
        listView.setAdapter(mNewsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsDetails currentNews = mNewsAdapter.getItem(position);
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW);
                websiteIntent.setData(Uri.parse(currentNews.getmUrl()));
                startActivity(websiteIntent);
            }
        });

        isConnected = checkInternetConnectivity();
        if (!isConnected) {
            emptyTextView.setText("Check Your Intenet Connection!");
        } else if (isConnected) {
            progressBar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        } else {
            emptyTextView.setText("No results found!");
        }
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        if (savedInstanceState == null) {
            if (!isConnected) {
                emptyTextView.setText("Check Your Intenet Connection!");
            } else if (isConnected) {
                progressBar.setVisibility(View.VISIBLE);
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(LOADER_ID, null, this);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search_view_menu_item);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                emptyTextView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                query = s;
                if (!isConnected) {
                    emptyTextView.setText("Check Your Intenet Connection!");
                } else if (isConnected) {
                    progressBar.setVisibility(View.VISIBLE);
                    getLoaderManager().restartLoader(LOADER_ID, null, MainActivity.this);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private Boolean checkInternetConnectivity() {
        // Code to check the network connectivity status
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<List<NewsDetails>> onCreateLoader(int id, Bundle args) {


        if (country == getString(R.string.select_all_value) && query != null && !query.isEmpty()) {

            Uri uri = Uri.parse(primaryUrlForEverythingAndSelectAllOption);
            Uri.Builder builder = uri.buildUpon();
            builder.appendQueryParameter("q", query);
            builder.appendQueryParameter("language", "en");
            builder.appendQueryParameter("apiKey", apikey);

            requestUrl = builder.toString();

        }else {

            {
                Uri baseUri = Uri.parse(primaryUrl);
                Uri.Builder uriBuilder = baseUri.buildUpon();
                if (query != null && !query.isEmpty()) {
                    uriBuilder.appendQueryParameter("q", query);
                }
                if (country != getString(R.string.select_all_value)) {
                    uriBuilder.appendQueryParameter("country", country);

                }
                uriBuilder.appendQueryParameter("category", category);
                uriBuilder.appendQueryParameter("apiKey", apikey);
                requestUrl = uriBuilder.toString();
            }
        }
        return new NewsLoader(this, requestUrl);

    }

    @Override
    public void onLoadFinished(Loader<List<NewsDetails>> loader, List<NewsDetails> data) {
        progressBar.setVisibility(View.GONE);

        mNewsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsDetails>> loader) {
        mNewsAdapter.clear();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.country_key))) {
            country = sharedPreferences.getString(key, getResources().getString(R.string.select_all_value));
            progressBar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        } else if (key.equals(getString(R.string.category_key))) {
            category = sharedPreferences.getString(key, getResources().getString(R.string.general_category_value));
            progressBar.setVisibility(View.VISIBLE);
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
