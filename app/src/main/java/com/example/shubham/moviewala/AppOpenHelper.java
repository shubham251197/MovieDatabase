package com.example.shubham.moviewala;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shubham on 04-08-2017.
 */

public class AppOpenHelper extends SQLiteOpenHelper {

    public static final String DB_TABLENAME ="moviewala";
    public static final String DB_TITLE ="title";
    public static final String DB_POSTER ="poster";
    public static final String DB_ID ="id";
    public static final String DB_GENRE ="genre";
    public static final String DB_VOTE_AVG ="voteavg";
    public static final String DB_WATCHLIST ="watchist";


    public AppOpenHelper(Context context) {
        super(context, "moviewala.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query="create table "+DB_TABLENAME+" ( "+DB_TITLE+" text, "+DB_POSTER+" text, "+DB_GENRE+" text, "+DB_ID+" integer, "+DB_VOTE_AVG+" integer, "+DB_WATCHLIST+" integer);";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
