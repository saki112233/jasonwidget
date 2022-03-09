package com.example.jsonwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;

import android.os.SystemClock;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExampleWidgetService extends RemoteViewsService {
    public ExampleWidgetService(ArrayList<Model> modelArrayList, MainActivity mainActivity, int example_widget_item_text) {

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ExampleWidgetItemFactory(getApplicationContext(),intent);
    }
    class ExampleWidgetItemFactory implements RemoteViewsFactory{
        private Context context;
        private ArrayList<Model>modelArrayList;
        private MyApi myApi;
        private ListView lv;
        private int appWidgetId;
        private String BaseUrl="https://jsonplaceholder.typicode.com/";
        ExampleWidgetItemFactory(Context context,Intent intent){
            this.context=context;
            this.appWidgetId=intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
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



                }

                @Override
                public void onFailure(Call<ArrayList<Model>> call, Throwable t) {

                }
            });
        }

        @Override
        public void onCreate() {
            SystemClock.sleep(3000);
            modelArrayList=new ArrayList<>();
            displayRetrofitData();
        }


        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return modelArrayList.size();
        }



        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views= new RemoteViews(context.getPackageName(),R.layout.example_widget_item);
            Model model=modelArrayList.get(position);
            views.setTextViewText(R.id.example_widget_item_text, model.getId());
            SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
