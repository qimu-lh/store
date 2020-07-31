package coursedesign.lancelot.sharingaccountbook.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import coursedesign.lancelot.sharingaccountbook.domain.Accountbook;

/**
 * Created by LENOVO on 2018/5/1.
 */

public class AccountbookDao {
    SQLiteDatabase db = null;
    public AccountbookDao(SQLiteDatabase db){
        this.db = db;
        String tableCreateSQL = "CREATE TABLE IF NOT EXISTS accountbook(" +
                "accountbook_id INTEGER PRIMARY KEY ," +
                "book_name VARCHAR(255) NOT NULL," +
                "create_user_id INTEGER NOT NULL," +
                "manager_user_id INTEGER NOT NULL);";
        db.execSQL(tableCreateSQL);
    }
    public long insert(Accountbook book){
        ContentValues cv = new ContentValues();
        cv.put("accountbook_id",book.getAccountbookId());
        cv.put("book_name",book.getBookName());
        cv.put("create_user_id",book.getCreateUserId());
        cv.put("manager_user_id",book.getManagerUserId());
        return db.insert("accountbook",null,cv);
    }
    public ArrayList<Accountbook> query(String[] columns,String selections,String [] selectionArgs,String groupby,String having,String orderby,String limits){
        ArrayList<Accountbook> result = new ArrayList<Accountbook>();
        Cursor c = db.query("accountbook",columns,selections,selectionArgs,groupby,having,orderby,limits);
        if(c.moveToFirst()){
            Accountbook temp = null;
            do{
                temp = new Accountbook();
                temp.setAccountbookId(c.getInt(c.getColumnIndex("accountbook_id")));
                temp.setBookName(c.getString(c.getColumnIndex("book_name")));
                temp.setCreateUserId(c.getInt(c.getColumnIndex("create_user_id")));
                temp.setManagerUserId(c.getInt(c.getColumnIndex("manager_user_id")));
                result.add(temp);
            }while(c.moveToNext());
        }
        return result;
    }


    public void update(Accountbook book,String whereClause,String[] whereArgs){
        ContentValues cv = new ContentValues();
        cv.put("accountbook_id",book.getAccountbookId());
        cv.put("book_name",book.getBookName());
        cv.put("create_user_id",book.getCreateUserId());
        cv.put("manager_user_id",book.getManagerUserId());
        db.update("accountbook",cv,whereClause,whereArgs);
    }

    public void delete(String whereClause,String[] whereArgs){
        db.delete("accountbook",whereClause,whereArgs);
    }
}
