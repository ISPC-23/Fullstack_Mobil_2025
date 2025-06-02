package com.example.tiendafull.UI.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tiendafull.R;

public class ContactActivity extends BaseActivity {

    private EditText etName, etEmail, etMessage;
    private Button btnSend;
    private ImageButton imageButtonWhatsApp;
    private ImageView ivMap;  // Agregado para el ícono de ubicación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_contact);
        etName = findViewById(R.id.editTextName);
        etEmail = findViewById(R.id.editTextEmail);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.buttonSend);
        imageButtonWhatsApp = findViewById(R.id.imageButtonWhatsApp);
        ivMap = findViewById(R.id.ivMap); // Referencia al ImageView del ícono de mapa
        btnSend.setOnClickListener(v -> sendEmail());
        imageButtonWhatsApp.setOnClickListener(v -> openWhatsApp());
        ivMap.setOnClickListener(v -> openMapLocation()); // Listener para abrir Maps
        cartViewModel.getCart(); // Método ya implementado en BaseActivity
    }

    private void sendEmail() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String message = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Por favor completá todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Correo electrónico inválido");
            etEmail.requestFocus();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Solo apps de correo
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ispctiendafull@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mensaje de contacto de " + name);
        intent.putExtra(Intent.EXTRA_TEXT, "Nombre: " + name + "\nCorreo: " + email + "\n\nMensaje:\n" + message);
        try {
            startActivity(Intent.createChooser(intent, "Enviar correo con..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No se encontró ninguna app de correo", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWhatsApp() {
        String phoneNumber = "+5493512121878";
        String message = "Hola, quiero hacer una consulta a Tienda Full Bike.";
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            intent.setData(Uri.parse(url));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMapLocation() {
        String address = "Javier Lascano Colodrero 2908, X5008 Córdoba";
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No se pudo abrir Google Maps", Toast.LENGTH_SHORT).show();
        }
    }
}
