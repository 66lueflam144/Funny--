package com.example.yosemite;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yosemite.adapter.ScheduleAdapter;
import com.example.yosemite.service.Add_Handler;
import com.example.yosemite.service.Schedule_Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main);

        RecyclerView recyclerView = findViewById(R.id.schedule_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ScheduleAdapter adapter = new ScheduleAdapter(Schedule_Handler.getInstance().getScheduleList());
        recyclerView.setAdapter(adapter);

        FloatingActionButton addScheduleButton = findViewById(R.id.add_schedule_button);


        addScheduleButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_Handler.class);
            startActivity(intent);
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.schedule_recycler_view);
        ScheduleAdapter adapter = new ScheduleAdapter(Schedule_Handler.getInstance().getScheduleList());
        recyclerView.setAdapter(adapter);
    }


}