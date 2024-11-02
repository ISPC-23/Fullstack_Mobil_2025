package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.SessionManager;

public class ContactActivity extends AppCompatActivity {
    private MenuItem carritoItem;
    private SessionManager sessionManager;
    private EditText etName;
    private EditText etEmail;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        sessionManager = SessionManager.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);

        etName = findViewById(R.id.editTextName);
        etEmail = findViewById(R.id.editTextEmail);
        btnSend = findViewById(R.id.buttonSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSendContactForm();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        carritoItem = menu.findItem(R.id.carrito);
        updateCartIconColor();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.productos:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.salir:
                Intent intentSalir = new Intent(this, MainActivity.class);
                intentSalir.putExtra("LOGOUT", true);
                startActivity(intentSalir);
                return true;
            case R.id.compras:
                Intent intentCompras = new Intent(this, MainActivity.class);
                intentCompras.putExtra("SHOW_PURCHASES_FRAGMENT", true);
                startActivity(intentCompras);
                return true;
            case R.id.carrito:
                startActivity(new Intent(this, CartActivity.class));
                return true;
            case R.id.contacto:
                Intent intentContacto = new Intent(this, ContactActivity.class);
                intentContacto.putExtra("CONTACTO", true);
                startActivity(intentContacto);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateCartIconColor() {
        if (carritoItem != null) {
            Drawable icon = carritoItem.getIcon();
            if (icon != null) {
                if (sessionManager.getCartProductCount() > 0) {
                    icon.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                } else {
                    icon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartIconColor();
    }

    private void attemptSendContactForm() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Por favor ingrese su nombre");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Por favor ingrese su correo electrónico");
            etEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Por favor ingrese un correo válido");
            etEmail.requestFocus();
            return;
        }

        Toast.makeText(this, "Formulario enviado correctamente", Toast.LENGTH_SHORT).show();
    }
}
