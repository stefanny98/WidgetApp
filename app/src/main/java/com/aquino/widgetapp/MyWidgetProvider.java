package com.aquino.widgetapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;

import android.appwidget.AppWidgetProvider;


import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;

import android.net.Uri;


import android.util.Log;
import android.widget.RemoteViews;

public class MyWidgetProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "";
    public static final String EXTRA_ITEM = "android.appwidget.action.EXTRA_ITEM";

    public static final String UPDATE_MEETING_ACTION = "android.appwidget.action.APPWIDGET_UPDATE";




    @Override
    public void onReceive(Context context, Intent intent) {

        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(UPDATE_MEETING_ACTION)) {

            int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context,MyWidgetProvider.class));

            Log.e("received", intent.getAction());

            mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listViewWidget);

        }

        super.onReceive(context, intent);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,

                         int[] appWidgetIds) {



        // update each of the app widgets with the remote adapter

        for (int i = 0; i < appWidgetIds.length; ++i) {





            Intent intent = new Intent(context, WidgetService.class);


            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));



            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_main);



            rv.setRemoteAdapter(appWidgetIds[i], R.id.listViewWidget, intent);



            Intent startActivityIntent = new Intent(context,MainActivity.class);

            PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            rv.setPendingIntentTemplate(R.id.listViewWidget, startActivityPendingIntent);



            rv.setEmptyView(R.id.listViewWidget, R.id.empty_view);


            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

}

/*
    @Override
    public void onUpdate(Context context, AppWidgetManager
            appWidgetManager,int[] appWidgetIds) {

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; ++i) {
            RemoteViews remoteViews = updateWidgetListView(context,
                    appWidgetIds[i]);
            appWidgetManager.updateAppWidget(appWidgetIds[i],
                    remoteViews);
        }


        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }



    private RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.widget_main);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.listViewWidget,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.listViewWidget, R.id.empty_view);
        return remoteViews;
    }
*/

