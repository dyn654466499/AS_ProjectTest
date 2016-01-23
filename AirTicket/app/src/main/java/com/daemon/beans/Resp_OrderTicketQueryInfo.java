package com.daemon.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/1/21.
 */
public class Resp_OrderTicketQueryInfo implements Parcelable{
    /**
     * 订单号
     */
    //@SerializedName("@OrderNo")
    public String OrderNo;
    /**
     * pnr
     */
   // @SerializedName("@PNR")
    public String PNR;
    /**
     *
     */
    //@SerializedName("@StayDiscount")
    public String StayDiscount;

    /**
     * 给客户的优惠点
     */
   // @SerializedName("@GrowDiscount")
    public String GrowDiscount;
    /**
     *
     */
   // @SerializedName("@PayMoney")
    public String PayMoney;
    /**
     * 经销商利润
     */
   // @SerializedName("@Profit")
    public String Profit;
    /**
     *
     */
   // @SerializedName("@Status")
    public String Status;
    /**
     *
     */
   // @SerializedName("@Date")
    public String Date;

    /**
     * 起飞机场三字码
     */
  //  @SerializedName("@Scity")
    public String Scity;
    /**
     * 降落机场三字码
     */
   // @SerializedName("@Ecity")
    public String Ecity;
    /**
     *  航班号
     */
  //  @SerializedName("@Flight")
    public String Flight;
    /**
     * 起飞时间
     */
   // @SerializedName("@Stime")
    public String Stime;
    /**
     * 降落时间
     */
   // @SerializedName("@Etime")
    public String Etime;
    /**
     * 舱位类型
     */
  //  @SerializedName("@Cabin")
    public String Cabin;
    /**
     *
     */
  //  @SerializedName("@Price")
    public String Price;
    /**
     *
     */
   // @SerializedName("@Tax")
    public String Tax;
    /**
     * 乘机人数
     */
  ///  @SerializedName("@PCount")
    public String PCount;
    /**
     * 乘客姓名 用|隔开
     */
  //  @SerializedName("@PName")
    public String PName;
    /**
     * 证件号码 用|隔开
     */
  //  @SerializedName("@CardNo")
    public String CardNo;
    /**
     * 改签说明
     */
    public String ZhuanQianStr;
    /**
     * 支付方式
     */
    public String PayWay;
    /**
     * 退废票说明
     */
    public String RInfo;

    protected Resp_OrderTicketQueryInfo(Parcel in) {
        OrderNo = in.readString();
        PNR = in.readString();
        StayDiscount = in.readString();
        GrowDiscount = in.readString();
        PayMoney = in.readString();
        Profit = in.readString();
        Status = in.readString();
        Date = in.readString();
        Scity = in.readString();
        Ecity = in.readString();
        Flight = in.readString();
        Stime = in.readString();
        Etime = in.readString();
        Cabin = in.readString();
        Price = in.readString();
        Tax = in.readString();
        PCount = in.readString();
        PName = in.readString();
        CardNo = in.readString();
        ZhuanQianStr = in.readString();
        PayWay = in.readString();
        RInfo = in.readString();
    }

    public static final Creator<Resp_OrderTicketQueryInfo> CREATOR = new Creator<Resp_OrderTicketQueryInfo>() {
        @Override
        public Resp_OrderTicketQueryInfo createFromParcel(Parcel in) {
            return new Resp_OrderTicketQueryInfo(in);
        }

        @Override
        public Resp_OrderTicketQueryInfo[] newArray(int size) {
            return new Resp_OrderTicketQueryInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OrderNo);
        dest.writeString(PNR);
        dest.writeString(StayDiscount);
        dest.writeString(GrowDiscount);
        dest.writeString(PayMoney);
        dest.writeString(Profit);
        dest.writeString(Status);
        dest.writeString(Date);
        dest.writeString(Scity);
        dest.writeString(Ecity);
        dest.writeString(Flight);
        dest.writeString(Stime);
        dest.writeString(Etime);
        dest.writeString(Cabin);
        dest.writeString(Price);
        dest.writeString(Tax);
        dest.writeString(PCount);
        dest.writeString(PName);
        dest.writeString(CardNo);
        dest.writeString(ZhuanQianStr);
        dest.writeString(PayWay);
        dest.writeString(RInfo);
    }
//    /**
//     *
//     */
//    @SerializedName("@JouneryInfo")
//    public String JouneryInfo;
//    /**
//     *  是否购买空险
//     */
//    @SerializedName("@IsBuyInsurance")
//    public String IsBuyInsurance;
}
