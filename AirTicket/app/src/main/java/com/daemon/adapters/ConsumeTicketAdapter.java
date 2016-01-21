package com.daemon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.beans.FlightInfo;

import java.util.List;

public class ConsumeTicketAdapter extends BaseAdapter {
	private List<FlightInfo> infos;
	private Context mContext;

	public ConsumeTicketAdapter(Context context, List<FlightInfo> objects) {
		super();
       mContext = context;
		infos = objects;
	}

	@Override
	public int getCount() {
		return infos.size();
	}

	@Override
	public FlightInfo getItem(int position) {
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

			holder.tv_my_consume_ticketState = (TextView) convertView
					.findViewById(R.id.tv_my_consume_ticketState);
			
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
//		holder.btn_order_endorse.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
////				Intent intent = new Intent(getContext(), EndorseActivity.class);
////				intent.putExtra(KEY_CHANGE, infos.get(position).Change);
////				intent.putExtra(KEY_RETURN, infos.get(position).Return);
////				getContext().startActivity(intent);
//			}
//		});
//		holder.tv_my_consume_ticketState.setText(getItem(position).Sdate);
//		 holder.tv_my_consume_Scity.setText(getItem(position).Scity);
//		 holder.tv_my_consume_ticketPrice.setText("￥" +getItem(position).P);
//
//		 holder.tv_my_consume_Sdate.setText(getItem(position).Sdate);
//		 holder.tv_my_consume_flightTime.setText(getItem(position).Stime+"至"+getItem(position).Etime);
//		 holder.tv_my_consume_flightType.setText(getItem(position).FlightType);
//		holder.tv_my_consume_Ecity.setText(getItem(position).Ecity);

		return convertView;
	}

	static class ViewHolder {
		Button btn_order_endorse;
		TextView tv_my_consume_ticketState, tv_my_consume_Scity,
				tv_my_consume_Ecity, tv_my_consume_Sdate,
				tv_my_consume_flightTime, tv_my_consume_flightType,
				tv_my_consume_ticketPrice;
	}
}
