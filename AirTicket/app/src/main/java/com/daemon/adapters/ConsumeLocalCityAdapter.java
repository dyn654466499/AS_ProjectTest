package com.daemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daemon.activities.OrderLocalCityDetailActivity;
import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderLocalCityList;
import com.daemon.consts.Constants;
import com.daemon.utils.SPUtil;

public class ConsumeLocalCityAdapter extends MyBaseAdapter {
	private Resp_OrderLocalCityList info;
	private Context mContext;
	public ConsumeLocalCityAdapter(Context context, Resp_OrderLocalCityList object) {
		super(context);
        mContext = context;
		info = object;
	}

	@Override
	public int getCount() {
		return info.rows.size();
	}

	@Override
	public Resp_OrderLocalCityList.Rows getItem(int position) {
		return info.rows.get(position);
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_consume_local_city, parent, false);

			holder.btn_my_consume_localCityPay = (Button) convertView
					.findViewById(R.id.btn_my_consume_localCityPay);
			
			holder.tv_my_consume_localCityPrice = (TextView) convertView
					.findViewById(R.id.tv_my_consume_localCityPrice);
			
			holder.tv_my_consume_localCityName = (TextView) convertView
					.findViewById(R.id.tv_my_consume_localCityName);

			holder.imageView_my_consume_localCityIcon = (ImageView)convertView.findViewById(R.id.imageView_my_consume_localCityIcon);

			convertView.setTag(holder);
			Glide.with(mContext)
					.load("http://www.icityto.com" + getItem(position).X6_Product_Pic)
					.into(holder.imageView_my_consume_localCityIcon);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, OrderLocalCityDetailActivity.class);
				intent.putExtra(Constants.KEY_PARCELABLE, getItem(position));
				mContext.startActivity(intent);
			}
		});
		holder.btn_my_consume_localCityPay.setText(SPUtil.getPayStatus(mContext).getString(getItem(position).X6_Product_Recommend,""));
        if(",1,".equals(getItem(position).X6_Product_Recommend)){
			holder.btn_my_consume_localCityPay.setTextColor(Color.WHITE);
			holder.btn_my_consume_localCityPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ticket_title_corner));
		}else if(",2,".equals(getItem(position).X6_Product_Recommend)){
			holder.btn_my_consume_localCityPay.setTextColor(Color.WHITE);
			holder.btn_my_consume_localCityPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ticket_title_corner));
			holder.btn_my_consume_localCityPay.setText("待确认");
		}else if(",3,".equals(getItem(position).X6_Product_Recommend)){
			holder.btn_my_consume_localCityPay.setTextColor(mContext.getResources().getColor(R.color.ticket_font_gray));
			holder.btn_my_consume_localCityPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_disable_corner));
			holder.btn_my_consume_localCityPay.setText("已支付");
		}else{
			holder.btn_my_consume_localCityPay.setTextColor(mContext.getResources().getColor(R.color.ticket_font_gray));
			holder.btn_my_consume_localCityPay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_disable_corner));
		}

		holder.tv_my_consume_localCityPrice.setText("￥"+getItem(position).Field_HDBMFY);
		holder.tv_my_consume_localCityName.setText(getItem(position).Field_HDMC);

		return convertView;
	}

	static class ViewHolder {
		TextView tv_my_consume_localCityName, tv_my_consume_localCityPrice;
		ImageView imageView_my_consume_localCityIcon;
		Button btn_my_consume_localCityPay;
	}
}
