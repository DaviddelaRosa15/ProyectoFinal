package com.example.proyectofinal;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyectofinal.database.Database;
import com.example.proyectofinal.models.Anime;


import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.nameTextView)
    TextView mNameTextVIew;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.currentTextView)
    TextView  mCurrentTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.episodeTextView)
    TextView  mEpisodeTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.descriptionTextView)
    TextView  mDescriptionTextView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.animeImageView)
    ImageView  mAnimeImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        int id= Objects.requireNonNull(getIntent().getExtras()).getInt("id");
        Database mDatabase = new Database(this);
        Anime mAnime = mDatabase.getAnime(id);

        mNameTextVIew.setText(mAnime.getName());

        mCurrentTextView.setText(String.valueOf(mAnime.getCurrent()));

        mEpisodeTextView.setText(String.valueOf(mAnime.getEpisode()));

        mDescriptionTextView.setText(mAnime.getDescription());

        Glide.with(this.getApplicationContext())
                .load(mAnime.getUrlImage())
                .into(mAnimeImageView);

    }
}
