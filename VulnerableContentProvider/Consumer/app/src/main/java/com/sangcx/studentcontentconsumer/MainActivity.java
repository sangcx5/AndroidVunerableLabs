package com.sangcx.studentcontentconsumer;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String TAG = "Consumer";
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button getData = findViewById(R.id.getData);
        TextView tv = findViewById(R.id.result);
        EditText search = findViewById(R.id.search);

        List<Student> emptyListStudent = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerViewStudents);
        studentAdapter = new StudentAdapter(emptyListStudent);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Getting....");
                String selection = "name = '" + search.getText() + "'";
                Cursor cursor = getContentResolver().query(Uri.parse("content://com.sangcx.studentprovider/students"), null, selection, null, null);
                List<Student> students = new ArrayList<>();
                if (cursor == null) {
                    tv.setText("Result is null");
                    return;
                }

                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Student student = cursorToStudent(cursor);
                    students.add(student);
                    cursor.moveToNext();
                }

                if (students.size() == 0) {
                    tv.setText("Empty result");
                }

                studentAdapter = new StudentAdapter(students);
                recyclerView.setAdapter(studentAdapter);
                studentAdapter.notifyDataSetChanged();
                cursor.close();
            }
        });
    }

    private Student cursorToStudent(Cursor cursor) {
        int idIndex = cursor.getColumnIndex("_id");
        int nameIndex = cursor.getColumnIndex("name");
        int ageIndex = cursor.getColumnIndex("age");

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(nameIndex);
        int age = cursor.getInt(ageIndex);

        return new Student(name, age);
    }
}

