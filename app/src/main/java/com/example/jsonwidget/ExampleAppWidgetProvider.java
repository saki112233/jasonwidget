package com.example.jsonwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId:appWidgetIds){
            Intent intent=new Intent(context,MainActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            Intent serviceIntent=new Intent(context,ExampleWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.example_widget);
            views.setOnClickPendingIntent(R.id.example_widget_button,pendingIntent);
            views.setRemoteAdapter(R.id.example_widget_stack_view,serviceIntent);
            views.setEmptyView(R.id.example_widget_stack_view,R.id.example_widget_empty_view);
            appWidgetManager.updateAppWidget(appWidgetId,views);
        }
    }
}
