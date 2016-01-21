package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class FlightInfoContainer implements Parcelable {
    @SerializedName("Response")
    public List<FlightRespInfo> infos;

    protected FlightInfoContainer(Parcel in) {
        infos = in.createTypedArrayList(FlightRespInfo.CREATOR);
    }

    public static final Creator<FlightInfoContainer> CREATOR = new Creator<FlightInfoContainer>() {
        @Override
        public FlightInfoContainer createFromParcel(Parcel in) {
            return new FlightInfoContainer(in);
        }

        @Override
        public FlightInfoContainer[] newArray(int size) {
            return new FlightInfoContainer[size];
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
