package com.dferreira

import android.app.Application
import android.content.ComponentCallbacks2
import com.dferreira.androidUtils.ResourcesCache

/**
 * Main class of the game engine application
 */
class GameEngineApplication : Application() {
    /**
     * Initialize the singletons so their instances
     * are bound to the application process.
     */
    private fun initSingletons() {
        // Initialize the instance of MySingleton
        ResourcesCache.initInstance(this)
    }

    /**
     *
     */
    override fun onCreate() {
        super.onCreate()
        initSingletons()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= ComponentCallbacks2.TRIM_MEMORY_MODERATE) {
            ResourcesCache.getInstance().evictAll()
        } else {
            if (level >= ComponentCallbacks2.TRIM_MEMORY_BACKGROUND) {
                ResourcesCache.getInstance().trimToSize(ResourcesCache.getInstance().size() / 2)
            }
        }
    }
}