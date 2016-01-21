package com.daemon.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/1/21.
 */
public class Resp_OrderTicketInfo {
    /**
     * 订单号
     */
    @SerializedName("@OrderNo")
    public String OrderNo;
    /**
     * pnr
     */
    @SerializedName("@PNR")
    public String PNR;
    /**
     *
     */
    @SerializedName("@StayDiscount")
    public String StayDiscount;

    /**
     * 给客户的优惠点
     */
    @SerializedName("@GrowDiscount")
    public String GrowDiscount;
    /**
     *
     */
    @SerializedName("@PayMoney")
    public String PayMoney;
    /**
     * 经销商利润
     */
    @SerializedName("@Profit")
    public String Profit;
    /**
     *
     */
    @SerializedName("@Status")
    public String Status;
    /**
     *
     */
    @SerializedName("@Date")
    public String Date;

    /**
     * 起飞机场三字码
     */
    @SerializedName("@Scity")
    public String Scity;
    /**
     * 降落机场三字码
     */
    @SerializedName("@Ecity")
    public String Ecity;
    /**
     * 起飞时间
     */
    @SerializedName("@Flight")
    public String Flight;
    /**
     * 起飞时间
     */
    @SerializedName("@Stime")
    public String Stime;
    /**
     * 降落时间
     */
    @SerializedName("@Etime")
    public String Etime;
    /**
     * 舱位类型
     */
    @SerializedName("@Cabin")
    public String Cabin;
    /**
     *
     */
    @SerializedName("@Price")
    public String Price;
    /**
     *
     */
    @SerializedName("@Tax")
    public String Tax;
    /**
     * 乘机人数
     */
    @SerializedName("@PCount")
    public String PCount;
    /**
     * 乘客姓名 用|隔开
     */
    @SerializedName("@PName")
    public String PName;
    /**
     * 证件号码
     */
    @SerializedName("@PCard")
    public String PCard;
    /**
     *
     */
    @SerializedName("@JouneryInfo")
    public String JouneryInfo;
    /**
     *  是否购买空险
     */
    @SerializedName("@IsBuyInsurance")
    public String IsBuyInsurance;
}
