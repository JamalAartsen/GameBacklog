package com.example.gamebacklog;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerViewGames;
    private List<Game> mGameList;
    private GameAdapter mGameAdapter;
    private GameRoomDatabase db;
    private GestureDetector mGestureDetector;
    private MainViewModel mMainViewModel;


    public static final String NEW_GAME = "Game";
    public static final String NEW_UPDATEGAME = "updateGame";
    public static final int REQUESTCODE = 1;
    public static final int REQUESTCODE2 = 2;
    private int mModifyPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        mGameList = new ArrayList<>();
        db = GameRoomDatabase.getDatabase(this);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getGames().observe(this, new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> reminders) {
                mGameList = reminders;
                updateUI();
            }
        });


        recyclerViewGames = findViewById(R.id.recyclerViewGames);
        recyclerViewGames.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewGames.setAdapter(new GameAdapter(mGameList));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        recyclerViewGames.addOnItemTouchListener(this);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    //Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                        //Get the index corresponding to the selected position
                        int position = (viewHolder.getAdapterPosition());

                        mMainViewModel.delete(mGameList.get(position));
                        mGameList.remove(position);
                        mGameAdapter.notifyItemRemoved(position);
                        updateUI();
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewGames);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete_item) {
            mMainViewModel.deleteAllGames();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateUI() {
        if (mGameAdapter == null) {
            mGameAdapter = new GameAdapter(mGameList);
            recyclerViewGames.setAdapter(mGameAdapter);
        } else {
            mGameAdapter.swapList(mGameList);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Game addGame = data.getParcelableExtra(MainActivity.NEW_GAME);
                mMainViewModel.insert(addGame);
            }
        }

        if (requestCode == REQUESTCODE2) {
            if (resultCode == RESULT_OK) {
                Game update = data.getParcelableExtra(MainActivity.NEW_UPDATEGAME);
                mMainViewModel.update(update);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

        int mAdapterPosition = recyclerView.getChildAdapterPosition(child);

        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

            Intent intent = new Intent(MainActivity.this, EditGame.class);
            mModifyPosition = mAdapterPosition;
            intent.putExtra(MainActivity.NEW_UPDATEGAME,  mGameList.get(mAdapterPosition));
            startActivityForResult(intent, REQUESTCODE2);

        }

        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
