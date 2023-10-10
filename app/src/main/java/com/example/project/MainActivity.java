package com.example.project;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Add a route
        long routeId = databaseHelper.addRoute(1);

        // Add a subscription
        long subscriptionId = databaseHelper.addSubscription("Customer1", "Address1", "ProductType1", 2, (int) routeId);

        // Add a product
        long productId = databaseHelper.addProduct("ProductName1", "ProductType1");

        // Retrieve and log all routes
        Cursor routeCursor = databaseHelper.getAllRoutes();
        while (routeCursor.moveToNext()) {
            int columnIndex = routeCursor.getColumnIndex(DatabaseHelper.COLUMN_ROUTE_ID);
            if (columnIndex != -1) {
                int route = routeCursor.getInt(columnIndex);
                Log.d("Database", "Route ID: " + route);
            }
        }

        // Retrieve and log all subscriptions
        Cursor subscriptionCursor = databaseHelper.getAllSubscriptions();
        while (subscriptionCursor.moveToNext()) {
            int columnIndex = subscriptionCursor.getColumnIndex(DatabaseHelper.COLUMN_SUBSCRIPTION_ID);
            if (columnIndex != -1) {
                int subscription = subscriptionCursor.getInt(columnIndex);
                Log.d("Database", "Subscription ID: " + subscription);
            }
        }

        // Retrieve and log all products
        Cursor productCursor = databaseHelper.getAllProducts();
        while (productCursor.moveToNext()) {
            int columnIndex = productCursor.getColumnIndex(DatabaseHelper.COLUMN_PRODUCT_ID);
            if (columnIndex != -1) {
                int product = productCursor.getInt(columnIndex);
                Log.d("Database", "Product ID: " + product);
            }
        }
    }
}
