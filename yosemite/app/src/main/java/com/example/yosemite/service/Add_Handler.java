package com.example.yosemite.service;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yosemite.R;
import com.example.yosemite.data.Schedule;

import java.util.Calendar;

public class Add_Handler extends AppCompatActivity {
    private EditText titleEditText, contentEditText;
    private Button dateButton, timeButton, confirmButton;
    private String selectedDate = "未设置";
    private String selectedTime = "未设置";
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.add_ui);

        titleEditText = findViewById(R.id.title);
        contentEditText = findViewById(R.id.content);
        dateButton = findViewById(R.id.date_button);
        timeButton = findViewById(R.id.time_button);
        confirmButton = findViewById(R.id.yes_button);

        dateButton.setOnClickListener(v-> {
            datePick();
            dateButton.setText(selectedDate);
        });

        timeButton.setOnClickListener(v-> {
            timePick();
        });


        confirmButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString().trim();
            String content = contentEditText.getText().toString().trim();

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "请填写完整",Toast.LENGTH_SHORT).show();
                return;
            }

            Schedule newschedule = new Schedule(title, content, selectedDate, selectedTime);

            Schedule_Handler.getInstance().addSchedule(newschedule);
            Toast.makeText(this, "日程创建成功", Toast.LENGTH_SHORT).show();
            finish();

        });
    }

    private void datePick() {
        int year;
        int month;
        int day;


        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
            dateButton.setText(selectedDate);
        }, year, month, day);

        datePickerDialog.setTitle("选择日期");
        datePickerDialog.show();


    }

    private void timePick() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
            timeButton.setText(selectedTime);
        }, hour, min, true); // `true` 为 24 小时制

        timePickerDialog.setTitle("选择时间");
        timePickerDialog.show();
    }
}
