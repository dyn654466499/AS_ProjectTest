package com.daemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.activities.EndorseActivity;
import com.daemon.airticket.R;
import com.daemon.beans.FlightDetailInfo;
import com.daemon.beans.FlightInfo;

import java.util.List;

public class OrderTicketAdapter extends BaseAdapter {
	private Context mContext;
	private List<FlightDetailInfo> detailInfos;
	
	public OrderTicketAdapter(Context mContext, List<FlightDetailInfo> detailInfos) {
		super();
		this.mContext = mContext;
		this.detailInfos = detailInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return detailInfos.size();
	}

	@Override
	public FlightInfo getItem(int position) {
		// TODO Auto-generated method stub
		return detailInfos.get(position);
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
			
            holder.btn_order_endorse.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					mContext.startActivity(new Intent(mContext,EndorseActivity.class));
				}
			});

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 holder.tv_order_ticket_takeOffDate.setText(detailInfos.get(position).Sdate);
		 holder.tv_order_ticket_takeOffTime.setText(detailInfos.get(position).Stime);
		 holder.tv_order_ticket_landingTime.setText(detailInfos.get(position).Etime);
		 holder.tv_order_ticket_spacePrice.setText(detailInfos.get(position).P);
		 holder.tv_order_ticket_takeOffPort.setText(detailInfos.get(position).Scity);
		 holder.tv_order_ticket_landingPort.setText(detailInfos.get(position).Ecity);
		 holder.tv_order_ticket_airPortBuildPrice.setText(detailInfos.get(position).airPortBuildPrice);
		 holder.tv_order_ticket_airLine.setText(detailInfos.get(position).AirLine);
		 holder.tv_order_ticket_oilPrice.setText(detailInfos.get(position).oilPrice);
		 
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
