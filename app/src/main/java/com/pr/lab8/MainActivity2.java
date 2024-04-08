package com.pr.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    EditText note;
    String noteText;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        noteText = getIntent().getStringExtra("text");
        id = getIntent().getIntExtra("id", 0);

        note = findViewById(R.id.note_text);

        note.setText(noteText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Вы уверены?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        DB.getInstance(getApplicationContext()).delete(id);
                        setResult(RESULT_OK);
                        finish();
                    })
                    .setNegativeButton("Нет", (dialog, which) -> {

                    }).show();
        } else if (item.getItemId() == R.id.modify) {
            String newText = note.getText().toString();
            DB.getInstance(getApplicationContext()).update(new Note(id, newText));
            setResult(RESULT_OK);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}