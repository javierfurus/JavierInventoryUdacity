package com.example.android.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventory.data.ProductContract.ProductEntry;

public class ProductDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory.db";

    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "inventory";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUCT_TABLE = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_PROD_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PROD_PRICE + " TEXT, "
                + ProductEntry.COLUMN_PROD_QUANTITY + " INTEGER NOT NULL, "
                + ProductEntry.COLUMN_SUPL_PHONE_NUMBER + " TEXT, "
                + ProductEntry.COLUMN_SUPL_NAME + " TEXT );";

        db.execSQL(SQL_CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
