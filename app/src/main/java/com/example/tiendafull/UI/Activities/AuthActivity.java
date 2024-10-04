package com.example.tiendafull.UI.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tiendafull.R;

public class AuthActivity extends AppCompatActivity {
    Button blogin,blogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        getSupportFragmentManager().beginTransaction().add(R.id.frame,new LoginFragment() ).commit();
        blogin=findViewById(R.id.button1);
        blogout = findViewById(R.id.button2);
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, new LogoutFragment()).commit();
            }
        });
    //    blogout.setOnClickListener(new View.OnClickListener() {
    //      @Override
    //        public void onClick(View view) {
    //            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new RegisterFragment()).commit();
    //        }
    //    });
    }}