package tw.holidaybear.viewhelper

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.AppSettingsDialog

class PermissionHelper : Fragment(), EasyPermissions.PermissionCallbacks {

    private var callback: PermissionCallback? = null

    companion object {
        const val RC_CAMERA_PERM = 101
        fun newInstance() = PermissionHelper()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is PermissionCallback) {
            callback = context
        } else {
            throw IllegalArgumentException("$context must implement PermissionCallback")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    fun checkCameraPermission() {
        if (EasyPermissions.hasPermissions(context!!, Manifest.permission.CAMERA)) {
            callback?.onPermissionGranted()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        callback?.onPermissionGranted()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

        }
    }
}