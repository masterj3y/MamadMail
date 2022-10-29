package masterj3y.github.mamadmail

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initializeTimber()
    }

    private fun initializeTimber() =
        Timber.plant(Timber.DebugTree())
}