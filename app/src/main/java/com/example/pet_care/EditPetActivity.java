package com.example.pet_care;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditPetActivity extends AppCompatActivity {

    private EditText editTextPetName;
    private EditText editTextPetType;
    private EditText editTextPetSex;
    private EditText editTextPetAge;
    private EditText editTextPetNote;

    private DbHelper dbHelper;

    private ListView listViewPets;
    private long selectedPetId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);

        dbHelper = new DbHelper(this);
        listViewPets = findViewById(R.id.listViewPets);

        editTextPetName = findViewById(R.id.editTextPetName);
        editTextPetType = findViewById(R.id.editTextPetType);
        editTextPetSex = findViewById(R.id.editTextPetSex);
        editTextPetAge = findViewById(R.id.editTextPetAge);
        editTextPetNote = findViewById(R.id.editTextPetNote);

        hidePetDetailsLayout();

        listViewPets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Retrieve the selected item's data from the cursor
                selectedPetId = id;
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (cursor != null) {
                    @SuppressLint("Range") String petName = cursor.getString(cursor.getColumnIndex(UserContract.PetEntry.COLUMN_NAME_PET_NAME));
                    @SuppressLint("Range") String petType = cursor.getString(cursor.getColumnIndex(UserContract.PetEntry.COLUMN_NAME_PET_TYPE));
                    @SuppressLint("Range") String petSex = cursor.getString(cursor.getColumnIndex(UserContract.PetEntry.COLUMN_NAME_PET_SEX));
                    @SuppressLint("Range") String petAge = cursor.getString(cursor.getColumnIndex(UserContract.PetEntry.COLUMN_NAME_PET_AGE));
                    @SuppressLint("Range") String petNote = cursor.getString(cursor.getColumnIndex(UserContract.PetEntry.COLUMN_NAME_PET_NOTE));

                    // Set the retrieved data to the EditText fields
                    editTextPetName.setText(petName);
                    editTextPetType.setText(petType);
                    editTextPetSex.setText(petSex);
                    editTextPetAge.setText(petAge);
                    editTextPetNote.setText(petNote);

                    showPetDetailsLayout();
                }
            }
        });

        Button btnUpdatePet = findViewById(R.id.btnUpdatePet);
        btnUpdatePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updatePet();
            }
        });

        Button btnDeletePet = findViewById(R.id.btnDeletePet);
        btnDeletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletePet();
            }
        });

        // Load user details for editing
        displayUserPets();
    }

    private void showPetDetailsLayout() {
        LinearLayout petDetailsContainer = findViewById(R.id.petDetailsContainer);
        petDetailsContainer.setVisibility(View.VISIBLE);
    }

    private void hidePetDetailsLayout() {
        LinearLayout petDetailsContainer = findViewById(R.id.petDetailsContainer);
        petDetailsContainer.setVisibility(View.GONE);
    }

    private long getSelectedPetId() {
        return selectedPetId;
    }

    private void displayUserPets() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Retrieve logged-in user's email
        String userEmail = getUserEmail();

        String[] projection = {
                UserContract.PetEntry._ID,
                UserContract.PetEntry.COLUMN_NAME_PET_NAME,
                UserContract.PetEntry.COLUMN_NAME_PET_TYPE,
                UserContract.PetEntry.COLUMN_NAME_PET_SEX,
                UserContract.PetEntry.COLUMN_NAME_PET_AGE,
                UserContract.PetEntry.COLUMN_NAME_PET_NOTE
        };

        String selection = UserContract.PetEntry.COLUMN_NAME_OWNER_EMAIL + " = ?";
        String[] selectionArgs = {userEmail};

        Cursor cursor = db.query(
                UserContract.PetEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String[] fromColumns = {
                UserContract.PetEntry.COLUMN_NAME_PET_NAME,
                UserContract.PetEntry.COLUMN_NAME_PET_TYPE,
                UserContract.PetEntry.COLUMN_NAME_PET_SEX,
                UserContract.PetEntry.COLUMN_NAME_PET_AGE,
                UserContract.PetEntry.COLUMN_NAME_PET_NOTE
        };

        int[] toViews = {
                R.id.textPetName,
                R.id.textPetType,
                R.id.textPetSex,
                R.id.textPetAge,
                R.id.textPetNote
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item_pet,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listViewPets.setAdapter(adapter);
    }

    private void updatePet() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Retrieve user email from SharedPreferences
        String userEmail = getUserEmail();

        String petName = editTextPetName.getText().toString();
        String petType = editTextPetType.getText().toString();
        String petSex = editTextPetSex.getText().toString();
        String petAge = editTextPetAge.getText().toString();
        String petNote = editTextPetNote.getText().toString();

        ContentValues values = new ContentValues();
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_NAME, petName);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_TYPE, petType);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_SEX, petSex);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_AGE, petAge);
        values.put(UserContract.PetEntry.COLUMN_NAME_PET_NOTE, petNote);

        long selectedPetId = getSelectedPetId();

        String selection = UserContract.PetEntry._ID + " = ? AND " + UserContract.PetEntry.COLUMN_NAME_OWNER_EMAIL + " = ?";
        String[] selectionArgs = {String.valueOf(selectedPetId), userEmail};

        int count = db.update(
                UserContract.PetEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Pet updated successfully!", Toast.LENGTH_SHORT).show();
            displayUserPets();
            hidePetDetailsLayout();
        } else {
            Toast.makeText(this, "Failed to update pet", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void deletePet() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long selectedPetId = getSelectedPetId();

        String selection = UserContract.PetEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(selectedPetId)};

        int count = db.delete(
                UserContract.PetEntry.TABLE_NAME,
                selection,
                selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Pet deleted successfully!", Toast.LENGTH_SHORT).show();
            displayUserPets();
            editTextPetName.setText("");
            editTextPetType.setText("");
            editTextPetSex.setText("");
            editTextPetAge.setText("");
            editTextPetNote.setText("");
            hidePetDetailsLayout();
        } else {
            Toast.makeText(this, "Failed to delete pet", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private String getUserEmail() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_email", "");
    }
}
