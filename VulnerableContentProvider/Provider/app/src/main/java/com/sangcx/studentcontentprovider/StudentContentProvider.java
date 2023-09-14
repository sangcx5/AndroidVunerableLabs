package com.sangcx.studentcontentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StudentContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.sangcx.studentprovider";
    String TAG = "Provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/students");

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/students";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/student";

    public static final String[] PROJECTION_ALL = {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_AGE};

    public static Uri insert(ContentResolver contentResolver, ContentValues values) {

        return contentResolver.insert(CONTENT_URI, values);
    }

    public static int update(ContentResolver contentResolver, ContentValues values, String selection, String[] selectionArgs) {
        return contentResolver.update(CONTENT_URI, values, selection, selectionArgs);
    }

    public static int delete(ContentResolver contentResolver, String selection, String[] selectionArgs) {
        return contentResolver.delete(CONTENT_URI, selection, selectionArgs);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String s1) {

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(DatabaseHelper.TABLE_STUDENTS, null, selection, selectionArgs, null, null, null);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
