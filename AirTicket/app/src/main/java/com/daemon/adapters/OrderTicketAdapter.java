package com.daemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.activities.EndorseActivity;
import com.daemon.airticket.R;
import com.daemon.beans.Req_FlightInfo;
import com.daemon.utils.SPUtil;

import java.util.List;

import static  com.daemon.consts.Constants.*;

public class OrderTicketAdapter extends BaseAdapter {
	private Context mContext;
	private List<Req_FlightInfo> infos;
	private SharedPreferences sp_cabin,sp_airLine,sp_airPort;
	public OrderTicketAdapter(Context mContext, List<Req_FlightInfo> infos) {
		super();
		this.mContext = mContext;
		this.infos = infos;
		sp_cabin = SPUtil.getCabin(mContext);
		sp_airLine = SPUtil.getAirLine(mContext);
		sp_airPort = SPUtil.getAirPort(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Req_FlightInfo getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_ticket_info, parent, false);
			
			holder.btn_order_endorse = (Button) convertView
					.findViewById(R.id.btn_order_endorse);
			
			holder.tv_order_ticket_takeOffDate = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_takeOffDate);
			
			holder.tv_order_ticket_takeOffTime = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_takeOffTime);
			
			holder.tv_order_ticket_landingTime = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_landingTime);
			
			holder.tv_order_ticket_takeOffPort = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_takeOffPort);
			
			holder.tv_order_ticket_landingPort = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_landingPort);
			
			holder.tv_order_ticket_spacePrice = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_spacePrice);
			
			holder.tv_order_ticket_airPortBuildPrice = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_airPortBuildPrice);
			
			holder.tv_order_ticket_oilPrice = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_oilPrice);
			
			holder.tv_order_ticket_airLine = (TextView) convertView
					.findViewById(R.id.tv_order_ticket_airLine);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.btn_order_endorse.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, EndorseActivity.class);
				intent.putExtra(KEY_CHANGE, infos.get(position).Change);
				intent.putExtra(KEY_RETURN, infos.get(position).Return);
				mContext.startActivity(intent);
			}
		});
		 String[] airTerminal = infos.get(position).AirTerminal.split(",");

		 holder.tv_order_ticket_takeOffDate.setText(infos.get(position).Sdate);
		 holder.tv_order_ticket_takeOffTime.setText(infos.get(position).Stime);
		 holder.tv_order_ticket_landingTime.setText(infos.get(position).Etime);
		 holder.tv_order_ticket_spacePrice.setText(sp_cabin.getString(infos.get(position).cabinType,"")+ "￥" +infos.get(position).P);
		 holder.tv_order_ticket_takeOffPort.setText(sp_airPort.getString(infos.get(position).Scity, "") + airTerminal[0]);
		 holder.tv_order_ticket_landingPort.setText(sp_airPort.getString(infos.get(position).Ecity, "") + airTerminal[1]);
		 holder.tv_order_ticket_airPortBuildPrice.setText("民航基金￥" +infos.get(position).airPortBuildPrice);
		 holder.tv_order_ticket_airLine.setText(sp_airLine.getString(infos.get(position).AirLine, "")+infos.get(position).FlightNo);
		 holder.tv_order_ticket_oilPrice.setText("燃油￥" +infos.get(position).oilPrice);
		 
		return convertView;
	}

	static class ViewHolder {
		Button btn_order_endorse;
		TextView tv_order_ticket_takeOffTime, tv_order_ticket_landingTime,
				tv_order_ticket_spacePrice, tv_order_ticket_takeOffPort,
				tv_order_ticket_landingPort, tv_order_ticket_airPortBuildPrice,
				tv_order_ticket_airLine, tv_order_ticket_takeOffDate,tv_order_ticket_oilPrice;
	}
}
