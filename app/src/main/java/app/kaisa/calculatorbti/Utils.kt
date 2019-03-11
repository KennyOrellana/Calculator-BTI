package app.kaisa.calculatorbti

import android.widget.EditText
import java.text.NumberFormat
import java.util.*
import android.content.Intent
import android.content.Context
import android.net.Uri

object Utils {

    fun stringToDouble(string: String) : Double{
        return try {
            string.toDouble()
        } catch (e: NumberFormatException){
            0.0
        }
    }

    fun parallelepipedVolume(long: EditText, wide: EditText, tall: EditText): Double {
        return stringToDouble(long.text.toString()) * stringToDouble(wide.text.toString()) * stringToDouble(tall.text.toString())
    }

    fun cylinderVolume(long: EditText, diameter: EditText): Double {
        return stringToDouble(long.text.toString()) * circleArea(diameter = diameter)
    }

    private fun circleArea(diameter: EditText? = null, radius: EditText? = null): Double {
        return if(radius != null){
            Math.PI * Math.pow(stringToDouble(radius.text.toString()), 2.0)
        } else if(diameter != null) {
            Math.PI * Math.pow(stringToDouble(diameter.text.toString()) / 2, 2.0)
        } else {
            0.0
        }
    }

    fun doubleToString(value: Double): String {
        return NumberFormat.getNumberInstance(Locale.US).format(value)
    }

    fun websiteIntent(context: Context?){
        val url = "http://www.kaisa.app"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        context?.startActivity(i)
    }
}