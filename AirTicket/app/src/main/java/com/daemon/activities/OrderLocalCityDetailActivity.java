package com.daemon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderLocalCityDetail;
import com.daemon.beans.Resp_OrderLocalCityList;
import com.daemon.consts.Constants;
import com.daemon.interfaces.Commands;
import com.daemon.models.OrderModel;
import com.daemon.utils.DialogUtil;
import com.daemon.utils.SPUtil;

import java.util.HashMap;

import static com.daemon.consts.Constants.KEY_PARCELABLE;

/**
 * 同城订单详情
 */
public class OrderLocalCityDetailActivity extends BaseActivity {
   private Resp_OrderLocalCityList.Rows row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_local_city_detail);

        setModelDelegate(new OrderModel(handler, this));
        setViewChangeListener(this);

        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.title_order_detail));

        Button btn_back = (Button)findViewById(R.id.btn_title_back);
        btn_back.setOnClickListener(this);

        if(getIntent().hasExtra(KEY_PARCELABLE)){
            row = getIntent().getParcelableExtra(KEY_PARCELABLE);

            HashMap<String, String> params_map = new HashMap<String, String>();
            params_map.put("UId", "yesicity2015");
            params_map.put("Field_YHID", "1");
            params_map.put("Yesicity", "1");
            params_map.put("Id", row.X6_Product_Id);

            notifyModelChange(Message.obtain(handler, Constants.MODEL_ORDER_LOCAL_CITY_DETAIL_QUERY, params_map));
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
     switch (v.getId()){
        case R.id.btn_title_back:
         finish();
         break;

     }
    }

    @Override
    public void onViewChange(Message msg) {
        switch (msg.what){
            case Constants.VIEW_ORDER_LOCAL_CITY_DETAIL_QUERY:
                if(msg.obj instanceof String){
                    DialogUtil.showDialog(OrderLocalCityDetailActivity.this, getString(R.string.title_order_detail), (String) msg.obj, new Commands() {
                        @Override
                        public void executeCommand(Message msg_params) {

                        }
                    });
                }else {
                    if(!isDestroyed) {
                        Resp_OrderLocalCityDetail resp_OrderLocalCityDetail = (Resp_OrderLocalCityDetail) msg.obj;
                        TextView tv_order_localCity_detail_status = (TextView)findViewById(R.id.tv_order_localCity_detail_status);
                        tv_order_localCity_detail_status.setText(SPUtil.getPayStatus(this).getString(resp_OrderLocalCityDetail.rows.get(0).X6_Product_Recommend,""));

                        TextView tv_order_localCity_detail_name = (TextView)findViewById(R.id.tv_order_localCity_detail_name);
                        tv_order_localCity_detail_name.setText(resp_OrderLocalCityDetail.rows.get(0).Field_HDMC);

                        TextView tv_order_localCity_detail_personCount = (TextView)findViewById(R.id.tv_order_localCity_detail_personCount);
                        tv_order_localCity_detail_personCount.setText(resp_OrderLocalCityDetail.rows.get(0).Field_HDBCBMRS);

                        TextView tv_order_localCity_detail_price = (TextView)findViewById(R.id.tv_order_localCity_detail_price);
                        tv_order_localCity_detail_price.setText("￥" +resp_OrderLocalCityDetail.rows.get(0).Field_HDBMFY);
                        tv_order_localCity_detail_price.setTextColor(getResources().getColor(R.color.ticket_title_color));

                        TextView tv_order_localCity_detail_orderNo = (TextView)findViewById(R.id.tv_order_localCity_detail_orderNo);
                        tv_order_localCity_detail_orderNo.setText(resp_OrderLocalCityDetail.rows.get(0).Field_DDBH);

                        TextView tv_order_localCity_detail_orderTime = (TextView)findViewById(R.id.tv_order_localCity_detail_orderTime);
                        tv_order_localCity_detail_orderTime.setText(resp_OrderLocalCityDetail.rows.get(0).Field_DDSJ);

                    }
                }
                break;
        }
    }

}
