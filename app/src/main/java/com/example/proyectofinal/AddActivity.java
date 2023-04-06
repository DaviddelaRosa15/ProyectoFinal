package com.example.proyectofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.database.Database;
import com.example.proyectofinal.models.Anime;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.currentEditText)
    EditText mCurrentEditText;

    @BindView(R.id.episodeEditText)
    EditText mEpisodeEditText;

    @BindView(R.id.descriptionEditText)
    EditText mDescriptionEditText;

    @BindView(R.id.urlEditText)
    EditText mUrlEditText;

    @BindView(R.id.animeButton)
    Button mAnimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        int user = Objects.requireNonNull(getIntent().getExtras()).getInt("user");
        Database dataBase = new Database(this);

        mAnimeButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString();
            String c = mCurrentEditText.getText().toString();
            String e = mEpisodeEditText.getText().toString();
            String description = mDescriptionEditText.getText().toString();
            String url = mUrlEditText.getText().toString();

            if (name.length() == 0 || c.length() == 0 || e.length() == 0 || description.length() == 0 || url.length() == 0) {
                // this method will call when email and password fields are empty.
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Advertencia")
                        .setMessage("Debe completar todos los campos")
                        .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                builder.setIcon(R.drawable.baseline_warning_24);
                builder.create();
                builder.show();
            }else{
                int current = Integer.parseInt(c);
                int episode = Integer.parseInt(e);
                Anime mNewAnime = new Anime(name, current, episode, description, url, user);
                dataBase.newAnime(mNewAnime);

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }

        });

    }
}
