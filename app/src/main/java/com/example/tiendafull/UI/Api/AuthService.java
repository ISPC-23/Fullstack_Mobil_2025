package com.example.tiendafull.UI.Api;


import com.example.tiendafull.UI.Models.LoginRequest;
import com.example.tiendafull.UI.Models.LoginResponse;
import com.example.tiendafull.UI.Models.LogoutResponse;
import com.example.tiendafull.UI.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {


    @POST("users/register")
    Call<User> registerUser(@Body User user);

    @POST("users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("users/logout")
    Call<LogoutResponse> logoutUser();
}
