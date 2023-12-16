package com.muqadisaejaz.smdproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
// FindAMentor.java
public class FindAMentor extends AppCompatActivity {

    private RecyclerView recyclerViewUserList;
    private MentorAdapter mentorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_amentor);

        recyclerViewUserList = findViewById(R.id.recyclerViewUserList);
        mentorAdapter = new MentorAdapter(this);

        // Set the RecyclerView's layout manager and adapter
        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUserList.setAdapter(mentorAdapter);

        // Make API request to fetch mentors
        fetchMentors();
    }

    private void fetchMentors() {
        String url = "http://10.0.2.2:4200/api/student/find-mentors";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d("MentorsResponse", response.toString());

                            // Parse the JSON response and populate the mentors list
                            List<Mentor> mentors = parseMentors(response);
                            mentorAdapter.setMentors(mentors);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(FindAMentor.this, "Error fetching mentors", Toast.LENGTH_SHORT).show();
                        Log.e("MentorsError", "Error: " + error.toString());
                    }
                });

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(request);
    }

    private List<Mentor> parseMentors(JSONArray response) throws JSONException {
        List<Mentor> mentors = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject mentorObject = response.getJSONObject(i);

            Mentor mentor = new Mentor();
            mentor.setName(mentorObject.getString("name"));
            mentor.setEmail(mentorObject.getString("email"));
            mentor.setBudget(mentorObject.getString("budget"));
            Log.d("MentorDetails", "Name: " + mentor.getName() + ", Email: " + mentor.getEmail() + ", Budget: " + mentor.getBudget());
            mentors.add(mentor);
        }

        return mentors;
    }
}
