package com.daemon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.daemon.adapters.OrderCateringDishesAdapter;
import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderCateringDetail;
import com.daemon.beans.Resp_OrderCateringDishesDetail;
import com.daemon.beans.Resp_OrderCateringList;
import com.daemon.consts.Constants;
import com.daemon.interfaces.Commands;
import com.daemon.models.OrderModel;
import com.daemon.utils.DialogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static com.daemon.consts.Constants.KEY_PARCELABLE;
import static com.daemon.consts.Constants.MODEL_ORDER_CATERING_GOODS_DETAIL_QUERY;

/**
 * 餐饮订单详情
 */
public class OrderCateringDetailActivity extends BaseActivity {
   private Resp_OrderCateringList.Rows row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_catering_detail);
        setModelDelegate(new OrderModel(handler,this));
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
            params_map.put("type", row.Field_Type);
            params_map.put("Id", row.X6_Product_Id);

            notifyModelChange(Message.obtain(handler, Constants.MODEL_ORDER_CATERING_DETAIL_QUERY, params_map));

            notifyModelChange(Message.obtain(handler, MODEL_ORDER_CATERING_GOODS_DETAIL_QUERY,params_map));
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
            case Constants.VIEW_ORDER_CATERING_DETAIL_QUERY:
                if(msg.obj instanceof String){
                    DialogUtil.showDialog(OrderCateringDetailActivity.this, getString(R.string.title_order_detail), (String) msg.obj, new Commands() {
                        @Override
                        public void executeCommand(Message msg_params) {

                        }
                    });
                }else {
                    if(!isDestroyed) {
                        Resp_OrderCateringDetail resp_orderCateringDetail = (Resp_OrderCateringDetail) msg.obj;
                        TextView tv_order_catering_personName = (TextView)findViewById(R.id.tv_order_catering_personName);
                        tv_order_catering_personName.setText(resp_orderCateringDetail.rows.get(0).Field_DWXM);

                        TextView tv_order_catering_phone = (TextView)findViewById(R.id.tv_order_catering_phone);
                        tv_order_catering_phone.setText(resp_orderCateringDetail.rows.get(0).Field_YHSJ);

                        TextView tv_order_catering_personCount = (TextView)findViewById(R.id.tv_order_catering_personCount);
                        tv_order_catering_personCount.setText(resp_orderCateringDetail.rows.get(0).Field_RS);

                        TextView tv_order_catering_time = (TextView)findViewById(R.id.tv_order_catering_time);
                        tv_order_catering_time.setText(resp_orderCateringDetail.rows.get(0).Field_DWYCSJ);

                        TextView tv_order_catering_beizhu = (TextView)findViewById(R.id.tv_order_catering_beizhu);
                        tv_order_catering_beizhu.setText(resp_orderCateringDetail.rows.get(0).Field_DWBZ);

                        EditText et_order_catering_storeName = (EditText)findViewById(R.id.et_order_catering_storeName);
                        et_order_catering_storeName.setText(resp_orderCateringDetail.rows.get(0).Field_DPMC);

                        TextView tv_order_catering_totalPrice = (TextView)findViewById(R.id.tv_order_catering_totalPrice);
                        tv_order_catering_totalPrice.setText("￥" + resp_orderCateringDetail.rows.get(0).Field_CYDDJE);


                    }
                }
                break;

            case Constants.VIEW_ORDER_CATERING_GOODS_DETAIL_QUERY:
                if(msg.obj instanceof String){
                    DialogUtil.showDialog(OrderCateringDetailActivity.this, getString(R.string.title_order_detail), (String) msg.obj, new Commands() {
                        @Override
                        public void executeCommand(Message msg_params) {

                        }
                    });
                }else {
                    if(!isDestroyed) {
                        Resp_OrderCateringDishesDetail resp_orderCateringDishesDetail = (Resp_OrderCateringDishesDetail) msg.obj;
                        /**
                         *  --------------------------------------     菜品列表 start         -------------------------------------------
                         */
                        ListView lv_order_catering_dishes_detail = (ListView)findViewById(R.id.lv_order_catering_dishes_detail);
                        OrderCateringDishesAdapter adapter = new OrderCateringDishesAdapter(this,resp_orderCateringDishesDetail);
                        lv_order_catering_dishes_detail.setAdapter(adapter);
                        /**
                         *  --------------------------------------     菜品列表  end        -------------------------------------------
                         */

                    }
                }
                break;
        }
    }

    private String getTimeInterval(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long interval = (time2 - time1)/1000;
        String str_time = String.valueOf(interval/3600)+"h"+
                ((interval/60)%60 < 10 ? "0"+String.valueOf((interval/60)%60):String.valueOf((interval/60)%60))+"m";
        return str_time;
    }

    private String getFormatTime(String smdate){
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
            cal.setTime(new Date(System.currentTimeMillis()));
        }
        long time = cal.getTimeInMillis();
        sdf =  new SimpleDateFormat("HH:mm");
        String str_time = sdf.format(new Date(time));
        return str_time;
    }

    private String getWeekOfDate(String date) {
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = cal.getTimeInMillis();
        return  dateFm.format(new Date(time));
    }
}
