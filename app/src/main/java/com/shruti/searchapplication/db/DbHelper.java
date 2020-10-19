package com.shruti.searchapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.shruti.searchapplication.dao.ArticlesResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper mInstance = null;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DB";
    private static final String TABLE_NAME = "Articles";
    private static final String KEY_ID = "id";
    private static final String KEY_PUBLICATION_DATE = "publication_date";
    private static final String KEY_ARTICLE_TYPE = "article_type";
    private static final String KEY_ABSTRACT = "abstract";
    private static final String KEY_TITLE_DISPLAY = "title_display";


    private static final String[] COLUMNS = {KEY_ID, KEY_PUBLICATION_DATE, KEY_ARTICLE_TYPE, KEY_ABSTRACT, KEY_TITLE_DISPLAY};

    public static DbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public DbHelper(@Nullable Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + KEY_ID + " VARCHAR PRIMARY KEY , "
                + KEY_PUBLICATION_DATE + " VARCHAR," + KEY_ARTICLE_TYPE + " VARCHAR,"
                + KEY_ABSTRACT + " VARCHAR," + KEY_TITLE_DISPLAY + " VARCHAR)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 > i) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            this.onCreate(sqLiteDatabase);
        }
    }

    public List<ArticlesResponse.Docs> getDocs(String title) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ArticlesResponse.Docs> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                KEY_TITLE_DISPLAY + " LIKE ?", // c. selections
                new String[]{"%" + String.valueOf(title) + "%"}, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        ArticlesResponse.Docs docs = new ArticlesResponse.Docs();
        if (cursor != null && cursor.getCount()>0) {
            do {
                docs.setId(cursor.getString(0));
                docs.setPublication_date(cursor.getString(1));
                docs.setArticle_type(cursor.getString(2));
                List<String> abs = new ArrayList<>();
                abs.add(cursor.getString(3).replaceAll("\\[","").replaceAll("\\]",""));
                docs.setAbstract(abs);
                docs.setAbstractData(Arrays.toString(docs.getAbstract().toArray(new String[0]))
                        .replaceAll("\\[","").replaceAll("\\]",""));
                docs.setTitle_display(cursor.getString(4));
                list.add(docs);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public List<ArticlesResponse.Docs> allArticles() {

        List<ArticlesResponse.Docs> docsList = new LinkedList<ArticlesResponse.Docs>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArticlesResponse.Docs docs = null;

        if (cursor.moveToFirst()) {
            do {
                docs = new ArticlesResponse.Docs();
                docs.setId(cursor.getString(0));
                docs.setPublication_date(cursor.getString(1));
                docs.setArticle_type(cursor.getString(2));
                List<String> abs = new ArrayList<>();
                abs.add(cursor.getString(3).replaceAll("\\[","").replaceAll("\\]",""));
                docs.setAbstract(abs);
                docs.setAbstractData(Arrays.toString(docs.getAbstract().toArray(new String[0]))
                        .replaceAll("\\[","").replaceAll("\\]",""));
                docs.setTitle_display(cursor.getString(4));
                docsList.add(docs);
            } while (cursor.moveToNext());
        }

        return docsList;
    }

    public void addPlayer(ArticlesResponse.Docs docs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, docs.getId());
        values.put(KEY_PUBLICATION_DATE, docs.getPublication_date());
        values.put(KEY_ARTICLE_TYPE, docs.getArticle_type());
        values.put(KEY_ABSTRACT, Arrays.toString(docs.getAbstract().toArray(new String[0])));
        values.put(KEY_TITLE_DISPLAY, docs.getTitle_display());
        // insert
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

}
