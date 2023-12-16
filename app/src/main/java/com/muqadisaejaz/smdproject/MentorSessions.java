package com.muqadisaejaz.smdproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MentorSessions extends AppCompatActivity implements
        SessionAdapter.OnAcceptButtonClickListener, SessionAdapter.OnRejectButtonClickListener {

    private RecyclerView recyclerViewSessionList;
    private SessionAdapter sessionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_sessions);

        recyclerViewSessionList = findViewById(R.id.recyclerViewSeesionList);
        sessionAdapter = new SessionAdapter(new ArrayList<>(), this, this);

        recyclerViewSessionList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSessionList.setAdapter(sessionAdapter);

        // Fetch sessions from the backend
        fetchSessions();
    }

    private void fetchSessions() {
        String url = "http://10.0.2.2:4200/api/mentor/get-session-request"; // Replace with your backend API endpoint

        // Retrieve mentor token from SharedPreferences
        String mentorToken = retrieveMentorToken();

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("MentorsResponse", response.toString());
                            // Parse the JSON response and populate the sessions list
                            List<Session> sessions = parseSessions(response);
                            sessionAdapter.setSessions(sessions);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MentorSessions.this, "Error fetching sessions", Toast.LENGTH_SHORT).show();
                        Log.e("SessionsError", "Error: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                // Set the token in the headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + mentorToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    private String retrieveMentorToken() {
        // Retrieve mentor token from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private List<Session> parseSessions(JSONArray response) throws JSONException {
        List<Session> sessions = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject mentorObject = response.getJSONObject(i);

            String name = mentorObject.getString("studentName");
            String email = mentorObject.getString("studentEmail");

            JSONArray sessionsArray = mentorObject.getJSONArray("sessions");
            for (int j = 0; j < sessionsArray.length(); j++) {
                JSONObject sessionObject = sessionsArray.getJSONObject(j);

                String topic = sessionObject.getString("topic");
                String mode = sessionObject.getString("sessionType");
                String time = sessionObject.getString("time");

                Session session = new Session(name, email, topic, mode, time);
                Log.d("MentorDetails", "Name: " + session.getName() + ", Email: " + session.getEmail());
                sessions.add(session);
            }
        }

        return sessions;
    }

    @Override
    public void onAcceptButtonClick(Session session) {
        // Implement logic to send the accept request to the backend
        // Retrieve the token from SharedPreferences
        String mentorToken = retrieveMentorToken();

        // Use the session object to get studentEmail, sessionType, topic, and time
        String studentEmail = session.getEmail();
        String sessionType = session.getMode();
        String topic = session.getTopic();
        String time = session.getTime();

        // Now, send the accept request to the backend
        sendAcceptRequestToBackend(mentorToken, studentEmail, sessionType, topic, time);
    }

    private void sendAcceptRequestToBackend(String mentorToken, String studentEmail, String sessionType, String topic, String time) {
        String url = "http://10.0.2.2:4200/api/mentor/accept-sessionrequest"; // Replace with your backend API endpoint

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", studentEmail);
            jsonBody.put("studentEmail", studentEmail);
            jsonBody.put("sessionType", sessionType);
            jsonBody.put("topic", topic);
            jsonBody.put("time", time);
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
                            Toast.makeText(MentorSessions.this, message, Toast.LENGTH_SHORT).show();
                            // After accepting, refresh the session list
                            fetchSessions();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MentorSessions.this, "Error accepting session", Toast.LENGTH_SHORT).show();
                        Log.e("AcceptSessionError", "Error: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                // Set the token in the headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + mentorToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onRejectButtonClick(Session session) {
        // Implement logic to send the reject request to the backend
        // Retrieve the token from SharedPreferences
        String mentorToken = retrieveMentorToken();

        // Use the session object to get studentEmail, sessionType, topic, and time
        String studentEmail = session.getEmail();
        String sessionType = session.getMode();
        String topic = session.getTopic();
        String time = session.getTime();

        // Now, send the reject request to the backend
        sendRejectRequestToBackend(mentorToken, studentEmail, sessionType, topic, time);
    }

    private void sendRejectRequestToBackend(String mentorToken, String studentEmail, String sessionType, String topic, String time) {
        String url = "http://10.0.2.2:4200/api/mentor/reject-sessionrequest"; // Replace with your backend API endpoint

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", studentEmail);
            jsonBody.put("studentEmail", studentEmail);
            jsonBody.put("sessionType", sessionType);
            jsonBody.put("topic", topic);
            jsonBody.put("time", time);
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
                            Toast.makeText(MentorSessions.this, message, Toast.LENGTH_SHORT).show();
                            // After rejecting, refresh the session list
                            fetchSessions();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(MentorSessions.this, "Error rejecting session", Toast.LENGTH_SHORT).show();
                        Log.e("RejectSessionError", "Error: " + error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                // Set the token in the headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + mentorToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }
}
