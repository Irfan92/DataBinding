package com.irfan.databindingapps.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.irfan.databindingapps.R;
import com.irfan.databindingapps.databinding.ActivityBooksDetailBinding;
import com.irfan.databindingapps.util.DataBindingApplication;
import com.irfan.databindingapps.viewmodel.BookViewModel;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class BooksDetailActivity extends AppCompatActivity {

    public static final String BOOK_POSITION = "BOOK_POSITION";
    private BookViewModel mBookDetails = new BookViewModel();
    private int mBookPosition;
    private ActivityBooksDetailBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mBinding = DataBindingUtil.setContentView(BooksDetailActivity.this, R.layout.activity_books_detail);
        mBookPosition = getIntent().getIntExtra(BOOK_POSITION, 0);
        mBookDetails.setVolumeInfoServer(((DataBindingApplication) getApplication()).getBooks().get(mBookPosition).getVolumeInfoServer());
        mBookDetails.setIndex(mBookPosition);
        mBinding.setBookDetails(mBookDetails);
    }

    private void onShowNextBook(View view){
        mBookDetails.setIndex(++mBookPosition);
        mBookDetails.setVolumeInfoServer(((DataBindingApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).getVolumeInfoServer());
    }

    private void onShowPreviousBook(View view){
        mBookDetails.setIndex(--mBookPosition);
        mBookDetails.setVolumeInfoServer(((DataBindingApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).getVolumeInfoServer());
    }
}
