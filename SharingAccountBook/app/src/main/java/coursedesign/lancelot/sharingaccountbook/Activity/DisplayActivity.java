package coursedesign.lancelot.sharingaccountbook.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import coursedesign.lancelot.sharingaccountbook.R;
import coursedesign.lancelot.sharingaccountbook.Utils.UserCodeGenerator;
import coursedesign.lancelot.sharingaccountbook.Utils.myTime;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookMembershipDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.ExpenditureRecordDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.LocalDatabaseAccess;
import coursedesign.lancelot.sharingaccountbook.dataAccess.UserDao;
import coursedesign.lancelot.sharingaccountbook.domain.Accountbook;
import coursedesign.lancelot.sharingaccountbook.domain.AccountbookMembership;
import coursedesign.lancelot.sharingaccountbook.domain.ExpenditureRecord;
import coursedesign.lancelot.sharingaccountbook.domain.User;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static java.security.AccessController.getContext;
public  class DisplayActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    private User loginUser;
    private Accountbook newAddedBook;
    private AccountbookMembership newAddedMembership;
    private String tag = "DisplayActivity";
    private ListView accountBook;
    private AccountBookAdapter ABAdapter;
    private TextView username;
    private EditText dia_dis_name;
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private CircleImageView image;

    //服务器地址
    private final String  REMOTEIP = "http://118.24.187.42:8080";
    //获取账本信息
    ArrayList<Accountbook> accountBookInfo;
    //账单信息
    ArrayList<String> accBookname = new ArrayList<>();
//    private Button da_BT_updata;
    private ArrayList<Integer> bookId = new ArrayList<Integer>();
    //用于表明现在操作到第几个位置了
    private Integer posPointer = 0;
    //下面几个变量用于存放从服务器同步来的数据，要存入本地数据库
    private HashMap<Integer,ExpenditureRecord> synRecordRst = new HashMap<Integer, ExpenditureRecord>();
    private HashMap<Integer,AccountbookMembership> synAcbookMembershipRst = new HashMap<Integer, AccountbookMembership>();
    private HashMap<Integer,Accountbook> synAcbookRst = new HashMap<Integer, Accountbook>();
    private HashMap<Integer,User> synUserRst = new HashMap<Integer, User>();

    private HashMap<Integer,User> localUser = new HashMap<Integer,User>();
    private HashMap<Integer,Accountbook> localBook = new HashMap<Integer, Accountbook>();
    private HashMap<Integer,ExpenditureRecord> localRecord = new HashMap<Integer, ExpenditureRecord>();
    private HashMap<Integer,AccountbookMembership> localMembership = new HashMap<Integer, AccountbookMembership>();

//    private ArrayList<ExpenditureRecord> localRecords = new ArrayList<ExpenditureRecord>();
    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //接收到Message后先通过bookId.length()来确认是不是本用户所有的相关Accountbook全部都操作完了
            //如果是就将同步来的数据全部写入数据库否则继续请求服务器
            switch(msg.what){
                case 0:
                    if (bookId.size() != posPointer){
                        //继续请求
                        synchronizationWithRemoteServer(loginUser,posPointer);
                    }else{
                        writeIntoLocalDb();
                        loadAccountbook();
                        getAccountBookInfo();
                        synAcbookMembershipRst.clear();
                        synUserRst.clear();
                        synRecordRst.clear();
                        synAcbookRst.clear();
                        accountBookInfo = getAccountBookInfo();
                        ABAdapter.notifyDataSetChanged();
                        toast("同步完成");
                    }
                    break;
                case 1:
                    toast("User同步失败，终止");
//                    da_BT_updata.setClickable(true);
                    break;
                case 2:
                    toast("accountbook同步失败，终止");
//                    da_BT_updata.setClickable(true);
                    break;
                case 3:
                    toast("records同步失败，终止");
//                    da_BT_updata.setClickable(true);
                    break;
            }
            return true;
        }
    });

    Handler newAccountbookHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    addNewbookToLocalDb(newAddedBook);
                    addNewMembershipToLocalDb(newAddedMembership);
                    newAddedBook = null;
                    newAddedMembership = null;
                    bookId = new ArrayList<Integer>(new TreeSet<Integer>(bookId));
                    accountBookInfo = getAccountBookInfo();
                    ABAdapter.notifyDataSetChanged();
                    toast("新账本添加成功");
                    break;
                case -1:
                    toast("add Failed");
                    break;
            }

            return true;
        }
    });

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("test", "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        loginUser = (User)getIntent().getSerializableExtra("loginUser");
        Log.i(tag,"usernamme:::::"+loginUser.getUserName());
        askAccountbookListOfNowUser();


    }

    protected  void onResume(){
        super.onResume();
        loadAccountbook();
        if (bookId.size() != 0) {
            accountBookInfo = getAccountBookInfo();
        }else {
            accountBookInfo = new ArrayList<Accountbook>();
        }
        initUi();
        //获取姓名在头像下方显示
        username.setText(loginUser.getNickName());
        accountBook = (ListView) findViewById(R.id.accountBook);
        ABAdapter = new AccountBookAdapter(getApplicationContext(),accBookname);
        accountBook.setAdapter(ABAdapter);
        posPointer = 0;
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                builder.setTitle("警告");
                builder.setIcon(R.drawable.warning);
                builder.setMessage("是否退出当前账户？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences("userdata",MODE_PRIVATE).edit();
                        editor.clear();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();



            }
        });
        synchronizationWithRemoteServer(loginUser,posPointer);
    }




    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        //Toast.makeText(this, "clicked label: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }


    public void onRFACItemIconClick(int position, RFACLabelItem item) {

        //position0,1 分别是“创建新账本”，“同步数据”
        if(position==0){
            final AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View layout  =inflater.inflate(R.layout.dialog_disnewacct,null);
            dia_dis_name = (EditText)layout.findViewById(R.id.dia_dis_name);
            builder.setView(layout);
            builder.setTitle("添加新账本");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = dia_dis_name.getText().toString();
                    myTime t  = new myTime();
                    String Time = t.Mgettime();
                   //这里写添加新账本（账本名和时间）的代码逻辑@陈天肃
                    addNewAccountbookOnServer(loginUser,dia_dis_name.getText().toString());
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        if (position==1){
            //accountbookId应该由查询本地数据库得到所有的AccountbookId形成集合并全部向服务器查询
            //  da_BT_updata.setClickable(false);
//            initBookids();//实际上就是loadA从countbook的作用
            loadAccountbook();
            posPointer = 0;
            toast("请耐心等待同步完成");
            synchronizationWithRemoteServer(loginUser, posPointer);
        }
        rfabHelper.toggleContent();
    }


    //从本地数据库加载账本信息
    public void loadAccountbook(){
        //@陈天肃,将所有本用户有关的账本Id加载到bookId中
        bookId.clear();
        String [] userid ={ loginUser.getUserId()+""};
        SQLiteDatabase users;
        AccountbookMembershipDao accountbookMembershipDao = new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
        String [] columns = {"accountbook_id","membership_id","user_id","if_manager"};
        ArrayList<AccountbookMembership> accountbookMemberships= accountbookMembershipDao.query(columns,"user_id = ?",userid,null,null,null,null);
        for(AccountbookMembership abm:accountbookMemberships){
            bookId.add(abm.getAccountbookId());
        }
    }

    //将本地的数据库查询出来显示
    public  ArrayList<Accountbook>  getAccountBookInfo(){
        accBookname.clear();
        ArrayList<Accountbook> Info ;
        String [] userid ={loginUser.getUserId()+""};
        SQLiteDatabase users;
        AccountbookDao accountbookDao = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        String [] columns = {"accountbook_id","book_name","create_user_id","manager_user_id"};
        String idRanges = "(";
        for(Integer id:bookId){
            idRanges = idRanges +""+ "?" +",";
        }
        idRanges = idRanges.substring(0,idRanges.length()-1);
        idRanges += ")";
        String [] ids = new String[bookId.size()];
        for(int i= 0;i < ids.length;i++){
            ids[i] = bookId.get(i) + "";
        }
        Info= accountbookDao.query(columns,"accountbook_id IN "+idRanges,ids,null,null,null,null);
        for (Accountbook accountbooks: Info) {
            if (accBookname.indexOf(accountbooks.getBookName()) == -1)
                accBookname.add(accountbooks.getBookName());
        }
        return Info;
    }

    public void askAccountbookListOfNowUser(){
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP +"/accountBookServer/listAccountbookOfOneUser?" +
                "operatorId="+loginUser.getUserId()+
                "&userCode="+userCode;
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
                String jsonStr = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                JSONArray booksArray = jsonObject.getJSONArray("detail");
                JSONArray membersArray = jsonObject.getJSONArray("membership");
                Message msg = new Message();
                AccountbookDao abd = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
                AccountbookMembershipDao abm = new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
                Accountbook tempBook;
                AccountbookMembership tempMembership;
                if (booksArray.size() != 0){
                    for (Object o:booksArray){
                        tempBook = new Accountbook();
                        JSONObject obj = (JSONObject)o;
                        tempBook.setAccountbookId(obj.getInteger("accountbookId"));
                        tempBook.setCreateUserId(obj.getInteger("createUserId"));
                        tempBook.setManagerUserId(obj.getInteger("managerUserId"));
                        tempBook.setBookName(obj.getString("bookName"));
                        if (abd.insert(tempBook) == -1){
                            abd.update(tempBook,"accountbook_id = ?",new String []{tempBook.getAccountbookId()+""});
                        }
                    }

                }
                if (membersArray.size() != 0){
                    for (Object o:membersArray){
                        tempMembership = new AccountbookMembership();
                        JSONObject obj = (JSONObject)o;
                        tempMembership.setAccountbookId(obj.getInteger("accountbookId"));
                        tempMembership.setMembershipId(obj.getInteger("membershipId"));
                        tempMembership.setUserId(obj.getInteger("userId"));
                        tempMembership.setIfManager(obj.getString("ifManager"));
                        if ( abm.insert(tempMembership) == -1){
                            abm.update(tempMembership," membership_id = ?",new String[]{tempMembership.getMembershipId()+""});
                        }
                    }
                }

            }
        });

    }

    public void synchronizationWithRemoteServer(User u,int pos){
        if (bookId.size() == 0){
            toast("没有账本信息可供同步，请先加入账本");
            return;
        }
        String userCode = UserCodeGenerator.generateCode(u);
        Log.d("synchronization", "userCode "+userCode);
        String url = REMOTEIP+"/accountBookServer/sychronization?" +
                "userId="+u.getUserId()+
                "&operatorId="+u.getUserId()+
                "&userCode="+userCode+
                "&accountbookId="+bookId.get(pos);
//        bookId.remove(pos);
        posPointer ++ ;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            Message msg= new Message();
            @Override
            public void onFailure(Call call, IOException e) {
                msg.what = -1;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //在这里组装数据，组装完成后向主线程发送Message
                String jsonSource = response.body().string();
                JSONObject sourceObj = JSON.parseObject(jsonSource);
                JSONObject allRecords = sourceObj.getJSONObject("allRecords");
                JSONObject allAccountbooks = sourceObj.getJSONObject("allAccountbooks");
                JSONObject allUsers = sourceObj.getJSONObject("allUsers");
                jsonToUser(allUsers);
                jsonToAccountbook(allAccountbooks);
                jsonToRecords(allRecords);
                msg.what = 0;
                myHandler.sendMessage(msg);

            }
        });
    }

    public void addNewAccountbookOnServer(User loginUser,String newAccountbookName){
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP+"/accountBookServer/addNewAccountbook?" +
                "userId="+loginUser.getUserId()+
                "&inputUserCode="+userCode+
                "&bookName="+newAccountbookName;
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
                String jsonStr = response.body().string();
                JSONObject jsonObject = JSONObject.parseObject(jsonStr);
                Message msg = new Message();
                Integer code = jsonObject.getInteger("code");
                if (code != 1){
                    newAccountbookHandler.sendMessage(msg);
                }
                newAddedBook = new Accountbook();
                newAddedMembership =new AccountbookMembership();
                JSONObject newBook = jsonObject.getJSONObject("newBook");
                JSONObject newMembership = jsonObject.getJSONObject("newMembership");
                newAddedBook.setManagerUserId(newBook.getInteger("managerUserId"));
                newAddedBook.setAccountbookId(newBook.getInteger("accountbookId"));
                newAddedBook.setCreateUserId(newBook.getInteger("createUserId"));
                newAddedBook.setBookName(newBook.getString("bookName"));
                bookId.add(newAddedBook.getAccountbookId());
                newAddedMembership.setUserId(newMembership.getInteger("userId"));
                newAddedMembership.setMembershipId(newMembership.getInteger("membershipId"));
                newAddedMembership.setAccountbookId(newMembership.getInteger("accountbookId"));
                newAddedMembership.setIfManager(newMembership.getString("ifManager"));
                msg.what = code;
                newAccountbookHandler.sendMessage(msg);

            }
        });
    }

    private void loadLocal(){
        localUser.clear();
        localBook.clear();
        localRecord.clear();
        localMembership.clear();
        UserDao uDao = new UserDao(LocalDatabaseAccess.initDatabaseAccess());
        AccountbookDao bDao = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        ExpenditureRecordDao rDao = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
        AccountbookMembershipDao mDao = new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
        String[] userColumns = {"user_id","user_name","phone_number","nick_name","identity","pic_path"};
        String[] accountbookCoulumns = {"accountbook_id","book_name","create_user_id","manager_user_id"};
        String[] recordColumns = {"record_id","accountbook_id","add_date","type","value","reason","comment","operator_id"};
        String[] membershipColumns = {"membership_id","accountbook_id","user_id","if_manager"};
        for (User u:uDao.query(userColumns,null,null,null,null,null,null)){
            localUser.put(u.getUserId(),u);
        }
        for (Accountbook b:bDao.query(accountbookCoulumns,null,null,null,null,null,null)){
            localBook.put(b.getAccountbookId(),b);
        }
        for (AccountbookMembership m:mDao.query(membershipColumns,null,null,null,null,null,null)){
            localMembership.put(m.getMembershipId(),m);
        }
        for (ExpenditureRecord r:rDao.query(recordColumns,null,null,null,null,null,null)){
            localRecord.put(r.getRecordId(),r);
        }
    }

    private String formRanges(Set<Integer> keySet){
        String idRanges = "(";
        for(Integer id:keySet){
            idRanges = idRanges +""+ "?" +",";
        }
        idRanges = idRanges.substring(0,idRanges.length()-1);
        idRanges += ")";
        return idRanges;
    }

    private void writeIntoLocalDb(){
        //@陈天肃，用于将所有同步来的数据写入本地数据库
        loadLocal();
        ExpenditureRecord record;
        User user;
        Accountbook book;
        AccountbookMembership membership;
        for (Integer key : synRecordRst.keySet()) {
            record = synRecordRst.get(key);
            ExpenditureRecordDao expenditureRecord = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
            if(expenditureRecord.insert(record) == -1){
                expenditureRecord.update(record," record_id = ? ",new String[]{""+record.getRecordId()});
            }
            localRecord.remove(record.getRecordId());
        }
        for (Integer key : synAcbookMembershipRst.keySet()) {
            membership = synAcbookMembershipRst.get(key);
            AccountbookMembershipDao accountbookMembershipDao = new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
            if (accountbookMembershipDao.insert(membership) == -1){
                accountbookMembershipDao.update(membership," membership_id = ? ",new String[]{""+membership.getMembershipId()});
            }
            localMembership.remove(membership.getMembershipId());
        }
        for (Integer key : synAcbookRst.keySet()) {
            book = synAcbookRst.get(key);
            AccountbookDao accountbookDao = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
            if (accountbookDao.insert(book) == -1){
                accountbookDao.update(book," accountbook_id = ? ",new String[]{""+book.getAccountbookId()});
            }
            localBook.remove(book.getAccountbookId());
        }
        for (Integer key : synUserRst.keySet()) {
            user = synUserRst.get(key);
            UserDao userDao = new UserDao(LocalDatabaseAccess.initDatabaseAccess());
           if ( userDao.insert(user) == -1){
               userDao.update(user," user_id = ? ",new String[]{""+user.getUserId()});
           }
           localUser.remove(user.getUserId());
        }

//        User记录始终保持。
//        if (localUser.size() != 0){
//            int i = 0;
//            String ranges = formRanges(localUser.keySet());
//            String [] userIds = new String[localUser.size()];
//            for (Integer index:localUser.keySet()){
//                userIds[i] = index+"";
//                i ++;
//            }
//            new UserDao(LocalDatabaseAccess.initDatabaseAccess()).delete(" user_id IN "+ranges,userIds);
//        }

        if (localBook.size() != 0){
            int i = 0;
            String ranges = formRanges(localBook.keySet());
            String [] bookIds = new String[localBook.size()];
            for (Integer index:localBook.keySet()){
                bookIds[i] = index+"";
                i ++;
            }
            new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess()).delete(" accountbook_id IN "+ranges,bookIds);
        }

        if (localRecord.size() != 0){
            int i = 0;
            String ranges = formRanges(localRecord.keySet());
            String [] recordIds = new String[localRecord.size()];
            for (Integer index:localRecord.keySet()){
                recordIds[i] = index+"";
                i ++;
            }
            new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess()).delete(" record_id IN "+ranges,recordIds);
        }

        if (localMembership.size() != 0){
            int i = 0;
            String ranges = formRanges(localMembership.keySet());
            String [] membershipIds = new String[localMembership.size()];
            for (Integer index:localMembership.keySet()){
                membershipIds[i] = index+"";
                i ++;
            }
            new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess()).delete(" membership_id IN "+ranges,membershipIds);
        }


    }

    private void addNewbookToLocalDb(Accountbook newBook){
        AccountbookDao accountbookDao = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        accountbookDao.insert(newBook);
    }

    private void addNewMembershipToLocalDb(AccountbookMembership abm){
        AccountbookMembershipDao abmd = new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
        abmd.insert(abm);
    }

    private void jsonToUser(JSONObject allUsers){
        Message msg = new Message();
        int code = allUsers.getInteger("code");
        if (code != 1){
            msg.arg1= 1;//代表User问题
            msg.what = code;
            myHandler.sendMessage(msg);
            return;
        }
        JSONArray users = allUsers.getJSONArray("users");
        JSONArray memberships = allUsers.getJSONArray("membership");
        User tempUser;
        AccountbookMembership tempAbm;
        for(Object u:users){
            tempUser = new User();
            JSONObject jsonUser = (JSONObject) u;
            tempUser.setUserId(jsonUser.getInteger("userId"));
            tempUser.setUserName(jsonUser.getString("userName"));
            tempUser.setNickName(jsonUser.getString("nickName"));
            tempUser.setIdentity(jsonUser.getString("identity"));
            tempUser.setPhoneNumber(jsonUser.getString("phoneNumber"));
            tempUser.setPicPath("null");
            synUserRst.put(tempUser.getUserId(),tempUser);
        }
        for(Object m:memberships){
            tempAbm = new AccountbookMembership();
            JSONObject jsonAbm = (JSONObject)m;
            tempAbm.setUserId(jsonAbm.getInteger("userId"));
            tempAbm.setMembershipId(jsonAbm.getInteger("membershipId"));
            tempAbm.setAccountbookId(jsonAbm.getInteger("accountbookId"));
            tempAbm.setIfManager(jsonAbm.getString("ifManager"));
            synAcbookMembershipRst.put(tempAbm.getMembershipId(),tempAbm);

        }

    }

    private void jsonToAccountbook(JSONObject allAccountbooks){
        Message msg = new Message();
        int code = allAccountbooks.getInteger("code");
        if (code != 1){
            msg.arg1= 2;//代表Accountbook问题
            msg.what = code;
            myHandler.sendMessage(msg);
            return;
        }
        JSONArray books = allAccountbooks.getJSONArray("detail");
        Accountbook book;
        for(Object b :books){
            book = new Accountbook();
            JSONObject jsonBook = (JSONObject)b;
            book.setAccountbookId(jsonBook.getInteger("accountbookId"));
            book.setBookName(jsonBook.getString("bookName"));
            book.setCreateUserId(jsonBook.getInteger("createUserId"));
            book.setManagerUserId(jsonBook.getInteger("managerUserId"));
            synAcbookRst.put(book.getAccountbookId(),book);
        }
    }

    private void jsonToRecords(JSONObject allRecords){
        Message msg = new Message();
        int code = allRecords.getInteger("code");
        if (code != 1){
            msg.arg1= 3;//代表Records问题
            msg.what = code;
            myHandler.sendMessage(msg);
            return;
        }


        JSONArray records = allRecords.getJSONArray("records");
        ExpenditureRecord temp;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        for (Object r : records){
            temp = new ExpenditureRecord();
            JSONObject jsonRecord = (JSONObject)r;
            temp.setRecordId(jsonRecord.getInteger("recordId"));
            temp.setAccountbookId(jsonRecord.getInteger("accountbookId"));
            temp.setOperatorId(jsonRecord.getInteger("operatorId"));
            temp.setType(jsonRecord.getString("type"));
            temp.setValue(jsonRecord.getFloat("value"));
            temp.setComment(jsonRecord.getString("comment"));
            temp.setReason(jsonRecord.getString("reason"));
            date.setTime(jsonRecord.getLong("addDate"));
            temp.setAddDate(sdf.format(date));
            synRecordRst.put(temp.getRecordId(),temp);
        }


    }

    public void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
//    public void loadLocalRecords(){
//        //将本地的记录加载到变量localRecords
//    }


    public void initUi(){
        accountBook = (ListView) findViewById(R.id.accountBook);
        rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.dis_rfal);
        rfaBtn = (RapidFloatingActionButton) findViewById(R.id.dis_rfab);
        image =  (CircleImageView)findViewById(R.id.image);
        username= (TextView)findViewById(R.id.username);
        rfaLayout.buildDrawingCache();

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);

        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);

        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("创建新账本")
                .setResId(R.drawable.add)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelColor(0xffd84315)
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("同步数据")
                .setResId(R.drawable.tongbu)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(0xff056f00)
                .setWrapper(2)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;

        rfabHelper = new RapidFloatingActionHelper(
                DisplayActivity.this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    class AccountBookAdapter extends BaseAdapter {
        private ArrayList<String> UName;
        private Context mContext;
        //获取用户名和创建时间
        public AccountBookAdapter(Context mContext, ArrayList<String> UName  ) {
            super();
            this.mContext = mContext;
            this.UName= UName;
        }

        class ViewHolder{
            TextView UserName;
            Button button1;
            Button button2;
        }
        @Override
        public int getCount()
        {
            return UName.size();
        }
        @Override
        public Object getItem(int position) {
            return null;
        } @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            //如果数据为空
            LayoutInflater inflater = LayoutInflater.from(mContext);
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.accountbookitem, null);
                holder = new ViewHolder();
                holder.button1 = (Button) convertView.findViewById(R.id.button1);
                holder.button2 = (Button) convertView.findViewById(R.id.button2);
                holder.UserName = (TextView) convertView.findViewById(R.id.UName);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click","button");
                    DisplayActivity displayActivity = new DisplayActivity();
                    Intent intent = new Intent();
                    intent.setClass(mContext, AccountEditActivity.class);
                    intent.putExtra("loginUser",loginUser);
                    intent.putExtra("bookName",accBookname.get(position));

                    mContext.startActivity(intent);
                }
            });
            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DisplayActivity displayActivity = new DisplayActivity();
                    Intent intent = new Intent();
                    intent.setClass(mContext, AccountNoteActivity.class);
                    intent.putExtra("loginUser",loginUser);
                    intent.putExtra("bookName",accBookname.get(position));
                    mContext.startActivity(intent);
                }
            });
            holder.UserName.setText(UName.get(position));
            return convertView;


        }
    }

}

