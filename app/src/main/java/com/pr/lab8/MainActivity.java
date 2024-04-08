package com.pr.lab8;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Note> notes = new ArrayList<>();
    ArrayAdapter<Note> adapter;

    void updateList() {
        notes.clear();
        notes.addAll(DB.getInstance(getApplicationContext()).getAllNotes());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Note note = adapter.getItem(position);


            Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
            intent.putExtra("id", note.getId());
            intent.putExtra("text", note.getText());

            startActivityForResult(intent, 0);
        });



        updateList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.println(Log.ERROR, "asdasd", "asdasdas");
        updateList();
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.new_item) {
            int maxId = DB.getInstance(getApplicationContext()).maxNoteId();

            DB.getInstance(getApplicationContext()).createNode(new Note(maxId + 1, "test"));

            updateList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}