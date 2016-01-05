package com.irfan.databindingapps.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.irfan.databindingapps.R;
import com.irfan.databindingapps.apiservice.GoogleBooksService;
import com.irfan.databindingapps.databinding.ActivityMainBinding;
import com.irfan.databindingapps.model.SearchResult;
import com.irfan.databindingapps.util.DataBindingApplication;
import com.irfan.databindingapps.util.RecyclerViewItemClickListener;
import com.irfan.databindingapps.view.adapter.BookRecyclerViewAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.search_edit_text)
    protected EditText mSeaEditText;

    @InjectView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    @InjectView(R.id.search_results_recycler_view)
    protected RecyclerView mRecylerRecyclerView;

    private GoogleBooksService mGoogleBooksService;
    private BookRecyclerViewAdapter mBookRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        ButterKnife.inject(MainActivity.this);
        mRecylerRecyclerView = (RecyclerView) findViewById(R.id.search_results_recycler_view);
        mRecylerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecylerRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(MainActivity.this, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, float x, float y) {
                Intent intent = new Intent(MainActivity.this, BooksDetailActivity.class);
                intent.putExtra(BooksDetailActivity.BOOK_POSITION, position);
                startActivity(intent);
            }
        }));
        initRequest();
    }

    @OnClick(R.id.button_search)
    public void onSearchButtonClicked() {
        handleSearchRequest();
    }

    @OnEditorAction(R.id.search_edit_text)
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            handleSearchRequest();
            return true;
        }
        return false;
    }

    private void initRequest() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        mGoogleBooksService = restAdapter.create(GoogleBooksService.class);
    }

    private void handleSearchRequest() {
        String query = mSeaEditText.getText().toString().trim();
        if (TextUtils.isEmpty(query)) {
            mSeaEditText.setError(getString(R.string.required));
        } else {
            searchBooksByQuery(query);
        }
    }

    private void searchBooksByQuery(String query) {
        hideKeyboard();
        displayProgress(true);
        mGoogleBooksService.search(query, mSearchResultCallback);
    }

    private Callback<SearchResult> mSearchResultCallback = new Callback<SearchResult>() {
        @Override
        public void success(SearchResult searchResult, Response response) {
            displayResult(searchResult);
        }

        @Override
        public void failure(RetrofitError error) {
            displayError(error);
        }
    };

    private void displayError(RetrofitError error){
        Timber.e("Search failed with error: " + error.getKind());

        displayProgress(false);

        if (((DataBindingApplication) getApplication()).getBooks() != null) {
            ((DataBindingApplication) getApplication()).getBooks().clear();
            if (mBookRecyclerViewAdapter != null) {
                mBookRecyclerViewAdapter.notifyDataSetChanged();
            }
        }
        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            Toast.makeText(this, getString(R.string.msg_error_network), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_error_generic), Toast.LENGTH_LONG).show();
        }
    }

    private void displayResult(SearchResult searchResult) {
        Timber.d("Total search result : ", searchResult.mTotalItems);
        Timber.d("Got results : ", searchResult.books.size());

        displayProgress(false);

        if (mBookRecyclerViewAdapter == null) {
            mBookRecyclerViewAdapter = new BookRecyclerViewAdapter(searchResult.books);
            mRecylerRecyclerView.setAdapter(mBookRecyclerViewAdapter);
            //Without Binder Adapter
            //mSearchResultsRecyclerView.setAdapter(new BooksRecyclerWithoutBinderAdapter(searchResults.books));
            ((DataBindingApplication) getApplication()).setBooks(searchResult.books);
        } else {
            ((DataBindingApplication) getApplication()).getBooks().clear();
            ((DataBindingApplication) getApplication()).getBooks().addAll(searchResult.books);
            mBookRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void displayProgress(boolean show) {
        if (show) {
            mRecylerRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mRecylerRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
