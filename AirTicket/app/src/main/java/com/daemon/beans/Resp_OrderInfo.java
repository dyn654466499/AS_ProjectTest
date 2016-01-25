package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_OrderInfo implements Parcelable{
    public String total;
    public List<Rows> rows;
    public String PageCount;
    public String page;
    public String time;

    protected Resp_OrderInfo(Parcel in) {
        total = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
        PageCount = in.readString();
        page = in.readString();
        time = in.readString();
    }

    public static final Creator<Resp_OrderInfo> CREATOR = new Creator<Resp_OrderInfo>() {
        @Override
        public Resp_OrderInfo createFromParcel(Parcel in) {
            return new Resp_OrderInfo(in);
        }

        @Override
        public Resp_OrderInfo[] newArray(int size) {
            return new Resp_OrderInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total);
        dest.writeTypedList(rows);
        dest.writeString(PageCount);
        dest.writeString(page);
        dest.writeString(time);
    }


    public static class Rows implements Parcelable{
    public String X6_Product_Id;
    public String Field_OrderId;
    public String Field_OrderStatus;
    public String TonyX7_Pager_RowRank;

        protected Rows(Parcel in) {
            X6_Product_Id = in.readString();
            Field_OrderId = in.readString();
            Field_OrderStatus = in.readString();
            TonyX7_Pager_RowRank = in.readString();
        }

        public static final Creator<Rows> CREATOR = new Creator<Rows>() {
            @Override
            public Rows createFromParcel(Parcel in) {
                return new Rows(in);
            }

            @Override
            public Rows[] newArray(int size) {
                return new Rows[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(X6_Product_Id);
            dest.writeString(Field_OrderId);
            dest.writeString(Field_OrderStatus);
            dest.writeString(TonyX7_Pager_RowRank);
        }
    }

}
