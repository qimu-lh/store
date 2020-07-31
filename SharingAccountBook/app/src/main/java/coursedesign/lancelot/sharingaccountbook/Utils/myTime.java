package coursedesign.lancelot.sharingaccountbook.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 下一季不见 on 2018/5/15.
 */
//获取当前系统时间
public class myTime {
    private String newTime;
    public myTime(){}

    //精确到天的时间
    public String Dgettime(){
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        newTime = formatter.format(curDate);
        return newTime;
    }


    //精确到分钟的时间
    public String Mgettime(){
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-MM-dd HH:mm");
        Date curDate =  new Date(System.currentTimeMillis());
        newTime = formatter.format(curDate);
        return newTime;
    }

}
