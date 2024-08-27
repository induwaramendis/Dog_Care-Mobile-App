package com.example.pet_care;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pet_care.UserContract.PetEntry;

public class CaregiverViewPetsActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private ListView listViewPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_view_pets);

        dbHelper = new DbHelper(this);
        listViewPets = findViewById(R.id.listViewPets);

        displayAllPets();
    }
    public void onBookPetClick(View view) {
        // Retrieve pet information from the clicked item
        Cursor cursor = (Cursor) listViewPets.getItemAtPosition(listViewPets.getPositionForView(view));

        // Extract pet details
        String petId = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_NAME_ID));
        String ownerEmail = cursor.getString(cursor.getColumnIndexOrThrow(PetEntry.COLUMN_NAME_OWNER_EMAIL));

        // Insert into BookedPetsEntry table
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserContract.BookedPetsEntry.COLUMN_NAME_PET_ID, petId);
        values.put(UserContract.BookedPetsEntry.COLUMN_NAME_CUSTOMER_EMAIL, ownerEmail);

        long newRowId = db.insert(UserContract.BookedPetsEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            // Successful booking
            // You may want to show a success message or perform additional actions
            Toast.makeText(this, "Pet booked successfully!", Toast.LENGTH_SHORT).show();
            // Reload the pets list to reflect the booking changes

        } else {
            // Failed to book pet
            Toast.makeText(this, "Failed to book pet", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


    private void displayAllPets() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_NAME_PET_NAME,
                PetEntry.COLUMN_NAME_PET_TYPE,
                PetEntry.COLUMN_NAME_PET_SEX,
                PetEntry.COLUMN_NAME_PET_AGE,
                PetEntry.COLUMN_NAME_PET_NOTE,
                PetEntry.COLUMN_NAME_OWNER_EMAIL
        };

        // No selection or selectionArgs, fetch all pets
        Cursor cursor = db.query(
                PetEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        String[] fromColumns = {
                PetEntry.COLUMN_NAME_PET_NAME,
                PetEntry.COLUMN_NAME_PET_TYPE,
                PetEntry.COLUMN_NAME_PET_SEX,
                PetEntry.COLUMN_NAME_PET_AGE,
                PetEntry.COLUMN_NAME_PET_NOTE
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
                R.layout.list_item_caregiver_pet,
                cursor,
                fromColumns,
                toViews,
                0
        );

        listViewPets.setAdapter(adapter);
    }
}
