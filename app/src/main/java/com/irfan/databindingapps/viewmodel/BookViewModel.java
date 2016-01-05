package com.irfan.databindingapps.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.irfan.databindingapps.BR;
import com.irfan.databindingapps.model.VolumeInfoServer;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class BookViewModel extends BaseObservable {

    private VolumeInfoServer volumeInfoServer;
    private int index;

    @Bindable
    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
        notifyPropertyChanged(BR.index);
    }

    @Bindable
    public VolumeInfoServer getVolumeInfoServer(){
        return volumeInfoServer;
    }

    public void setVolumeInfoServer(VolumeInfoServer volumeInfoServer){
        this.volumeInfoServer = volumeInfoServer;
        notifyPropertyChanged(BR.volumeInfoServer);
    }
}
