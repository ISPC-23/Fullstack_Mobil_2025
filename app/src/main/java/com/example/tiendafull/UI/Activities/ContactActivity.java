package com.example.tiendafull.UI.Activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.tiendafull.R;

public class ContactActivity extends BaseActivity {

    private EditText etName, etEmail, etMessage;
    private Button btnSend, btnWhatsApp;
    private ImageButton imageButtonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_contact);

        etName = findViewById(R.id.editTextName);
        etEmail = findViewById(R.id.editTextEmail);
        etMessage = findViewById(R.id.et_message);
        btnSend = findViewById(R.id.buttonSend);
        btnWhatsApp = findViewById(R.id.buttonWhatsApp);
        imageButtonMap = findViewById(R.id.imageButton);

        btnSend.setOnClickListener(v -> sendEmail());
        btnWhatsApp.setOnClickListener(v -> openWhatsApp());
        imageButtonMap.setOnClickListener(v -> openMap());

        cartViewModel.getCart();
    }

    private void sendEmail() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String message = etMessage.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Por favor completá todos los campos", Toast.LENGTH_SHORT).show();
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
        String phoneNumber = "+5493512121878"; // ← Número con código de país (sin guiones ni espacios)
        String message = "Hola, quiero hacer una consulta a Tienda Full Bike.";

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);
            intent.setData(Uri.parse(url));
            intent.setPackage("com.whatsapp"); // Asegura que se abra WhatsApp
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "WhatsApp no está instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMap() {
        Uri mapUri = Uri.parse("geo:-31.4167,-64.1833?q=bicicleteria");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "No se encontró la app de mapas", Toast.LENGTH_SHORT).show();
        }
    }
}
