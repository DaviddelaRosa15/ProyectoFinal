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

public class ForgotActivity extends AppCompatActivity {

    @BindView(R.id.forgotButton)
    Button forgotBtn;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.userEditText)
    EditText userEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        ButterKnife.bind(this);

        Database database = new Database(this);

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String username = userEditText.getText().toString();

                if(email.length() == 0 || username.length() == 0 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Advertencia")
                            .setMessage("Debe completar todos los campos")
                            .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                    builder.setIcon(R.drawable.baseline_warning_24);
                    builder.create();
                    builder.show();
                }else {
                    User found = database.forgotPass(username, email);
                    if(!email.contains("@")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Advertencia")
                                .setMessage("Debe ingresar un correo electónico válido")
                                .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                        builder.setIcon(R.drawable.baseline_warning_24);
                        builder.create();
                        builder.show();
                    }else if(found.getName() == null){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Advertencia")
                                .setMessage("El usuario o el correo electrónico están incorrectos")
                                .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                        builder.setIcon(R.drawable.baseline_warning_24);
                        builder.create();
                        builder.show();

                    } else{
                        Intent intent = new Intent(ForgotActivity.this, RestoreActivity.class);
                        intent.putExtra("user", found.getId());
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}
