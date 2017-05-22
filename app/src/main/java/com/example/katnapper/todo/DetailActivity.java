package com.example.katnapper.todo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DetailActivity extends AppCompatActivity {

    public static final String TODO_NAME = "kcatnapper.example.com.Todo_list_activity.TODO_NAME";
    EditText todoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        todoEditText = (EditText) findViewById(R.id.ab_et_todo_name);
    }

    public void save_todo(View view) {
        Intent detailIntent = new Intent();
        detailIntent.putExtra(TODO_NAME,todoEditText.getText().toString());
        setResult(Activity.RESULT_OK,detailIntent);
        finish();
    }
}
