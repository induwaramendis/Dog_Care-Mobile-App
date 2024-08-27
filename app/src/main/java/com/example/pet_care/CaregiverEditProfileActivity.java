package com.example.pet_care;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet_care.UserContract.CareGiverEntry;

public class CaregiverEditProfileActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextContactNumber;
    private EditText editTextResidentialAddress;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_edit_profile);

        dbHelper = new DbHelper(this);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextResidentialAddress = findViewById(R.id.editTextResidentialAddress);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);

        Button btnUpdateProfile = findViewById(R.id.btnUpdateProfile);
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile();
            }
        });

        // Load user details for editing
        loadCaregiverProfile();
    }

    private void loadCaregiverProfile() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Retrieve caregiver email from SharedPreferences
        String caregiverEmail = getCaregiverEmail();

        String[] projection = {
                CareGiverEntry._ID,
                CareGiverEntry.COLUMN_NAME_FULL_NAME,
                CareGiverEntry.COLUMN_NAME_EMAIL,
                CareGiverEntry.COLUMN_NAME_CONTACT_NUMBER,
                CareGiverEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS,
                CareGiverEntry.COLUMN_NAME_USERNAME,
                CareGiverEntry.COLUMN_NAME_PASSWORD
        };

        String selection = CareGiverEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {caregiverEmail};

        Cursor cursor = db.query(
                CareGiverEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // Log retrieved values
            Log.d("CaregiverEditProfile", "Full Name: " + cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_FULL_NAME)));
            Log.d("CaregiverEditProfile", "Email: " + cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_EMAIL)));
            Log.d("CaregiverEditProfile", "Contact Number: " + cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_CONTACT_NUMBER)));
            Log.d("CaregiverEditProfile", "Residential Address: " + cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS)));
            Log.d("CaregiverEditProfile", "Username: " + cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_USERNAME)));
            Log.d("CaregiverEditProfile", "Password: " + cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_PASSWORD)));

            // Populate EditText fields with caregiver details
            editTextFullName.setText(cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_FULL_NAME)));
            editTextEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_EMAIL)));
            editTextContactNumber.setText(cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_CONTACT_NUMBER)));
            editTextResidentialAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS)));
            editTextUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_USERNAME)));
            editTextPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow(CareGiverEntry.COLUMN_NAME_PASSWORD)));
        } else {
            Log.e("CaregiverEditProfile", "No data found for caregiver with email: " + caregiverEmail);
        }

        cursor.close();
        db.close();
    }

    private void updateProfile() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Retrieve user email from SharedPreferences
        String caregiverEmail = getCaregiverEmail();

        String fullName = editTextFullName.getText().toString();
        String email = editTextEmail.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString();
        String residentialAddress = editTextResidentialAddress.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        ContentValues values = new ContentValues();
        values.put(CareGiverEntry.COLUMN_NAME_FULL_NAME, fullName);
        values.put(CareGiverEntry.COLUMN_NAME_EMAIL, email);
        values.put(CareGiverEntry.COLUMN_NAME_CONTACT_NUMBER, contactNumber);
        values.put(CareGiverEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS, residentialAddress);
        values.put(CareGiverEntry.COLUMN_NAME_USERNAME, username);
        values.put(CareGiverEntry.COLUMN_NAME_PASSWORD, password);

        String selection = CareGiverEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {caregiverEmail};

        int count = db.update(
                CareGiverEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            loadCaregiverProfile();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private String getCaregiverEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("caregiver_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("caregiver_email", "");
    }
}

