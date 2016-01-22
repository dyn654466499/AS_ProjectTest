package com.daemon.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.daemon.utils.SPUtil;

/**
 * Created by Administrator on 2016/1/21.
 */
public class MyBaseAdapter  extends BaseAdapter{
    private Context mContext;
    protected SharedPreferences sp_cabin,sp_airLine,sp_airPort,sp_city,sp_orderStatus;

    public MyBaseAdapter(Context mContext) {
        this.mContext = mContext;
        sp_cabin = SPUtil.getCabin(mContext);
        sp_airLine = SPUtil.getAirLine(mContext);
        sp_airPort = SPUtil.getAirPort(mContext);
        sp_city = SPUtil.getCity(mContext);
        sp_orderStatus = SPUtil.getOrderStatus(mContext);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
