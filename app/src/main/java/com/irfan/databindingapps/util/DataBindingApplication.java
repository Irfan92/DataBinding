package com.irfan.databindingapps.util;

import android.app.Application;

import com.irfan.databindingapps.BuildConfig;
import com.irfan.databindingapps.model.Book;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class DataBindingApplication extends Application {

    private List<Book> mBooks;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {

        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); //just add to log
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR : ", message, args);
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);
        }
    }

    public List<Book> getBooks() {
        return mBooks;
    }

    public void setBooks(List<Book> mBooks) {
        this.mBooks = mBooks;
    }
}
