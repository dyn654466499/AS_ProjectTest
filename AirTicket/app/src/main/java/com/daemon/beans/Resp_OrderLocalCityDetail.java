package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_OrderLocalCityDetail implements Parcelable{
    public String total;
    public List<Rows> rows;


    protected Resp_OrderLocalCityDetail(Parcel in) {
        total = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
    }

    public static final Creator<Resp_OrderLocalCityDetail> CREATOR = new Creator<Resp_OrderLocalCityDetail>() {
        @Override
        public Resp_OrderLocalCityDetail createFromParcel(Parcel in) {
            return new Resp_OrderLocalCityDetail(in);
        }

        @Override
        public Resp_OrderLocalCityDetail[] newArray(int size) {
            return new Resp_OrderLocalCityDetail[size];
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

    }


    public static class Rows implements Parcelable{
        /**
         *  活动ID
         */
    public String Field_HDID;
        /**
         *  订单状态
          1, ：待付款
         ,2, ：已付款待确认
         ,3, ：已完成
         ,4, ：已作废
         */
        public String X6_Product_Recommend;
        /**
         * 下单时间
         */
    public String Field_DDSJ;
        /**
         * 订单编号
         */
    public String Field_DDBH;
        /**
         * 活动报名费用
         */
    public String Field_HDBMFY;
        /**
         * 活动报名人数
         */
        public String Field_HDBCBMRS;
        /**
         * 活动名称
         */
    public String Field_HDMC;

        protected Rows(Parcel in) {
            Field_HDID = in.readString();
            X6_Product_Recommend = in.readString();
            Field_DDSJ = in.readString();
            Field_DDBH = in.readString();
            Field_HDBMFY = in.readString();
            Field_HDBCBMRS = in.readString();
            Field_HDMC = in.readString();
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(Field_HDID);
            dest.writeString(X6_Product_Recommend);
            dest.writeString(Field_DDSJ);
            dest.writeString(Field_DDBH);
            dest.writeString(Field_HDBMFY);
            dest.writeString(Field_HDBCBMRS);
            dest.writeString(Field_HDMC);
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
