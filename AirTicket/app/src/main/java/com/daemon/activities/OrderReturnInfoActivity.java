package com.daemon.activities;

import android.os.Bundle;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.beans.Resp_OrderTicketQueryInfo;
import com.daemon.interfaces.Commands;
import com.daemon.models.OrderReturnInfoModel;
import com.daemon.utils.DialogUtil;

import java.util.HashMap;

import static com.daemon.consts.Constants.KEY_PARCELABLE;
import static com.daemon.consts.Constants.KEY_TYPE;
import static com.daemon.consts.Constants.MODEL_ORDER_TICKET_ENDORSE;
import static com.daemon.consts.Constants.MODEL_ORDER_TICKET_RETURN;
import static com.daemon.consts.Constants.VIEW_ORDER_TICKET_ENDORSE;
import static com.daemon.consts.Constants.VIEW_ORDER_TICKET_RETURN;

public class OrderReturnInfoActivity extends BaseActivity {
    private Resp_OrderTicketQueryInfo resp_orderTicketQueryInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_return_info);
        setModelDelegate(new OrderReturnInfoModel(handler, this));
        setViewChangeListener(this);

        if(getIntent().hasExtra(KEY_PARCELABLE)) {
            resp_orderTicketQueryInfo = getIntent().getParcelableExtra(KEY_PARCELABLE);

            Button btn_order_ticket_detail_commitApply = (Button) findViewById(R.id.btn_order_ticket_detail_commitApply);
            btn_order_ticket_detail_commitApply.setOnClickListener(this);

            Button btn_order_ticket_detail_cancelApply = (Button) findViewById(R.id.btn_order_ticket_detail_cancelApply);
            btn_order_ticket_detail_cancelApply.setOnClickListener(this);


            String temp = "";
            String desc = "";
            if(MODEL_ORDER_TICKET_ENDORSE == getIntent().getIntExtra(KEY_TYPE,0)){
                temp = "改签须知：";
                desc = resp_orderTicketQueryInfo.ZhuanQianStr;
            }else if(MODEL_ORDER_TICKET_RETURN == getIntent().getIntExtra(KEY_TYPE,0)){
                temp = "退废票须知：";
                desc = resp_orderTicketQueryInfo.RInfo;
            }

            if(TextUtils.isEmpty(desc))desc = "暂无信息";
            SpannableString spannableString = new SpannableString(temp+desc);
            spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.ticket_title_color)), 0,temp.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            spannableString.setSpan(new AbsoluteSizeSpan(14,true), temp.length(), spannableString.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            TextView tv_order_ticket_detail_desc = (TextView)findViewById(R.id.tv_order_ticket_detail_desc);
            tv_order_ticket_detail_desc.setText(spannableString);
        }else{

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_order_ticket_detail_commitApply:
                if(MODEL_ORDER_TICKET_ENDORSE == getIntent().getIntExtra(KEY_TYPE,0)){

                }else if(MODEL_ORDER_TICKET_RETURN == getIntent().getIntExtra(KEY_TYPE,0)){
                    HashMap<String, String> params_map = new HashMap<String, String>();
                    params_map.put("UserName", "wang87654321");
                    params_map.put("OrderNo", "W2016012104024160509");
                    params_map.put("PNR", resp_orderTicketQueryInfo.PNR);
                    params_map.put("PayType", resp_orderTicketQueryInfo.PayWay);
                    params_map.put("CanBeizhu", "测试用的");
                    notifyModelChange(Message.obtain(handler, MODEL_ORDER_TICKET_RETURN,params_map));
                }

                break;

            case R.id.btn_order_ticket_detail_cancelApply:
                finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onViewChange(Message msg) {
        switch (msg.what){
            case VIEW_ORDER_TICKET_ENDORSE:

                break;

            case VIEW_ORDER_TICKET_RETURN:
                String message = (String)msg.obj;
                DialogUtil.showDialog(OrderReturnInfoActivity.this, "提示", message, new Commands() {
                    @Override
                    public void executeCommand(Message msg_params) {

                    }
                });
                break;
        }
    }
}
