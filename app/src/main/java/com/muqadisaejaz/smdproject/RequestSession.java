//package com.muqadisaejaz.smdproject;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class RequestSession extends AppCompatActivity {
//
//    private EditText editTextTopic, editTextAvailability;
//    private EditText spinnerSessionType;
//    private Button buttonSubmitRequest;
//
//    private String selectedMentor;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_request_session);
//
//        // Retrieve mentor information passed from the previous activity
//        selectedMentor = getIntent().getStringExtra("mentorEmail");
//
//        editTextTopic = findViewById(R.id.editTextTopic);
//        editTextAvailability = findViewById(R.id.editTextAvailability);
//        spinnerSessionType = findViewById(R.id.editTextSessionType);
//        buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);
//
//        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Retrieve student token from SharedPreferences
//                String studentToken = retrieveStudentToken();
//
//                // Retrieve input values
//                String topic = editTextTopic.getText().toString().trim();
//                String availability = editTextAvailability.getText().toString().trim();
//                String sessionType = spinnerSessionType.getText().toString().trim();
//
//                // Check if any input is empty
//                if (TextUtils.isEmpty(topic) || TextUtils.isEmpty(availability)) {
//                    Toast.makeText(RequestSession.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Send session request to backend
//                sendSessionRequest(studentToken, topic, availability, sessionType);
//            }
//        });
//    }
//
//    private String retrieveStudentToken() {
//        // Retrieve student token from SharedPreferences
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        return sharedPreferences.getString("token", "");
//    }
//
//    private void sendSessionRequest(String studentToken, String topic, String availability, String sessionType) {
//        // Get the mentor's email from the selected mentor
//        String mentorEmail = selectedMentor;
//        Log.d("MentorEmail******",mentorEmail );
//        // Define the URL for your backend API
//        String url = "http://10.0.2.2:4200/api/student/session-request";
//
//        // Create the JSON object to be sent in the request body
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.put("mentorEmail", mentorEmail);
//            requestBody.put("sessions", new JSONArray().put(new JSONObject()
//                    .put("sessionType", sessionType)
//                    .put("time", availability)
//                    .put("topic", topic)));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // Create a JsonObjectRequest with a POST method
//        JsonObjectRequest request = new JsonObjectRequest(
//                Request.Method.POST,
//                url,
//                requestBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle the successful response from the backend
//                        Toast.makeText(RequestSession.this, "Session request sent successfully", Toast.LENGTH_SHORT).show();
//                        finish(); // Finish the activity or handle the UI accordingly
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle the error response from the backend
//                        Toast.makeText(RequestSession.this, "Error sending session request", Toast.LENGTH_SHORT).show();
//                        Log.e("SessionRequestError", "Error: " + error.toString());
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                // Include the student token in the headers
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer " + studentToken);
//                return headers;
//            }
//        };
//
//        // Add the request to the RequestQueue
//        Volley.newRequestQueue(this).add(request);
//    }
//
//}
package com.muqadisaejaz.smdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RequestSession extends AppCompatActivity {

    private EditText editTextTopic, editTextAvailability;
    private EditText spinnerSessionType;
    private Button buttonSubmitRequest;

    private String selectedMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_session);

        // Retrieve mentor information passed from the previous activity
        selectedMentor = getIntent().getStringExtra("mentorEmail");

        editTextTopic = findViewById(R.id.editTextTopic);
        editTextAvailability = findViewById(R.id.editTextAvailability);
        spinnerSessionType = findViewById(R.id.editTextSessionType);
        buttonSubmitRequest = findViewById(R.id.buttonSubmitRequest);

        buttonSubmitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve student token from SharedPreferences
                String studentToken = retrieveStudentToken();

                // Retrieve input values
                String topic = editTextTopic.getText().toString().trim();
                String availability = editTextAvailability.getText().toString().trim();
                String sessionType = spinnerSessionType.getText().toString().trim();

                // Check if any input is empty
                if (TextUtils.isEmpty(topic) || TextUtils.isEmpty(availability)) {
                    Toast.makeText(RequestSession.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send session request to backend
                sendSessionRequest(studentToken, selectedMentor, topic, availability, sessionType);
            }
        });
    }

    private String retrieveStudentToken() {
        // Retrieve student token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private void sendSessionRequest(String studentToken, String mentorEmail, String topic, String availability, String sessionType) {
        // Define the URL for your backend API
        String url = "http://10.0.2.2:4200/api/student/session-request";

        // Create the JSON object to be sent in the request body
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("mentorEmail", mentorEmail);
            requestBody.put("sessions", new JSONArray().put(new JSONObject()
                    .put("sessionType", sessionType)
                    .put("time", availability)
                    .put("topic", topic)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a JsonObjectRequest with a POST method
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the successful response from the backend
                        Toast.makeText(RequestSession.this, "Session request sent successfully", Toast.LENGTH_SHORT).show();

                        // Send notification to the mentor
                        sendNotificationToMentor(mentorEmail, topic);

                        finish(); // Finish the activity or handle the UI accordingly
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response from the backend
                        Toast.makeText(RequestSession.this, "Error sending session request", Toast.LENGTH_SHORT).show();
                        Log.e("SessionRequestError", "Error: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Include the student token in the headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + studentToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    // Method to send notification to the mentor
    private void sendNotificationToMentor(String mentorEmail, String topic) {
        // Replace this URL with your backend API endpoint to retrieve the mentor's FCM token
        String mentorFCMTokenUrl = "http://10.0.2.2:4200/api/student/get-fcm?email=" + mentorEmail;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mentorFCMTokenUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Retrieve mentor's FCM token from the response
                            String mentorFCMToken = response.getString("token");

                            // Send a notification using FCM
                            sendFCMNotification(mentorFCMToken, topic);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error while retrieving mentor's FCM token
                        Log.e("FCMTokenError", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    // Method to send FCM notification
    private void sendFCMNotification(String mentorFCMToken, String topic) {
        Log.e("token ::::",mentorFCMToken);
        String fcmServerKey = "AAAA7gvUrg4:APA91bEaqiIeHgE5-uX742_XB2nnn2Xpw4qFn1eRSImARDluv2Z1lnqbN1xJcuUAYh6TdP9g40Ecravz5D_A5fmz_zzE_MO8lZ0J8nOT8fiv2oFQpXDWZxMNwbuVYpXpO10gKacD8Hsd"; // Replace with your FCM server key

        String fcmEndpoint = "https://fcm.googleapis.com/fcm/send";

        JSONObject notification = new JSONObject();
        JSONObject notificationBody = new JSONObject();

        try {
            notificationBody.put("title", "New Session Request");
            notificationBody.put("body", "A student has requested a session on " + topic);

            notification.put("to", mentorFCMToken);
            notification.put("data", notificationBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, fcmEndpoint, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success (notification sent successfully)
                        Log.d("FCMNotification", "Notification sent successfully");
                        Log.d("FCMNotificationResponse", "Response: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error while sending FCM notification
                        Log.e("FCMNotificationError", "Error: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Include FCM server key in headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "key=" + fcmServerKey);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }
}
