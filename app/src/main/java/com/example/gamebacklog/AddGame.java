package com.example.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddGame extends AppCompatActivity {

    Spinner inputStatus;
    String valueItem;
    EditText gameTitle;
    EditText platform;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        inputStatus = findViewById(R.id.inputStatus);
        gameTitle = findViewById(R.id.inputTitle);
        platform = findViewById(R.id.inputPlatform);
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        List<String> statusList = new ArrayList<>();
        statusList.add("Want to play");
        statusList.add("Playing");
        statusList.add("Stalled");
        statusList.add("Dropped");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputStatus.setAdapter(adapter);
        inputStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valueItem = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Game game = new Game(gameTitle.getText().toString(), platform.getText().toString(), valueItem, date);

                Intent intent = new Intent();
                intent.putExtra(MainActivity.NEW_GAME, game);
                intent.putExtra("SpinnerValue", getIntent().getExtras());

                setResult(Activity.RESULT_OK, intent);
                finish();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home);
            finish();
        return super.onOptionsItemSelected(item);
    }
}
