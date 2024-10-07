package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.UserViewModel;


public class LogoutFragment extends Fragment {
    private TextView textView;
    private UserViewModel userViewModel; // Agrega UserViewModel
    private Button btnLogout; // Agrega botón para hacer logout


    public LogoutFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView=view.findViewById(R.id.tvframent2);
        btnLogout=view.findViewById(R.id.confirmarlogout);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        userViewModel.setSessionManager(sessionManager);

        textView.setText(("esta seguto"));

        userViewModel.getLogoutLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean LoggedOut) {
                if( LoggedOut){

                    String username = sessionManager.getUsername(); // Obtener el nombre de usuario
                    Toast.makeText(getContext(), ""+username, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.logout();

            }
        });

    }
}