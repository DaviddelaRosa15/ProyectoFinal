package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.database.Database;
import com.example.proyectofinal.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerButton)
    Button registerBtn;
    @BindView(R.id.nameEditText)
    EditText nameEditText;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.userEditText)
    EditText userEditText;
    @BindView(R.id.passEditText)
    EditText passEditText;
    @BindView(R.id.confirmPassEditText)
    EditText confirmEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        Database database = new Database(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String p = phoneEditText.getText().toString();

                String email = emailEditText.getText().toString();
                String username = userEditText.getText().toString();
                String password = passEditText.getText().toString();
                String confirm = confirmEditText.getText().toString();

                if(name.length() == 0 || p.length() == 0 || email.length() == 0 || username.length() == 0 ||
                password.length() == 0 || confirm.length() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Advertencia")
                            .setMessage("Debe completar todos los campos")
                            .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                    builder.setIcon(R.drawable.baseline_warning_24);
                    builder.create();
                    builder.show();
                }else {
                    if(!email.contains("@")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Advertencia")
                                .setMessage("Debe ingresar un correo electónico válido")
                                .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                        builder.setIcon(R.drawable.baseline_warning_24);
                        builder.create();
                        builder.show();
                    } else if (password.equals(confirm)) {
                        long phone = Long.parseLong(p);
                        User newUser = new User(name, phone, email, username, password);
                        database.newUser(newUser);

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
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
