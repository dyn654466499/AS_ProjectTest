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
import android.widget.EditText;
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
    private EditText et_order_ticket_return_beizhu;
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

            et_order_ticket_return_beizhu = (EditText)findViewById(R.id.et_order_ticket_return_beizhu);


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
            /**
             * 提交申请
             */
            case R.id.btn_order_ticket_detail_commitApply:
                /**
                 * 改签暂时被停用
                 */
                if(MODEL_ORDER_TICKET_ENDORSE == getIntent().getIntExtra(KEY_TYPE,0)){

                }else if(MODEL_ORDER_TICKET_RETURN == getIntent().getIntExtra(KEY_TYPE,0)){
                    HashMap<String, String> params_map = new HashMap<String, String>();
                    params_map.put("UserName", "wang87654321");
                    params_map.put("OrderNo", resp_orderTicketQueryInfo.OrderNo);
                    params_map.put("Repeal", "");//0：不变  1：废票  2：退票 与乘客对应，多个以|隔开
                    params_map.put("PersonName", resp_orderTicketQueryInfo.PName);
                    params_map.put("Remarks", et_order_ticket_return_beizhu.getText().toString());
                    params_map.put("Type", "B");//A：废票   B：退票
                    params_map.put("Rnum", "B");//退票人数
                    params_map.put("Cause", "");//退票原因
                    params_map.put("TicketNo", "");//退废票号，|隔开
                    params_map.put("Ramount", resp_orderTicketQueryInfo.PayMoney);
                    params_map.put("IsCancelSeat", "是");

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
