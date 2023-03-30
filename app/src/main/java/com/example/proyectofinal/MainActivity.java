package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.database.Database;
import com.example.proyectofinal.models.Anime;
import com.example.proyectofinal.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.item_list)
    RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.signOut)
    FloatingActionButton signOutFab;

    AnimeAdapter mAnimeAdapter;

    GridLayoutManager mLayoutManager;

    private int idUser;
    public static final String SHARED_PREFS = "shared_prefs";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        this.idUser = Objects.requireNonNull(getIntent().getExtras()).getInt("user");
        Recycler();

        mFab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            intent.putExtra("user", this.idUser);
            startActivity(intent);
        });

        signOutFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void Recycler() {

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new GridLayoutManager(this, 1);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAnimeAdapter = new AnimeAdapter(new ArrayList<>());

        Content();
    }

    private void Content() {

        Database mDatabase = new Database(this);
        List<Anime> mAnime = mDatabase.listAnime(this.idUser);


        if (mAnime.size() > 0) {
            mAnimeAdapter = new AnimeAdapter(mAnime);
        } else {
            ArrayList<Anime> animeEmpty = new ArrayList<>();
            mAnimeAdapter.addItems(animeEmpty);
        }

        mRecyclerView.setAdapter(mAnimeAdapter);

    }

}
