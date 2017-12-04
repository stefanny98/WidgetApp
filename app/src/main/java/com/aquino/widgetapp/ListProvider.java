package com.aquino.widgetapp;

import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Toshiba on 03/12/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {


    private ArrayList listItemList = new ArrayList<>();


    public void setEventos(ArrayList listItemList){
        this.listItemList = listItemList;
    }

  //  private ArrayList listItemList = new ArrayList();
    private Context context = null;
    private int appWidgetId;

    public void onCreate() {
    }

    public void onDataSetChanged() {

    }
    public int getViewTypeCount(){

        return 1;

    }
    public void onDestroy(){



    }

    public boolean hasStableIds() {

        return true;

    }

    public RemoteViews getLoadingView() {

        return null;

    }

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
       for (int i = 0; i < 10; i++) {
            LauncherActivity.ListItem listItem = new LauncherActivity.ListItem();
            listItem.label = "prueba" + i;

            listItemList.add(listItem);
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Evento>> call = service.getEventos();

        call.enqueue(new Callback<List<Evento>>()

        {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {
                        List<Evento> eventos = response.body();
                        Log.d(TAG, "eventos: " + eventos);

                        for (int i = 0; i < eventos.size(); i++) {
                            LauncherActivity.ListItem listItem = new LauncherActivity.ListItem();
                            listItem.label = ""+eventos.get(i).getMensaje();
                            listItemList.add(listItem);
                        }


                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        //     Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                // Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
    *Similar to getView of Adapter where instead of View
    *we return RemoteViews
    *
    */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.widget_item);
        LauncherActivity.ListItem listItem = (LauncherActivity.ListItem) listItemList.get(position);
        remoteView.setTextViewText(R.id.item, listItem.label);

        return remoteView;
    }
}
