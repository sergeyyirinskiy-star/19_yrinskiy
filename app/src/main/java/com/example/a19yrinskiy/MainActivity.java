package com.example.a19yrinskiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    ListView userList;

    DatabaseHelper databaseHelper;

    SQLiteDatabase db;

    Cursor userCursor;

    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list);

        userList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        Intent intent =
                                new Intent(
                                        getApplicationContext(),
                                        UserActivity.class);

                        intent.putExtra("id", id);

                        startActivity(intent);
                    }
                });

        databaseHelper =
                new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {

        super.onResume();

        db = databaseHelper.getReadableDatabase();

        userCursor = db.rawQuery(
                "select * from " +
                        DatabaseHelper.TABLE,
                null);

        String[] headers = new String[] {
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_YEAR,
                DatabaseHelper.COLUMN_GROUP,
                DatabaseHelper.COLUMN_PHONE
        };

        int[] views = new int[] {
                R.id.nameText,
                R.id.yearText,
                R.id.groupText,
                R.id.phoneText
        };

        userAdapter = new SimpleCursorAdapter(
                this,
                R.layout.ac_m,
                userCursor,
                headers,
                views,
                0);

        userList.setAdapter(userAdapter);
    }

    public void add(View view) {

        Intent intent =
                new Intent(this, UserActivity.class);

        startActivity(intent);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        db.close();

        userCursor.close();
    }
}