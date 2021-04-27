package com.example.quickbookfacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.*;

public class MainActivity extends AppCompatActivity {

    //Declaring Widgets
    EditText editTextBookTitle, editTextAuthor, editTextRating;
    TextView textViewPageTitle, textViewBookTitle, textViewAuthor, textViewRating;
    Button buttonSave, buttonGetInfo;
    FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instantiating Widgets
        textViewPageTitle = findViewById(R.id.textViewPageTitle);
        textViewBookTitle = findViewById(R.id.textViewBookTitle);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        textViewRating = findViewById(R.id.textViewRating);

        editTextBookTitle = findViewById(R.id.editTextBookTitle);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextRating = findViewById(R.id.editTextRating);

        buttonSave = findViewById(R.id.buttonSave);
        buttonGetInfo = findViewById(R.id.buttonGetInfo);

        // Adding A Click event for the Save button (Send values to SQLite when clicked)
        buttonSave.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)  {
                // Save Inputted Values to SQLite
                SaveValuesSQLite();
            }
        });

        // Adding A Click event for button (Executing the convert method when clicked)
        buttonGetInfo.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v)  {
                // Navigate to second view
                startActivity(new Intent(MainActivity.this, DBActivity.class));
            }
        });

        // Initialize SQLite DB
        dbHelper = new FeedReaderDbHelper(getApplicationContext());
    }

    private void SaveValuesSQLite() {
        // This method will take the values entered in the 3 editText fields and send them to SQLite

        String valueBookTitle = editTextBookTitle.getText().toString();
        String valueAuthor = editTextAuthor.getText().toString();
        String valueRating = editTextRating.getText().toString();

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, valueBookTitle);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AUTHOR, valueAuthor);
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_RATING, valueRating);

        // Insert the new row, returning the primary key value of the new row
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }
}