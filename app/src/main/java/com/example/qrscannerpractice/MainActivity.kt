package com.example.qrscannerpractice

import android.Manifest
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.qrscannerpractice.databinding.ActivityMainBinding
import com.example.qrscannerpractice.databinding.ResultDialogLayoutBinding
import com.example.qrscannerpractice.ui.FavouritesFragment
import com.example.qrscannerpractice.ui.RecentFragment
import com.example.qrscannerpractice.ui.ScannerFragment
import com.example.qrscannerpractice.viewmodels.ScanItemModelFactory
import com.example.qrscannerpractice.viewmodels.ScanViewModel
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class MainActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val bindingDialog : ResultDialogLayoutBinding by lazy { ResultDialogLayoutBinding.inflate(layoutInflater) }

    private val viewModel: ScanViewModel by viewModels {
        ScanItemModelFactory((application as ScanApplication).repository)
    }

    private val zBar: ZBarScannerView by lazy { ZBarScannerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.historyList.observe(this) {}

        checkCameraPermission()

        binding.bottomNavigationView.selectedItemId = R.id.scan

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.scan -> {
                    showFragment(ScannerFragment.newInstance())
                }

                R.id.recent -> {
                    showFragment(RecentFragment.newInstance())
                }

                R.id.favourites -> {
                    showFragment(FavouritesFragment.newInstance())
                }
            }
            true
        }

        binding.buttonFlash.setOnCheckedChangeListener { _, isChecked ->
            zBar.flash = isChecked
        }

    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
            PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 3000)

        } else {
            showFragment(ScannerFragment.newInstance())

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

        if (requestCode == 3000 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showFragment(ScannerFragment.newInstance())
        }
    }

    override fun handleResult(p0: Result?) {
    }

    fun showDialog(message: String?) {
        val dialog = Dialog(this)
        dialog.setContentView(bindingDialog.root)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        bindingDialog.resultTextView.text = message
        bindingDialog.buttonCopy.setOnClickListener { copyResultToClipboard(message) }
        bindingDialog.buttonFavourite.setOnClickListener {  }
        bindingDialog.buttonShare.setOnClickListener { shareResult(message) }
        bindingDialog.buttonClose.setOnClickListener { dialog.dismiss() }

    }

    private fun copyResultToClipboard(result: String?){
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("result", result)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Copied", Toast.LENGTH_SHORT).show()
    }

    private fun shareResult (result: String?){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, result)
        startActivity(Intent.createChooser(intent, "Share to..."))
    }


}