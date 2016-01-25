package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_OrderCateringList implements Parcelable{
    public String total;
    public List<Rows> rows;
    public String PageCount;
    public String page;
    public String time;

    protected Resp_OrderCateringList(Parcel in) {
        total = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
        PageCount = in.readString();
        page = in.readString();
        time = in.readString();
    }

    public static final Creator<Resp_OrderCateringList> CREATOR = new Creator<Resp_OrderCateringList>() {
        @Override
        public Resp_OrderCateringList createFromParcel(Parcel in) {
            return new Resp_OrderCateringList(in);
        }

        @Override
        public Resp_OrderCateringList[] newArray(int size) {
            return new Resp_OrderCateringList[size];
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
         * 订单时间
         */
    public String X6_Product_Time;
        /**
         * 订单编号
         */
    public String Field_CYDDBH;
        /**
         * 订单金额
         */
    public String Field_CYDDJE;
        /**
         * 商家ID
         */
        public String Field_SJID;
        /**
         * 用户id
         */
    public String Field_YHID;
        /**
         * 商铺图片
         */
        public String X6_Product_Pic;
        /**
         * 商家名称
         */
        public String Field_DPMC;
        /**
         * 分类
         */
        public String Field_Type;
        /**
         * 忽略
         */
        public String TonyX7_Pager_RowRank;

        protected Rows(Parcel in) {
            X6_Product_Id = in.readString();
            X6_Product_Time = in.readString();
            Field_CYDDBH = in.readString();
            Field_CYDDJE = in.readString();
            Field_SJID = in.readString();
            Field_YHID = in.readString();
            X6_Product_Pic = in.readString();
            Field_DPMC = in.readString();
            Field_Type = in.readString();
            TonyX7_Pager_RowRank = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(X6_Product_Id);
            dest.writeString(X6_Product_Time);
            dest.writeString(Field_CYDDBH);
            dest.writeString(Field_CYDDJE);
            dest.writeString(Field_SJID);
            dest.writeString(Field_YHID);
            dest.writeString(X6_Product_Pic);
            dest.writeString(Field_DPMC);
            dest.writeString(Field_Type);
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
