package com.example.android.inventory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.inventory.data.ProductContract.ProductEntry;
import com.example.android.inventory.data.ProductDbHelper;

/**
 * Displays list of products that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    ProductDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        mDbHelper = new ProductDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the products database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Perform this raw SQL query "SELECT * FROM products"
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PROD_NAME,
                ProductEntry.COLUMN_PROD_PRICE,
                ProductEntry.COLUMN_PROD_QUANTITY,
                ProductEntry.COLUMN_SUPL_NAME,
                ProductEntry.COLUMN_SUPL_PHONE_NUMBER};

        // Perform a query on the products table
        Cursor cursor = db.query(
                ProductEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = findViewById(R.id.text_view_product);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The products table contains <number of rows in Cursor> products.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The products table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(ProductEntry._ID + " - " +
                    ProductEntry.COLUMN_PROD_NAME + " - " +
                    ProductEntry.COLUMN_PROD_PRICE + " - " +
                    ProductEntry.COLUMN_PROD_QUANTITY + " - " +
                    ProductEntry.COLUMN_SUPL_NAME + " - " +
                    ProductEntry.COLUMN_SUPL_PHONE_NUMBER + "\n"
            );


            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
            int prodNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PROD_NAME);
            int prodPriceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PROD_PRICE);
            int prodQuantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PROD_QUANTITY);
            int suplNameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPL_NAME);
            int suplPhoneColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_SUPL_PHONE_NUMBER);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentProdName = cursor.getString(prodNameColumnIndex);
                int currentQuantity = cursor.getInt(prodQuantityColumnIndex);
                int currentProductPrice = cursor.getInt(prodPriceColumnIndex);
                String currentSuplName = cursor.getString(suplNameColumnIndex);
                String currentSuplPhone = cursor.getString(suplPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - "
                        + currentProdName + " - "
                        + currentProductPrice + " - "
                        + currentQuantity + " - "
                        + currentSuplName + " - "
                        + currentSuplPhone + " \n "));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertProduct() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PROD_NAME, "Sony Xperia ABCD");
        values.put(ProductEntry.COLUMN_PROD_PRICE, 300);
        values.put(ProductEntry.COLUMN_PROD_QUANTITY, 10);
        values.put(ProductEntry.COLUMN_SUPL_NAME, "Sony");
        values.put(ProductEntry.COLUMN_SUPL_PHONE_NUMBER, "1 703 455 675");

        long newRowId = db.insert(ProductEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row ID" + newRowId);
    }
}