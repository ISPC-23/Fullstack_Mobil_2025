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
import android.widget.Toast;

import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.ViewModels.UserViewModel;


public class RegisterFragment extends Fragment {

    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;
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
        etPassword2 = view.findViewById(R.id.et_confirm_password);
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
        String password = etPassword.getText().toString();
        String password2 = etPassword2.getText().toString();
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String nroDocumentoStr = etNroDocumento.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Por favor ingrese su correo");
            etEmail.requestFocus();
            return;
        } else if (!isValidEmail(email)) {
            etEmail.setError("El correo no es válido");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Por favor ingrese su contraseña");
            etPassword.requestFocus();
            return;
        } else if (password.contains(" ")) {
            etPassword.setError("No se permiten espacios");
            etPassword.requestFocus();
            return;
        } else if (!isValidPassword(password)) {
            etPassword.setError("Debe tener al menos 8 caracteres, minúscula, mayúscula, número y símbolo");
            etPassword.requestFocus();
            return;
        } else if (!password.equals(password2)) {
            etPassword2.setError("Las contraseñas no coinciden");
            etPassword2.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("Por favor ingrese su nombre");
            etFirstName.requestFocus();
            return;
        } else if (!isValidName(firstName)) {
            etFirstName.setError("Nombre no válido");
            etFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Por favor ingrese su apellido");
            etLastName.requestFocus();
            return;
        } else if (!isValidName(lastName)) {
            etLastName.setError("Apellido no válido");
            etLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nroDocumentoStr)) {
            etNroDocumento.setError("Por favor ingrese su número de documento");
            etNroDocumento.requestFocus();
            return;
        } else if (!isValidDocument(nroDocumentoStr)) {
            etNroDocumento.setError("Formato no válido. Ingrese números sin puntos");
            etNroDocumento.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(telefono)) {
            etTelefono.setError("Por favor ingrese su teléfono");
            etTelefono.requestFocus();
            return;
        } else if (!isValidPhone(telefono)) {
            etTelefono.setError("Sin 0 y 15, sólo números: 3515432123");
            etTelefono.requestFocus();
            return;
        }

        long nroDocumento = Long.parseLong(nroDocumentoStr);
        userViewModel.register(email, password, firstName, lastName, nroDocumento, telefono);
        Toast.makeText(getContext(), "Registro enviado", Toast.LENGTH_LONG).show();

    }

    private boolean isValidPassword(String password) {
        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[¬~!¡@#$%^&*()_+-=\\[\\]{};':\"|,.<>/¿?])[A-Za-z0-9¬~!¡@#$%^&*()_+-=\\[\\]{};':\"|,.<>/¿?]{8,32}$";
        return password.matches(pattern);
    }

    private boolean isValidEmail(String email) {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,64}$";
        return email != null && email.matches(pattern);
    }

    private boolean isValidName(String name) {
        String pattern = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,32}$";
        return name != null && name.matches(pattern);
    }

    private boolean isValidDocument(String document) {
        String pattern = "^[0-9]{7,8}$";
        return document != null && document.matches(pattern);
    }

    private boolean isValidPhone(String phone) {
        String pattern = "^[0-9]{10}$";
        return phone != null && phone.matches(pattern);
    }
}