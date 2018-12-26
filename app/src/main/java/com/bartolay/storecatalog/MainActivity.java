package com.bartolay.storecatalog;

import android.arch.persistence.room.Room;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEditText((EditText) findViewById(R.id.searchText));

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        Toast.makeText(MainActivity.this, db.toString(), Toast.LENGTH_LONG);
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public void onButtonClick(View view) {
        if(view.getId() == R.id.imageButton) {

            promptSpeechInput();
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something");

        try {
            startActivityForResult(intent, 100);
        } catch(ActivityNotFoundException exception) {
            Toast.makeText(MainActivity.this, "Sorry your device doesnt support speech language.", Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code, int result_code, Intent intent) {
        super.onActivityResult(request_code, result_code, intent);

        switch (request_code) {
            case 100:
                if(result_code== RESULT_OK && intent != null) {
                    List<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    editText.setText(result.get(0));
                }
                break;
        }
    }
}
