package com.example.tiendafull.UI.Models;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static SessionManager instance;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "session_pref";
    private static final String AUTH_TOKEN = "auth_token";
    private static final String USERNAME = "username";
    private static final String IS_ADMIN = "is_admin";
    private static final String TOKEN_EXPIRATION_TIME = "token_expiration_time";
    private static final String CART_PRODUCT_COUNT = "cart_product_count"; // Clave para el conteo del carrito
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

    public void setLastPurchase(PurchaseConfirmResponse purchase) {
        this.lastPurchase = purchase;
    }

    public PurchaseConfirmResponse getLastPurchase() {
        return lastPurchase;
    }

    // Guardar el token de autenticación
    public void saveAuthToken(String token) {
        long expirationTime = System.currentTimeMillis() + TOKEN_LIFETIME;
        editor.putLong(TOKEN_EXPIRATION_TIME, expirationTime);
        editor.putString(AUTH_TOKEN, token);
        editor.apply();
    }

    // Obtener el token de autenticación
    public String getAuthToken() {
        long expirationTime = sharedPreferences.getLong(TOKEN_EXPIRATION_TIME, 0);
        if (System.currentTimeMillis() > expirationTime) {
            clearSession();
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
        editor.clear(); // Limpiar todas las preferencias
        editor.apply();
    }

    // Guardar el estado de isAdmin
    public void setIsAdmin(boolean isAdmin) {
        editor.putBoolean(IS_ADMIN, isAdmin);
        editor.apply();
    }

    public void setUsername(String username) {
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME, null);
    }

    // Obtener el estado de isAdmin
    public boolean isAdmin() {
        return sharedPreferences.getBoolean(IS_ADMIN, false);
    }

    // Verificar si el carrito tiene productos
    public boolean isCartNotEmpty() {
        return getCartProductCount() > 0;
    }

    // Obtener el conteo de productos en el carrito
    public int getCartProductCount() {
        return sharedPreferences.getInt(CART_PRODUCT_COUNT, 0);
    }

    // Establecer el conteo de productos en el carrito
    public void setCartProductCount(int count) {
        editor.putInt(CART_PRODUCT_COUNT, count);
        editor.apply();
    }

    // Incrementar el conteo de productos en el carrito
    public void incrementCartProductCount() {
        int count = getCartProductCount();
        editor.putInt(CART_PRODUCT_COUNT, count + 1);
        editor.apply();
    }

    // Decrementar el conteo de productos en el carrito
    public void decrementCartProductCount() {
        int count = getCartProductCount();
        if (count > 0) {
            editor.putInt(CART_PRODUCT_COUNT, count - 2); // Corrigiendo para disminuir en 1
            editor.apply();
        }
    }
}
