package com.example.cms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="register.db";
    public static final String TABLE_NAME_1 ="students";
    public static final String COL_1 ="STUID";
    public static final String COL_2 ="regno";
    public static final String COL_3 ="password";
    public static final String COL_4 ="ADMIN_ID";
    public static final String COL_5 ="user";
    public static final String COL_6 ="pass";
    public static final String COL_7 ="regno";
    public static final String TABLE_NAME_2 = "adminLogin";
    public static final String COL_10 ="ID";
    public static final String COL_11 ="regno";
    public static final String COL_12 ="portal";
    public static final String TABLE_NAME_3 ="attendance";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE candidates (STUDID INTEGER PRIMARY KEY AUTOINCREMENT, regno TEXT, name TEXT, age TEXT,phone TEXT, email TEXT, password TEXT, address TEXT, qualification TEXT, department TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE organiser (ORGID INTEGER PRIMARY  KEY AUTOINCREMENT, email TEXT, password TEXT)");
        sqLiteDatabase.execSQL("INSERT INTO organiser (email,password) VALUES ('jssanju@live.com','123')");
        sqLiteDatabase.execSQL("CREATE TABLE reviewer (RID INTEGER PRIMARY  KEY AUTOINCREMENT,id TEXT, email TEXT, name TEXT, cname TEXT, cid TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE conference (CONID INTEGER PRIMARY  KEY AUTOINCREMENT,cid TEXT,cname TEXT,topic TEXT,date TEXT,place TEXT, orgemail TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE papers (PID INTEGER PRIMARY  KEY AUTOINCREMENT, cid TEXT, email TEXT, status TEXT, pname TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE registration (STUDID INTEGER PRIMARY KEY AUTOINCREMENT, cid TEXT,regno TEXT, name TEXT, age TEXT,phone TEXT, email TEXT, address TEXT, qualification TEXT, department TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS candidates");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS organiser");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS reviewer");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS conference");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS papers");
        onCreate(sqLiteDatabase);
    }


    //Candidate

    public long addCandidate(String regno, String  name, String age, String phone, String email, String password, String address ,String qualification, String department){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regno", regno);
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("phone",phone);
        contentValues.put("email", email);
        contentValues.put("password",password);
        contentValues.put("address",address);
        contentValues.put("qualification",qualification);
        contentValues.put("department",department);
        long res = db.insert("candidates",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkCandidate(String username, String password){
        String[] columns = { "STUDID" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "email" + "=?" + " and " + "password" + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("candidates",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }

    public Cursor searchConference(String anything)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT cid,cname,topic,date,place FROM conference WHERE cid='" +anything+ "' OR cname='" +anything+ "' OR topic='"+anything+"' OR place='" +anything+ "' OR place='"+anything+"'",null);
        return res;
    }


    public Cursor getReviewerMail(String cid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT email FROM reviewer WHERE cid="+cid,null);
        return res;
    }

    public long submitPaper(String email, String pname, String cid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("pname", pname);
        contentValues.put("cid",cid);
        contentValues.put("status", "Not updated");
        long res = db.insert("papers",null,contentValues);
        db.close();
        return  res;
    }

    public Cursor viewStatus(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT cid,pname,status FROM papers WHERE email='"+email+"'",null);
        return res;
    }

    public Cursor getCandidateDetails(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT regno,name,age,phone,address,qualification,department FROM candidates WHERE email='"+email+"'",null);
        return res;
    }

    public Cursor registerDetails(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT cid FROM papers WHERE status='ACCEPTED' AND email='"+email+"'",null);
        return res;
    }

    public long addRegistration(String cid,String regno, String  name, String age, String phone, String email, String address ,String qualification, String department){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid",cid);
        contentValues.put("regno", regno);
        contentValues.put("name",name);
        contentValues.put("age",age);
        contentValues.put("phone",phone);
        contentValues.put("email", email);
        contentValues.put("address",address);
        contentValues.put("qualification",qualification);
        contentValues.put("department",department);
        long res = db.insert("registration",null,contentValues);
        db.close();
        return  res;
    }


    public Cursor getOrgMail(String cid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT orgemail FROM conference WHERE cid="+cid,null);
        return res;
    }

    public Cursor viewReg(String cid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT email,name FROM registration WHERE cid="+cid,null);
        return res;
    }

    public Cursor stuDetails(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT regno,name,age,phone,address,qualification,department FROM registration WHERE email='"+email+"'",null);
        return res;
    }

    //For Reviewer
    public boolean checkReviewer(String username, String password){
        String[] columns = { "RID" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "email" + "=?" + " and " + "password" + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("reviewer",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }

    public Cursor getReviewerDetails(String email)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT cid,cname FROM reviewer WHERE email='"+email+"'",null);
        return res;
    }

    public Cursor getPapers(String cid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT pname,email,status FROM papers WHERE cid="+cid,null);
        return res;
    }

    public boolean updateStatus(String pname){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("UPDATE papers SET status='ACCEPTED' WHERE pname='"+pname+"'", null);
        int count = res.getCount();
        res.close();
        db.close();
        if(count>0)
            return  false;
        else
            return  true;
    }

    //END

    //Organiser
    public boolean checkOrganiser(String username, String password){
        String[] columns = { "ORGID" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "email" + "=?" + " and " + "password" + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query("organiser",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }

    public long addConference(String cid, String cname, String topic, String date, String place, String orgemail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cid", cid);
        contentValues.put("cname", cname);
        contentValues.put("topic", topic);
        contentValues.put("date", date);
        contentValues.put("place", place);
        contentValues.put("orgemail",orgemail);
        long res = db.insert("conference",null,contentValues);
        db.close();
        return  res;
    }

    public long addReviewer(String id, String email, String name, String cname, String cid, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("email", email);
        contentValues.put("name",name);
        contentValues.put("cname", cname);
        contentValues.put("cid",cid);
        contentValues.put("password",password);
        long res = db.insert("reviewer",null,contentValues);
        db.close();
        return  res;
    }

    public Cursor viewReviews(String cid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT pname,email,status FROM papers WHERE cid="+cid,null);
        return res;
    }

    //END

}