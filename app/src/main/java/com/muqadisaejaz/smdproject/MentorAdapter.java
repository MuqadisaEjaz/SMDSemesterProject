//package com.muqadisaejaz.smdproject;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;  // Import TextView
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//// MentorAdapter.java
//public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {
//
//    private List<Mentor> mentors;
//
//    // Constructor
//    public MentorAdapter() {
//        this.mentors = new ArrayList<>();
//    }
//
//    @NonNull
//    @Override
//    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mentor_profile, parent, false);
//        return new MentorViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
//        Mentor mentor = mentors.get(position);
//        holder.bind(mentor);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mentors.size();
//    }
//
//    // Additional method to update the mentor list
//    public void setMentors(List<Mentor> mentors) {
//        this.mentors = mentors;
//        notifyDataSetChanged();
//    }
//
//    // MentorViewHolder class
//    class MentorViewHolder extends RecyclerView.ViewHolder {
//        TextView textViewName;
//        TextView textViewEmail;
//        TextView textViewExpertise;
//        TextView textViewBudget;
//        Button buttonRequestSession;
//
//        MentorViewHolder(View itemView) {
//            super(itemView);
//            textViewName = itemView.findViewById(R.id.textViewName);
//            textViewEmail = itemView.findViewById(R.id.textViewEmail);
//            textViewBudget = itemView.findViewById(R.id.textViewBudget);
//            buttonRequestSession = itemView.findViewById(R.id.buttonRequestSession);
//        }
//
//        void bind(Mentor mentor) {
//            textViewName.setText(mentor.getName());
//            textViewEmail.setText(mentor.getEmail());
//            textViewBudget.setText(mentor.getBudget());
//
//            // Handle button click or any other interactions as needed
//            buttonRequestSession.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Handle the button click (e.g., initiate session request)
//                    // You can access mentor details using 'mentor' object
//                }
//            });
//        }
//    }
//}
package com.muqadisaejaz.smdproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorViewHolder> {

    private List<Mentor> mentors;
    private Context context; // Add context to use in the Intent

    // Constructor
    public MentorAdapter(Context context) {
        this.context = context;
        this.mentors = new ArrayList<>();
    }

    @NonNull
    @Override
    public MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mentor_profile, parent, false);
        return new MentorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        Mentor mentor = mentors.get(position);
        holder.bind(mentor);
    }

    @Override
    public int getItemCount() {
        return mentors.size();
    }

    // Additional method to update the mentor list
    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
        notifyDataSetChanged();
    }

    // MentorViewHolder class
    class MentorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewEmail;
        TextView textViewBudget;
        Button buttonRequestSession;

        MentorViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewBudget = itemView.findViewById(R.id.textViewBudget);
            buttonRequestSession = itemView.findViewById(R.id.buttonRequestSession);
        }

        void bind(Mentor mentor) {
            textViewName.setText(mentor.getName());
            textViewEmail.setText(mentor.getEmail());
            textViewBudget.setText(mentor.getBudget());

            // Handle button click or any other interactions as needed
            buttonRequestSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Handle the button click (e.g., initiate session request)
                    // You can access mentor details using 'mentor' object
                    String mentorEmail = mentor.getEmail();
                    Log.d("MentorEmail******", "Email: " + mentorEmail );
                    // Pass mentor details to the SessionRequestActivity
                    Intent intent = new Intent(context, RequestSession.class);
                    intent.putExtra("mentorEmail", mentorEmail);
                    context.startActivity(intent);
                }
            });
        }
    }
}
