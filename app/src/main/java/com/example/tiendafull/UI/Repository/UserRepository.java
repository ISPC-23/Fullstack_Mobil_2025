package com.example.tiendafull.UI.Repository;


import com.example.tiendafull.UI.Api.AuthService;
import com.example.tiendafull.UI.Api.RetrofitClient;
import com.example.tiendafull.UI.Models.LoginRequest;
import com.example.tiendafull.UI.Models.LoginResponse;
import com.example.tiendafull.UI.Models.LogoutResponse;
import com.example.tiendafull.UI.Models.SessionManager;
import com.example.tiendafull.UI.Models.User;

import retrofit2.Call;

public class UserRepository {
    private AuthService authService;

    public UserRepository(SessionManager sessionManager) {
        authService = RetrofitClient.getRetrofit(sessionManager).create(AuthService.class);
    }

    public Call<LoginResponse> login(String username , String password) {
        LoginRequest loginRequest = new LoginRequest(username , password);
        return authService.loginUser(loginRequest);
    }
    public Call<User> register(String email, String password,long nrodocumento, String firstname, String lastname,  String telefono) {
        User user = new User(email, password,nrodocumento,firstname, lastname,  telefono);
        return authService.registerUser(user);
    }
    public Call<LogoutResponse> logout() {

        return authService.logoutUser(); //
    }
}