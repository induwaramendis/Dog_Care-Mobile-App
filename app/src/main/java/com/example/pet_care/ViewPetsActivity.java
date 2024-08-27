package com.example.pet_care;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ViewPetsActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    private ListView listViewPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pets);

        dbHelper = new DbHelper(this);
        listViewPets = findViewById(R.id.listViewPets);

        displayAllPets();
    }


    private void displayAllPets() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                UserContract.PetEntry._ID,
                UserContract.PetEntry.COLUMN_NAME_PET_NAME,
                UserContract.PetEntry.COLUMN_NAME_PET_TYPE,
                UserContract.PetEntry.COLUMN_NAME_PET_SEX,
                UserContract.PetEntry.COLUMN_NAME_PET_AGE,
                UserContract.PetEntry.COLUMN_NAME_PET_NOTE,
                UserContract.PetEntry.COLUMN_NAME_OWNER_EMAIL
        };

        // No selection or selectionArgs, fetch all pets
        Cursor cursor = db.query(
                UserContract.PetEntry.TABLE_NAME,
                projection,
                null,
                null,
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
}
