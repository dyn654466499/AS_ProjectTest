package com.daemon.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daemon.airticket.R;
import com.daemon.beans.Req_FlightInfo;
import com.daemon.interfaces.Commands;
import com.daemon.utils.ImageUtil;
import com.daemon.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class FlightResultAdapter extends BaseExpandableListAdapter {
	private Context mContext;
	private ExpandableListView elv;
	private Drawable ic_xiangshang;
	private List<Req_FlightInfo> resqFlightInfos_group;
	private List<List<Req_FlightInfo>> flightInfos_child;
	/**
	 * 命令接口
	 */
	private Commands commands;
	private SharedPreferences sp_cabin,sp_airLine,sp_airPort;

	public FlightResultAdapter(Context mContext, List<Req_FlightInfo> resqFlightInfos_group,
							   List<List<Req_FlightInfo>> flightInfos_child) {
		super();
		this.mContext = mContext;
		this.resqFlightInfos_group = resqFlightInfos_group;
		this.flightInfos_child = flightInfos_child;
		Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
				R.drawable.ic_xiangxia);
		bitmap = ImageUtil.rotateBitmap(bitmap, 180);
		ic_xiangshang = new BitmapDrawable(mContext.getResources(), bitmap);

		sp_cabin = SPUtil.getCabin(mContext);
		sp_airLine = SPUtil.getAirLine(mContext);
		sp_airPort = SPUtil.getAirPort(mContext);
	}

	public void setTicketBookCommands(Commands commands) {
		this.commands = commands;
	}

	public void setExpandableListView(ExpandableListView params_elv) {
		this.elv = params_elv;
	}

	@Override
	public Req_FlightInfo getGroup(int groupPosition) {
		return resqFlightInfos_group.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return resqFlightInfos_group.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}


	@Override
	public View getGroupView(final int groupPosition,final boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewHolderGroup group = null;
		if (convertView == null) {
			group = new ViewHolderGroup();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_flight_result, parent, false);
			group.btn_flight_result_unfold = (Button) convertView
					.findViewById(R.id.btn_flight_result_unfold);
			
			group.tv_flight_result_takeOffTime = (TextView) convertView
					.findViewById(R.id.tv_flight_result_takeOffTime);
			group.tv_flight_result_landingTime = (TextView) convertView
					.findViewById(R.id.tv_flight_result_landingTime);
			group.tv_flight_result_cabinPrice = (TextView) convertView
					.findViewById(R.id.tv_flight_result_cabinPrice);
			group.tv_flight_result_takeOffPort = (TextView) convertView
					.findViewById(R.id.tv_flight_result_takeOffPort);
			group.tv_flight_result_landingPort = (TextView) convertView
					.findViewById(R.id.tv_flight_result_landingPort);
			group.tv_flight_result_discount = (TextView) convertView
					.findViewById(R.id.tv_flight_result_discount);
			group.tv_flight_result_airLines = (TextView) convertView
					.findViewById(R.id.tv_flight_result_airLines);
			group.tv_flight_result_amount = (TextView) convertView
					.findViewById(R.id.tv_flight_result_amount);
			group.tv_flight_result_flightNum = (TextView) convertView
					.findViewById(R.id.tv_flight_result_flightNum);
			group.tv_flight_result_planeModel = (TextView) convertView
					.findViewById(R.id.tv_flight_result_planeModel);
			group.tv_flight_result_planeSize = (TextView) convertView
					.findViewById(R.id.tv_flight_result_planeSize);

			group.imageView_flight_result_airLines = (ImageView) convertView
					.findViewById(R.id.imageView_flight_result_airLines);
			Glide.with(mContext)
					.load("http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg")
					.into(group.imageView_flight_result_airLines);
			convertView.setTag(group);
		} else {
			group = (ViewHolderGroup) convertView.getTag();
		}
		
		
		if(!resqFlightInfos_group.get(groupPosition).isExpanded){
			group.btn_flight_result_unfold
			.setCompoundDrawablesWithIntrinsicBounds(
					null,
					null,
					mContext.getResources().getDrawable(R.drawable.ic_xiangxia),
					null);
			
		}else{
			group.btn_flight_result_unfold
			.setCompoundDrawablesWithIntrinsicBounds(
					null,
					null,
					ic_xiangshang,
					null);
			
		}
		 group.tv_flight_result_takeOffTime.setText(resqFlightInfos_group.get(groupPosition).Stime);
		 group.tv_flight_result_landingTime.setText(resqFlightInfos_group.get(groupPosition).Etime);
		 group.tv_flight_result_cabinPrice.setText("￥" + resqFlightInfos_group.get(groupPosition).P);
		 group.tv_flight_result_takeOffPort.setText(resqFlightInfos_group.get(groupPosition).Scity);
		 group.tv_flight_result_landingPort.setText(resqFlightInfos_group.get(groupPosition).Ecity);
		 group.tv_flight_result_discount.setText(resqFlightInfos_group.get(groupPosition).D);
		 group.tv_flight_result_airLines.setText(resqFlightInfos_group.get(groupPosition).AirLine);
		 group.tv_flight_result_amount.setText(resqFlightInfos_group.get(groupPosition).N);
		 group.tv_flight_result_flightNum.setText(resqFlightInfos_group.get(groupPosition).FlightNo);
		 group.tv_flight_result_planeModel.setText(resqFlightInfos_group.get(groupPosition).FlightType);
		 group.tv_flight_result_planeSize.setText(resqFlightInfos_group.get(groupPosition).planeSize);


		 group.btn_flight_result_unfold.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View v) {
				 // TODO Auto-generated method stub
				 //Log.e("groupPosition", "groupPosition="+groupPosition);
				 if (resqFlightInfos_group.get(groupPosition).isExpanded) {
					 elv.collapseGroup(groupPosition);
					 resqFlightInfos_group.get(groupPosition).isExpanded = false;
				 } else {
					 elv.expandGroup(groupPosition);
					 resqFlightInfos_group.get(groupPosition).isExpanded = true;
				 }
				 notifyDataSetChanged();
			 }
		 });
		return convertView;
	}

	@Override
	public Req_FlightInfo getChild(int groupPosition, int childPosition) {
		return flightInfos_child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return flightInfos_child.get(groupPosition).size();
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderChild child = null;
		if (convertView == null) {
			child = new ViewHolderChild();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_flight_result_detail, parent, false);
			child.btn_ticket_result_details_book = (Button) convertView
					.findViewById(R.id.btn_ticket_result_details_book);
			child.tv_flight_result_details_cabinType = (TextView) convertView
					.findViewById(R.id.tv_flight_result_details_cabinType);
			child.tv_flight_result_details_discount = (TextView) convertView
					.findViewById(R.id.tv_flight_result_details_discount);
			child.tv_flight_result_details_cabinPrice = (TextView) convertView
					.findViewById(R.id.tv_flight_result_details_cabinPrice);

			convertView.setTag(child);
		} else {
			child = (ViewHolderChild) convertView.getTag();
		}
		
		child.btn_ticket_result_details_book
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Log.e("btn_ticket_result_book", "groupPosition=" + groupPosition + ",childPosition=" + childPosition);
						if (commands != null) {
							Message msg = new Message();
							ArrayList<Req_FlightInfo> resqFlightInfos = new ArrayList<Req_FlightInfo>();
							resqFlightInfos.add(flightInfos_child.get(groupPosition).get(childPosition));
							msg.obj = resqFlightInfos;
							commands.executeCommand(msg);
						}

					}
				});
		 child.tv_flight_result_details_cabinType.setText(sp_cabin.getString(flightInfos_child.get(groupPosition).get(childPosition).cabinType, ""));
		 child.tv_flight_result_details_discount.setText(flightInfos_child.get(groupPosition).get(childPosition).D);
		 child.tv_flight_result_details_cabinPrice.setText("￥" +flightInfos_child.get(groupPosition).get(childPosition).P);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	static class ViewHolderGroup {
		Button btn_flight_result_unfold;
		TextView tv_flight_result_takeOffTime, tv_flight_result_landingTime,
				tv_flight_result_cabinPrice, tv_flight_result_takeOffPort,
				tv_flight_result_landingPort, tv_flight_result_discount,
				tv_flight_result_airLines, tv_flight_result_amount,
				tv_flight_result_flightNum,tv_flight_result_planeModel,
				tv_flight_result_planeSize;
		ImageView imageView_flight_result_airLines;
		int position = -1;
		public void setPosition(int position){
			this.position = position;
		}
	}

	static class ViewHolderChild {
		Button btn_ticket_result_details_book;
		TextView tv_flight_result_details_cabinType,
				tv_flight_result_details_discount,
				tv_flight_result_details_cabinPrice;
	}
	

}
