package tw.holidaybear.viewhelper

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer

class NetworkHelper : Fragment() {

    companion object {
        fun newInstance() = NetworkHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()

        val connectionLiveData = ConnectionLiveData(context!!)
        connectionLiveData.observe(this, Observer { isConnected ->
            if (!isConnected) {
                Toast.makeText(context, "NETWORK DISCONNECTED", Toast.LENGTH_LONG).show()
            }
        })
    }
}