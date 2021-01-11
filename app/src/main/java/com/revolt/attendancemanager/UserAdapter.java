package com.revolt.attendancemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<User> userList;
    OnClickListen onClickListen;
    public UserAdapter(List<User> userList, OnClickListen onClickListen) {
        this.userList = userList;
        this.onClickListen = onClickListen;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_single_item, parent, false);
        return new ViewHolder(view,onClickListen);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnClickListen onClickListen;
        TextView name;

        public ViewHolder(@NonNull View itemView, OnClickListen onClickListen) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
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
