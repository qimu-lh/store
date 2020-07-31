package coursedesign.lancelot.sharingaccountbook.dataAccess;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by LENOVO on 2018/5/1.
 * "/data/data/accountbook_test_01"是测试用数据库文件，正式使用时再更换
 */

public class LocalDatabaseAccess {
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/databases/accountbook_testMe.db";
    private static final String parentPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/databases";
    static File parentFile = new File(parentPath);
    static File dbFile = new File(filePath);

    private static SQLiteDatabase databaseAccess = null;
    public static SQLiteDatabase initDatabaseAccess(){
        try{
            if (!parentFile.exists()){
                parentFile.mkdirs();
            }
            if (!dbFile.exists()){
                dbFile.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //试图写成单例模式
        if (databaseAccess == null){
            databaseAccess = SQLiteDatabase.openOrCreateDatabase(dbFile,null);
        }
        return databaseAccess;
    }

}
