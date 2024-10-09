package com.example.tiendafull.UI.Activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tiendafull.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.UserViewModel;

public class RegisterFragment extends Fragment {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etFirstName; // Campo para el nombre
    private EditText etLastName;  // Campo para el apellido
    private EditText etNroDocumento; // Campo para el número de documento
    private EditText etTelefono; // Campo para el teléfono
    private Button btnRegister;
    private TextView tvLogin;

    private UserViewModel userViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etFirstName = view.findViewById(R.id.et_first_name);
        etLastName = view.findViewById(R.id.et_last_name);
        etNroDocumento = view.findViewById(R.id.et_nro_documento);
        etTelefono = view.findViewById(R.id.et_telefono);
        btnRegister = view.findViewById(R.id.btn_register);
        tvLogin=view.findViewById(R.id.tv_login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, new LoginFragment())
                        .addToBackStack(null) // Para permitir volver atrás
                        .commit();
            }
        });

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        SessionManager sessionManager = SessionManager.getInstance(getContext());
        userViewModel.setSessionManager(sessionManager);

        userViewModel.getRegistrationSuccessLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean registered) {
                if (registered) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame, new LoginFragment())
                            .commit();
                }
            }
        });

        btnRegister.setOnClickListener(v -> attemptRegister());
    }

    private void attemptRegister() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String nroDocumentoStr = etNroDocumento.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        // Validar los campos
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Por favor ingrese su correo");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Por favor ingrese su contraseña");
            etPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("Por favor ingrese su nombre");
            etFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Por favor ingrese su apellido");
            etLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nroDocumentoStr)) {
            etNroDocumento.setError("Por favor ingrese su número de documento");
            etNroDocumento.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(telefono)) {
            etTelefono.setError("Por favor ingrese su teléfono");
            etTelefono.requestFocus();
            return;
        }

        long nroDocumento = Long.parseLong(nroDocumentoStr);

        // Llamar a la función de registro en el ViewModel
        userViewModel.register(email, password, firstName, lastName, nroDocumento, telefono);



    }
}