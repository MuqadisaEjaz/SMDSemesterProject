package com.muqadisaejaz.smdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;

public class Login extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button buttonLogin;
    String fcmToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Inside the onCreate method of your Application class or main activity
        FirebaseApp.initializeApp(this);

            // This will throw an exception if the token retrieval fails

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        fcmToken = task.getResult();
                        Log.d("FCMToken", fcmToken);
                        //sendTokenToBackend(fcmToken);

                        // Now you can use 'fcmToken' to send notifications to this device
                    } else {
                        Log.e("FCMToken", "Failed to get FCM token", task.getException());
                    }
                });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
                sendTokenToBackend(fcmToken);
            }
        });
    }

    private void performLogin() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(Login.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:4200/api/login";


        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Handle successful login response
                            String role = response.getString("role");
                            String token = response.getString("token");
                            String userId = response.getString("userId");
                            Log.d("MentorDetails", role);

                            saveTokenToSharedPreferences(token);
                            if ("student".equals(role)) {
                                // Start Student Activity
                                Intent studentIntent = new Intent(Login.this, FindAMentor.class);
                                startActivity(studentIntent);
                            } else if ("mentor".equals(role)) {
                                // Start Mentor Activity
                                Intent mentorIntent = new Intent(Login.this, MentorSessions.class);
                                startActivity(mentorIntent);
                            } else {
                                // Handle other roles or show an error message
                                Toast.makeText(Login.this, "Invalid role", Toast.LENGTH_SHORT).show();
                            }
                            // You can handle the user authentication here

                            // TODO: Save the token and user info in SharedPreferences or elsewhere for future requests

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle unsuccessful login response
                        Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        Log.e("LoginError", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }


    private void sendTokenToBackend(String fcmToken) {
        String url = "http://10.0.2.2:4200/api/student/store-fcm"; // Replace with your backend API endpoint

        // Replace 'userEmail' with the actual user's email
        String email = editEmail.getText().toString().trim(); // Replace with the actual user's email
        Log.e("email sent", email);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            jsonBody.put("token", fcmToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Handle the response from the backend
                            String message = response.getString("message");
                            Log.d("BackendResponse", message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Log.e("BackendError", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    private void saveTokenToSharedPreferences(String token) {
        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Edit SharedPreferences to store the token
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
