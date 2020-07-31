package coursedesign.lancelot.sharingaccountbook.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import coursedesign.lancelot.sharingaccountbook.domain.User;

/**
 * Created by LENOVO on 2018/5/1.
 */

public class UserDao {
    SQLiteDatabase db ;
    public UserDao(SQLiteDatabase db){
        this.db = db;
        String tableCreateSQL = "CREATE TABLE IF NOT EXISTS user(" +
                "user_id INTEGER PRIMARY KEY ," +
                "user_name VARCHAR(255) NOT NULL," +
                "phone_number VARCHAR(11) NOT NULL," +
                "nick_name VARCHAR(255) NOT NULL," +
                "identity VARCHAR(60) NOT NULL," +
                "pic_path VARCHAR(255) NOT NULL);";
        db.execSQL(tableCreateSQL);
    }

    public long insert(User user){
        ContentValues cv = new ContentValues();
        cv.put("user_id",user.getUserId());
        cv.put("user_name",user.getUserName());
        cv.put("phone_number",user.getPhoneNumber());
        cv.put("nick_name",user.getNickName());
        cv.put("identity",user.getIdentity());
        cv.put("pic_path",user.getPicPath());
        return db.insert("user",null,cv);
    }
    public ArrayList<User> query(String[] columns,String selections,String [] selectionArgs,String groupby,String having,String orderby,String limits){
        ArrayList<User> result = new ArrayList<User>();
        Cursor c = db.query("user",columns,selections,selectionArgs,groupby,having,orderby,limits);
        if(c.moveToFirst()){
            User temp = null;
            do{
                temp = new User();
                //这里的字段应该是查出来的结果，所以要能够get这些字段要求columns数组必须要包含这些字段，后面也许会改
                temp.setUserId(c.getInt(c.getColumnIndex("user_id")));
                temp.setUserName(c.getString(c.getColumnIndex("user_name")));
                temp.setNickName(c.getString(c.getColumnIndex("nick_name")));
                temp.setPhoneNumber(c.getString(c.getColumnIndex("phone_number")));
                temp.setIdentity(c.getString(c.getColumnIndex("identity")));
                temp.setPicPath(c.getString(c.getColumnIndex("pic_path")));
                result.add(temp);
            }while(c.moveToNext());
        }
        return result;
    }
    public void update(User user,String whereClause,String[] whereArgs){
        ContentValues cv = new ContentValues();
        cv.put("user_name",user.getUserName());
        cv.put("phone_number",user.getPhoneNumber());
        cv.put("nick_name",user.getNickName());
        cv.put("identity",user.getIdentity());
        cv.put("pic_path",user.getPicPath());
        db.update("user",cv,whereClause,whereArgs);
    }
    public void delete(String whereClause,String[] whereArgs){
        db.delete("user",whereClause,whereArgs);
    }
}
