package com.example.pet_care;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION =4;


    private static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                    UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.UserEntry.COLUMN_NAME_FULL_NAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_CONTACT_NUMBER + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserContract.UserEntry.COLUMN_NAME_AGREED_TO_TERMS + " INTEGER)";

    private static final String SQL_CREATE_CAREGIVER_ENTRIES =
            "CREATE TABLE " + UserContract.CareGiverEntry.TABLE_NAME + " (" +
                    UserContract.CareGiverEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_FULL_NAME + " TEXT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_EMAIL + " TEXT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_CONTACT_NUMBER + " TEXT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS + " TEXT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    UserContract.CareGiverEntry.COLUMN_NAME_AGREED_TO_TERMS + " INTEGER)";

    // Updated table for pet details
    private static final String SQL_CREATE_PET_ENTRIES =
            "CREATE TABLE " + UserContract.PetEntry.TABLE_NAME + " (" +
                    UserContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    UserContract.PetEntry.COLUMN_NAME_PET_NAME + " TEXT," +
                    UserContract.PetEntry.COLUMN_NAME_PET_TYPE + " TEXT," +
                    UserContract.PetEntry.COLUMN_NAME_PET_SEX + " TEXT," +
                    UserContract.PetEntry.COLUMN_NAME_PET_AGE + " TEXT," +
                    UserContract.PetEntry.COLUMN_NAME_PET_NOTE + " TEXT," +
                    UserContract.PetEntry.COLUMN_NAME_OWNER_EMAIL + " TEXT)";

    private static final String SQL_CREATE_BOOKED_PETS =
            "CREATE TABLE " + UserContract.BookedPetsEntry.TABLE_NAME + " (" +
                    UserContract.BookedPetsEntry._ID + " INTEGER PRIMARY KEY," +
                    UserContract.BookedPetsEntry.COLUMN_NAME_BOOKING_ID + " TEXT," +
                    UserContract.BookedPetsEntry.COLUMN_NAME_PET_ID + " TEXT," +
                    UserContract.BookedPetsEntry.COLUMN_NAME_CUSTOMER_EMAIL + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.UserEntry.TABLE_NAME + "; " +
                    "DROP TABLE IF EXISTS " + UserContract.CareGiverEntry.TABLE_NAME + ";" +
                    "DROP TABLE IF EXISTS " + UserContract.PetEntry.TABLE_NAME;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER_ENTRIES);
        db.execSQL(SQL_CREATE_PET_ENTRIES);
        db.execSQL(SQL_CREATE_CAREGIVER_ENTRIES);
        db.execSQL(SQL_CREATE_BOOKED_PETS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}