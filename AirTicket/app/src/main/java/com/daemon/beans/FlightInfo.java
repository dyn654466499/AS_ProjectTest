package com.daemon.beans;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class FlightInfo implements Parcelable {
	
	/**
	 * 起飞日期
	 */
	public String takeOffDate;
	/**
	 * 起飞时间
	 */
	public String takeOffTime;
	/**
	 * 降落时间
	 */
	public String landingTime;
	/**
	 * 起飞机场
	 */
	public String takeOffPort;
	/**
	 * 降落机场
	 */
	public String landingPort;
	/**
	 * 舱位类型
	 */
	public String cabinType;
	/**
	 * 航空公司
	 */
	public String airLines;
	/**
	 * 价格
	 */
	public String cabinPrice;
	/**
	 * 折扣
	 */
	public String discount;
	/**
	 * 票数
	 */
	public String amount;
	/**
	 * 航空公司logo
	 */
	public Drawable ariLinesIcon;
	/**
	 * 是否展开的标识
	 */
	public boolean isExpanded;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
        dest.writeString(takeOffDate);
        dest.writeString(takeOffTime);
        dest.writeString(takeOffPort);
        dest.writeString(landingTime);
        dest.writeString(landingPort);
        dest.writeString(cabinType);
        dest.writeString(cabinPrice);
        dest.writeString(discount);
        dest.writeString(amount);
        dest.writeString(airLines);
        
	}
	
	public static final Parcelable.Creator<FlightInfo> CREATOR = new Creator<FlightInfo>() {
		
		@Override
		public FlightInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new FlightInfo[size];
		}
		
		@Override
		public FlightInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			FlightInfo info = new FlightInfo();
			info.setParcel(source);
			return info;
		}
	}; 
	
	private void setParcel(Parcel in){
		this.takeOffDate = in.readString();
		this.takeOffTime = in.readString();
		this.takeOffPort = in.readString();
		this.landingTime = in.readString();
		this.landingPort = in.readString();
		this.cabinType = in.readString();
		this.cabinPrice = in.readString();
		this.discount = in.readString();
		this.amount = in.readString();
		this.airLines = in.readString();
	}
}
