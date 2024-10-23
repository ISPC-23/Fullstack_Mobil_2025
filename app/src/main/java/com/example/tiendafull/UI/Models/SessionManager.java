package com.example.tiendafull.UI.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  String PREF_NAME = "session_pref";
    private  String AUTH_TOKEN = "auth_token";
    private  String USERNAME = "username";
    private  String IS_ADMIN = "is_admin"; // Nueva clave para isAdmin
    private  String TOKEN_EXPIRATION_TIME = "token_expiration_time";
    private long TOKEN_LIFETIME = 60 * 60 * 1000;
    private PurchaseConfirmResponse lastPurchase;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setLastPurchase(PurchaseConfirmResponse purchase){
        this.lastPurchase = purchase;
    }
    public  PurchaseConfirmResponse getLastPurchase(){
        return lastPurchase;
    }

    // Guardar el token de autenticación
    public void saveAuthToken(String token) {
        long expirationTime = System.currentTimeMillis() + TOKEN_LIFETIME;
        editor.putLong(TOKEN_EXPIRATION_TIME, expirationTime); // Guardar el tiempo de expiración
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    // Obtener el token de autenticación
    public String getAuthToken() {
        long expirationTime = sharedPreferences.getLong(TOKEN_EXPIRATION_TIME, 0);
        if (System.currentTimeMillis() > expirationTime) {
            clearSession(); // Eliminar el token si ha expirado
            return null;
        }
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public boolean isTokenExpired() {
        long expirationTime = sharedPreferences.getLong(TOKEN_EXPIRATION_TIME, 0);
        long currentTime = System.currentTimeMillis();
        return (currentTime - expirationTime) >= TOKEN_LIFETIME;
    }

    // Limpiar el token de autenticación
    public void clearSession() {
        editor.remove(AUTH_TOKEN);
        editor.remove(IS_ADMIN);
        editor.remove(TOKEN_EXPIRATION_TIME);
        editor.apply();
    }

    // Guardar el estado de isAdmin
    public void setIsAdmin(boolean isAdmin) {
        editor.putBoolean(IS_ADMIN, isAdmin);
        editor.apply();
    }

    public void setUsername(String username){
        editor.putString(USERNAME, username);
        editor.apply();
    }
    public String getUsername() {
        return sharedPreferences.getString(USERNAME, null); // Ajusta según cómo almacenes el nombre
    }

    // Obtener el estado de isAdmin
    public boolean isAdmin() {
        return sharedPreferences.getBoolean(IS_ADMIN, false);
    }
}