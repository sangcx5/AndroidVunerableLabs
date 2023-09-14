package com.sangcx.studentcontentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentDataSource {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public StudentDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addStudent(Student student) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, student.getName());
        values.put(DatabaseHelper.COLUMN_AGE, student.getAge());

        return database.insert(DatabaseHelper.TABLE_STUDENTS, null, values);
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        Cursor cursor = database.query(DatabaseHelper.TABLE_STUDENTS, null, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Student student = cursorToStudent(cursor);
            students.add(student);
            cursor.moveToNext();
        }

        cursor.close();
        return students;
    }

    private Student cursorToStudent(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
        int ageIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE);

        long id = cursor.getLong(idIndex);
        String name = cursor.getString(nameIndex);
        int age = cursor.getInt(ageIndex);

        return new Student(name, age);
    }
}
