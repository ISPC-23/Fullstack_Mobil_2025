package com.example.tiendafull.UI.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendafull.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private String getGeocodingUrl(String address) {
        String apiKey = "TU_API_KEY_DE_GOOGLE";
        return "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                address.replace(" ", "+") + "&key=" + apiKey;
    }

    private void getCoordinates(String address, GeocodingCallback callback) {
        OkHttpClient client = new OkHttpClient();
        String url = getGeocodingUrl(address);
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        JSONObject json = new JSONObject(responseData);
                        JSONArray results = json.getJSONArray("results");
                        if (results.length() > 0) {
                            JSONObject location = results.getJSONObject(0)
                                    .getJSONObject("geometry")
                                    .getJSONObject("location");
                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");
                            callback.onCoordinatesReceived(lat, lng);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Renombra tu interfaz personalizada para evitar conflicto con Callback de OkHttp
    interface GeocodingCallback {
        void onCoordinatesReceived(double lat, double lng);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudeopciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem
                                                 item) {
        int nro = item.getItemId();
        if (nro == R.id.entrar) {
            startActivity(new Intent(this, LoginActivity.class)); // Assuming LoginActivity exists
            return true;
        } else if (nro == R.id.productos) {
            startActivity(new Intent(this, MainActivity.class)); // Assuming LoginActivity exists
            return true;
        } else if (nro == R.id.contacto) {
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
}
