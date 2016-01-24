package com.daemon.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daemon.airticket.R;
import com.daemon.utils.CommonUtil;

import java.io.File;

import static com.daemon.consts.Constants.REQUEST_CODE_CAMERA;
import static com.daemon.consts.Constants.REQUEST_CODE_GALLERY;
import static com.daemon.consts.Constants.REQUEST_CODE_RESULT;

public class ImageSelectActivity extends BaseActivity {

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "personHead.jpg";

    private ImageView headImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);
        Button btn_image_select_photoAlbum = (Button) findViewById(R.id.btn_image_select_photoAlbum);
        btn_image_select_photoAlbum.setOnClickListener(this);

        Button btn_image_select_capture = (Button) findViewById(R.id.btn_image_select_capture);
        btn_image_select_capture.setOnClickListener(this);

        Button btn_image_select_cancel = (Button) findViewById(R.id.btn_image_select_cancel);
        btn_image_select_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_image_select_photoAlbum:
                choseHeadImageFromGallery();
                break;

            case R.id.btn_image_select_capture:
                choseHeadImageFromCameraCapture();
                break;

            case R.id.btn_image_select_cancel:
                finish();
                break;

        }
    }

    // 从本地相册选取图片作为头像
    private void choseHeadImageFromGallery() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery,REQUEST_CODE_GALLERY);
    }

    // 启动手机相机拍摄照片作为头像
    private void choseHeadImageFromCameraCapture() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(getFilesDir().getPath() + "/", IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
       if(resultCode == RESULT_OK){
        switch (requestCode) {
            case REQUEST_CODE_GALLERY:
                cropRawPhoto(intent.getData());
                break;

            case REQUEST_CODE_CAMERA:
                    File tempFile = new File(getFilesDir().getPath() + "/", IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                break;

            case REQUEST_CODE_RESULT:
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
        }

        super.onActivityResult(requestCode, resultCode, intent);
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", CommonUtil.Dp2Px(ImageSelectActivity.this,45));
        intent.putExtra("outputY", CommonUtil.Dp2Px(ImageSelectActivity.this,45));
        intent.putExtra("return-data", true);

        startActivityForResult(intent, REQUEST_CODE_RESULT);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onViewChange(Message msg) {

    }
}
