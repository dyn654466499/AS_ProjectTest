package com.daemon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderTicketQueryInfo;
import com.daemon.utils.SPUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.daemon.consts.Constants.KEY_PARCELABLE;
import static com.daemon.consts.Constants.KEY_TYPE;
import static com.daemon.consts.Constants.MODEL_ORDER_TICKET_ENDORSE;
import static com.daemon.consts.Constants.MODEL_ORDER_TICKET_RETURN;

public class OrderTicketDetailActivity extends BaseActivity {
   private Resp_OrderTicketQueryInfo resp_orderTicketQueryInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ticket_detail);
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText(getString(R.string.title_order_detail));

        Button btn_back = (Button)findViewById(R.id.btn_title_back);
        btn_back.setOnClickListener(this);

        if(getIntent().hasExtra(KEY_PARCELABLE)){
            resp_orderTicketQueryInfo = getIntent().getParcelableExtra(KEY_PARCELABLE);
            Button btn_order_ticket_detail_endorse = (Button)findViewById(R.id.btn_order_ticket_detail_endorse);
            btn_order_ticket_detail_endorse.setOnClickListener(this);

            Button btn_order_ticket_detail_return = (Button)findViewById(R.id.btn_order_ticket_detail_return);
            btn_order_ticket_detail_return.setOnClickListener(this);

            TextView tv_order_ticket_detail_orderNo = (TextView)findViewById(R.id.tv_order_ticket_detail_orderNo);
            tv_order_ticket_detail_orderNo.setText(resp_orderTicketQueryInfo.OrderNo);

            TextView tv_order_ticket_detail_status = (TextView)findViewById(R.id.tv_order_ticket_detail_status);
            tv_order_ticket_detail_status.setText(SPUtil.getOrderStatus(this).getString(resp_orderTicketQueryInfo.Status,""));

            TextView tv_order_ticket_detail_price = (TextView)findViewById(R.id.tv_order_ticket_detail_price);
            tv_order_ticket_detail_price.setText("￥"+resp_orderTicketQueryInfo.PayMoney);

            /**
             *  --------------------------------------     乘机人信息列表 start         -------------------------------------------
             */
            ListView lv_order_ticket_detail_passengerInfo = (ListView)findViewById(R.id.lv_order_ticket_detail_passengerInfo);
            String[] from = new String[]{"name","certType","certNo"};
            int[] to = new int[]{R.id.tv_order_ticket_detail_passengerInfo_name,
                    R.id.tv_order_ticket_detail_passengerInfo_certType,
                    R.id.tv_order_ticket_detail_passengerInfo_certNo,
            };
            String[] names = resp_orderTicketQueryInfo.PName.split("\\|");
            String[] certNos = resp_orderTicketQueryInfo.CardNo.split("\\|");

            List<Map<String,Object>> data = new LinkedList<>();
            for (int i = 0; i< names.length;i++){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("name",names[i]);
                map.put("certType","身份证");
                map.put("certNo",certNos[i]);
                data.add(map);
            }
            SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.item_order_ticket_detail_passenger_info,from,to);
            lv_order_ticket_detail_passengerInfo.setAdapter(adapter);

            /**
             *  --------------------------------------     乘机人信息列表  end        -------------------------------------------
             */

            TextView tv_order_ticket_detail_flight_date = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_date);
            tv_order_ticket_detail_flight_date.setText(resp_orderTicketQueryInfo.Date);

            TextView tv_order_ticket_detail_flight_day = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_day);
            tv_order_ticket_detail_flight_day.setText(getWeekOfDate(resp_orderTicketQueryInfo.Date));

            TextView tv_order_ticket_detail_flight_Scity = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_Scity);
            tv_order_ticket_detail_flight_Scity.setText(SPUtil.getCity(this).getString(resp_orderTicketQueryInfo.Scity,""));

            TextView tv_order_ticket_detail_flight_Ecity = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_Ecity);
            tv_order_ticket_detail_flight_Ecity.setText(SPUtil.getCity(this).getString(resp_orderTicketQueryInfo.Ecity,""));

            TextView tv_order_ticket_detail_flight_cabin = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_cabin);
            tv_order_ticket_detail_flight_cabin.setText(SPUtil.getCabin(this).getString(resp_orderTicketQueryInfo.Cabin,""));


            TextView tv_order_ticket_detail_flight_Stime = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_Stime);
            tv_order_ticket_detail_flight_Stime.setText(getFormatTime(resp_orderTicketQueryInfo.Stime));

            TextView tv_order_ticket_detail_flight_Etime = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_Etime);
            tv_order_ticket_detail_flight_Etime.setText(getFormatTime(resp_orderTicketQueryInfo.Etime));

            String str_interval = "0h";
            try {
                str_interval = getTimeInterval(resp_orderTicketQueryInfo.Stime,resp_orderTicketQueryInfo.Etime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            TextView tv_order_ticket_detail_flight_interval = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_interval);
            tv_order_ticket_detail_flight_interval.setText(str_interval);

            TextView tv_order_ticket_detail_flight_Sport = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_Sport);
            tv_order_ticket_detail_flight_Sport.setText(SPUtil.getAirPort(this).getString(resp_orderTicketQueryInfo.Scity,""));

            TextView tv_order_ticket_detail_flight_Eport = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_Eport);
            tv_order_ticket_detail_flight_Eport.setText(SPUtil.getAirPort(this).getString(resp_orderTicketQueryInfo.Ecity, ""));

            TextView tv_order_ticket_detail_flight_airLine = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_airLine);
            tv_order_ticket_detail_flight_airLine.setText(SPUtil.getAirLine(this).getString(resp_orderTicketQueryInfo.Flight.substring(0,2),""));

            TextView tv_order_ticket_detail_flight_flightNo = (TextView)findViewById(R.id.tv_order_ticket_detail_flight_flightNo);
            tv_order_ticket_detail_flight_flightNo.setText(resp_orderTicketQueryInfo.Flight);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
     switch (v.getId()){
        case R.id.btn_title_back:
         finish();
         break;

         case R.id.btn_order_ticket_detail_endorse:
             intent = new Intent(OrderTicketDetailActivity.this,OrderReturnInfoActivity.class);
             intent.putExtra(KEY_PARCELABLE,resp_orderTicketQueryInfo);
             intent.putExtra(KEY_TYPE,MODEL_ORDER_TICKET_ENDORSE);
             startActivity(intent);
             overridePendingTransition(0,0);
             break;

         case R.id.btn_order_ticket_detail_return:
             intent = new Intent(OrderTicketDetailActivity.this,OrderReturnInfoActivity.class);
             intent.putExtra(KEY_PARCELABLE,resp_orderTicketQueryInfo);
             intent.putExtra(KEY_TYPE,MODEL_ORDER_TICKET_RETURN);
             startActivity(intent);
             overridePendingTransition(0,0);
             break;
     }
    }

    @Override
    public void onViewChange(Message msg) {

    }

    private String getTimeInterval(String smdate, String bdate) throws java.text.ParseException {
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
