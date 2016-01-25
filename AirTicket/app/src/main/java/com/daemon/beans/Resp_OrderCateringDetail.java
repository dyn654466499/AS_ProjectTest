package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2016/1/17 0017.
 */
public class Resp_OrderCateringDetail implements Parcelable{
    public String total;
    public List<Rows> rows;


    protected Resp_OrderCateringDetail(Parcel in) {
        total = in.readString();
        rows = in.createTypedArrayList(Rows.CREATOR);
    }

    public static final Creator<Resp_OrderCateringDetail> CREATOR = new Creator<Resp_OrderCateringDetail>() {
        @Override
        public Resp_OrderCateringDetail createFromParcel(Parcel in) {
            return new Resp_OrderCateringDetail(in);
        }

        @Override
        public Resp_OrderCateringDetail[] newArray(int size) {
            return new Resp_OrderCateringDetail[size];
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
         *  订单状态
          1, ：待付款
         ,2, ：已付款待确认
         ,3, ：已完成
         ,4, ：已作废
         */
        public String X6_Product_Recommend;
        /**
         * 用户手机
         */
    public String Field_YHSJ;
        /**
         * 订单编号
         */
    public String Field_CYDDBH;
        /**
         * 订单总金额
         */
    public String Field_CYDDJE;
        /**
         * 订餐备注
         */
        public String Field_DWBZ;
        /**
         * 订餐人姓名
         */
    public String Field_DWXM;

        /**
         * 店铺名称
         */
        public String Field_DPMC;
        /**
         * 用餐人数
         */
        public String Field_RS;
        /**
         * 用餐时间
         */
        public String Field_DWYCSJ;

        protected Rows(Parcel in) {
            X6_Product_Id = in.readString();
            X6_Product_Recommend = in.readString();
            Field_YHSJ = in.readString();
            Field_CYDDBH = in.readString();
            Field_CYDDJE = in.readString();
            Field_DWBZ = in.readString();
            Field_DWXM = in.readString();
            Field_DPMC = in.readString();
            Field_RS = in.readString();
            Field_DWYCSJ = in.readString();
        }
        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(X6_Product_Id);
            dest.writeString(X6_Product_Recommend);
            dest.writeString(Field_YHSJ);
            dest.writeString(Field_CYDDBH);
            dest.writeString(Field_CYDDJE);
            dest.writeString(Field_DWBZ);
            dest.writeString(Field_DWXM);
            dest.writeString(Field_DPMC);
            dest.writeString(Field_RS);
            dest.writeString(Field_DWYCSJ);
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
