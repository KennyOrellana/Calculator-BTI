package app.kaisa.calculatorbti

import com.google.firebase.analytics.FirebaseAnalytics
import android.content.Context
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.*


object AnalyticsManager {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private val sdf = SimpleDateFormat("yyyyMMdd", Locale.US)

    fun initialize(context: Context){
        firebaseAnalytics = FirebaseAnalytics.getInstance(context)
    }

    fun trackAppStart(){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "home")
        bundle.putLong(FirebaseAnalytics.Param.START_DATE, System.currentTimeMillis())
        bundle.putString(FirebaseAnalytics.Param.START_DATE, getDateString())
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, bundle)
    }

    fun trackClear(){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "clear")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    fun trackDeepLink(){
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "website")
        bundle.putString(FirebaseAnalytics.Param.SOURCE, "deeplink")
        bundle.putLong(FirebaseAnalytics.Param.START_DATE, System.currentTimeMillis())
        bundle.putString(FirebaseAnalytics.Param.START_DATE, getDateString())
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    private fun getDateString(): String {
        return sdf.format(Date())
    }
}