package com.example.pet_care;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewProfileActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewContactNumber;
    private TextView textViewResidentialAddress;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        // Initialize your TextView elements
        textViewName = findViewById(R.id.textViewName);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);
        textViewResidentialAddress = findViewById(R.id.textViewResidentialAddress);

        dbHelper = new DbHelper(this);

        // Retrieve user details from the database
        displayUserProfile();
    }

    private void displayUserProfile() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                UserContract.UserEntry.COLUMN_NAME_FULL_NAME,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_CONTACT_NUMBER,
                UserContract.UserEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS
        };

        // Retrieve user email from SharedPreferences
        String userEmail = getUserEmail();

        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        Cursor cursor = db.query(
                UserContract.UserEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // Retrieve data from the cursor
            @SuppressLint("Range") String fullName = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_FULL_NAME));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_EMAIL));
            @SuppressLint("Range") String contactNumber = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_CONTACT_NUMBER));
            @SuppressLint("Range") String residentialAddress = cursor.getString(cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS));

            // Set the retrieved user details to TextView elements
            textViewName.setText("Full Name: " + fullName);
            textViewEmail.setText("Email: " + email);
            textViewContactNumber.setText("Contact Number: " + contactNumber);
            textViewResidentialAddress.setText("Residential Address: " + residentialAddress);
        }

        cursor.close();
        db.close();
    }

    private String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", "");
    }
}
