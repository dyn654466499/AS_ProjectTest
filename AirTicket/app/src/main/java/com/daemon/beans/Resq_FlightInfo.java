package com.daemon.beans;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class Resq_FlightInfo implements Parcelable {
	
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
	 * 改签规定
	 */
	public String Change;
	/**
	 * 退票规定
	 */
	public String Return;
	/**
	 * 航空公司logo
	 */
	public Drawable ariLinesIcon;
	/**
	 * 是否展开的标识
	 */
	public boolean isExpanded;

	/**
	 * 政策ID
	 */
	public String RID;
	/**
	 * 供应商ID
	 */
	public String ID;
	/**
	 *  采购返点
	 */
	public String K;

	public String AirTerminal;

	public Resq_FlightInfo(Parcel in) {
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
		Change = in.readString();
		Return = in.readString();

		airPortBuildPrice = in.readString();
		isExpanded = in.readByte() != 0;

		RID = in.readString();
		ID = in.readString();
		K = in.readString();
		AirTerminal = in.readString();
	}

	public static final Creator<Resq_FlightInfo> CREATOR = new Creator<Resq_FlightInfo>() {
		@Override
		public Resq_FlightInfo createFromParcel(Parcel in) {
			return new Resq_FlightInfo(in);
		}

		@Override
		public Resq_FlightInfo[] newArray(int size) {
			return new Resq_FlightInfo[size];
		}
	};

	public Resq_FlightInfo() {

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
		dest.writeString(Change);
		dest.writeString(Return);
		dest.writeString(airPortBuildPrice);
		dest.writeByte((byte) (isExpanded ? 1 : 0));

		dest.writeString(RID);
		dest.writeString(ID);
		dest.writeString(K);
		dest.writeString(AirTerminal);
	}
}
