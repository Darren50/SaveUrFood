package com.sp.saveurfood1.ShoppingList;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Helper2 extends SQLiteOpenHelper{
    private static final String DATABASE_NAME  = "list.dh";
    private static final int SCHEMA_VERSION = 1;
    public static final String TABLE_NAME = "lists_table";
    public static final String CONTACTS_COLUMN_TITLE="listName";
    public static final String LIST_BARCODE_TITLE="listBC";
    public static final String LIST_COLUMN_ID="_id";


    public Helper2(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Will be called once when the database is not created
        db.execSQL("CREATE TABLE lists_table ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " listName TEXT, listBC TEXT, listDes TEXT, byteArray BLOB);");
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
        //Will not be called until SCHEMA_VERSION increases
        //Here we can upgrade the database e.g. add more table
    }

    /* Read all records from restaurants_table */
    public Cursor getAll(){
        return (getReadableDatabase().rawQuery(
                "SELECT _id, listName, listBC," +
                        " listDes, byteArray FROM lists_table ORDER BY listName", null));

    }

    public Cursor getById(String id){
        String[] args = {id};
        return (getReadableDatabase().rawQuery(
                "SELECT _id, listName, listBC, " +
                        " listDes, byteArray FROM lists_table WHERE _ID = ?", args));
    }
    /* Writes a record into restaurants_table */
    public void insert(String listName,
                       String listBC,String listDes, byte[]byteArray) {
        ContentValues cv = new ContentValues();

        cv.put("listName", listName);
        cv.put("listBC", listBC);
        cv.put("listDes", listDes);
        cv.put("byteArray", byteArray);
        getWritableDatabase().insert("lists_table", "listName", cv);

    }

    /* Update a particular record in restaurants_table with id provided */

    public void update (String id, String listName, String listBC,String listDes,
                        byte[]byteArray) {
        ContentValues cv= new ContentValues();
        String[] args ={id};
        cv.put("listName", listName);
        cv.put("listBC", listBC);
        cv.put("listDes", listDes);
        cv.put("byteArray", byteArray);

        getWritableDatabase().update("lists_table", cv, "_ID = ?", args);
    }

    public void deleteItem(String ID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE " + LIST_COLUMN_ID + "='" + ID + "'");
        db.close();
    }

    public void deleteItem2(String barcode)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME+  " WHERE " + LIST_BARCODE_TITLE + "='" + barcode +"'" );
        db.close();
    }


    public String getID(Cursor c) {return (c.getString(0));}

    public String getListName(Cursor c){
        return (c.getString(1));
    }

    public String getListBC(Cursor c){
        return (c.getString(2));
    }




    public String getListDes(Cursor c){
        return (c.getString(3));
    }



    public byte[] getImage(Cursor c) {return(c.getBlob(4));}



}