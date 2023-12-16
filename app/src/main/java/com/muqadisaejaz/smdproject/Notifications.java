package com.muqadisaejaz.smdproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
            FirebaseApp.initializeApp(this);
            FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        }
    }
