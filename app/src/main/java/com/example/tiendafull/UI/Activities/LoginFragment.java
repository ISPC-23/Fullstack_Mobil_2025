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
    private TextView ok, irReg;

    private UserViewModel userViewModel;
    private int intentosFallidos = 0; // Nueva variable para contar intentos fallidos

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
        ok = view.findViewById(R.id.ok);
        irReg = view.findViewById(R.id.tv_registrate);

        irReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        userViewModel.setSessionManager(sessionManager);

        userViewModel.getLoginResponseLiveData().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    // Login exitoso, reinicia el contador de intentos fallidos
                    intentosFallidos = 0;
                    String userInfo = "Usuario: " + loginResponse.getUser().getUsername() +
                            "\nEmail: " + loginResponse.getUser().getEmail() +
                            "\nToken: " + loginResponse.getToken() +
                            "\nIsAdmin: " + loginResponse.getUser().getIsAdmin();

                    Toast.makeText(getActivity(), "Bienvenido " + userInfo, Toast.LENGTH_SHORT).show();
                    Intent x = new Intent(getContext(), MainActivity.class);
                    startActivity(x);
                    getActivity().finish();
                }
            }
        });

        userViewModel.getErrorLiveData().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                // Incrementa el contador de intentos fallidos
                intentosFallidos++;
                if (intentosFallidos >= 3) {
                    // Mostrar mensaje de usuario bloqueado después de 3 intentos fallidos
                    Toast.makeText(getActivity(), "Usuario Bloqueado", Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(false); // Opcional: deshabilita el botón de login
                } else {
                    Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                }
                ok.setText(errorMessage);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        String username = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            etEmail.setError("Por favor ingrese su correo");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Por favor ingrese su contraseña");
            etPassword.requestFocus();
            return;
        }

        userViewModel.login(username, password);
    }
}
