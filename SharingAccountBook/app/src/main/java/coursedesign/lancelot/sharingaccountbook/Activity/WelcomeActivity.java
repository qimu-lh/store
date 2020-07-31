package coursedesign.lancelot.sharingaccountbook.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import coursedesign.lancelot.sharingaccountbook.R;
import coursedesign.lancelot.sharingaccountbook.Utils.AesImp;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookMembershipDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.ExpenditureRecordDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.UserDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.LocalDatabaseAccess;
import coursedesign.lancelot.sharingaccountbook.domain.Accountbook;
import coursedesign.lancelot.sharingaccountbook.domain.AccountbookMembership;
import coursedesign.lancelot.sharingaccountbook.domain.ExpenditureRecord;
import coursedesign.lancelot.sharingaccountbook.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class WelcomeActivity extends AppCompatActivity {
    private final String  REMOTEIP = "http://118.24.187.42:8080";
    private final User loginUser = new User();
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    private LocalDatabaseAccess ldba = new LocalDatabaseAccess();
    private com.roger.match.library.MatchButton MatchButton;
    AesImp myAes = new AesImp();

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what)
            {
                case -1:
                    toast("loginFailed!");
                    loginUser.setIdentity("loginFailed");
                    break;
                case 1:
                    Intent intent = new Intent(WelcomeActivity.this, DisplayActivity.class);
                    intent.putExtra("loginUser",loginUser);
                    startActivity(intent);
                    finish();
            }
            return true;
        }
    });





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        checkPermission();
        SQLiteDatabase db = ldba.initDatabaseAccess();
        Log.d("TAG", "onCreate: "+myAes.encode("123456789"));


        MatchButton = (com.roger.match.library.MatchButton)findViewById(R.id.MatchButton);
        MatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("userdata",MODE_PRIVATE);
                String userName = preferences.getString("userName","无");
                String encodePwd = preferences.getString("encodePwd","无");

                if (userName.equals("无")){

                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Login(userName,encodePwd);
                }

            }
        });
    }

    private void Login(String userName, String encodePwd) {
        String url = REMOTEIP+"/accountBookServer/login?" +
                "uName="+userName+
                "&pwd="+encodePwd;
        Log.d("okhttp", "remoteLoginTestify: "+url);
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                toast("connection time out");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonStr = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                String state = jsonObject.getString("identity");
                Message msg = new Message();
                if (state.equals("loginFailed")){
                    msg.what = -1;
                    myHandler.sendMessage(msg);
                    return;
                }

                loginUser.setUserId(jsonObject.getInteger("userId"));
                loginUser.setUserName(jsonObject.getString("userName"));
                loginUser.setNickName(jsonObject.getString("nickName"));
                loginUser.setIdentity(jsonObject.getString("identity"));
                loginUser.setPhoneNumber(jsonObject.getString("phoneNumber"));
                msg.what = 1;
                myHandler.sendMessage(msg);
            }
        });


    }

    public void checkPermission(){
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                }
        );

        if (isAllGranted) {
            ldba.initDatabaseAccess();
            Log.d("wxt_Database", "checkPermission: databaseInited");
            return;
        }
        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }

    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                Log.d("wxt_database", "checkPermissionAllGranted: 权限检查");
                return false;
            }
        }
        return true;
    }

    public void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                ldba.initDatabaseAccess();
                Log.d("wxt_database", "onRequestPermissionsResult: databaseInited");
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
//                openAppDetails();
                toast("需要授权！");

            }
        }
    }
    public void dbTest(SQLiteDatabase db){
        //临时向数据库添加内容，最后要删除
        UserDao uDao = new UserDao(db);
        User uTemp0 = new User();
        uTemp0.setUserName("wxtAdmin");
        uTemp0.setNickName("lancelot");
        uTemp0.setPhoneNumber("12345678901");
        uTemp0.setIdentity("tester");
        uTemp0.setPicPath("null");
        uDao.insert(uTemp0);

        User uTemp1;

        String[] userColumns = {"user_id","user_name","nick_name","phone_number","identity","pic_path"};
        uTemp1 = uDao.query(userColumns,null,null,null,null,null,null).get(0);
        Log.d("queryUser", "dbTest: userTemp("+uTemp1.getUserId()+" "+uTemp1.getUserName()+" "+uTemp1.getNickName()+" "+uTemp1.getIdentity()+" "+uTemp1.getPhoneNumber()+" "+ uTemp1.getPicPath() +")");

        AccountbookDao abDao = new AccountbookDao(db);
        Accountbook abTemp0 = new Accountbook();
        abTemp0.setBookName("testBook");
        abTemp0.setCreateUserId(uTemp1.getUserId());
        abTemp0.setManagerUserId(uTemp1.getUserId());
        abDao.insert(abTemp0);

        Accountbook abTemp1;
        String[] abColumns = {"accountbook_id","book_name","create_user_id","manager_user_id"};
        abTemp1 = abDao.query(abColumns,null,null,null,null,null,null).get(0);
        Log.d("queryAccountbook", "dbTest: accountbookTemp("+abTemp1.getAccountbookId()+" "+abTemp1.getBookName()+" "+ abTemp1.getCreateUserId()+" "+ abTemp1.getManagerUserId() +") ");

        AccountbookMembershipDao abmDao = new AccountbookMembershipDao(db);
        AccountbookMembership abmTemp0 = new AccountbookMembership();
        abmTemp0.setAccountbookId(abTemp1.getAccountbookId());
        abmTemp0.setUserId(uTemp1.getUserId());
        abmTemp0.setIfManager("Yes");
        abmDao.insert(abmTemp0);

        AccountbookMembership abmTemp1;
        String[] abmColumns = {"membership_id","accountbook_id","user_id","if_manager"};
        abmTemp1 = abmDao.query(abmColumns,null,null,null,null,null,null).get(0);
        Log.d("queryABMembership", "dbTest: accountbookTemp("+ abmTemp1.getMembershipId()+" "+ abmTemp1.getAccountbookId()+" "+ abmTemp1.getUserId()+" "+ abmTemp1.getIfManager()+")");

        ExpenditureRecordDao erDao =new ExpenditureRecordDao(db);
        ExpenditureRecord rTemp0 = new ExpenditureRecord();
        rTemp0.setAccountbookId(abTemp1.getAccountbookId());
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Log.d("wxt_database_timetest", "dbTest: "+sdf.format(now));
        rTemp0.setAddDate(sdf.format(now));
        rTemp0.setOperatorId(uTemp1.getUserId());
        rTemp0.setReason("吃");
        rTemp0.setType("Out");
        rTemp0.setValue(25);
        erDao.insert(rTemp0);

        ExpenditureRecord rTemp1;
        String[] rColumns = {"record_id","accountbook_id","add_date","type","value","reason","comment","operator_id"};
        rTemp1 = erDao.query(rColumns,null,null,null,null,null,null).get(0);
        Log.d("queryExpenditureRecord", "dbTest: recordTemp("+ rTemp1.getRecordId()+" "+ rTemp1.getAccountbookId()+" "+ rTemp1.getAddDate()+" "+ rTemp1.getType()+" "+ rTemp1.getValue()+" "+ rTemp1.getReason()+" "+ rTemp1.getComment()+" "+ rTemp1.getOperatorId()+")");






    }
    public void okhttpTest(){
        //最终需要删除
        String url = "http://192.168.1.3:8080/accountBookServer/login?uName=mytEst423&pwd=password423";
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
                Log.d("okhttpTest_wxt", "onResponse: "+response.body().string());
            }
        });

    }


}
