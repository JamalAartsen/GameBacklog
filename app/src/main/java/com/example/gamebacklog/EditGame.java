package com.example.gamebacklog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EditGame extends AppCompatActivity {

    Spinner inputStatus;
    String valueItem;
    EditText gameTitle;
    EditText platform;
    String date;
    List<String> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game);
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

        final Game gameUpdate = getIntent().getParcelableExtra(MainActivity.NEW_UPDATEGAME);
        gameTitle.setText(gameUpdate.getGameTitle());
        platform.setText(gameUpdate.getPlatform());

        statusList = new ArrayList<>();
        statusList.add("Want to play");
        statusList.add("Playing");
        statusList.add("Stalled");
        statusList.add("Dropped");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inputStatus.setAdapter(adapter);
        inputStatus.setSelection(statusList.indexOf(gameUpdate.getStatus()));

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
                    String title = gameTitle.getText().toString();
                    String platformName = platform.getText().toString();
                    String dateName = date;
                    String statusName = valueItem;

                    gameUpdate.setGameTitle(title);
                    gameUpdate.setPlatform(platformName);
                    gameUpdate.setDate(dateName);
                    gameUpdate.setStatus(statusName);

                    Intent intent = new Intent();
                    intent.putExtra(MainActivity.NEW_UPDATEGAME, gameUpdate);
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
