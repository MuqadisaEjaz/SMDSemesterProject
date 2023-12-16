package com.muqadisaejaz.smdproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.SessionViewHolder> {

    private static List<Session> sessions;
    private OnAcceptButtonClickListener acceptButtonClickListener;
    private OnRejectButtonClickListener rejectButtonClickListener;

    public SessionAdapter(List<Session> sessions, OnAcceptButtonClickListener acceptListener, OnRejectButtonClickListener rejectListener) {
        this.sessions = sessions;
        this.acceptButtonClickListener = acceptListener;
        this.rejectButtonClickListener = rejectListener;
    }

    public interface OnAcceptButtonClickListener {
        void onAcceptButtonClick(Session session);
    }

    public interface OnRejectButtonClickListener {
        void onRejectButtonClick(Session session);
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_session_box, parent, false);
        return new SessionViewHolder(view, acceptButtonClickListener, rejectButtonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.bind(session);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
        notifyDataSetChanged();
    }

    static class SessionViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewEmail;
        TextView textViewTopic;
        TextView textViewMode;
        TextView textViewTime;
        Button buttonAccept;
        Button buttonReject;

        SessionViewHolder(View itemView, OnAcceptButtonClickListener acceptListener, OnRejectButtonClickListener rejectListener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewTopic = itemView.findViewById(R.id.textViewTopic);
            textViewMode = itemView.findViewById(R.id.textViewMode);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            buttonAccept = itemView.findViewById(R.id.buttonAcceptSession);
            buttonReject = itemView.findViewById(R.id.buttonDeclineSession);

            buttonAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (acceptListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            acceptListener.onAcceptButtonClick(sessions.get(position));
                        }
                    }
                }
            });

            buttonReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rejectListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            rejectListener.onRejectButtonClick(sessions.get(position));
                        }
                    }
                }
            });
        }

        void bind(Session session) {
            textViewName.setText(session.getName());
            textViewEmail.setText(session.getEmail());
            textViewTopic.setText(session.getTopic());
            textViewMode.setText(session.getMode());
            textViewTime.setText(session.getTime());
        }
    }
}
