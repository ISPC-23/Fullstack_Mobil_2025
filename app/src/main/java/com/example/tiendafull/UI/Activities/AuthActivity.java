package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.SessionManager;

public class AuthActivity extends AppCompatActivity {
    Button blogin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame, new LoginFragment())
                    .commit();


        blogin = findViewById(R.id.button2);

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new RegisterFragment())
                        .commit();
            }
        });
    }

}
