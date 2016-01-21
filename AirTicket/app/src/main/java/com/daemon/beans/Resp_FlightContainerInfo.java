package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class Resp_FlightContainerInfo implements Parcelable {
    @SerializedName("Response")
    public List<Resp_FlightInfo> infos;

    protected Resp_FlightContainerInfo(Parcel in) {
        infos = in.createTypedArrayList(Resp_FlightInfo.CREATOR);
    }

    public static final Creator<Resp_FlightContainerInfo> CREATOR = new Creator<Resp_FlightContainerInfo>() {
        @Override
        public Resp_FlightContainerInfo createFromParcel(Parcel in) {
            return new Resp_FlightContainerInfo(in);
        }

        @Override
        public Resp_FlightContainerInfo[] newArray(int size) {
            return new Resp_FlightContainerInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeList(infos);
    }
}
