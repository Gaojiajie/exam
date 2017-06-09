package gjj.lock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Test";
    private static final String TABLE_NAME="pwdManager";
    private static final int VERSION=1;
    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_USERNAME="userName";
    private static final String KEY_PASSWORD = "password";

    private static final String CREATE_TABLE="create table "+TABLE_NAME+"("+KEY_ID+
            " integer primary key autoincrement,"+KEY_NAME+" text not null,"+
            KEY_USERNAME+" text not null,"+ KEY_PASSWORD+" text not null);";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addPwd(Pwd pwd){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,pwd.getName());
        values.put(KEY_USERNAME,pwd.getUserName());
        values.put(KEY_PASSWORD,pwd.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public Pwd getPwd(String name){
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_NAME,KEY_USERNAME,KEY_PASSWORD},
                KEY_NAME+"=?",new String[]{name},null,null,null,null);


        Pwd pwd=null;

        if(cursor.moveToFirst()){
            pwd=new Pwd(cursor.getInt(0),cursor.getString(1), cursor.getString(2),cursor.getString(3));
        }
        return pwd;
    }
    public int getPwdCounts(){
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        cursor.close();

        return cursor.getCount();
    }
    public List<Pwd> getALllPwd(){
        List<Pwd> studentList=new ArrayList<Pwd>();

        String selectQuery="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Pwd pwd=new Pwd();
                pwd.setId(Integer.parseInt(cursor.getString(0)));
                pwd.setName(cursor.getString(1));
                pwd.setUserName(cursor.getString(2));
                pwd.setPassword(cursor.getString(3));
                studentList.add(pwd);
            }while(cursor.moveToNext());
        }
        return studentList;
    }
    public int updatePwd(Pwd pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME,pwd.getName());
        values.put(KEY_USERNAME,pwd.getUserName());
        values.put(KEY_PASSWORD,pwd.getPassword());
        return db.update(TABLE_NAME,values,KEY_ID+"=?",new String[]{String.valueOf(pwd.getId())});
    }
    public void deletePwd(Pwd pwd){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,KEY_ID+"=?",new String[]{String.valueOf(pwd.getId())});
        db.close();
    }
}
