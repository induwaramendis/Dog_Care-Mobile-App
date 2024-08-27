package com.example.pet_care;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_edit_profile);

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
        loadUserProfile();
    }

    private void loadUserProfile() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Retrieve user email from SharedPreferences
        String userEmail = getUserEmail();

        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_FULL_NAME,
                UserContract.UserEntry.COLUMN_NAME_EMAIL,
                UserContract.UserEntry.COLUMN_NAME_CONTACT_NUMBER,
                UserContract.UserEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS,
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };

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
            // Populate EditText fields with user details
            editTextFullName.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_FULL_NAME)));
            editTextEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_EMAIL)));
            editTextContactNumber.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_CONTACT_NUMBER)));
            editTextResidentialAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS)));
            editTextUsername.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_USERNAME)));
            editTextPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserContract.UserEntry.COLUMN_NAME_PASSWORD)));
        }

        cursor.close();
        db.close();
    }

    private void updateProfile() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Retrieve user email from SharedPreferences
        String userEmail = getUserEmail();

        String fullName = editTextFullName.getText().toString();
        String email = editTextEmail.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString();
        String residentialAddress = editTextResidentialAddress.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_NAME_FULL_NAME, fullName);
        values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_NAME_CONTACT_NUMBER, contactNumber);
        values.put(UserContract.UserEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS, residentialAddress);
        values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.UserEntry.COLUMN_NAME_PASSWORD, password);

        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        int count = db.update(
                UserContract.UserEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            loadUserProfile();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", "");
    }
}
