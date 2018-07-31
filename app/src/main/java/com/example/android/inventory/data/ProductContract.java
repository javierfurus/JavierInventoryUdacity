package com.example.android.inventory.data;

import android.provider.BaseColumns;

public final class ProductContract {

    private ProductContract() {
    }

    public static final class ProductEntry implements BaseColumns {
        public static final String TABLE_NAME = "products";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PROD_NAME = "productname";
        public static final String COLUMN_PROD_PRICE = "price";
        public static final String COLUMN_PROD_QUANTITY = "quantity";
        public static final String COLUMN_SUPL_NAME = "suppliername";
        public static final String COLUMN_SUPL_PHONE_NUMBER = "phone";

    }

}
