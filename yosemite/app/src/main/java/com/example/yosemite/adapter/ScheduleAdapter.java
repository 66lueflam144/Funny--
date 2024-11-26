package com.example.yosemite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yosemite.R;
import com.example.yosemite.data.Schedule;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {
    private List<Schedule> scheduleList;

    public ScheduleAdapter(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    @NonNull
    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);
        holder.titleTextView.setText(schedule.getTitle());
        holder.startTimeTextView.setText("日期：" + schedule.getDate());
        holder.endTimeTextView.setText("时间：" + schedule.getTime());
    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }



    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, startTimeTextView, endTimeTextView;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text);
            startTimeTextView = itemView.findViewById(R.id.start_time_text);
            endTimeTextView = itemView.findViewById(R.id.end_time_text);
        }
    }

}
