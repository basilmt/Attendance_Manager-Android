package com.revolt.attendancemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    List<String> time;
    OnClickListen onClickListen;
    public TimeAdapter(List<String> time, OnClickListen onClickListen) {
        this.time = time;
        this.onClickListen = onClickListen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.time_single_item, parent, false);
        return new ViewHolder(view,onClickListen);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(time.get(position)));

        String dateString = new SimpleDateFormat("dd / MMM / yyyy").format(cal.getTime());
        String timeString = new SimpleDateFormat("hh:mm a").format(cal.getTime());

        holder.date.setText(dateString);
        holder.time.setText(timeString);
    }

    @Override
    public int getItemCount() {
        return time.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnClickListen onClickListen;
        TextView date, time;

        public ViewHolder(@NonNull View itemView, OnClickListen onClickListen) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            this.onClickListen = onClickListen;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onClickListen.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnClickListen{
        void onNoteClick(int position);
    }
}
