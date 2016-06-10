package com.example.adriana.babycare;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.adriana.babycare.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;

import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;

public class AnalyticsTracker extends Application {
    private static AnalyticsTracker analyticsTracker = null;
    private Tracker tracker;

    private AnalyticsTracker(Context context){
        GoogleAnalytics analytics =  GoogleAnalytics.getInstance(context);
        tracker = analytics.newTracker(R.xml.global_tracker);
    }

    public static AnalyticsTracker getAnalyticsTracker(Context context){
        if (analyticsTracker == null){
            analyticsTracker = new AnalyticsTracker(context);
        }
        return analyticsTracker;
    }

    public void trackScreen (String screenName){
        Log.i(screenName, "Setting screen name : " + screenName);
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void trackEvent(String category, String action, String label){
        tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    public void trackException (Exception e, Context context){
        if (e != null){
            tracker.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(new StandardExceptionParser(context,null)
                            .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }
}