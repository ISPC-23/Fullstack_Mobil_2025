package com.example.tiendafull.UI.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiendafull.R;
import com.example.tiendafull.UI.Models.LoginResponse;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.UserViewModel;


public class LoginFragment extends Fragment {
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView ok;

    private UserViewModel userViewModel;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);
        ok=view.findViewById(R.id.ok);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        userViewModel.setSessionManager(sessionManager);


        userViewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    // Login exitoso
                    String userInfo = "Usuario: " + loginResponse.getUser().getUsername() +
                            "\nEmail: " + loginResponse.getUser().getEmail() +
                            "\nToken: " + loginResponse.getToken() +
                            "\nIsAdmin: " + loginResponse.getUser().getIsAdmin();

                    Toast.makeText(getActivity(), "Login exitoso"+userInfo, Toast.LENGTH_SHORT).show();
                    ok.setText(""+userInfo); // Aquí puedes redirigir al usuario a otra actividad
                    Intent x=new Intent(getContext(), MainActivity.class);
                    startActivity(x);

                }
            }
        });
        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                // Mostrar el error
                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                ok.setText(""+errorMessage);

            }
        });

        // Manejar el clic del botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }


    private void attemptLogin() {


        // Obtener los valores de los campos
        String username  = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar los campos
        if (TextUtils.isEmpty(username )) {
            etEmail.setError("Por favor ingrese su correo");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Por favor ingrese su contraseña");
            etPassword.requestFocus();
            return;
        }

        // Llamar a la función de login en el ViewModel
        userViewModel.login(username , password);
    }
}