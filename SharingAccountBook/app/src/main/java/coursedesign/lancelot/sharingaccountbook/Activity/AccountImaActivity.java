package coursedesign.lancelot.sharingaccountbook.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import coursedesign.lancelot.sharingaccountbook.R;
import coursedesign.lancelot.sharingaccountbook.dataAccess.AccountbookDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.ExpenditureRecordDao;
import coursedesign.lancelot.sharingaccountbook.dataAccess.LocalDatabaseAccess;
import coursedesign.lancelot.sharingaccountbook.domain.Accountbook;
import coursedesign.lancelot.sharingaccountbook.domain.ExpenditureRecord;
import coursedesign.lancelot.sharingaccountbook.domain.User;

public class AccountImaActivity extends AppCompatActivity {
    private String tag = "AccountImaActivity";
    private User loginUser;
    private Accountbook targetAccountbook;
    private Integer targetBookId;
    private  LineChart lineChart;
    private   PieChart pieChart;
    private Button ai_bt_complete;
    private TextView ai_T_Aname;
    private ArrayList<Entry> In;
    private ArrayList<Entry> Out;
    List<PieEntry> pieDataList;
    private String[] reasons = {"吃喝","交通","服饰鞋包","化妆护肤","日用品","话费网费","房租","学习"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_ima);
        targetBookId = (Integer)getIntent().getSerializableExtra("targetBookId");
        loginUser = (User)getIntent().getSerializableExtra("loginUser");
        Log.i(tag,"usernamme:::::"+loginUser.getUserName());
        loadAccountbook();
        loadRecords(generateDate());
        //初始化UI
        intUI();

        ai_bt_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //设置横坐标轴格式
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                String [] date = generateDate();

                return date[(int) value];
            }
        };
        //将横坐标轴格式应用到折线图
        XAxis x = lineChart.getXAxis();
        x.setGranularity(1f);
        x.setValueFormatter(formatter);
        //组装为两组独立数据集,相当于两根曲线
        LineDataSet myDataSetOut = new LineDataSet(Out,"七日支出曲线");
        LineDataSet myDataSetIn = new LineDataSet(In,"七日收入曲线");
        //为支出曲线设置格式
        myDataSetOut.setDrawCircles(true);
        myDataSetOut.setDrawFilled(false);
        myDataSetOut.setLineWidth(2f);
        myDataSetOut.setCircleRadius(5f);
        myDataSetOut.setColor(Color.rgb(244, 117, 117));
        myDataSetOut.setAxisDependency(YAxis.AxisDependency.LEFT);
        //为收入曲线设置格式
        myDataSetIn.setDrawCircles(true);
        myDataSetIn.setDrawFilled(false);
        myDataSetIn.setLineWidth(2f);
        myDataSetIn.setCircleRadius(5f);
        myDataSetIn.setColor(Color.rgb(159, 230, 160));
        myDataSetIn.setAxisDependency(YAxis.AxisDependency.LEFT);
        //将两只曲线数据装入折线数据集中并应用于折线图对象
        List<ILineDataSet> dataset = new ArrayList<ILineDataSet>();
        dataset.add(myDataSetOut);
        dataset.add(myDataSetIn);
        LineData lineData = new LineData(dataset);
        lineChart.setData(lineData);
        //绘制
        lineChart.invalidate();
        //对饼图进行操作
//        pieDataList = new ArrayList<PieEntry>();
//        pieDataList.add(new PieEntry(90f,"日用品"));
//        pieDataList.add(new PieEntry(920f,"饮食"));
//        pieDataList.add(new PieEntry(360.5f,"交通"));
//        pieDataList.add(new PieEntry(207.5f,"娱乐"));
        //组装数据
        PieDataSet pieDataSet = new PieDataSet(pieDataList,"");
        //设置颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(205,205,205));
        colors.add(Color.rgb(110, 180, 220));
        colors.add(Color.rgb(255, 123, 120));
        colors.add(Color.rgb(50, 135, 200));
        colors.add(Color.rgb(150, 165, 10));
        colors.add(Color.rgb(210, 55, 20));
        colors.add(Color.rgb(100, 15, 160));
        colors.add(Color.rgb(55, 195, 230));
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        //设置饼图格式
        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.setCenterText("七日消费占比");
        //设置图例
        Legend myLegend = pieChart.getLegend();
        myLegend.setOrientation(Legend.LegendOrientation.VERTICAL);
        myLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        myLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        pieChart.invalidate();


    }

    private void intUI() {
        //或得饼图和折线图
        pieChart = (PieChart) findViewById(R.id.ai_ima_CircleIma);
        lineChart = (LineChart) findViewById(R.id.ai_ima_LineIma);
        ai_bt_complete = (Button)findViewById(R.id.ai_bt_complete);
        ai_T_Aname = (TextView) findViewById(R.id.ai_T_Aname);

        ai_T_Aname.setText(targetAccountbook.getBookName());
    }

    public void loadAccountbook(){
        AccountbookDao abd = new AccountbookDao(LocalDatabaseAccess.initDatabaseAccess());
        String [] columns = {"accountbook_id","book_name","create_user_id","manager_user_id"};
        String[] args = {""+targetBookId};
        targetAccountbook = abd.query(columns," accountbook_id = ?",args,null,null,null,null).get(0);
    }
    private void loadRecords(String[] date){
        In = new ArrayList<Entry>();
        Out = new ArrayList<Entry>();
        pieDataList = new ArrayList<PieEntry>();
        ExpenditureRecordDao erd = new ExpenditureRecordDao(LocalDatabaseAccess.initDatabaseAccess());
        String[] rColumns = {"record_id","accountbook_id","add_date","type","value","reason","comment","operator_id"};
        ArrayList<ExpenditureRecord> rst = erd.query(rColumns," accountbook_id = ?",new String[]{targetBookId+""},null,null,null,null);
        float sumIn,sumOut,sum,index;
        SimpleDateFormat sdfOld = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfNew = new SimpleDateFormat("yy/MM/dd");
        index = 0;
        for (String dayTime:date){
            sumIn = 0;
            sumOut = 0;
            for (ExpenditureRecord record:rst){
                try {
                    String recordDate = sdfNew.format(sdfOld.parse(record.getAddDate().split(" ")[0]));
                    if (recordDate.equals(dayTime)){
                        switch (record.getType()){
                            case "IN":
                                sumIn += record.getValue();
                                break;
                            case  "OUT":
                                sumOut += record.getValue();
                                break;
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            In.add(new Entry(index,sumIn));
            Out.add(new Entry(index,sumOut));
            index ++;
        }
        for (String reason:reasons){
            sum = 0;
            for (ExpenditureRecord record:rst){
                if (record.getReason().equals(reason) && record.getType().equals("OUT")){
                    sum += record.getValue();
                }
            }
            pieDataList.add(new PieEntry(sum,reason));
        }
    }
    private String[] generateDate(){
        String[] date = new String[7];
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE,-7);
        today = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        date[0] = sdf.format(today);
        cal.add(Calendar.DATE,1);
        today = cal.getTime();
        date[1] = sdf.format(today);
        cal.add(Calendar.DATE,1);
        today = cal.getTime();
        date[2] = sdf.format(today);
        cal.add(Calendar.DATE,1);
        today = cal.getTime();
        date[3] = sdf.format(today);
        cal.add(Calendar.DATE,1);
        today = cal.getTime();
        date[4] = sdf.format(today);
        cal.add(Calendar.DATE,1);
        today = cal.getTime();
        date[5] = sdf.format(today);
        cal.add(Calendar.DATE,1);
        today = cal.getTime();
        date[6] = sdf.format(today);
        return date;
    }
}
