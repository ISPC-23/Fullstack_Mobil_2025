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

    public Call<LoginResponse> login(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        return authService.loginUser(loginRequest);
    }
    public Call<User> register(String username, String email, String password, String address) {
        User user = new User(username, email, password, address);
        return authService.registerUser(user);
    }
    public Call<LogoutResponse> logout() {

        return authService.logoutUser(); //
    }
}