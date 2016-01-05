package com.irfan.databindingapps.model;

/**
 * Created by Irfan Irawan S on 05-Jan-16.
 */
public class Book {

    private VolumeInfoServer volumeInfoServer;

    public VolumeInfoServer getVolumeInfoServer(){
        return volumeInfoServer;
    }

    public void setVolumeInfoServer(VolumeInfoServer volumeInfoServer){
        this.volumeInfoServer = volumeInfoServer;
    }
}
