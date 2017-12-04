package com.aquino.widgetapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class WidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        return (new StackRemoteViewsFactory(this.getApplicationContext(), intent));

    }

    class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

       private List<Evento> events = new ArrayList<>();

        private Context context = null;
        private int appWidgetId;

        public void onCreate() {

            ApiService service = ApiServiceGenerator.createService(ApiService.class);

            Call<List<Evento>> call = service.getEventos();

            try {
             List<Evento> ev =   call.execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
        // call.execute().body
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

                            events = eventos;



                        } else {
                            Log.e(TAG, "onError: " + response.errorBody().string());
                            throw new Exception("Error en el servicio");
                        }

                    } catch (Throwable t) {
                        try {
                            Log.e(TAG, "onThrowable: " + t.toString(), t);

                        } catch (Throwable x) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Evento>> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.toString());

                }

            });

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

        public StackRemoteViewsFactory(Context context, Intent intent) {
            this.context = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }


        @Override
        public int getCount() {
            return events.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            rv.setTextViewText(R.id.text1, events.get(position).getMensaje());


            Bundle extras = new Bundle();
            extras.putInt(MyWidgetProvider.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);

            rv.setOnClickFillInIntent(R.id.text1, fillInIntent);


            return rv;
        }



    }
}