package com.example.jsonwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Model> modelArrayList;
    private MyApi myApi;
    private ListView lv;
    private int appWidgetId;
    private String BaseUrl="https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.example_widget_stack_view);
        modelArrayList=new ArrayList<>();
        displayRetrofitData();

    }

    private void displayRetrofitData() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi=retrofit.create(MyApi.class);
        Call<ArrayList<Model>> arrayListCall=myApi.callModel();
        arrayListCall.enqueue(new Callback<ArrayList<Model>>() {
            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                modelArrayList=response.body();
                for (int i=0;i<modelArrayList.size();i++);

ExampleWidgetService exampleWidgetService=new ExampleWidgetService(modelArrayList,MainActivity.this,R.id.example_widget_item_text);

            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {

            }
        });
    }
}