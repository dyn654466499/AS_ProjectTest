package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_CabinInfo implements Parcelable{
    @SerializedName("@C")
    public String C;
    @SerializedName("@N")
    public String N;
    @SerializedName("@D")
    public String D;
    @SerializedName("@P")
    public String P;
    @SerializedName("@T")
    public String T;
    @SerializedName("@L")
    public String L;

    @SerializedName("@K")
    public String K;
    @SerializedName("@RID")
    public String RID;
    @SerializedName("@ID")
    public String ID;
    @SerializedName("@XF")
    public String XF;
    @SerializedName("@PI")
    public String PI;
    @SerializedName("@RT")
    public String RT;
    @SerializedName("@RM")
    public String RM;
    @SerializedName("@OfficeNum")
    public String OfficeNum;
    @SerializedName("@Change")
    public String Change;
    @SerializedName("@Return")
    public String Return;

    protected Resp_CabinInfo(Parcel in) {
        C = in.readString();
        N = in.readString();
        D = in.readString();
        P = in.readString();
        T = in.readString();
        L = in.readString();
        K = in.readString();
        RID = in.readString();
        ID = in.readString();
        XF = in.readString();
        PI = in.readString();
        RT = in.readString();
        RM = in.readString();
        OfficeNum = in.readString();
        Change = in.readString();
        Return = in.readString();
    }

    public static final Creator<Resp_CabinInfo> CREATOR = new Creator<Resp_CabinInfo>() {
        @Override
        public Resp_CabinInfo createFromParcel(Parcel in) {
            return new Resp_CabinInfo(in);
        }

        @Override
        public Resp_CabinInfo[] newArray(int size) {
            return new Resp_CabinInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(C);
        dest.writeString(N);
        dest.writeString(D);
        dest.writeString(P);
        dest.writeString(T);
        dest.writeString(L);
        dest.writeString(K);
        dest.writeString(RID);
        dest.writeString(ID);
        dest.writeString(XF);
        dest.writeString(PI);
        dest.writeString(RT);
        dest.writeString(RM);
        dest.writeString(OfficeNum);
        dest.writeString(Change);
        dest.writeString(Return);
    }
}
