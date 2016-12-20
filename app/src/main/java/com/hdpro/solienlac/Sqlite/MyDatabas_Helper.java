package com.hdpro.solienlac.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hdpro.solienlac.Diem;
import com.hdpro.solienlac.Phanhoi;
import com.hdpro.solienlac.Phuhuynh;
import com.hdpro.solienlac.Thoikhoabieu;
import com.hdpro.solienlac.Tinnhan;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by User on 11/11/2016.
 */

public class MyDatabas_Helper extends SQLiteOpenHelper {
    private SQLiteDatabase mySqlDB;
    private static String tenDatabaseName="QLHSnew6.db";
    private static int phienban=1;
    private  String sTable_Tinnhan ="tblTinnhan";
    private String sTable_Bangdiem = "tblBangdiem";
    private String sTable_Phanhoi="tblPhanhoi";
    private String sTable_Thoikhoabieu="tblThoikhoabieu";
    private String sTable_Phuhuynh="tblPhuhuynh";
    //create table tblTinnhan
    private String sSqlCreateTinnhan = "Create Table "+sTable_Tinnhan
            +"(tn_id Integer Not null primary key AutoIncrement,"
            +"tn_idHS Integer,"
            +"tn_noidung nVarchar(255),"
            +"tn_ngaynhan nVarchar(20),"
            +"tn_trangthai Integer)";
    //Drop table tin nhan
    private String sSqlDropTinnhan = "Drop Table if Exist "+sTable_Tinnhan;
    //create table bangdiem
    private String sSqlCreateBangdiem="Create table "+sTable_Bangdiem
            +"(bd_id Integer Not null primary key AutoIncrement,"
            +"bd_idHS Integer,"
            +"bd_tenmonhoc nVarchar(20),"
            +"bd_diem1 nVarchar(5),"
            +"bd_diem2 nVarchar(5),"
            +"bd_diem3 nVarchar(5),"
            +"bd_hk Integer)";
    //drop table bang diem
    private String sSqlDropBangdiem ="Drop Table if Exist "+sTable_Bangdiem;
    //create table tblthoikhoabieu
    private String sSqlCreatePhanhoi ="Create Table "+sTable_Phanhoi+"(" +
            "ph_id integer Not Null Primary key AutoIncrement," +
            "ph_hs_id integer," +
            "ph_tieude nVarchar(150)," +
            "ph_noidung nVarchar(500)," +
            "ph_ngaygui nVarchar(30))";
    //drop table thoi khoa bieu
    private String sSqlDropPhanhoi="Drop Table if Exist "+sTable_Phanhoi;
    //create table tablThoikhoabieu
    private String sSqlCreateThoikhoabieu="Create Table "+sTable_Thoikhoabieu+"(" +
            "tkb_id integer Not null Primary key AutoIncrement," +
            "tkb_thu integer," +
            "tkb_monhoc nVarchar(20)," +
            "tkb_tiet integer," +
            "tkb_tenlop nVarchar(10))";
    //drop table thoi khoa bieu
    private String sSqlDropThoikhoabieu="Drop Table if Exist "+sTable_Thoikhoabieu;
    //create table tablePhuhuynh
    private String sSqlcreatePhuhuynh="Create Table "+sTable_Phuhuynh+"("+
            "phhs_id interger unique," +
            "phhs_tenlop nVarchar(10)," +
            "phhs_tenph nVarchar(20)," +
            "phhs_tenhs nVarchar(20))";
    //drop table Phu huynh
    private String sSqlDropPhuhuynh="Drop Table if Exist "+sTable_Phuhuynh;
    public MyDatabas_Helper(Context context) {
        super(context,tenDatabaseName, null, phienban);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sSqlCreateTinnhan);
        sqLiteDatabase.execSQL(sSqlCreateBangdiem);
        sqLiteDatabase.execSQL(sSqlCreatePhanhoi);
        sqLiteDatabase.execSQL(sSqlCreateThoikhoabieu);
        sqLiteDatabase.execSQL(sSqlcreatePhuhuynh);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(sSqlDropTinnhan);
        sqLiteDatabase.execSQL(sSqlDropBangdiem);
        sqLiteDatabase.execSQL(sSqlDropThoikhoabieu);
        sqLiteDatabase.execSQL(sSqlDropPhanhoi);
        sqLiteDatabase.execSQL(sSqlDropPhuhuynh);
        onCreate(sqLiteDatabase);
    }
    ////////////Them du lieu////////////
    public long themTN(int idHS,String noidung,String ngaynhan){
        mySqlDB=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tn_idHS",idHS);
        cv.put("tn_noidung",noidung);
        cv.put("tn_ngaynhan",ngaynhan);
        cv.put("tn_trangthai",0);
        long kq = mySqlDB.insert(sTable_Tinnhan,null,cv);
        mySqlDB.close();
        return kq;
    }
    public long ThemDiem(int idHS, String tenmonhoc,String diem1,String diem2,String diem3, int hocky){
        mySqlDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("bd_idHS",idHS);
        cv.put("bd_tenmonhoc",tenmonhoc);
        cv.put("bd_diem1",diem1);
        cv.put("bd_diem2",diem2);
        cv.put("bd_diem3",diem3);
        cv.put("bd_hk",hocky);
        long kq = mySqlDB.insert(sTable_Bangdiem,null,cv);
        mySqlDB.close();
        return kq;
    }
    public long ThemPhanhoi(int idHS,String tieude,String noidung,String ngaygui){
        mySqlDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ph_hs_id",idHS);
        cv.put("ph_tieude",tieude);
        cv.put("ph_noidung",noidung);
        cv.put("ph_ngaygui",ngaygui);
        long kq = mySqlDB.insert(sTable_Phanhoi,null,cv);
        mySqlDB.close();
        return kq;
    }
    public long ThemThoikhoabieu(int thu,String monhoc, int tiet,String tenlop){
        mySqlDB=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tkb_thu",thu);
        cv.put("tkb_monhoc",monhoc);
        cv.put("tkb_tiet",tiet);
        cv.put("tkb_tenlop",tenlop);
        long kq = mySqlDB.insert(sTable_Thoikhoabieu,null,cv);
        mySqlDB.close();
        return kq;
    }
    public long ThemPhuhuynh(int id,String tenlop, String tenPH,String tenHS){
        mySqlDB=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("phhs_id",id);
        cv.put("phhs_tenlop",tenlop);
        cv.put("phhs_tenph",tenPH);
        cv.put("phhs_tenhs",tenHS);
        long kq = mySqlDB.insert(sTable_Phuhuynh,null,cv);
        mySqlDB.close();
        return kq;
    }
    ////////////lay du lieu////////////
    public ArrayList<Phuhuynh> LayDSPhuHuynh(String tenlop,int IDHS){
        mySqlDB=this.getReadableDatabase();
        ArrayList<Phuhuynh> dsPhuhuynh = new ArrayList<Phuhuynh>();
        String sql="Select phhs_id,phhs_tenlop,phhs_tenph,phhs_tenhs from "+sTable_Phuhuynh+" Where phhs_tenlop='"+tenlop+"'";
        Cursor myCursor = mySqlDB.rawQuery(sql,null);
        while (myCursor.moveToNext()){
            if(myCursor.getInt(0)!=IDHS){
                dsPhuhuynh.add(new Phuhuynh(myCursor.getInt(0),myCursor.getString(1),myCursor.getString(2),myCursor.getString(3)));
            }
        }
        return dsPhuhuynh;
    }
    public ArrayList<Diem> LayDSBD(int idHS,int hocky){
        mySqlDB=this.getReadableDatabase();
        ArrayList<Diem> dsDiem = new ArrayList<Diem>();
        String sql ="Select bd_tenmonhoc,bd_diem1,bd_diem2,bd_diem3 From "+sTable_Bangdiem+" Where bd_idHS="+idHS+" AND bd_hk="+hocky;
        Cursor myCursor = mySqlDB.rawQuery(sql,null);
        while (myCursor.moveToNext()){
            dsDiem.add(new Diem(myCursor.getString(0),myCursor.getString(1),myCursor.getString(2),myCursor.getString(3)));
        }
        return  dsDiem;
    }
    public ArrayList<Tinnhan> layDSTN(int idHS){
        mySqlDB=this.getReadableDatabase();
        ArrayList<Tinnhan> dsTinnhan = new ArrayList<Tinnhan>();
        String sql="Select tn_noidung,tn_ngaynhan,tn_trangthai,tn_id From "+sTable_Tinnhan+" Where tn_idHS="+idHS+" ORDER BY tn_ngaynhan DESC";
        Cursor myCursor = mySqlDB.rawQuery(sql,null);
        while (myCursor.moveToNext()){
            dsTinnhan.add(new Tinnhan(myCursor.getInt(3),myCursor.getString(0),myCursor.getString(1),myCursor.getInt(2)));
        }
        return dsTinnhan;
    }
    public ArrayList<Thoikhoabieu> layDSMonhoc(String tenlop,int thu){
        mySqlDB=this.getReadableDatabase();
        ArrayList<Thoikhoabieu> dsMonhoc = new ArrayList<Thoikhoabieu>();
        String sql="Select tkb_thu,tkb_monhoc,tkb_tiet From "+sTable_Thoikhoabieu+" Where tkb_tenlop='"+tenlop+"' AND tkb_thu="+thu;
        Cursor cursor = mySqlDB.rawQuery(sql,null);
        while (cursor.moveToNext()){
            dsMonhoc.add(new Thoikhoabieu(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
        }
        return dsMonhoc;
    }
    public ArrayList<Phanhoi> layDSPhanhoi(int idhs){
        mySqlDB=this.getReadableDatabase();
        ArrayList<Phanhoi> dsPhanhoi = new ArrayList<Phanhoi>();
        String sql = "Select ph_tieude,ph_noidung,ph_ngaygui From "+sTable_Phanhoi+" Where ph_hs_id="+idhs+" ORDER BY ph_ngaygui DESC";
        Cursor cursor = mySqlDB.rawQuery(sql,null);
        while (cursor.moveToNext()){
            dsPhanhoi.add(new Phanhoi(idhs,cursor.getString(0),cursor.getString(1),cursor.getString(2)));
        }
        return dsPhanhoi;
    }
    ///// sua //////
    public long SuaTN(int idHS,int idtn){
        mySqlDB=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("tn_trangthai",1);
        long kq = mySqlDB.update(sTable_Tinnhan,cv,"tn_id="+idtn,null);
        mySqlDB.close();
        return kq;
    }
    /// Drop ///
    public long DropThoikhoabieucu(String tenlop){
        mySqlDB=this.getWritableDatabase();
        long kq =mySqlDB.delete(sTable_Thoikhoabieu,"tkb_tenlop='"+tenlop+"'",null);
        return kq;
    }
    public  long DropDiem(int idHS){
        mySqlDB=this.getWritableDatabase();
        long kq = mySqlDB.delete(sTable_Bangdiem,"bd_idHS="+idHS,null);
        return kq;
    }
    public long DeleteItemPhanhoi(String tieude,String noidung){
        mySqlDB=this.getWritableDatabase();
        long kq = mySqlDB.delete(sTable_Phanhoi,"ph_tieude='"+tieude+"' And ph_noidung='"+noidung+"'",null);
        return kq;
    }
    public long DeleteAllPhanhoi(int IDHS){
        mySqlDB=this.getWritableDatabase();
        long kq = mySqlDB.delete(sTable_Phanhoi,"ph_hs_id="+IDHS,null);
        return kq;
    }
    public long DeleteItemTinnhan(int IDTN){
        mySqlDB=this.getWritableDatabase();
        long kq = mySqlDB.delete(sTable_Tinnhan,"tn_id="+IDTN,null);
        return kq;
    }
    public long DeleteAllTinnhan(int IDHS){
        mySqlDB=this.getWritableDatabase();
        long kq = mySqlDB.delete(sTable_Tinnhan,"tn_idHS="+IDHS,null);
        return kq;
    }
}
