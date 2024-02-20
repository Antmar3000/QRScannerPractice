package com.example.qrscannerpractice

import android.Manifest
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.qrscannerpractice.databinding.ActivityMainBinding
import com.example.qrscannerpractice.databinding.ResultDialogLayoutBinding
import com.example.qrscannerpractice.room.ScanItem
import com.example.qrscannerpractice.ui.Router
import com.example.qrscannerpractice.viewmodels.ScanViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


//    private val viewModel: ScanViewModel by viewModels ()

    private val viewModel: ScanViewModel by viewModels()

    @Inject
    lateinit var router : Router


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.historyList.observe(this) {}
        viewModel.showDialog.observe(this) {
            showDialog(it)
        }
        viewModel.fragment.observe(this) {
            showFragment(router.choose(it))
        }

        checkCameraPermission()

        binding.bottomNavigationView.selectedItemId = R.id.scan

        binding.bottomNavigationView.setOnItemSelectedListener {
            viewModel.onMenuClicked(it.itemId)
            true
        }

        binding.buttonFlash.setOnCheckedChangeListener { _, isChecked ->
            setFlashlight(isChecked)
        }

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), Constances.REQUEST_CODE)
        } else {
            viewModel.onPermissionGranted()
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == Constances.REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.onPermissionGranted()
        }
    }


    private fun showDialog(scanItem: ScanItem) {
        val bindingDialog = ResultDialogLayoutBinding.inflate(layoutInflater)
        AlertDialog.Builder(this).apply { setView(bindingDialog.root) }.create().let { dialog ->
            bindingDialog.resultTextView.text = scanItem.message
            bindingDialog.buttonFavourite.setImageResource(
                if (scanItem.favourite) R.drawable.star_icon else R.drawable.favourite_icon
            )
            bindingDialog.buttonFavourite.setOnClickListener { setFavourite(scanItem) }
            bindingDialog.buttonShare.setOnClickListener { shareResult(scanItem.message) }
            bindingDialog.buttonCopy.setOnClickListener { copyResultToClipboard(scanItem.message) }
            bindingDialog.buttonClose.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }


    }

    private fun copyResultToClipboard(result: String?) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("result", result)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, R.string.copied, Toast.LENGTH_SHORT).show()
    }

    private fun shareResult(result: String?) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, result)
        startActivity(Intent.createChooser(intent, "Share to..."))
    }

    private fun setFavourite(scanItem: ScanItem) {
        viewModel.updateItemFavourite(scanItem)
    }

    private fun setFlashlight(flashlightState: Boolean) {
        val cameraManager : CameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        val cameraId: String
        try {
            cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, flashlightState)
        } catch (e: Exception) {
            Log.e(getString(R.string.app_name), "flashlight fail")
            e.printStackTrace()
        }
    }


//    val listener = object : ClickListener {
//        override fun openScanItem(scanItem: ScanItem) {
//            showDialog(scanItem)
//        }
//    }



}