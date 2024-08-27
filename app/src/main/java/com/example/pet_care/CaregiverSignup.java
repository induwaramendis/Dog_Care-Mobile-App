package com.example.pet_care;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class CaregiverSignup extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextEmail;
    private EditText editTextContactNumber;
    private EditText editTextResidentialAddress;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private CheckBox checkboxTerms;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_signup);

        dbHelper = new DbHelper(this);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        editTextResidentialAddress = findViewById(R.id.editTextResidentialAddress);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        checkboxTerms = findViewById(R.id.checkboxTerms);

        Button btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    private void signUp() {
        String fullName = editTextFullName.getText().toString();
        String email = editTextEmail.getText().toString();
        String contactNumber = editTextContactNumber.getText().toString();
        String residentialAddress = editTextResidentialAddress.getText().toString();
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        boolean agreedToTerms = checkboxTerms.isChecked();

        if (!agreedToTerms) {
            // Show an error message or handle the case where terms are not agreed
            return;
        }

        if (!password.equals(confirmPassword)) {
            // Show an error message or handle the case where passwords don't match
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_FULL_NAME, fullName);
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_EMAIL, email);
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_CONTACT_NUMBER, contactNumber);
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_RESIDENTIAL_ADDRESS, residentialAddress);
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_USERNAME, username);
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_PASSWORD, password);
        values.put(UserContract.CareGiverEntry.COLUMN_NAME_AGREED_TO_TERMS, agreedToTerms ? 1 : 0);

        long newRowId = db.insert(UserContract.CareGiverEntry.TABLE_NAME, null, values);

        db.close();

        finish();
    }
}
