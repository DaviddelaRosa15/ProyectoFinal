package com.example.proyectofinal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectofinal.database.Database;
import com.example.proyectofinal.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String USERNAME_KEY = "username_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String username, password;

    @BindView(R.id.loginButton)
    Button loginBtn;
    @BindView(R.id.registerButton)
    Button registerBtn;
    @BindView(R.id.userEditText)
    EditText usernameEdt;
    @BindView(R.id.passEditText)
    EditText passwordEdt;
    @BindView(R.id.forgotTextView)
    TextView forgotTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        // getting the data which is stored in shared preferences.
        Database database = new Database(this);
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        username = sharedpreferences.getString("USERNAME_KEY", null);
        password = sharedpreferences.getString("PASSWORD_KEY", null);

        // calling on click listener for login button.
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usernameEdt.getText().toString();
                String pass = passwordEdt.getText().toString();
                if (user.length() == 0 || pass.length() == 0) {
                    // this method will call when email and password fields are empty.
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Advertencia")
                            .setMessage("Debe completar ambos campos")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    builder.setIcon(R.drawable.baseline_warning_24);
                    builder.create();
                    builder.show();
                } else {
                    User logged = database.login(user, pass);

                    if(logged.getName() != null){
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString(USERNAME_KEY, logged.getUserName());
                        editor.putString(PASSWORD_KEY, logged.getPassword());

                        editor.apply();

                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("user", logged.getId());
                        startActivity(i);
                        finish();
                    }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Advertencia")
                                .setMessage("El nombre de usuario o la contraseña están incorrectos")
                                .setPositiveButton("Aceptar", (dialogInterface, i) -> dialogInterface.cancel());

                        builder.setIcon(R.drawable.baseline_warning_24);
                        builder.create();
                        builder.show();
                    }
                }
            }
        });

        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(i);
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (username != null && password != null) {
            Database data = new Database(this);
            User log = data.login(username, password);
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.putExtra("user", log.getId());
            startActivity(i);
        }
    }
}