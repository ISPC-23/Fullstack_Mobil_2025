package com.example.tiendafull.UI.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.tiendafull.UI.Models.LoginResponse;
import com.example.tiendafull.UI.Models.LogoutResponse;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.Models.User;
import com.example.tiendafull.UI.Repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private SessionManager sessionManager;
    private MutableLiveData<LoginResponse> loginResponseLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> logoutLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> registrationSuccessLiveData = new MutableLiveData<>(); // LiveData para el registro


    public UserViewModel() {

    }
    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.userRepository = new UserRepository(sessionManager);
    }
    public LiveData<LoginResponse> getLoginResponseLiveData() {
        return loginResponseLiveData;
    }
    public LiveData<Boolean> getRegistrationSuccessLiveData() {
        return registrationSuccessLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }
    public LiveData<Boolean> getLogoutLiveData() { // Método para obtener la LiveData de logout
        return logoutLiveData;
    }

    public void login(String username,  String password) {
        userRepository.login(username , password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    sessionManager.saveAuthToken(response.body().getToken());
                    sessionManager.setIsAdmin(response.body().is_staff());
                    sessionManager.setUsername(response.body().getUser().getUsername());


                    loginResponseLiveData.postValue(response.body());
                } else {
                    errorLiveData.postValue("Login fallido");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());

            }
        });
    }
    public void logout() {
        String token = sessionManager.getAuthToken();
        if (token != null) {
        userRepository.logout().enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                if (response.isSuccessful()){
                    // Aquí puedes realizar cualquier limpieza necesaria
                    // Por ejemplo, eliminar el token o limpiar la información del usuario

                    sessionManager.clearSession();
                    loginResponseLiveData.postValue(null); // Limpiar la información del usuario

                    // Notificar que el usuario ha cerrado sesión
                    logoutLiveData.postValue(true); // Notificar logout

                }
                else {
                    errorLiveData.postValue("Token no disponible para logout");
                }
            }

            @Override
            public void onFailure(Call<LogoutResponse> call, Throwable t) {
                errorLiveData.postValue("Error de red: " + t.getMessage());
            }
        });

        }

    }
    public void register(String email, String password, String firstName, String lastName, long nroDocumento, String telefono) {
        userRepository.register(email, password,nroDocumento,  lastName,firstName,  telefono).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    registrationSuccessLiveData.postValue(true);
                } else {
                    errorLiveData.postValue("Registro fallido"); // Mensaje de error
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errorLiveData.postValue(t.getMessage());
            }
        });
    }
}