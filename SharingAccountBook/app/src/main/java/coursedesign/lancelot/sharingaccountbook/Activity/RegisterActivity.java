package coursedesign.lancelot.sharingaccountbook.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import coursedesign.lancelot.sharingaccountbook.R;
import coursedesign.lancelot.sharingaccountbook.Utils.AesImp;
import coursedesign.lancelot.sharingaccountbook.Utils.CodeUtils;
import coursedesign.lancelot.sharingaccountbook.Utils.ReUtil;
import coursedesign.lancelot.sharingaccountbook.Utils.simpleDialog;
import coursedesign.lancelot.sharingaccountbook.dataAccess.LocalDatabaseAccess;
import coursedesign.lancelot.sharingaccountbook.dataAccess.UserDao;
import coursedesign.lancelot.sharingaccountbook.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    final User newUser = new User();

    private final String  REMOTEIP = "http://118.24.187.42:8080";

    private String tag ="RegisterActivity";
    private ImageView reg_Ima_Hportrait;
    private ImageView reg_Ima_Check;
    private Button reg_bt_cancel;
    private Button reg_bt_submit;
    private Button reg_bt_Hportrait;
    private Button reg_bt_check;

    private TextView reg_T_Uname;
    private TextView reg_T_Psd;
    private TextView reg_T_RPsd;
    private TextView reg_T_Nickname;



    private Resources res;
    private Bitmap defaultmHeadbm;

    private Bitmap mHeadbm;//头像图片
    private EditText reg_Edt_Uname;//账号
    private EditText reg_Edt_Psd;//密码
    private EditText reg_Edt_RPsd;//确认密码
    private EditText reg_Edt_Nickname;//昵称
    private EditText reg_Edt_phone;//手机
    private Spinner reg_Sp_ID;//角色类型
    private EditText reg_Edt_Check;//验证码
    private static final int IMAGE = 1;

    private String mReg_Edt_Uname;
    private String mReg_Edt_Psd;
    private String mReg_Edt_RPsd;
    private String mReg_Edt_Nickname;
    private String mReg_Sp_ID;
    private String mReg_Edt_Check;
    private String mReg_Edt_phone;

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    toast("注册失败，输入长度错误");
                    break;
                case 2:
                    toast("注册失败，账号已存在");
                    break;
                case 3:
                    toast("注册失败，用户名已存在");
                    break;
            }
            return true;

        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        iniUI();

        Bitmap bp = new CodeUtils().createBitmap();
        reg_Ima_Check.setImageBitmap(bp);

        //点击选择图片
        reg_bt_Hportrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
            }
        });


        //点击验证码的更换图片
        reg_bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bp = new CodeUtils().createBitmap();
                reg_Ima_Check.setImageBitmap(bp);

            }
        });

        //点击取消返回登录界面
        reg_bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击注册处理数据
        reg_bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ck =CodeUtils.getCode();
                Log.i(tag,"ck!!!!!!!!:"+ck);
                //获取注册信息
                getdata();
                //检查注册信息
                if(checckdata()==1 && reChecks()){
                    newUser.setUserName(mReg_Edt_Uname);
                    newUser.setNickName(mReg_Edt_Nickname);
                    newUser.setIdentity(mReg_Sp_ID);
                    newUser.setPhoneNumber(mReg_Edt_phone);
                    newUser.setPicPath("123");
                    updata(mReg_Edt_Psd);
                }

            }
        });


        //身份下拉框处理
        List<String> data_list = new ArrayList<String>();
        data_list.add("student");
        data_list.add("teacher");
        data_list.add("workers");
        data_list.add("other");

        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reg_Sp_ID.setAdapter(arr_adapter);
    }

    /**
     * 获取注册数据
     */
    private void getdata() {
        mReg_Edt_Uname = reg_Edt_Uname.getText().toString();
        mReg_Edt_Psd = reg_Edt_Psd.getText().toString();
        mReg_Edt_RPsd = reg_Edt_RPsd.getText().toString();
        mReg_Edt_Nickname = reg_Edt_Nickname.getText().toString();
        mReg_Edt_phone = reg_Edt_phone.getText().toString();
        mReg_Sp_ID = (String)reg_Sp_ID.getSelectedItem();
        mReg_Edt_Check = reg_Edt_Check.getText().toString();

    }



    /**
     *检查注册数据
     */
    private int checckdata() {
        int i=1;
        //输入为空检查
        if(TextUtils.isEmpty(mReg_Edt_Uname)==true){
            simpleDialog.show(RegisterActivity.this,"错误","账号不能为空");
            i=0;
        }else {
            i=1;
        }
        if(TextUtils.isEmpty(mReg_Edt_Psd)==true){
            simpleDialog.show(RegisterActivity.this,"错误","密码不能为空");
            i=0;
        }else {
            i=1;
        }
        if(TextUtils.isEmpty(mReg_Edt_Nickname)==true){
            simpleDialog.show(RegisterActivity.this,"错误","昵称不能为空");
            i=0;
        }else {
            i=1;
        }
        if(TextUtils.isEmpty(mReg_Edt_phone)==true){
            simpleDialog.show(RegisterActivity.this,"错误","手机号不能为空");
            i=0;
        }else {
            i=1;
        }

        //手机位数检查
        if (mReg_Edt_phone.length()!=11){
            simpleDialog.show(RegisterActivity.this,"错误","请输入正确的手机号");
            i=0;
        }

        //密码一致检查
        if(mReg_Edt_Psd.equals(mReg_Edt_RPsd)!=true){
            i=0;
            simpleDialog.show(RegisterActivity.this,"错误","两次密码不一致");
            reg_T_Psd.setTextColor(Color.RED);
            reg_T_RPsd.setTextColor(Color.RED);
        }
        else {
            reg_T_Psd.setTextColor(Color.BLACK);
            reg_T_RPsd.setTextColor(Color.BLACK);
            i=1;
        }

        //验证码检查
        String ck = new CodeUtils().getCode();
        if (mReg_Edt_Check.equals(ck)!=true){
            simpleDialog.show(RegisterActivity.this,"错误","验证码错误");
            i=0;
        }else {
            i=1;
        }
        return i;
    }
    private boolean reChecks(){
        boolean rst = true;
        ReUtil reHandler = new ReUtil();
        //reHandler.useDefaultNamePattern();
        reHandler.useUserPattern("([\\u4e00-\\u9fa5\\w.,\\\\<>\\(\\)!~?\\/\"\':+-\\;=_])*");
        if (!reHandler.matched(mReg_Edt_Uname)){
            toast("用户名有非法字符！");
            return false;
        }
        if (!reHandler.matched(mReg_Edt_Nickname)){
            toast("昵称有非法字符！");
            return false;
        }
        reHandler.useDefaultPwdPattern();
        if (!reHandler.matched(mReg_Edt_Psd)||!reHandler.matched(mReg_Edt_RPsd)){
            toast("密码或密码确认栏有非法字符！");
            return false;
        }
        reHandler.useUserPattern("([0-9])*");
        if (!reHandler.matched(mReg_Edt_phone)){
            toast("手机号只接受数字输入！");
            return false;
        }
        return rst;
    }

    /**
     * 上传注册信息到数据库
     */
    private void updata(String pwd) {
        AesImp myAes = new AesImp();
        String unseenPwd = null;
        try{
            unseenPwd = myAes.encode(pwd);

        }catch (Exception e){
            e.printStackTrace();
        }
        if (unseenPwd != null){
            String url = REMOTEIP+"/accountBookServer/addNewUser?" +
                    "uName="+newUser.getUserName()+
                    "&nickName="+newUser.getNickName()+
                    "&pwd="+unseenPwd+
                    "&identity="+newUser.getIdentity()+
                    "&phoneNumber="+newUser.getPhoneNumber();

            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // 本地数据库操作
                    //后面要判断是否可以转数字
                    try {
                        String jsonStr = response.body().string();
                        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                        String state = jsonObject.getString("state");
                        if (state.equals("failed")){
                            //与主线程通信
                            Message msg = new Message();
                            Integer code =jsonObject.getInteger("code");
                            msg.what = code;
                            myHandler.sendMessage(msg);
                            return ;
                        }
                        Integer id = jsonObject.getInteger("userId");
                        addUserToLocalData(newUser, id);

                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }else{
            toast("注册失败");
        }


    }

    /**
     * 头像
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String image = c.getString(columnIndex);
            //显示头像
            showImage(image);
            c.close();
        }
    }

    private void showImage(String image) {
        mHeadbm = BitmapFactory.decodeFile(image);
        reg_Ima_Hportrait.setImageBitmap(mHeadbm);
    }


    private void iniUI() {
        reg_bt_cancel = (Button)findViewById(R.id.reg_bt_cancel);
        reg_bt_submit = (Button)findViewById(R.id.reg_bt_submit);
        reg_Sp_ID = (Spinner)findViewById(R.id.reg_Sp_ID);
        reg_bt_Hportrait = (Button)findViewById(R.id.reg_bt_Hportrait);
        reg_Ima_Hportrait = (ImageView)findViewById(R.id.reg_Ima_Hportrait);
        reg_Ima_Check = (ImageView)findViewById(R.id.reg_Ima_Check);
        reg_bt_check = (Button)findViewById(R.id.reg_bt_check);

        reg_Edt_Uname = (EditText)findViewById(R.id.reg_Edt_Uname);
        reg_Edt_Psd = (EditText)findViewById(R.id.reg_Edt_Psd);
        reg_Edt_RPsd = (EditText)findViewById(R.id.reg_Edt_RPsd);
        reg_Edt_Nickname = (EditText)findViewById(R.id.reg_Edt_Nickname);
        reg_Edt_phone = (EditText)findViewById(R.id.reg_Edt_phone);
        reg_Sp_ID = (Spinner)findViewById(R.id.reg_Sp_ID);
        reg_Edt_Check = (EditText)findViewById(R.id.reg_Edt_Check);

        reg_T_Uname = (TextView)findViewById(R.id.reg_T_Uname);
        reg_T_Psd = (TextView)findViewById(R.id.reg_T_Psd);
        reg_T_RPsd = (TextView)findViewById(R.id.reg_T_RPsd);
        reg_T_Nickname = (TextView)findViewById(R.id.reg_T_Nickname);

        res = RegisterActivity.this.getResources();
        defaultmHeadbm = BitmapFactory.decodeResource(res, R.drawable.hportrait);//默认头像

    }
    private void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_LONG).show();
    }
    //操作本地数据库
    private void addUserToLocalData(User user,Integer userId){
        user.setUserId(userId);
        SQLiteDatabase users;
        LocalDatabaseAccess db = new LocalDatabaseAccess();
        users = db.initDatabaseAccess();
        UserDao userDao = new UserDao(users);
        userDao.insert(user);
    }
}
