package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_OrderLocalCityList implements Parcelable{
    public String total;
    public List<Rows> rows;
    public String PageCount;
    public String page;
    public String time;

    protected Resp_OrderLocalCityList(Parcel in) {
        total = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
        PageCount = in.readString();
        page = in.readString();
        time = in.readString();
    }

    public static final Creator<Resp_OrderLocalCityList> CREATOR = new Creator<Resp_OrderLocalCityList>() {
        @Override
        public Resp_OrderLocalCityList createFromParcel(Parcel in) {
            return new Resp_OrderLocalCityList(in);
        }

        @Override
        public Resp_OrderLocalCityList[] newArray(int size) {
            return new Resp_OrderLocalCityList[size];
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
        /**
         *  订单ID
         */
    public String X6_Product_Id;
        /**
         * 订单状态
         */
    public String X6_Product_Recommend;
        /**
         * 商铺图片
         */
        public String X6_Product_Pic;
        /**
         * 活动名称
         */
    public String Field_HDMC;
        /**
         * 活动报名费用
         */
    public String Field_HDBMFY;
        /**
         * 订单编号
         */
        public String Field_DDBH;
        /**
         * 忽略
         */
        public String TonyX7_Pager_RowRank;

        protected Rows(Parcel in) {
            X6_Product_Id = in.readString();
            X6_Product_Recommend = in.readString();
            X6_Product_Pic = in.readString();
            Field_HDMC = in.readString();
            Field_HDBMFY = in.readString();
            Field_DDBH = in.readString();
            TonyX7_Pager_RowRank = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(X6_Product_Id);
            dest.writeString(X6_Product_Recommend);
            dest.writeString(X6_Product_Pic);
            dest.writeString(Field_HDMC);
            dest.writeString(Field_HDBMFY);
            dest.writeString(Field_DDBH);
            dest.writeString(TonyX7_Pager_RowRank);
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


    }

}
