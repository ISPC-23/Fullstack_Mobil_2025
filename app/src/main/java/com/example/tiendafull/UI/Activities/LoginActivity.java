package com.example.tiendafull.UI.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendafull.R;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnIniciarSesion;
    private OkHttpClient client;
    private String token = null; // Variable para almacenar el token

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom);
            return insets;
        });



        // Inicialización de vistas
        etEmail = findViewById(R.id.etemail);
        etPassword = findViewById(R.id.etpassword);
        btnIniciarSesion = findViewById(R.id.btniniciarsesion);

        client = new OkHttpClient();
        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Llamar al método de autenticación para obtener el token
                authenticate(email, password);
            }
        });
    }

    private void authenticate(String email, String password) {
        // String url = "http://10.0.2.2:8000/api/login/";
        String url = "https://deploy-django-sjie.onrender.com/api/login/";
        // Crear el cuerpo de la solicitud de autenticación
        RequestBody formBody = new FormBody.Builder()
                .add("username", email)  // En la API se usa 'username' para el campo del email
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error en la conexión", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();

                    TokenResponse tokenResponse = gson.fromJson(responseBody, TokenResponse.class);
                    token = tokenResponse.getToken(); // Guardar el token

                    String firstName = tokenResponse.getUser().getFirstName();
                    String lastName = tokenResponse.getUser().getLastName();

                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Bienvenido " + firstName + " " + lastName, Toast.LENGTH_SHORT).show();
                        Log.d("TOKEN", "Token obtenido: " + token);

                        // Redirigir a MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Opcional: terminar la actividad de login para que no se vuelva a mostrar al presionar atrás
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // Clase para deserializar la respuesta del token
    private class TokenResponse {
        private User user;
        private String token;

        public User getUser() {
            return user;
        }

        public String getToken() {
            return token;
        }
    }

    // Clase interna para representar al usuario dentro de la respuesta
    private class User {
        private String first_name;
        private String last_name;

        public String getFirstName() {
            return first_name;
        }

        public String getLastName() {
            return last_name;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int nro = item.getItemId();
        if (nro == R.id.entrar) {
            return true;
        } else if (nro == R.id.productos) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else if (nro == R.id.contacto) {
            startActivity(new Intent(this, ContactActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem loginItem = menu.findItem(R.id.entrar);
        MenuItem productoItem = menu.findItem(R.id.productos);
        MenuItem contactoItem = menu.findItem(R.id.contacto);

        loginItem.setVisible(true);
        productoItem.setVisible(true);
        contactoItem.setVisible(true);

        if (this.getClass().equals(LoginActivity.class)) {
            loginItem.setVisible(false);
        }
        if (this.getClass().equals(MainActivity.class)) {
            productoItem.setVisible(false);
        }
        if (this.getClass().equals(ContactActivity.class)) {
            contactoItem.setVisible(false);
        }
        return true;
    }

    public void irARegistrarse(View v) {
        Intent intento = new Intent(this, RegisterActivity.class);
        startActivity(intento);
    }
}