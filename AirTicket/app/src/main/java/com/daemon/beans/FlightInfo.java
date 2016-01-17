package com.daemon.beans;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class FlightInfo implements Parcelable {
	
	/**
	 * 起飞日期
	 */
	public String Sdate;
	/**
	 * 起飞时间
	 */
	public String Stime;
	/**
	 * 降落时间
	 */
	public String Etime;
	/**
	 * 起飞机场三字码
	 */
	public String Scity;
	/**
	 * 降落机场三字码
	 */
	public String Ecity;
	/**
	 * 舱位类型
	 */
	public String cabinType;
	/**
	 * 航空公司
	 */
	public String AirLine;
	/**
	 * 航班号
	 */
	public String FlightNo;
	/**
	 * 价格
	 */
	public String P;
	/**
	 * 折扣
	 */
	public String D;
	/**
	 * 票数
	 */
	public String N;
	/**
	 * 飞机尺寸（大、中、小）
	 */
	public String planeSize;
	/**
	 * 飞机型号
	 */
	public String FlightType;
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
        dest.writeString(Sdate);
        dest.writeString(Stime);
        dest.writeString(Scity);
        dest.writeString(Etime);
        dest.writeString(Ecity);
        dest.writeString(cabinType);
        dest.writeString(P);
        dest.writeString(D);
        dest.writeString(N);
        dest.writeString(AirLine);
		dest.writeString(FlightNo);
		dest.writeString(planeSize);
		dest.writeString(FlightType);
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
		this.Sdate = in.readString();
		this.Stime = in.readString();
		this.Scity = in.readString();
		this.Etime = in.readString();
		this.Ecity = in.readString();
		this.cabinType = in.readString();
		this.P = in.readString();
		this.D = in.readString();
		this.N = in.readString();
		this.AirLine = in.readString();
		this.FlightNo = in.readString();
		this.planeSize = in.readString();
		this.FlightType = in.readString();
	}
}
