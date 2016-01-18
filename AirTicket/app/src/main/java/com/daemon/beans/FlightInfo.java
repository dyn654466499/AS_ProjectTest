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
	 * 燃油费
	 */
	public String oilPrice;
	/**
	 * 机场建设费
	 */
	public String airPortBuildPrice;
	/**
	 * 航空公司logo
	 */
	public Drawable ariLinesIcon;
	/**
	 * 是否展开的标识
	 */
	public boolean isExpanded;

	public FlightInfo(Parcel in) {
		Sdate = in.readString();
		Stime = in.readString();
		Etime = in.readString();
		Scity = in.readString();
		Ecity = in.readString();
		cabinType = in.readString();
		AirLine = in.readString();
		FlightNo = in.readString();
		P = in.readString();
		D = in.readString();
		N = in.readString();
		planeSize = in.readString();
		FlightType = in.readString();
		oilPrice = in.readString();
		airPortBuildPrice = in.readString();
		isExpanded = in.readByte() != 0;
	}

	public static final Creator<FlightInfo> CREATOR = new Creator<FlightInfo>() {
		@Override
		public FlightInfo createFromParcel(Parcel in) {
			return new FlightInfo(in);
		}

		@Override
		public FlightInfo[] newArray(int size) {
			return new FlightInfo[size];
		}
	};

	public FlightInfo() {

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(Sdate);
		dest.writeString(Stime);
		dest.writeString(Etime);
		dest.writeString(Scity);
		dest.writeString(Ecity);
		dest.writeString(cabinType);
		dest.writeString(AirLine);
		dest.writeString(FlightNo);
		dest.writeString(P);
		dest.writeString(D);
		dest.writeString(N);
		dest.writeString(planeSize);
		dest.writeString(FlightType);
		dest.writeString(oilPrice);
		dest.writeString(airPortBuildPrice);
		dest.writeByte((byte) (isExpanded ? 1 : 0));
	}
}
