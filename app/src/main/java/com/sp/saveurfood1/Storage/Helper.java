    package com.sp.saveurfood1.Storage;
    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.speech.tts.TextToSpeech;
    import java.text.SimpleDateFormat;
    import java.util.Calendar;
    import java.util.Locale;


    public class Helper extends SQLiteOpenHelper{
        private TextToSpeech Food,Food1;
        private static final String DATABASE_NAME  = "homeactivity.dh";
        private static final int SCHEMA_VERSION = 1;
        public static final String TABLE_NAME = "contacts_table";
        public static final String CONTACTS_COLUMN_TITLE="contactName";
        public static final String CONTACTS_COLUMN_ID="_id";


        public Helper(Context context) {
            super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //Will be called once when the database is not created
            db.execSQL("CREATE TABLE contacts_table ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " contactName TEXT, contactTel TEXT, btToday TEXT, btExpiry TEXT, tvResult TEXT, byteArray BLOB);");
        }
        @Override
        public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){
            //Will not be called until SCHEMA_VERSION increases
            //Here we can upgrade the database e.g. add more table
        }

        /* Read all records from restaurants_table */
        public Cursor getAll(){
            return (getReadableDatabase().rawQuery(
                    "SELECT _id, contactName, contactTel," +
                            " btToday, btExpiry, tvResult, byteArray FROM contacts_table ORDER BY contactName", null));

        }






        public Cursor getById(String id){
            String[] args = {id};
            return (getReadableDatabase().rawQuery(
                    "SELECT _id, contactName,contactTel, " +
                            " btToday, btExpiry, tvResult ,byteArray FROM contacts_table WHERE _ID = ?", args));
        }




        /* Writes a record into restaurants_table */
        public void insert(String contactName,
                           String contactTel,String btToday, String btExpiry, String tvResult, byte[]byteArray) {
            ContentValues cv = new ContentValues();

            cv.put("contactName", contactName);

            cv.put("contactTel", contactTel);
            cv.put("btToday", btToday);
            cv.put("btExpiry", btExpiry);
            cv.put("tvResult", tvResult);

            cv.put("byteArray", byteArray);

            getWritableDatabase().insert("contacts_table", "contactName", cv);

        }

        /* Update a particular record in restaurants_table with id provided */

        public void update (String id, String contactName, String contactTel,String btToday, String btExpiry, String tvResult,
                            byte[]byteArray) {
            ContentValues cv= new ContentValues();
            String[] args ={id};
            cv.put("contactName", contactName);
            cv.put("contactTel", contactTel);
            cv.put("btToday", btToday);
            cv.put("btExpiry", btExpiry);
            cv.put("tvResult", tvResult);
            cv.put("byteArray", byteArray);

            getWritableDatabase().update("contacts_table", cv, "_ID = ?", args);
        }

        public void deleteItem(String ID)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE " + CONTACTS_COLUMN_ID + "='" + ID + "'");
            db.close();
        }





        public String getID(Cursor c) {return (c.getString(0));}

        public String getContactName(Cursor c){
            return (c.getString(1));
        }

        public String getContactTel(Cursor c){
            return (c.getString(2));
        }



        public String getbtToday(Cursor c){
            return (c.getString(3));
        }


        public String getbtExpiry(Cursor c){
            return (c.getString(4));
        }

        public String gettvResult(Cursor c){
            return (c.getString(5));
        }




        public byte[] getImage(Cursor c) {return(c.getBlob(6));}



    }
