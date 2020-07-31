package coursedesign.lancelot.sharingaccountbook.dataAccess;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import coursedesign.lancelot.sharingaccountbook.domain.ExpenditureRecord;

/**
 * Created by LENOVO on 2018/5/1.
 */

public class ExpenditureRecordDao {
    SQLiteDatabase db;
    public ExpenditureRecordDao(SQLiteDatabase db){
        this.db = db;
        String createTableSQL = "CREATE TABLE IF NOT EXISTS expenditure_record(" +
                "record_id INTEGER PRIMARY KEY ," +
                "accountbook_id INTEGER NOT NULL," +
                "add_date TEXT NOT NULL," +
                "type VARCHAR(255) NOT NULL," +
                "value FLOAT NOT NULL," +
                "reason VARCHAR(255) NOT NULL," +
                "comment VARCHAR(255) DEFAULT NULL," +
                "operator_id INTEGER NOT NULL);";
        db.execSQL(createTableSQL);
    }
    public long insert(ExpenditureRecord record){
        ContentValues cv = new ContentValues();
        cv.put("record_id",record.getRecordId());
        cv.put("accountbook_id",record.getAccountbookId());
        cv.put("add_date",record.getAddDate());
        cv.put("type",record.getType());
        cv.put("value",record.getValue());
        cv.put("reason",record.getReason());
        cv.put("comment",record.getComment());
        cv.put("operator_id",record.getOperatorId());
        return db.insert("expenditure_record",null,cv);
    }

    public ArrayList<ExpenditureRecord> query(String[] columns, String selections, String [] selectionArgs, String groupby, String having, String orderby, String limits){
        ArrayList<ExpenditureRecord> result = new ArrayList<ExpenditureRecord>();
        Cursor c = db.query("expenditure_record",columns,selections,selectionArgs,groupby,having,orderby,limits);
        if(c.moveToFirst()){
            ExpenditureRecord temp = null;
            do{
                temp = new ExpenditureRecord();
                temp.setRecordId(c.getInt(c.getColumnIndex("record_id")));
                temp.setAccountbookId(c.getInt(c.getColumnIndex("accountbook_id")));
                temp.setAddDate(c.getString(c.getColumnIndex("add_date")));
                temp.setType(c.getString(c.getColumnIndex("type")));
                temp.setValue(c.getFloat(c.getColumnIndex("value")));
                temp.setReason(c.getString(c.getColumnIndex("reason")));
                temp.setComment(c.getString(c.getColumnIndex("comment")));
                temp.setOperatorId(c.getInt(c.getColumnIndex("operator_id")));
                result.add(temp);
            }while(c.moveToNext());
        }
        return result;
    }
    public void update(ExpenditureRecord record,String whereClause,String[] whereArgs){
        ContentValues cv = new ContentValues();
        cv.put("accountbook_id",record.getAccountbookId());
        cv.put("add_date",record.getAddDate());
        cv.put("type",record.getType());
        cv.put("value",record.getValue());
        cv.put("reason",record.getReason());
        cv.put("comment",record.getComment());
        cv.put("operator_id",record.getOperatorId());
        db.update("expenditure_record",cv,whereClause,whereArgs);
    }
    public void delete(String whereClause,String[] whereArgs){
        db.delete("expenditure_record",whereClause,whereArgs);
    }
}
