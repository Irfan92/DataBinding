package com.irfan.databindingapps.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.irfan.databindingapps.BR;
import com.irfan.databindingapps.R;
import com.irfan.databindingapps.model.Book;

import java.util.List;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class BookRecyclerViewAdapter extends RecyclerView.Adapter<BookRecyclerViewAdapter.BindingHolder> {
    private List<Book> mBooks;

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BindingHolder(View v) {
            super(v);
            binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }

    public BookRecyclerViewAdapter(List<Book> mBooks) {
        this.mBooks = mBooks;
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result, parent, false);
        BindingHolder holder = new BindingHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final Book book = mBooks.get(position);
        holder.getBinding().setVariable(BR.book, book);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

}
