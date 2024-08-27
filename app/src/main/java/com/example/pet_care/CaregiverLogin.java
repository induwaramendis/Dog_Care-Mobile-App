package com.example.pet_care;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet_care.UserContract.CareGiverEntry;

public class CaregiverLogin extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private DbHelper dbHelper;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_login);

        dbHelper = new DbHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        signup = findViewById(R.id.textViewSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CaregiverLogin.this, CaregiverSignup.class);
                startActivity(intent);
            }
        });

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        // Store the caregiver's email in SharedPreferences
        saveCaregiverEmail(email);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CareGiverEntry._ID,
                CareGiverEntry.COLUMN_NAME_USERNAME,
                CareGiverEntry.COLUMN_NAME_PASSWORD
        };

        String selection = CareGiverEntry.COLUMN_NAME_EMAIL + " = ? AND " +
                CareGiverEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

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
            // Login successful
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CaregiverLogin.this, CaregiverHome.class);
            startActivity(intent);
        } else {
            // Login failed
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }



    private void saveCaregiverEmail(String caregiverEmail) {
        SharedPreferences sharedPreferences = getSharedPreferences("caregiver_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("caregiver_email", caregiverEmail);
        editor.apply();
    }
}
