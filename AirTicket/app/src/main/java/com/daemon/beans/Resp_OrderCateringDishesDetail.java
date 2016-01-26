package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_OrderCateringDishesDetail implements Parcelable{
    public String total;
    public List<Rows> rows;


    protected Resp_OrderCateringDishesDetail(Parcel in) {
        total = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
    }

    public static final Creator<Resp_OrderCateringDishesDetail> CREATOR = new Creator<Resp_OrderCateringDishesDetail>() {
        @Override
        public Resp_OrderCateringDishesDetail createFromParcel(Parcel in) {
            return new Resp_OrderCateringDishesDetail(in);
        }

        @Override
        public Resp_OrderCateringDishesDetail[] newArray(int size) {
            return new Resp_OrderCateringDishesDetail[size];
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
         *  订单ID
         */
    public String X6_Product_Id;
        /**
         *   菜品图片
         */
        public String X6_Product_Pic;
        /**
         * 用户Id
         */
    public String Field_YHID;
        /**
         * 菜品名称
         */
    public String Field_CPPM;
        /**
         * 菜品单价
         */
    public String Field_CPDJ;
        /**
         * 菜品数量
         */
        public String Field_CPSL;
        /**
         * 菜品小计
         */
    public String Field_CPJS;

        /**
         * 商家ID
         */
        public String Field_SJID;

        protected Rows(Parcel in) {
            X6_Product_Id = in.readString();
            X6_Product_Pic = in.readString();
            Field_YHID = in.readString();
            Field_CPPM = in.readString();
            Field_CPDJ = in.readString();
            Field_CPSL = in.readString();
            Field_CPJS = in.readString();
            Field_SJID = in.readString();
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(X6_Product_Id);
            dest.writeString(X6_Product_Pic);
            dest.writeString(Field_YHID);
            dest.writeString(Field_CPPM);
            dest.writeString(Field_CPDJ);
            dest.writeString(Field_CPSL);
            dest.writeString(Field_CPJS);
            dest.writeString(Field_SJID);
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
