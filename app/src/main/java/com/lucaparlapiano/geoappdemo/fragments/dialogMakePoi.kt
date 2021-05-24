package com.lucaparlapiano.geoappdemo.fragments

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.lucaparlapiano.geoappdemo.Constants.Companion.SELECT_PICTURE
import com.lucaparlapiano.geoappdemo.Constants.Companion.TAKE_PICTURE
import com.lucaparlapiano.geoappdemo.R
import com.lucaparlapiano.geoappdemo.model.poiPoint
import com.lucaparlapiano.geoappdemo.viewModel.poiViewModel
import kotlinx.android.synthetic.main.alert_create_poi.*
import kotlinx.android.synthetic.main.alert_create_poi.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class dialogMakePoi : DialogFragment() {

    private val ViewModel: poiViewModel by activityViewModels()
    var currentPath: String? = null
    var imgPath: Uri? = null

    companion object {
        private const val KEY_LONG = "LONG"
        private const val KEY_LAT = "LAT"

        fun newInstance(long: String, latitude: String): dialogMakePoi {
            val args = Bundle()
            args.putString(KEY_LONG, long)
            args.putString(KEY_LAT, latitude)
            val fragment = dialogMakePoi()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alert_create_poi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    private fun setupView(view: View) {
        //Adding round on double function
        view.latLong.text = arguments?.getString(KEY_LONG) + " " + arguments?.getString(KEY_LAT)
    }

    private fun setupClickListeners(view: View) {
        view.cancelAlert.setOnClickListener {
            dismiss()
        }

        //Show Camera for image
        view.button9.setOnClickListener {
            openCamera()
        }

        //Show gallery per image
        view.button10.setOnClickListener {
            openGallery()
        }


        view.submitPoi.setOnClickListener {
            // Log.d("Luca", arguments?.getString(KEY_LONG) +" "+ arguments?.getString(KEY_LAT))
            var long = arguments?.getString(KEY_LONG)
            var lat = arguments?.getString(KEY_LAT)
            var name = view.editTextTextPersonName.getText().toString()

            ViewModel.insert(
                poiPoint(
                    name = name,
                    imagUrl = imgPath.toString(),
                    longitude = long.toString(),
                    latitude = lat.toString()
                )
            )
            dismiss()
            Toast.makeText(requireContext(), getString(R.string.poiInserited), Toast.LENGTH_LONG)
                .show()
        }

    }

    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
            var photoFile: File? = null
            photoFile = createFile()

            if (photoFile != null) {
                var photoUrl = FileProvider.getUriForFile(
                    requireActivity(),
                    "com.lucaparlapiano.geoappdemo.fileprovider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUrl)
                startActivityForResult(intent, TAKE_PICTURE)
            }
        }
    }

    fun createFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageName = "JPEG_" + timestamp
        var storageDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image = File.createTempFile(imageName, ".jpg", storageDir)
        currentPath = image.absolutePath
        return image
    }

    fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(Intent.createChooser(intent, "Select image"), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val file = File(currentPath)
                val url = Uri.fromFile(file)
                previewImage.setImageURI(url)
                imgPath = url
                Log.d("IMGURL PHOTO", url.toString())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            try {
                val url = data!!.data
                imgPath = url
                Log.d("IMGURL GALLERY", url.toString())
                previewImage.setImageURI(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

