package com.example.tiendafull.UI.Api;



import com.example.tiendafull.UI.Models.SessionManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpProvider {
    private static OkHttpClient client = null;
    private static SessionManager sessionManager;

    // Método para inicializar el OkHttpClient con el SessionManager
    public static OkHttpClient getClient(SessionManager sessionMgr) {
        sessionManager = sessionMgr;

        if (client == null) {
            // Interceptor para logs
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Interceptor para agregar el token en las solicitudes
            Interceptor authInterceptor = chain -> {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();

                // Obtener el token desde SessionManager
                String token = sessionManager.getAuthToken();
                if (token != null) {
                    // Agregar el token a la cabecera si existe
                    requestBuilder.addHeader("Authorization", "Token " + token);
                }

                Request requestWithAuth = requestBuilder.build();
                return chain.proceed(requestWithAuth);
            };

            // Crear el cliente OkHttp con los interceptores
            client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor)
                    .build();
        }
        return client;
    }
}