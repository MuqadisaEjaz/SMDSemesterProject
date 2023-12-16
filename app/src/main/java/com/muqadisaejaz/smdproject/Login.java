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
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;

public class Login extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
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

    private void saveTokenToSharedPreferences(String token) {
        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Edit SharedPreferences to store the token
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }
}
