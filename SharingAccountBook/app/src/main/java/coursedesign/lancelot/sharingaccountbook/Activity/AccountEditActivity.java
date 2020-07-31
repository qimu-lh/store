package coursedesign.lancelot.sharingaccountbook.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import coursedesign.lancelot.sharingaccountbook.R;
import coursedesign.lancelot.sharingaccountbook.Utils.CheckState;
import coursedesign.lancelot.sharingaccountbook.Utils.ReUtil;
import coursedesign.lancelot.sharingaccountbook.Utils.UserCodeGenerator;
import coursedesign.lancelot.sharingaccountbook.Utils.myTime;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookMembershipDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.LocalDatabaseAccess;
import coursedesign.lancelot.sharingaccountbook.dataAccess.UserDao;
import coursedesign.lancelot.sharingaccountbook.domain.Accountbook;
import coursedesign.lancelot.sharingaccountbook.domain.AccountbookMembership;
import coursedesign.lancelot.sharingaccountbook.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class AccountEditActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    private ListView ae_LV_acct;
    private String tag = "AccountEditActivity";
    private Myadapter myadapter;
    private Myadapter.ViewHolder vh;
//    private List<CheckState> list;


    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaBtn;
    private RapidFloatingActionHelper rfabHelper;

    private EditText dg_edt_Newname;
    private EditText dg_edt_tarMemberName;
    private TextView ae_T_Manager;
    private TextView ae_T_CreatP;
    private TextView ae_T_acName;
//    private String newName;


//    private ArrayList<String> Name = new ArrayList<String>();
//    private ArrayList<String> Time = new ArrayList<String>();


    private final String  REMOTEIP = "http://118.24.187.42:8080";

    /**
     * 本页面所有的数据操作都依托这几个指定对象进行,有的可能用不到吧
     */
    private User userToAdd;
    private User userToDel;

    private ArrayList<String> userId = new ArrayList<String>();
    private ArrayList<String> userName = new ArrayList<String>();
    private ArrayList<User> taegetUser;
    private String  accountbook_id;
    private String createrName;
    private Integer createrUserId;

    private Accountbook targetAccountbook;
    private Accountbook bookToHandover;
    private ArrayList<AccountbookMembership> taegetMemberShip;
    private String targetBookName;
    private AccountbookMembership newMembership;
    private AccountbookMembership oldMembership;
    private User loginUser;
    private Integer membershipIdFromServer;
    private Integer nowAccountbookId;
    private SQLiteDatabase db = new LocalDatabaseAccess().initDatabaseAccess();
    private Integer membershipAllowedToDel ;

    private Integer finishCode = 0;
    private int userNamePos;

    private Handler myNewMembershipHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            //以下操作从 ae_bt_Addnew.setOnClickListener搬运过来的
            switch (msg.what){
                case 1:
                    addNewMembershipToLocalData(newMembership);
                    loadAccountMenbership();
                    break;

                case -1:
                    toast("服务器：操作被拒绝");
                    break;
                case -2:
                    toast("服务器:此成员已存在");
                    break;
                case  -3:
                    toast("服务器:账本或用户名不存在");
                    break;
            }

            return true;
        }
    });

    Handler membershipHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case -4:
                    toast("此操作需要账本管理者权限");
                    break;
                case -3:
                    toast("操作被拒绝");
                    break;
                case -2:
                    toast("账本或用户名不存在");
                    break;
                case -1:
                    toast("账本管理员不能被移除");
                    break;
                case 1:
                    deleteMemberFromAccountInLocalData(membershipAllowedToDel);
                    userName.remove(userNamePos);
                    myadapter.notifyDataSetChanged();
                    toast("移除完成");
                    if (finishCode == 1){
                        AccountEditActivity.this.finish();
                    }


                    break;
            }

            return true;
        }
    });

    Handler handoverBookHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case -2:
                    toast("此操作需要账本管理者权限");
                    break;
                case -1:
                    toast("操作被拒绝");
                    break;
                case 0:
                    toast("只有账本成员才有资格拥有该账本");
                    break;
                case 1:
                    changeAccountbookOwnerInLocalData(newMembership,oldMembership,bookToHandover);
                    toast("递交完成");
                    break;
            }
            return true;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        myadapter = new Myadapter();
        loginUser = (User)getIntent().getSerializableExtra("loginUser");
        targetBookName = (String) getIntent().getSerializableExtra("bookName");
        loadAccountbook();
        loadAccountMenbership();
        intUI();
        ae_LV_acct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //这里是条目的点击事件
                Log.i(tag,"position::::::"+position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(AccountEditActivity.this);
                dialog.setTitle("警告");
                dialog.setIcon(R.drawable.warning);
                dialog.setMessage("是否要删除成员："+userName.get(position)+" ?");
                ae_LV_acct.setClickable(false);
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //这里写删除的代码
                        userNamePos = position;
                        delOneMemberOnServer(myadapter.getItem(position));
                        dialog.dismiss();
                    }
                });


                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


    }

    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        //Toast.makeText(this, "clicked label: " + position, Toast.LENGTH_SHORT).show();
        rfabHelper.toggleContent();
    }


    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        //position0,1,2 分别是“添加成员”，“转交账本”，“退出账本”
        //Toast.makeText(this, "clicked icon: " + position, Toast.LENGTH_SHORT).show();


        if(position == 0){
            final AlertDialog.Builder builder = new AlertDialog.Builder(AccountEditActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View layout  =inflater.inflate(R.layout.dialog_newmember,null);
            builder.setView(layout);
            builder.setTitle("添加新成员");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dg_edt_Newname = (EditText)layout.findViewById(R.id.dg_edt_Newname);
                    String newMame = dg_edt_Newname.getText().toString();
                    if (reCheck(newMame)) {
                        addNewMemberOnServer(newMame, targetAccountbook.getAccountbookId());
                    }
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else if (position == 1){
            final AlertDialog.Builder builder = new AlertDialog.Builder(AccountEditActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            final View layout  =inflater.inflate(R.layout.dialog_targetmember,null);
            builder.setView(layout);
            builder.setTitle("转交账本");

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dg_edt_tarMemberName = (EditText)layout.findViewById(R.id.dg_edt_tarMemberName);
                    String newName = dg_edt_tarMemberName.getText().toString();
                    if (reCheck(newName)) {
                        informServerOfOwnershipChange(newName, targetAccountbook.getAccountbookId());
                    }
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }else if (position == 2){
            finishCode = 1;
            delOneMemberOnServer(loginUser.getNickName());
        }



        rfabHelper.toggleContent();
    }

    //请求服务器数据库
    private void addNewMemberOnServer(String newUserNickName,Integer targetBookId){
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP+"/accountBookServer/addNewMembership?" +
                "nickName="+newUserNickName +
                "&operatorId="+loginUser.getUserId() +
                "&accountbookId="+targetBookId +
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
                String jsonSource = response.body().string();
                JSONObject sourceObj = JSON.parseObject(jsonSource);
                int code = sourceObj.getInteger("code");
                Message msg = new Message();
                if (code != 1){
                    msg.what = code;
                    myNewMembershipHandler.sendMessage(msg);
                    return;
                }
                JSONObject m = sourceObj.getJSONObject("newMembership");
                newMembership = new AccountbookMembership();
                newMembership.setAccountbookId(m.getInteger("accountbookId"));
                newMembership.setMembershipId(m.getInteger("membershipId"));
                newMembership.setUserId(m.getInteger("userId"));
                newMembership.setIfManager(m.getString("ifManager"));
                membershipIdFromServer = m.getInteger("membershipId");
                msg.what = code;
                myNewMembershipHandler.sendMessage(msg);

            }
        });
    }

    private void delOneMemberOnServer(String userName){
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP + "/accountBookServer/removeMembership?" +
                "nickName=" + userName +
                "&operatorId=" + loginUser.getUserId() +
                "&accountbookId=" + targetAccountbook.getAccountbookId() +
                "&userCode=" + userCode;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                ae_LV_acct.setClickable(true);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ae_LV_acct.setClickable(true);
                String jsonSource = response.body().string();
                JSONObject sourceObj = JSON.parseObject(jsonSource);
                Message msg = new Message();
                Integer code = sourceObj.getInteger("code");
                msg.what = code;
                if (code != 1){
                    membershipHandler.sendMessage(msg);
                    return;
                }
                JSONObject membership = sourceObj.getJSONObject("memebership");
                membershipAllowedToDel = membership.getInteger("membershipId");
                membershipHandler.sendMessage(msg);
            }
        });
    }

    private void informServerOfOwnershipChange(String targetUserName,Integer targetBookId){
        String[] columns = {"user_id","user_name","nick_name","phone_number","identity","pic_path"};
        User targetUser;
        ArrayList<User> users = new UserDao(LocalDatabaseAccess.initDatabaseAccess()).query(columns,"user_name = ?",new String[]{targetUserName},null,null,null,null);
        if (users.size() == 0){
            toast("target user must be a member of this book");
            return;
        }
        targetUser = users.get(0);
        String url = REMOTEIP+"/accountBookServer/handoverAccountbook?" +
                "operatorId="+loginUser.getUserId()+
                "&targetUserId="+targetUser.getUserId()+
                "&accountbookId="+targetBookId+
                "&userCode="+UserCodeGenerator.generateCode(loginUser);
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
                String jsonSource = response.body().string();
                JSONObject sourceObj = JSON.parseObject(jsonSource);
                int code = sourceObj.getInteger("code");
                Message msg = new Message();
                msg.what = code;
                if (code != 1){
                    handoverBookHandler.sendMessage(msg);
                    return;
                }
                bookToHandover = new Accountbook();
                newMembership = new AccountbookMembership();
                oldMembership = new AccountbookMembership();
                JSONObject bookObj = sourceObj.getJSONObject("newAccountbook");
                JSONObject newMem = sourceObj.getJSONObject("newMembership");
                JSONObject oldMem = sourceObj.getJSONObject("oldMembership");
                bookToHandover.setAccountbookId(bookObj.getInteger("accountbookId"));
                bookToHandover.setBookName(bookObj.getString("bookName"));
                bookToHandover.setManagerUserId(bookObj.getInteger("managerUserId"));
                bookToHandover.setCreateUserId(bookObj.getInteger("createUserId"));
                newMembership.setMembershipId(newMem.getInteger("membershipId"));
                oldMembership.setMembershipId(oldMem.getInteger("membershipId"));
                newMembership.setUserId(newMem.getInteger("userId"));
                oldMembership.setUserId(oldMem.getInteger("userId"));
                newMembership.setAccountbookId(newMem.getInteger("accountbookId"));
                oldMembership.setAccountbookId(oldMem.getInteger("accountbookId"));
                newMembership.setIfManager(newMem.getString("ifManager"));
                oldMembership.setIfManager(oldMem.getString("ifManager"));
                handoverBookHandler.sendMessage(msg);



            }
        });
    }
    //这些操作都只能由Manager发起，如果是普通成员，应该拒绝，这里要做身份验证
    private void addNewMembershipToLocalData(AccountbookMembership m){
        AccountbookMembershipDao abmDao = new AccountbookMembershipDao(db);
        abmDao.insert(m);
    }

    private void deleteMemberFromAccountInLocalData(Integer membershipId){
        /**
         * 删掉指定的membership记录就好
         */
        new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess()).delete(" membership_id = ? ",new String[]{membershipId+""});

    }

    private void changeAccountbookOwnerInLocalData(AccountbookMembership newMembership,AccountbookMembership oldMembership,Accountbook book){
        /**
         * 一次移交账本涉及到账本表一条记录和成员关系表中的两条记录，共计三条记录
         * 账本表中将目标账本的manager_user_id字段更新
         * 成员关系表中将旧的管理员的if_manager字段改成No，新管理员的if_manager字段改成Yes
         * 这里直接更新就行了，服务器会传回新数据的
         */
        AccountbookMembershipDao abd = new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
        AccountbookDao ad = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        abd.update(newMembership," membership_id = ? ",new String[]{newMembership.getMembershipId()+""});
        abd.update(oldMembership," membership_id = ? ",new String[]{oldMembership.getMembershipId()+""});
        ad.update(book," accountbook_id = ? ",new String[]{newMembership.getAccountbookId()+""});
    }

    public void loadAccountbook(){
        AccountbookDao abd = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        String [] columns = {"accountbook_id","book_name","create_user_id","manager_user_id"};
        String[] args = {targetBookName};
        targetAccountbook = abd.query(columns," book_name = ?",args,null,null,null,null).get(0);
    }

    public void loadAccountMenbership(){
        userName.clear();
        userId.clear();
        AccountbookMembershipDao accountbookMembershipDao =new AccountbookMembershipDao(LocalDatabaseAccess.initDatabaseAccess());
        accountbook_id = String.valueOf(targetAccountbook.getAccountbookId());
        String [] args = {accountbook_id};
        String [] columnM= {"membership_id","accountbook_id","user_id","if_manager"};
        taegetMemberShip = accountbookMembershipDao.query(columnM,"accountbook_id = ?",args,null,null,null,null);
        for (int i=0;i<taegetMemberShip.size();i++){
            userId.add(String.valueOf(taegetMemberShip.get(i).getUserId()));
            if (taegetMemberShip.get(i).getIfManager().equals("Yes")){
                createrUserId = taegetMemberShip.get(i).getUserId();
            }
        }

        UserDao userDao = new UserDao(LocalDatabaseAccess.initDatabaseAccess());
        String[] columsU = {"user_id","user_name","phone_number","nick_name","identity","pic_path"};
        String[] ids = new String[userId.size()];
        String select  = formRanges(userId);
        userId.toArray(ids);

        taegetUser = userDao.query(columsU,"user_id IN"+select,ids,null,null,null,null);
        for (int i = 0;i<taegetUser.size();i++){
            userName.add(taegetUser.get(i).getNickName());
            if (taegetUser.get(i).getUserId() == createrUserId){
                createrName = taegetUser.get(i).getNickName();
            }
        }
        myadapter.notifyDataSetChanged();
    }

    public void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }

    private String formRanges(ArrayList<String> Id){
        String idRanges = "(";
        for(String id:Id){
            idRanges = idRanges +""+ "?" +",";
        }
        idRanges = idRanges.substring(0,idRanges.length()-1);
        idRanges += ")";
        return idRanges;
    }

    private void intUI() {
        UserDao ud = new UserDao(LocalDatabaseAccess.initDatabaseAccess());
        String[] columns = {"user_id","user_name","nick_name","phone_number","identity","pic_path"};;
        ArrayList<User> utemp1 = new ArrayList<User>();
        ArrayList<User> utemp2 = new ArrayList<User>();
        utemp1 = ud.query(columns," user_id = ?",new String[]{targetAccountbook.getCreateUserId()+""},null,null,null,null);
        utemp2 = ud.query(columns," user_id = ?",new String[]{targetAccountbook.getManagerUserId()+""},null,null,null,null);

        ae_T_acName = (TextView)findViewById(R.id.ae_T_Acname);
        ae_T_acName.setText(targetBookName);

        ae_LV_acct = (ListView)findViewById(R.id.ae_LV_acct);
        ae_LV_acct.setAdapter(myadapter);
        // ae_bt_Addnew = (Button)findViewById(R.id.ae_bt_Addnew);
        ae_T_CreatP = (TextView)findViewById(R.id.ae_T_CreatP);
        ae_T_Manager = (TextView)findViewById(R.id.ae_T_Manager);
        if (utemp1.size() == 1){
            ae_T_CreatP.setText("创建人:" + utemp1.get(0).getNickName());
        }else{
            ae_T_CreatP.setText("创建人:" + "unknown");
        }
        if (utemp2.size() == 1){
            ae_T_Manager.setText("管理人:" + utemp2.get(0).getNickName());
        }else{
            ae_T_Manager.setText("管理人:" + "unknown");
        }
        rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.edt_rfal);
        rfaBtn = (RapidFloatingActionButton) findViewById(R.id.edt_rfab);

        rfaLayout.buildDrawingCache();

        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);

        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);

        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("添加成员")
                .setResId(R.drawable.add)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelColor(0xffd84315)
                .setWrapper(0)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("转交账本")
                .setResId(R.drawable.ic_swap_horiz )
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(0xff4e342e)
                .setWrapper(1)
        );
        items.add(new RFACLabelItem<Integer>()
                .setLabel("退出账本")
                .setResId(R.drawable.ic_exit_to_app )
                .setIconNormalColor(0xffFFB400)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(0xffFFB400)
                .setWrapper(1)
        );
        rfaContent
                .setItems(items)
                .setIconShadowRadius(5)
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(5)
        ;

        rfabHelper = new RapidFloatingActionHelper(
                AccountEditActivity.this,
                rfaLayout,
                rfaBtn,
                rfaContent
        ).build();
    }

    private boolean reCheck(String nameString){
        boolean rst = true;
        ReUtil reHandler = new ReUtil();
        //reHandler.useDefaultNamePattern();
        reHandler.useUserPattern("([\\u4e00-\\u9fa5\\w.,\\\\<>\\(\\)!~?\\/\"\':+-\\;=_])*");
        if (!reHandler.matched(nameString)){
            toast("名称中有非法字符！");
            return false;
        }
        return rst;
    }
    class Myadapter extends BaseAdapter{

        private CheckState checkState;

        @Override
        public int getCount() {
            return userName.size();
        }

        @Override
        public String getItem(int position) {
            return userName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View item;
            if (convertView == null){
                item = View.inflate(AccountEditActivity.this,R.layout.ae_lv_acct_item,null);
                vh = new ViewHolder();
                item.setTag(vh);
                vh.tv1 = (TextView)item.findViewById(R.id.name);
            }
            else {
                item = convertView;
                vh =  (ViewHolder)item.getTag();
            }
            vh.tv1.setText(userName.get(position));

            return item;

        }

        class ViewHolder{
            TextView tv1;
        }



    }
}
