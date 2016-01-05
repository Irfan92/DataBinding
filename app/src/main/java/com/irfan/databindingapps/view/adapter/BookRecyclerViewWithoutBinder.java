package com.irfan.databindingapps.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irfan.databindingapps.R;
import com.irfan.databindingapps.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class BookRecyclerViewWithoutBinder extends RecyclerView.Adapter<BookRecyclerViewWithoutBinder.ViewHolder> {

    private List<Book> mBooks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView publisher;
        private ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.book_title_textview);
            author = (TextView) v.findViewById(R.id.book_authors_textview);
            publisher = (TextView) v.findViewById(R.id.book_publisher_textview);
            thumbnail = (ImageView) v.findViewById(R.id.book_thumbnail_imageview);
        }
    }

    public BookRecyclerViewWithoutBinder(List<Book> mBooks){
        this.mBooks = mBooks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result_without_binder, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Book book = mBooks.get(position);
        holder.author.setText(book.getVolumeInfoServer().authors.get(0));
        holder.title.setText(book.getVolumeInfoServer().title);
        holder.publisher.setText(book.getVolumeInfoServer().publisher);
        Picasso.with(holder.thumbnail.getContext())
                .load(book.getVolumeInfoServer().mImageLinks.mThumbnail)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}
