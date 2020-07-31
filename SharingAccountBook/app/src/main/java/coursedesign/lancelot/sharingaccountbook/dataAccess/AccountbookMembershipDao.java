package coursedesign.lancelot.sharingaccountbook.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import coursedesign.lancelot.sharingaccountbook.domain.AccountbookMembership;

/**
 * Created by LENOVO on 2018/5/1.
 */

public class AccountbookMembershipDao{
    SQLiteDatabase db = null;
    public AccountbookMembershipDao(SQLiteDatabase db){
        this.db = db;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS accountbook_membership(" +
                "membership_id INTEGER PRIMARY KEY ," +
                "accountbook_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "if_manager VARCHAR(255) NOT NULL);";
        db.execSQL(createTableSQL);
    }
    public long insert(AccountbookMembership abm){
        ContentValues cv = new ContentValues();
        cv.put("membership_id",abm.getMembershipId());
        cv.put("accountbook_id",abm.getAccountbookId());
        cv.put("user_id",abm.getUserId());
        cv.put("if_manager",abm.getIfManager());
        return db.insert("accountbook_membership",null,cv);
    }
    public ArrayList<AccountbookMembership> query(String[] columns,String selections,String [] selectionArgs,String groupby,String having,String orderby,String limits){
        ArrayList<AccountbookMembership> result = new ArrayList<AccountbookMembership>();
        Cursor c = db.query("accountbook_membership",columns,selections,selectionArgs,groupby,having,orderby,limits);
        if(c.moveToFirst()){
            AccountbookMembership temp = null;
            do{
                temp = new AccountbookMembership();
                temp.setMembershipId(c.getInt(c.getColumnIndex("membership_id")));
                temp.setAccountbookId(c.getInt(c.getColumnIndex("accountbook_id")));
                temp.setUserId(c.getInt(c.getColumnIndex("user_id")));
                temp.setIfManager(c.getString(c.getColumnIndex("if_manager")));
                result.add(temp);
            }while(c.moveToNext());
        }
        return result;
    }
    public void update(AccountbookMembership membership,String whereClause,String[] whereArgs){
        ContentValues cv = new ContentValues();
        cv.put("accountbook_id",membership.getAccountbookId());
        cv.put("user_id",membership.getUserId());
        cv.put("if_manager",membership.getIfManager());
        db.update("accountbook_membership",cv,whereClause,whereArgs);
    }
    public void delete(String whereClause,String[] whereArgs){
        db.delete("accountbook_membership",whereClause,whereArgs);
    }

}
