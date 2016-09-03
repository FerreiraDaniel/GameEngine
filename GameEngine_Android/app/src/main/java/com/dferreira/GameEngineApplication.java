package com.dferreira;

import android.app.Application;

import com.dferreira.commons.utils.ResourcesCache;

/**
 * Main class of the game engine application
 */
public class GameEngineApplication extends Application {

    /**
     * Initialize the singletons so their instances
     * are bound to the application process.
     */
    private void initSingletons() {
        // Initialize the instance of MySingleton
        ResourcesCache.initInstance(this);
    }

    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        initSingletons();


    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (level >= TRIM_MEMORY_MODERATE) {
            ResourcesCache.getInstance().evictAll();
        } else {
            if (level >= TRIM_MEMORY_BACKGROUND) {
                ResourcesCache.getInstance().trimToSize(ResourcesCache.getInstance().size() / 2);
            }
        }
    }

}
