package app.kaisa.calculatorbti

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doAfterTextChanged
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AnalyticsManager.initialize(context = this)
//        AnalyticsManager.trackAppStart()

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)
        setupView()
    }

    private fun setupView(){
        et_long.doAfterTextChanged { calculateVolumeShape() }
        et_wide.doAfterTextChanged { calculateVolumeShape() }
        et_tall.doAfterTextChanged { calculateVolumeShape() }
        et_diameter.doAfterTextChanged { calculateVolumeShape() }
        et_volume.doAfterTextChanged { convertVolume() }

        rg_shape.setOnCheckedChangeListener { _, id ->
            when(id) {
                rb_parallelepiped.id -> {
                    et_long?.visibility = View.VISIBLE
                    et_wide?.visibility = View.VISIBLE
                    et_diameter.visibility = View.INVISIBLE
                }

                rb_cylinder.id -> {
                    et_long?.visibility = View.GONE
                    et_wide?.visibility = View.GONE
                    et_diameter.visibility = View.VISIBLE
                }
            }

            calculateVolumeShape()
        }

        rg_units.setOnCheckedChangeListener { _, _ ->
            calculateVolumeShape()
        }

        rg_volumes.setOnCheckedChangeListener { _, _ ->
            convertVolume()
        }

        cl_about?.setOnClickListener {
            Utils.websiteIntent(this)
//            AnalyticsManager.trackDeepLink()
        }

        fab?.setOnClickListener { clear() }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tv_info?.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
    }

    private fun calculateVolumeShape(){
        val volume : Double = when(rg_shape.checkedRadioButtonId) {
            rb_parallelepiped.id -> Utils.parallelepipedVolume(et_long, et_wide, et_tall)
            rb_cylinder.id -> Utils.cylinderVolume(et_tall, et_diameter)
            else -> 0.0
        }

        when(rg_units.checkedRadioButtonId) {
            rb_cms.id -> refreshResultsShape(volume / 1000)
            rb_in.id -> refreshResultsShape(volume / 61.024)
            else -> refreshResultsShape(0.0)
        }
    }

    private fun refreshResultsShape(liters: Double){
        tv_liters?.text = getString(R.string.liters_placeholder, Utils.doubleToString(liters))
        tv_bti?.text = getString(R.string.bti_placeholder, Utils.doubleToString(liters * 0.005))
    }

    private fun convertVolume(){
        var volume : Double = Utils.stringToDouble(et_volume.text.toString())

        when(rg_volumes.checkedRadioButtonId) {
            rb_galons.id -> volume *= 3.78541
            rb_meters.id -> volume *= 1000
        }

        refreshResultsConversion(volume)
    }

    private fun refreshResultsConversion(liters: Double){
        tv_liters_volume?.text = getString(R.string.liters_placeholder, Utils.doubleToString(liters))
        tv_bti_volume?.text = getString(R.string.bti_placeholder, Utils.doubleToString(liters * 0.005))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    private fun clear(){
        et_long?.editableText?.clear()
        et_wide?.editableText?.clear()
        et_tall?.editableText?.clear()
        et_diameter?.editableText?.clear()
        et_volume?.editableText?.clear()

//                AnalyticsManager.trackClear()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.clear -> {
                clear()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
