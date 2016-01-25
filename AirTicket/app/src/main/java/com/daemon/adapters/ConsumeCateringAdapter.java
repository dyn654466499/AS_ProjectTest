package com.daemon.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daemon.activities.OrderCateringDetailActivity;
import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderCateringList;
import com.daemon.consts.Constants;
import com.daemon.utils.VolleyUtil;

public class ConsumeCateringAdapter extends MyBaseAdapter {
	private Resp_OrderCateringList info;
	private Context mContext;

	public ConsumeCateringAdapter(Context context, Resp_OrderCateringList object) {
		super(context);
        mContext = context;
		info = object;
	}

	@Override
	public int getCount() {
		return info.rows.size();
	}

	@Override
	public Resp_OrderCateringList.Rows getItem(int position) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_consume_catering, parent, false);

			holder.tv_my_consume_cateringType = (TextView) convertView
					.findViewById(R.id.tv_my_consume_cateringType);
			
			holder.tv_my_consume_cateringPrice = (TextView) convertView
					.findViewById(R.id.tv_my_consume_cateringPrice);
			
			holder.tv_my_consume_cateringName = (TextView) convertView
					.findViewById(R.id.tv_my_consume_cateringName);
			
			holder.tv_my_consume_cateringTime = (TextView) convertView
					.findViewById(R.id.tv_my_consume_cateringTime);
			
			holder.tv_my_consume_cateringId = (TextView) convertView
					.findViewById(R.id.tv_my_consume_cateringId);

			holder.imageView_my_consume_cateringIcon = (ImageView)convertView.findViewById(R.id.imageView_my_consume_cateringIcon);
			VolleyUtil.loadImage(mContext,
					holder.imageView_my_consume_cateringIcon,
					"http://www.icityto.com" + getItem(position).X6_Product_Pic
			);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, OrderCateringDetailActivity.class);
				intent.putExtra(Constants.KEY_PARCELABLE, getItem(position));
				mContext.startActivity(intent);
			}
		});

		holder.tv_my_consume_cateringType.setText(getItem(position).Field_Type);
		 holder.tv_my_consume_cateringPrice.setText("￥"+getItem(position).Field_CYDDJE);
		 holder.tv_my_consume_cateringTime.setText(getItem(position).X6_Product_Time);
		 holder.tv_my_consume_cateringId.setText(getItem(position).Field_CYDDBH);
		holder.tv_my_consume_cateringName.setText(getItem(position).Field_DPMC);

		return convertView;
	}

	static class ViewHolder {
		TextView tv_my_consume_cateringType, tv_my_consume_cateringPrice,
				tv_my_consume_cateringName, tv_my_consume_cateringId,
				tv_my_consume_cateringTime;
		ImageView imageView_my_consume_cateringIcon;
	}
}