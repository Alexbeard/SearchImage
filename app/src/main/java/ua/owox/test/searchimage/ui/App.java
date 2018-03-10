package ua.owox.test.searchimage.ui;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ua.owox.test.searchimage.di.component.AppComponent;
import ua.owox.test.searchimage.di.component.DaggerAppComponent;
import ua.owox.test.searchimage.di.module.DataModule;


public class App extends Application {

    public static Context context;

    private static AppComponent sAppComponent;

    public static Context getContext() {
        return context;
    }

    @NonNull
    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();

        sAppComponent = DaggerAppComponent.builder()
                .dataModule(new DataModule(context))
                .build();

    }


}
