package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.CartViewModel;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setActivityContent(R.layout.activity_main);

        cartViewModel.getCart();

        boolean shouldLogout = getIntent().getBooleanExtra("LOGOUT", false);
        boolean showPurchasesFragment = getIntent().getBooleanExtra("SHOW_PURCHASES_FRAGMENT", false);

        if (shouldLogout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new LogoutFragment())
                    .commit();
        } else if (showPurchasesFragment) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new UserPurchasesFragment())
                    .commit();
        } else if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame3, new ProductFragment())
                    .commit();
        }
    }

}