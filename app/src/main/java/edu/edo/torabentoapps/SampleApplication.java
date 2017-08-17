package edu.edo.torabentoapps;

import android.app.Application;
import android.graphics.Typeface;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by anggy on 17/08/2017.
 */

public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
