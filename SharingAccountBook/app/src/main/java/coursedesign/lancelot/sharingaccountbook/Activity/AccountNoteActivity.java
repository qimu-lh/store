package coursedesign.lancelot.sharingaccountbook.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.melnykov.fab.FloatingActionButton;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import coursedesign.lancelot.sharingaccountbook.R;
import coursedesign.lancelot.sharingaccountbook.Utils.ReUtil;
import coursedesign.lancelot.sharingaccountbook.Utils.UserCodeGenerator;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.ExpenditureRecordDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.LocalDatabaseAccess;
import coursedesign.lancelot.sharingaccountbook.dataAccess.UserDao;
import coursedesign.lancelot.sharingaccountbook.domain.Accountbook;
import coursedesign.lancelot.sharingaccountbook.domain.ExpenditureRecord;
import coursedesign.lancelot.sharingaccountbook.domain.User;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AccountNoteActivity extends AppCompatActivity {
    private Float IN,OUT;
    private String tag = "AccountNoteActivity";
    private Spinner usage_Sp_ID;
    private RadioButton Rbt1,Rbt2;
    private Calendar calendar ;
    private android.widget.RadioGroup RadioGroup;
    private String INorOUT,sp;
    private EditText moneyui;
    private EditText psui;
    private String newTime;
    private ExpenditureRecord newRecord,editRecord,delRecord;
    private TextView an_T_name,an_T_income,an_T_out,an_T_last;
    private List<String> LrecordId = new ArrayList<String>();
    private List<String> thing = new ArrayList<String>();
    private List<Float> money = new ArrayList<Float>();
    private List<String> IO = new ArrayList<String>();
    private List<String> ps = new ArrayList<String>();
    private List<String> time = new ArrayList<String>();
    private List<String> name = new ArrayList<String>();
    private Myadapter.ViewHolder vh;
    private Button an_BT_ima;
    private FloatingActionButton an_bt_Addnew;
    private ListView an_LV_acct;
    private Myadapter myadapter;
    private User loginUser;

    //目前操作的账本
    private Accountbook targetAccountbook;
    private String targetBookName;
    //目前登陆的用户
    private User nowUser;

    private ArrayList<ExpenditureRecord> targetRecord;
    private final String  REMOTEIP = "http://118.24.187.42:8080";

    private Handler myHandler = new Handler(new Handler.Callback(){
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    toast("添加成功");
                    addNewRecordToLocal(newRecord);
                    loadExpenditurRecord();
                    refreshINandOUT();
                    break;
                case 2:
                    toast("成功");
                    updateEidtRecordToLocal(editRecord);
                    loadExpenditurRecord();
                    refreshINandOUT();
                    break;
                case 3:
                    toast("删除成功");
                    deleteRecordToLocal();
                    loadExpenditurRecord();
                    refreshINandOUT();
                    break;
                case -1:
                    toast("失败");
                    break;
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_note);

        loginUser = (User)getIntent().getSerializableExtra("loginUser");
        targetBookName = (String) getIntent().getSerializableExtra("bookName");
        loadAccountbook();
        Log.i(tag,"usernamme:::::"+loginUser.getUserName());
        iniUI();
        myadapter = new Myadapter();
        loadExpenditurRecord();
        an_LV_acct.setAdapter(myadapter);
        an_LV_acct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //这里是条目的点击事件
                editDialogshow(position,thing.get(position),LrecordId.get(position));
            }
        });


        //查看统计图
        an_BT_ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AccountImaActivity.class);
                intent.putExtra("loginUser",loginUser);
                intent.putExtra("targetBookId",targetAccountbook.getAccountbookId());
                startActivity(intent);
            }
        });


        an_bt_Addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "添加新账目";
                addDialogshow(title);

            }
        });
        refreshINandOUT();



    }


    private void addDialogshow(String title) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountNoteActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout  =inflater.inflate(R.layout.dialog_newaccount,null);
        usage_Sp_ID = (Spinner)layout.findViewById(R.id.usages_Sp_ID);
        final List<String> data_list = new ArrayList<String>();
        data_list.add("请选择..........");
        data_list.add("吃喝");
        data_list.add("交通");
        data_list.add("服饰鞋包");
        data_list.add("化妆护肤");
        data_list.add("日用品");
        data_list.add("话费网费");
        data_list.add("房租");
        data_list.add("学习");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(getApplicationContext()
                ,android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usage_Sp_ID.setAdapter(arr_adapter);

        usage_Sp_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp = data_list.get(position);
                Log.i(tag,"spspspspspsspspsp:"+sp);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sp = data_list.get(0);
            }
        });
        moneyui = (EditText)layout.findViewById(R.id.money);
        psui = (EditText)layout.findViewById(R.id.ps);
        Button changetime = (Button)layout.findViewById(R.id.changetime);
        Rbt1 = (RadioButton)layout.findViewById(R.id.Rbt1);
        Rbt2 = (RadioButton)layout.findViewById(R.id.Rbt2);
        RadioGroup = (RadioGroup)layout.findViewById(R.id.RadioGroup);
        final TextView showtime = (TextView)layout.findViewById(R.id.showtime);
        RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (Rbt1.getId() == checkedId) {
                    INorOUT = "OUT";
                    Log.i(tag,"INorOUT:"+INorOUT);
                } else if (Rbt2.getId() == checkedId) {
                    INorOUT = "IN";
                    Log.i(tag,"INorOUT:"+INorOUT);
                }

            }
        });
        calendar = Calendar.getInstance();
        //设置默认时间为现在的时间

        Calendar defaultdate = Calendar.getInstance();
        String date1 = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(new java.util.Date(defaultdate.getTimeInMillis()));
        TextView defaulttime = (TextView)layout.findViewById(R.id.showtime);
        defaulttime.setText(date1);


        builder.setView(layout);
        builder.setTitle(title);
        changetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AccountNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);

                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog dialog = new TimePickerDialog(AccountNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(new java.util.Date(calendar.getTimeInMillis()));
                                TextView showtime = (TextView)layout.findViewById(R.id.showtime);
                                showtime.setText(date);
                            }
                        },0,0,true);
                        dialog.show();

                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (reCheck(moneyui.getText().toString(),psui.getText().toString())){
                    if (moneyui.getText().toString().equals("")||Float.parseFloat(moneyui.getText().toString())==0){
                        toast("金额不能为空且不能为零，操作终止");
                        return ;
                    }
                    addNewRecordOnServer(sp,Float.parseFloat(moneyui.getText().toString()),INorOUT,psui.getText().toString(),calendar.getTimeInMillis());
                }
                dialog.dismiss();

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

    private void editDialogshow(int locat, String thg, final String recordId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountNoteActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout  =inflater.inflate(R.layout.dialog_newaccount,null);
        usage_Sp_ID = (Spinner)layout.findViewById(R.id.usages_Sp_ID);
        final List<String> data_list = new ArrayList<String>();
        data_list.add("请选择..........");
        data_list.add("吃喝");
        data_list.add("交通");
        data_list.add("服饰鞋包");
        data_list.add("化妆护肤");
        data_list.add("日用品");
        data_list.add("话费网费");
        data_list.add("房租");
        data_list.add("学习");
        ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(getApplicationContext()
                ,android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        usage_Sp_ID.setAdapter(arr_adapter);
        moneyui = (EditText)layout.findViewById(R.id.money);
        psui = (EditText)layout.findViewById(R.id.ps);
        Rbt1 = (RadioButton)layout.findViewById(R.id.Rbt1);
        Rbt2 = (RadioButton)layout.findViewById(R.id.Rbt2);
        RadioGroup = (RadioGroup)layout.findViewById(R.id.RadioGroup);
        Button changetime = (Button)layout.findViewById(R.id.changetime);

        Calendar defaultdate = Calendar.getInstance();
        String date1 = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(new java.util.Date(defaultdate.getTimeInMillis()));
        TextView defaulttime = (TextView)layout.findViewById(R.id.showtime);
        defaulttime.setText(date1);


        calendar = Calendar.getInstance();
        //设置原有值
        if(IO.get(locat).equals("OUT")){
            INorOUT="OUT";
            Rbt1.setChecked(true);
            Rbt2.setChecked(false);
        }else {
            INorOUT="IN";
            Rbt2.setChecked(true);
            Rbt1.setChecked(false);
        }
        moneyui.setText(String.valueOf(money.get(locat)));
        psui.setText(ps.get(locat));
        int j=0;
        for (int i = 0;i<data_list.size();i++){
            if (data_list.get(i).equals(thg)){
                j=i;break;
            }
        }

        usage_Sp_ID.setSelection(j);

        usage_Sp_ID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp = data_list.get(position);
                Log.i(tag,"spspspspspsspspsp:"+sp);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sp = data_list.get(0);
            }
        });
        RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (Rbt1.getId() == checkedId) {
                    INorOUT = "OUT";
                    Log.i(tag,"INorOUT:"+INorOUT);
                } else if (Rbt2.getId() == checkedId) {
                    INorOUT = "IN";
                    Log.i(tag,"INorOUT:"+INorOUT);
                }

            }
        });
        builder.setView(layout);
        builder.setTitle("查看/编辑");
        changetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AccountNoteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                        TimePickerDialog dialog = new TimePickerDialog(AccountNoteActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(new java.util.Date(calendar.getTimeInMillis()));
                                TextView showtime = (TextView)layout.findViewById(R.id.showtime);
                                showtime.setText(date);


                            }
                        },0,0,true);
                        dialog.show();

                    }
                    },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (moneyui.getText().toString().equals("0")){
                    toast("金额不能为0");
                }else {
                    if (reCheck(moneyui.getText().toString(),psui.getText().toString())){
                        if (moneyui.getText().toString().equals("")||Float.parseFloat(moneyui.getText().toString())==0){
                           toast("金额不能为空且不能为零，操作终止");
                            return ;
                        }
                        updateRecordOnServer(sp, Float.parseFloat(moneyui.getText().toString()),
                                INorOUT, psui.getText().toString(), recordId, calendar.getTimeInMillis() );
                    }
                    dialog.dismiss();

                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRecordOnServer(recordId);
                dialog.dismiss();

            }
        });
        builder.show();

    }

    private void deleteRecordOnServer(String recordId) {
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP + "/accountBookServer/removeRecord?"+
                "recordId="+Integer.valueOf(recordId)+
                "&operatorId="+loginUser.getUserId()+
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
                if (code==-1) {
                    msg.what = code;
                    myHandler.sendMessage(msg);
                }
                delRecord = new ExpenditureRecord();
                delRecord.setRecordId(sourceObj.getInteger("recordId"));

                msg.what=3;
                myHandler.sendMessage(msg);
            }
        });

    }

    private void deleteRecordToLocal() {
        ExpenditureRecordDao expenditureRecordDao = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
        expenditureRecordDao.delete(" record_id=? ",new String[]{""+String.valueOf(delRecord.getRecordId())});
    }

    private void updateRecordOnServer(String reason,float value,String type,String comment,String recordId,long time) {
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP + "/accountBookServer/updateRecord?"+
                "recordId="+Integer.valueOf(recordId)+
                "&operatorId="+loginUser.getUserId()+
                "&userCode="+userCode+
                "&type="+type+
                "&value="+value+
                "&reason="+reason+
                "&comment="+comment+
                "&time="+time;
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
                if (code==-1){
                    msg.what = code;
                    myHandler.sendMessage(msg);
                }
                JSONObject m = sourceObj.getJSONObject("record");
                editRecord = new ExpenditureRecord();
                editRecord.setRecordId(m.getInteger("recordId"));
                editRecord.setAccountbookId(m.getInteger("accountbookId"));
                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy/MM/dd/HH:mm");
                editRecord.setAddDate(formatter.format(m.getDate("addDate")));
                editRecord.setComment(m.getString("comment"));
                editRecord.setOperatorId(m.getInteger("operatorId"));
                editRecord.setReason(m.getString("reason"));
                editRecord.setType(m.getString("type"));
                editRecord.setValue(m.getFloat("value"));
                msg.what=2;
                myHandler.sendMessage(msg);
            }
        });
    }

    private void updateEidtRecordToLocal(ExpenditureRecord editRecord) {
        ExpenditureRecordDao expenditureRecordDao = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
        expenditureRecordDao.update(editRecord," record_id=? ",new String[]{""+String.valueOf(editRecord.getRecordId())});
    }

    public void addNewRecordOnServer(String reason,float value,String type,String comment,long time){
        String userCode = UserCodeGenerator.generateCode(loginUser);
        String url = REMOTEIP+"/accountBookServer/addRecord?" +
                "accountbookId="+targetAccountbook.getAccountbookId()+
                "&operatorId="+loginUser.getUserId()+
                "&userCode="+userCode+
                "&type="+type+
                "&value="+value+
                "&reason="+reason+
                "&comment="+comment+
                "&time="+time;
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
                if (code==-1){
                    msg.what = code;
                    myHandler.sendMessage(msg);
                }
                JSONObject m = sourceObj.getJSONObject("record");
                newRecord = new ExpenditureRecord();
                newRecord.setRecordId(m.getInteger("recordId"));
                newRecord.setAccountbookId(m.getInteger("accountbookId"));
                SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm");
                newRecord.setAddDate(formatter.format(m.getDate("addDate")));
                newRecord.setComment(m.getString("comment"));
                newRecord.setOperatorId(m.getInteger("operatorId"));
                newRecord.setReason(m.getString("reason"));
                newRecord.setType(m.getString("type"));
                newRecord.setValue(m.getFloat("value"));
                msg.what = code;
                myHandler.sendMessage(msg);
            }
        });

    }

    public void addNewRecordToLocal(ExpenditureRecord NEWrecord){
        ExpenditureRecordDao expenditureRecordDao = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
        expenditureRecordDao.insert(NEWrecord);

    }

    public void loadAccountbook(){

        AccountbookDao abd = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        String [] columns = {"accountbook_id","book_name","create_user_id","manager_user_id"};
        String[] args = {targetBookName};
        targetAccountbook = abd.query(columns," book_name = ?",args,null,null,null,null).get(0);
    }

    public void loadExpenditurRecord(){
        LrecordId.clear();
        IO.clear();
        thing.clear();
        money.clear();
        time.clear();
        ps.clear();
        name.clear();

        ExpenditureRecordDao erd = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
        String[] colums = {"record_id","accountbook_id","add_date","type","value","reason","comment","operator_id"};
        String[] args = {String.valueOf(targetAccountbook.getAccountbookId())};


        targetRecord =erd.query(colums,"accountbook_id = ?",args,null,null,null,null);
        for (int i=0;i<targetRecord.size();i++){
            IO.add(targetRecord.get(i).getType());
            thing.add(targetRecord.get(i).getReason());
            money.add(targetRecord.get(i).getValue());
            time.add(targetRecord.get(i).getAddDate());
            ps.add(targetRecord.get(i).getComment());

            UserDao userDao = new UserDao(LocalDatabaseAccess.initDatabaseAccess());
            String[] coulumsU ={"user_id","user_name","phone_number","nick_name","identity","pic_path"};
            String[] ids ={String.valueOf(targetRecord.get(i).getOperatorId())};
            ArrayList<User> user = new ArrayList<User>();
            user = userDao.query(coulumsU,"user_id = ?",ids,null,null,null,null);

            name.add(user.get(0).getNickName());
            LrecordId.add(String.valueOf(targetRecord.get(i).getRecordId()));
        }
        myadapter.notifyDataSetChanged();


    }


    private void refreshINandOUT() {
        IN= Float.valueOf(0);
        OUT = Float.valueOf(0);
        for (int i =0;i<IO.size();i++){
            if (IO.get(i).equals("IN")){
                IN=IN+money.get(i);
            }
            if (IO.get(i).equals("OUT")){
                OUT=OUT+money.get(i);
            }
        }
        an_T_income.setTextColor(0xff006633);
        an_T_income.setText(String.valueOf(IN));

        an_T_out.setTextColor(0xffff0000);
        an_T_out.setText(String.valueOf(OUT));

        if ((IN-OUT)<0){
            an_T_last.setTextColor(0xffff0000);
        }else {
            an_T_last.setTextColor(0xff006633);
        }
        an_T_last.setText(String.valueOf(IN-OUT));


    }

    private void iniUI() {
        an_BT_ima = (Button)findViewById(R.id.an_BT_ima);
        an_bt_Addnew = (FloatingActionButton)findViewById(R.id.an_bt_Addnew);
        an_LV_acct = (ListView)findViewById(R.id.an_LV_acct);

        //设置按钮样式
        an_bt_Addnew.setType(FloatingActionButton.TYPE_MINI);
        an_bt_Addnew.setColorPressed(getResources().getColor(R.color.pressed));
        an_bt_Addnew.setShadow(true);
        // an_bt_Addnew.show(true); // Show without an animation
        //an_bt_Addnew.hide(true); // Hide without an animation
        an_bt_Addnew.setColorRipple(getResources().getColor(R.color.colorPrimary));
        an_bt_Addnew.attachToListView(an_LV_acct);
        an_T_name = (TextView)findViewById(R.id.an_T_name);
        an_T_name.setText(targetAccountbook.getBookName());
        an_T_income = (TextView)findViewById(R.id.an_T_income);
        an_T_out = (TextView)findViewById(R.id.an_T_out);
        an_T_last=(TextView)findViewById(R.id.an_T_last);

    }

    class Myadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return IO.size();
        }

        @Override
        public Object getItem(int position) {
            return IO.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View item;
            if (convertView == null){
                item = View.inflate(AccountNoteActivity.this,R.layout.an_lv_acct_item,null);
                vh = new ViewHolder();
                item.setTag(vh);
                vh.tv1 = (TextView) item.findViewById(R.id.an_lv_name);
                vh.tv2 = (TextView) item.findViewById(R.id.an_lv_IO);
                vh.tv3 = (TextView) item.findViewById(R.id.an_lv_time);
            }
            else {
                item = convertView;
                vh =  (ViewHolder)item.getTag();
            }
            vh.tv1.setText(name.get(position));
            if (IO.get(position).equals("OUT")){
                vh.tv2.setTextColor(0xffff0000);
            }
            else {
                vh.tv2.setTextColor(0xff006633);
            }
            vh.tv2.setText(IO.get(position)+"/"+money.get(position));

            vh.tv3.setText(time.get(position));

            return item;
        }
        class ViewHolder{
            TextView tv1;
            TextView tv2;
            TextView tv3;
        }

    }

    public void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
    private boolean reCheck(String money,String comment){
        boolean rst = true;
        ReUtil reHandler = new ReUtil();
        reHandler.useUserPattern("([0-9.])*");
        if (!reHandler.matched(money)){
            toast("金额栏只能填写数字！");
            return false;
        }
        reHandler.useUserPattern("([\\u4e00-\\u9fa5\\w.,\\\\<>\\(\\)!~?\\/\"\':+-\\;=_])*");
        if (!reHandler.matched(comment)){
            toast("备注栏有非法内容！");
            return false;
        }
        return rst;
    }

}
