package com.example.proyectofinal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.example.proyectofinal.models.Anime;
import com.example.proyectofinal.models.User;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION =	1;
    private static final String DATABASE_NAME = "FINALDB";
    private final static String TABLE_ANIME ="ANIME";
    private final static String TABLE_USER ="USER";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_PHONE = "PHONE";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_USERNAME = "USERNAME";
    private static final String COLUMN_PASSWORD = "PASSWORD";
    private static final String COLUMN_CURRENT = "CURRENT";
    private static final String COLUMN_EPISODE = "EPISODE";
    private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    private static final String COLUMN_URLIMAGE = "URLIMAGE";
    private static final String COLUMN_IDUSER = "IDUSER";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ANIME_TABLE = "CREATE	TABLE " + TABLE_ANIME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME+ " TEXT," + COLUMN_CURRENT + " INTEGER," + COLUMN_EPISODE+ " INTEGER," + COLUMN_DESCRIPTION+ " TEXT," + COLUMN_URLIMAGE+ " TEXT," + COLUMN_IDUSER+ " INTEGER, FOREIGN KEY(" + COLUMN_IDUSER + ") REFERENCES USER(ID));";
        String CREATE_USER_TABLE = "CREATE	TABLE " + TABLE_USER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME+ " TEXT," + COLUMN_PHONE + " INTEGER," + COLUMN_EMAIL+ " TEXT," + COLUMN_USERNAME+ " TEXT," + COLUMN_PASSWORD+ " TEXT);";
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ANIME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public List<Anime> listAnime(int idUser){
        String sql = "select * from " + TABLE_ANIME + " where " + COLUMN_IDUSER + "	= ?";
        SQLiteDatabase db = this.getReadableDatabase();
        List<Anime> listAnime = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,new String[] {String.valueOf(idUser)});
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int current = cursor.getInt(2);
                int episode = cursor.getInt(3);
                String desc = cursor.getString(4);
                String url = cursor.getString(5);
                int user = cursor.getInt(6);
                listAnime.add(new Anime(id, name, current, episode, desc, url, user));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listAnime;
    }

    public  Anime getAnime(int id){
        String sql = "select * from " + TABLE_ANIME + " where " + COLUMN_ID + "	= ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Anime mAnime = new Anime();

        Cursor cursor=db.rawQuery(sql, new String[] { String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
                mAnime.setName(cursor.getString(1));
                mAnime.setCurrent(cursor.getInt(2));
                mAnime.setEpisode(cursor.getInt(3));
                mAnime.setDescription(cursor.getString(4));
                mAnime.setUrlImage( cursor.getString(5));
                mAnime.setIdUser(cursor.getInt(6));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return mAnime;
    }

    public void newAnime(Anime mAnime){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, mAnime.getName());
        values.put(COLUMN_CURRENT, mAnime.getCurrent());
        values.put(COLUMN_EPISODE, mAnime.getEpisode());
        values.put(COLUMN_DESCRIPTION, mAnime.getDescription());
        values.put(COLUMN_URLIMAGE, mAnime.getUrlImage());
        values.put(COLUMN_IDUSER, mAnime.getIdUser());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_ANIME, null, values);
    }

    public  void updateAnime(Anime mAnime, int id){
        ContentValues values= new ContentValues();
        values.put(COLUMN_NAME, mAnime.getName());
        values.put(COLUMN_CURRENT, mAnime.getCurrent());
        values.put(COLUMN_EPISODE, mAnime.getEpisode());
        values.put(COLUMN_DESCRIPTION, mAnime.getDescription());
        values.put(COLUMN_URLIMAGE, mAnime.getUrlImage());
        values.put(COLUMN_IDUSER, mAnime.getIdUser());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_ANIME, values, COLUMN_ID + "=" + id,null);
    }

    public void deleteAnime(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ANIME, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }

    public User getUser(int id){
        String sql = "select * from " + TABLE_USER + " where " + COLUMN_ID + "	= ? " ;
        SQLiteDatabase db = this.getReadableDatabase();

        User mUser = new User();

        Cursor cursor=db.rawQuery(sql, new String[] { String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
                mUser.setId(cursor.getInt(0));
                mUser.setName(cursor.getString(1));
                mUser.setPhone(cursor.getInt(2));
                mUser.setEmail(cursor.getString(3));
                mUser.setUserName(cursor.getString(4));
                mUser.setPassword(cursor.getString(5));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return mUser;
    }
    public User login(String userName, String password){
        String sql = "select * from " + TABLE_USER + " where " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?" ;
        SQLiteDatabase db = this.getReadableDatabase();

        User mUser = new User();

        Cursor cursor=db.rawQuery(sql, new String[] { userName, password});
        if(cursor.moveToFirst()){
            do{
                mUser.setId(cursor.getInt(0));
                mUser.setName(cursor.getString(1));
                mUser.setPhone(cursor.getInt(2));
                mUser.setEmail(cursor.getString(3));
                mUser.setUserName(cursor.getString(4));
                mUser.setPassword(cursor.getString(5));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return mUser;
    }

    public User forgotPass(String userName, String email){
        String sql = "select * from " + TABLE_USER + " where " + COLUMN_USERNAME + " = ? AND " + COLUMN_EMAIL + " = ?" ;
        SQLiteDatabase db = this.getReadableDatabase();

        User mUser = new User();

        Cursor cursor=db.rawQuery(sql, new String[] { userName, email});
        if(cursor.moveToFirst()){
            do{
                mUser.setId(cursor.getInt(0));
                mUser.setName(cursor.getString(1));
                mUser.setPhone(cursor.getInt(2));
                mUser.setEmail(cursor.getString(3));
                mUser.setUserName(cursor.getString(4));
                mUser.setPassword(cursor.getString(5));

            }while (cursor.moveToNext());
        }
        cursor.close();
        return mUser;
    }
    public void newUser(User mUser){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, mUser.getName());
        values.put(COLUMN_PHONE, mUser.getPhone());
        values.put(COLUMN_EMAIL, mUser.getEmail());
        values.put(COLUMN_USERNAME, mUser.getUserName());
        values.put(COLUMN_PASSWORD, mUser.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USER, null, values);
    }

    public  void updateUser(User mUser, int id){
        ContentValues values= new ContentValues();
        values.put(COLUMN_NAME, mUser.getName());
        values.put(COLUMN_PHONE, mUser.getPhone());
        values.put(COLUMN_EMAIL, mUser.getEmail());
        values.put(COLUMN_USERNAME, mUser.getUserName());
        values.put(COLUMN_PASSWORD, mUser.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USER, values, COLUMN_ID + "=" + id,null);
    }
}
