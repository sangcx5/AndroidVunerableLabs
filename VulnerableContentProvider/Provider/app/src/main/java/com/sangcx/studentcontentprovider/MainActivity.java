package com.sangcx.studentcontentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StudentAdapter studentAdapter;
    private StudentDataSource dataSource;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new StudentDataSource(this);
        dataSource.open();

        List<Student> studentList = dataSource.getAllStudents();
        recyclerView = findViewById(R.id.recyclerViewStudents);
        studentAdapter = new StudentAdapter(studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();

        if (studentList.size() == 0) {
            addStudent("SangCX", 1);
        }

        Button addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEditText = findViewById(R.id.editTextName);
                EditText ageEditText = findViewById(R.id.editTextAge);
                String name = nameEditText.getText().toString();
                int age = Integer.parseInt(ageEditText.getText().toString());
                addStudent(name, age);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void addStudent(String name, int age) {

        Student student = new Student(name, age);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_NAME, name);
        contentValues.put(DatabaseHelper.COLUMN_AGE, age);

        StudentContentProvider.insert(getContentResolver(), contentValues);
        dataSource.addStudent(student);

        studentAdapter = new StudentAdapter(dataSource.getAllStudents());
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
    }
}
