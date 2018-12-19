package tw.holidaybear.viewhelper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), PermissionCallback {

    private lateinit var networkHelper: NetworkHelper
    private lateinit var permissionHelper: PermissionHelper

    companion object {
        const val RC_IMAGE_CAPTURE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkHelper = supportFragmentManager.findFragmentByTag("network_helper")
                as NetworkHelper? ?: NetworkHelper.newInstance().also {
            addFragmentToActivity(it, "network_helper")
        }

        permissionHelper = supportFragmentManager.findFragmentByTag("permission_helper")
                as PermissionHelper? ?: PermissionHelper.newInstance().also {
            addFragmentToActivity(it, "permission_helper")
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            permissionHelper.checkCameraPermission()
        }
    }

    override fun onPermissionGranted() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_IMAGE_CAPTURE && resultCode == RESULT_OK) {

        }
    }
}
