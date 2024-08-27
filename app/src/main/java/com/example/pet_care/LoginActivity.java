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

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private DbHelper dbHelper;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbHelper = new DbHelper(this);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        signup = findViewById(R.id.textViewSignup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
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



        // Store the user's email in SharedPreferences
        saveUserEmail(email);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME_USERNAME,
                UserContract.UserEntry.COLUMN_NAME_PASSWORD
        };

        String selection = UserContract.UserEntry.COLUMN_NAME_EMAIL + " = ? AND " +
                UserContract.UserEntry.COLUMN_NAME_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

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
            // Login successful
            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
            saveUserEmail(email);
            // You may want to start a new activity or perform other actions here.
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            startActivity(intent);
        } else {
            // Login failed
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }

    private void saveUserEmail(String userEmail) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_email", userEmail);
        editor.apply();
    }
}
