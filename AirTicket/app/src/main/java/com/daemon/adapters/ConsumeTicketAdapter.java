package com.daemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.activities.EndorseActivity;
import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderTicketQueryInfo;

import java.util.List;

import  static com.daemon.consts.Constants.*;
public class ConsumeTicketAdapter extends MyBaseAdapter {
	private List<Resp_OrderTicketQueryInfo> infos;
	private Context mContext;

	public ConsumeTicketAdapter(Context context, List<Resp_OrderTicketQueryInfo> objects) {
		super(context);
       mContext = context;
		infos = objects;
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public Resp_OrderTicketQueryInfo getItem(int position) {
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_consume_ticket, parent, false);

			holder.tv_my_consume_ticketStatus = (TextView) convertView
					.findViewById(R.id.tv_my_consume_ticketStatus);
			
			holder.tv_my_consume_Scity = (TextView) convertView
					.findViewById(R.id.tv_my_consume_Scity);
			
			holder.tv_my_consume_Ecity = (TextView) convertView
					.findViewById(R.id.tv_my_consume_Ecity);
			
			holder.tv_my_consume_flightTime = (TextView) convertView
					.findViewById(R.id.tv_my_consume_flightTime);
			
			holder.tv_my_consume_Sdate = (TextView) convertView
					.findViewById(R.id.tv_my_consume_Sdate);
			
			holder.tv_my_consume_flightType = (TextView) convertView
					.findViewById(R.id.tv_my_consume_flightType);

			holder.tv_my_consume_ticketPrice = (TextView) convertView
					.findViewById(R.id.tv_my_consume_ticketPrice);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, EndorseActivity.class);
				intent.putExtra(KEY_PARCELABLE, getItem(position));
				mContext.startActivity(intent);
			}
		});
		holder.tv_my_consume_ticketStatus.setText(getItem(position).Status);
		 holder.tv_my_consume_Scity.setText(sp_airPort.getString(getItem(position).Scity,""));
		 holder.tv_my_consume_ticketPrice.setText("￥" +getItem(position).Price);

		 holder.tv_my_consume_Sdate.setText(getItem(position).Date);
		 holder.tv_my_consume_flightTime.setText(
				 getItem(position).Stime.trim().replace(getItem(position).Date,"")+
						 "至"+
				 getItem(position).Etime.trim().replace(getItem(position).Date,""));
		 holder.tv_my_consume_flightType.setText(getItem(position).Flight);
		holder.tv_my_consume_Ecity.setText(sp_airPort.getString(getItem(position).Ecity,""));

		return convertView;
	}

	static class ViewHolder {
		Button btn_order_endorse;
		TextView tv_my_consume_ticketStatus, tv_my_consume_Scity,
				tv_my_consume_Ecity, tv_my_consume_Sdate,
				tv_my_consume_flightTime, tv_my_consume_flightType,
				tv_my_consume_ticketPrice;
	}
}
