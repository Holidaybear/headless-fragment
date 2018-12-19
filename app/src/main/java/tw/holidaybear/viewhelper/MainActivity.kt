package tw.holidaybear.viewhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    private lateinit var networkHelper: NetworkHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkHelper = supportFragmentManager.findFragmentByTag("network_helper")
                as NetworkHelper? ?: NetworkHelper.newInstance().also {
            addFragmentToActivity(it, "network_helper")
        }
    }
}
