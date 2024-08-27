package com.example.pet_care;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPetActivity extends AppCompatActivity {

    private EditText editTextPetName;
    private EditText editTextPetType;
    private EditText editTextPetSex;
    private EditText editTextPetAge;
    private EditText editTextPetNote;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        dbHelper = new DbHelper(this);

        editTextPetName = findViewById(R.id.editTextPetName);
        editTextPetType = findViewById(R.id.editTextPetType);
        editTextPetSex = findViewById(R.id.editTextPetSex);
        editTextPetAge = findViewById(R.id.editTextPetAge);
        editTextPetNote = findViewById(R.id.editTextPetNote);

        Button btnAddPet = findViewById(R.id.btnAddPet);
        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPet();
            }
        });
    }

    private void addPet() {
        String petName = editTextPetName.getText().toString();
        String petType = editTextPetType.getText().toString();
        String petSex = editTextPetSex.getText().toString();
        String petAge = editTextPetAge.getText().toString();
        String petNote = editTextPetNote.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_NAME, petName);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_TYPE, petType);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_SEX, petSex);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_AGE, petAge);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_NOTE, petNote);
        values.put(UserContract.PetEntry.COLUMN_NAME_OWNER_EMAIL, getCurrentUserEmail());

        long newRowId = db.insert(UserContract.PetEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Pet added successfully", Toast.LENGTH_SHORT).show();
            // After adding the pet, start the ViewPetsActivity to refresh the pet list
            startActivity(new Intent(this, ViewPetsActivity.class));
            finish(); // Optional: Finish the current activity to prevent going back to it with back button
        } else {
            Toast.makeText(this, "Failed to add pet", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private String getCurrentUserEmail() {
        // Implement the logic to get the current user's email
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", "");
    }
}

