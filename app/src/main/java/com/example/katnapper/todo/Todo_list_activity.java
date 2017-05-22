package com.example.katnapper.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Todo_list_activity extends AppCompatActivity implements DownloadCallBack {

    @Override
    public void updatestufffromdownload(JSONObject object) {
        if(object!=null)
        {
            try {
                JSONArray jsonArray = object.getJSONArray("todos");
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.get("title").toString();
                    todos.add(title);
                }
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) list.getAdapter();
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    List<String> todos = new ArrayList<String>();
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_activity);
        list = (ListView) findViewById(R.id.lv_todos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todos);
        retriveTodos();
        list.setAdapter(adapter);

    }

    private void retriveTodos(){
        todos.add("Eat Ice Cream");
        todos.add("Eat choclate");
        todos.add("Eat sugar");
        todos.add("Eat yoghurt");
        todos.add("Eat tofu");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mi_add:
                    Intent detailIntent = new Intent(this, DetailActivity.class);
                    startActivityForResult(detailIntent,1024);
                    return true;
            case R.id.btn_download:
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager != null)
                {
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected() && (networkInfo.getType()==ConnectivityManager.TYPE_MOBILE || networkInfo.getType()==ConnectivityManager.TYPE_WIFI) ){
                        new DownloadItem(this).execute("http://demo9827299.mockable.io/todos");


                    }
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1024:
                    if(resultCode == Activity.RESULT_OK){
                        if(data.hasExtra(DetailActivity.TODO_NAME)){
                            String todo = data.getStringExtra(DetailActivity.TODO_NAME);
                            todos.add(todo);
                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) list.getAdapter();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    break;
        }
    }
}
