package com.daemon.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daemon.airticket.R;
import com.daemon.utils.ImageUtil;

import java.io.File;

import static com.daemon.consts.Constants.REQUEST_CODE_SET_HEAD;
public class EditPersonInfoActivity extends BaseActivity {

    /**
     * 名字不能带下划线
     */
    private static final String IMAGE_HEAD_NAME = "personHead";

    private ImageView imageView_person_info_head = null;
   private EditText et_person_info_nickname,et_person_info_name,et_person_info_phone,et_person_info_email,
           et_person_info_city,et_person_info_company,et_person_info_university,
           et_person_info_sign,et_person_info_hobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_info);
        TextView tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setText("编辑资料");

        Button btn_title_back = (Button) findViewById(R.id.btn_title_back);
        btn_title_back.setOnClickListener(this);
        btn_title_back.requestFocus();

        Button btn_person_info_save = (Button) findViewById(R.id.btn_person_info_save);
        btn_person_info_save.setOnClickListener(this);

        Button btn_person_info_head = (Button) findViewById(R.id.btn_person_info_head);
        btn_person_info_head.setOnClickListener(this);

         et_person_info_nickname = (EditText)findViewById(R.id.et_person_info_nickname);

         et_person_info_name = (EditText)findViewById(R.id.et_person_info_name);

         et_person_info_email = (EditText)findViewById(R.id.et_person_info_email);

         et_person_info_phone = (EditText)findViewById(R.id.et_person_info_phone);

         et_person_info_city = (EditText)findViewById(R.id.et_person_info_city);

         et_person_info_company = (EditText)findViewById(R.id.et_person_info_company);

         et_person_info_university = (EditText)findViewById(R.id.et_person_info_university);

         et_person_info_sign = (EditText)findViewById(R.id.et_person_info_sign);

         et_person_info_hobby = (EditText)findViewById(R.id.et_person_info_hobby);


         imageView_person_info_head = (ImageView)findViewById(R.id.imageView_person_info_head);

        File user_imagefile = new File(new File(getFilesDir().getPath() + "/"), IMAGE_HEAD_NAME + ".jpg");
        if(user_imagefile.exists()){
            imageView_person_info_head.setVisibility(View.VISIBLE);
            imageView_person_info_head.setImageBitmap(BitmapFactory.decodeFile(user_imagefile.getPath()));
        }else{
            imageView_person_info_head.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_title_back:
                finish();
                break;

            case R.id.btn_person_info_save:
                et_person_info_nickname.getText().toString();
                et_person_info_name.getText().toString();
                et_person_info_phone.getText().toString();
                et_person_info_email.getText().toString();
                et_person_info_city.getText().toString();
                et_person_info_company.getText().toString();
                et_person_info_university.getText().toString();
                et_person_info_sign.getText().toString();
                et_person_info_hobby.getText().toString();
                break;

            case R.id.btn_person_info_head:
                Intent intent = new Intent(EditPersonInfoActivity.this,ImageSelectActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SET_HEAD);
                overridePendingTransition(0,0);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case REQUEST_CODE_SET_HEAD:
                    imageView_person_info_head.setVisibility(View.VISIBLE);
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        Bitmap photo = extras.getParcelable("data");
                        ImageUtil.storeImageToSDcard(photo, IMAGE_HEAD_NAME,getFilesDir().getPath()+ "/");
                        imageView_person_info_head.setImageBitmap(photo);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
    @Override
    public void onViewChange(Message msg) {

    }
}
