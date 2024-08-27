package com.example.pet_care;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private RecyclerView recyclerViewResults;
    private SearchResultAdapter resultAdapter;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dbHelper = new DbHelper(this);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerViewResults = findViewById(R.id.recyclerViewResults);

        // Set up RecyclerView
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new SearchResultAdapter();
        recyclerViewResults.setAdapter(resultAdapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String searchText = editTextSearch.getText().toString().trim();

        if (TextUtils.isEmpty(searchText)) {
            Toast.makeText(this, "Please enter a pet name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = UserContract.PetEntry.COLUMN_NAME_PET_NAME + " LIKE ?";
        String[] selectionArgs = {"%" + searchText + "%"};

        Cursor cursor = db.query(
                UserContract.PetEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        List<Pet> searchResults = new ArrayList<>();

        // Process the search results and add them to the list
        while (cursor.moveToNext()) {
            int petId = cursor.getInt(cursor.getColumnIndexOrThrow(UserContract.PetEntry._ID));
            String petName = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PetEntry.COLUMN_NAME_PET_NAME));
            String petType = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PetEntry.COLUMN_NAME_PET_TYPE));
            String petSex = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PetEntry.COLUMN_NAME_PET_SEX));
            String petAge = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PetEntry.COLUMN_NAME_PET_AGE));
            String petNote = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PetEntry.COLUMN_NAME_PET_NOTE));
            String ownerEmail = cursor.getString(cursor.getColumnIndexOrThrow(UserContract.PetEntry.COLUMN_NAME_OWNER_EMAIL));

            Pet pet = new Pet(petId, petName, petType, petSex, petAge, petNote, ownerEmail);
            searchResults.add(pet);
        }

        // Update the RecyclerView with the search results
        resultAdapter.setSearchResults(searchResults);

        // Close the cursor and database when done
        cursor.close();
        db.close();
    }
}
