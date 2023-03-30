package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.database.Database;
import com.example.proyectofinal.models.User;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestoreActivity extends AppCompatActivity {

    @BindView(R.id.restoreButton)
    Button restoreBtn;
    @BindView(R.id.passEditText)
    EditText passEditText;
    @BindView(R.id.confirmPassEditText)
    EditText confirmEditText;
    @BindView(R.id.nameTextView)
    TextView nameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore);

        ButterKnife.bind(this);

        Database database = new Database(this);
        int idUser = Objects.requireNonNull(getIntent().getExtras()).getInt("user");
        User user = database.getUser(idUser);

        nameTextView.setText("Usuario " + user.getName() + " ingrese su nueva contraseña");
        restoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = passEditText.getText().toString();
                String confirmPass = confirmEditText.getText().toString();

                if(pass.length() == 0 || confirmPass.length() == 0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Advertencia")
                            .setMessage("Debe completar todos los campos")
                            .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                    builder.setIcon(R.drawable.baseline_warning_24);
                    builder.create();
                    builder.show();
                }else {
                    if(pass.equals(confirmPass)){
                        user.setPassword(pass);
                        database.updateUser(user, user.getId());

                        Intent intent = new Intent(RestoreActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Advertencia")
                                .setMessage("Los campos contraseña y confirmar contraseña deben ser iguales")
                                .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                        builder.setIcon(R.drawable.baseline_warning_24);
                        builder.create();
                        builder.show();
                    }
                }
            }
        });
    }
}