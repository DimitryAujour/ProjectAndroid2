package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "deliveryApp.db";
    private static final int DATABASE_VERSION = 1;

    // Routes Table
    private static final String TABLE_ROUTES = "Routes";
    public static final String COLUMN_ROUTE_ID = "route_id";
    public static final String COLUMN_ROUTE_NUMBER = "route_number";

    private static final String COLUMN_DELIVERY_PERSON_ID = "delivery_person_id";

    // Deliverers Table
    private static final String TABLE_DELIVERERS = "Deliverers";
    private static final String COLUMN_DELIVERER_ID = "deliverer_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phone";

    // Subscriptions Table
    private static final String TABLE_SUBSCRIPTIONS = "Subscriptions";
    static final String COLUMN_SUBSCRIPTION_ID = "subscription_id";
    private static final String COLUMN_CUSTOMER_NAME = "customer_name";
    private static final String COLUMN_SUBSCRIPTION_PRODUCT_TYPE = "subscription_product_type";  // Renamed
    private static final String COLUMN_QUANTITY = "quantity";

    // Products Table
    private static final String TABLE_PRODUCTS = "Products";
    public static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRODUCT_TYPE_NAME = "product_type_name";  // Renamed

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Routes table
        String createRoutesTable = "CREATE TABLE " + TABLE_ROUTES + "(" +
                COLUMN_ROUTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DELIVERY_PERSON_ID + " INTEGER);";
        db.execSQL(createRoutesTable);

        // Create Deliverers table
        String createDeliverersTable = "CREATE TABLE " + TABLE_DELIVERERS + "(" +
                COLUMN_DELIVERER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_PHONE + " TEXT);";
        db.execSQL(createDeliverersTable);

        // Create Subscriptions table
        String createSubscriptionsTable = "CREATE TABLE " + TABLE_SUBSCRIPTIONS + "(" +
                COLUMN_SUBSCRIPTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CUSTOMER_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_SUBSCRIPTION_PRODUCT_TYPE + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_ROUTE_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_ROUTE_ID + ") REFERENCES " + TABLE_ROUTES + "(" + COLUMN_ROUTE_ID + "));";  // This is the foreign key constraint
        db.execSQL(createSubscriptionsTable);



        // Create Products table
        String createProductsTable = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_TYPE_NAME + " TEXT);";  // Updated
        db.execSQL(createProductsTable);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);

        // Create tables again
        onCreate(db);
    }

    //Adding a new Route
    public long addRoute(int routeNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ROUTE_NUMBER, routeNumber);
        return db.insert(TABLE_ROUTES, null, contentValues);
    }
    //Getting all Routes
    public Cursor getAllRoutes() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ROUTES, null);
    }
    //For Subscriptions
    public long addSubscription(String customerName, String address, String productType, int quantity, int routeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CUSTOMER_NAME, customerName);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_SUBSCRIPTION_PRODUCT_TYPE, productType);
        contentValues.put(COLUMN_QUANTITY, quantity);
        contentValues.put(COLUMN_ROUTE_ID, routeId);
        return db.insert(TABLE_SUBSCRIPTIONS, null, contentValues);
    }
    //Getting all Subscriptions
    public Cursor getAllSubscriptions() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_SUBSCRIPTIONS, null);
    }
    //Adding a new Product
    public long addProduct(String productName, String productType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, productName);
        contentValues.put(COLUMN_PRODUCT_TYPE_NAME, productType);
        return db.insert(TABLE_PRODUCTS, null, contentValues);
    }
    //Getting all Products
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }

}
