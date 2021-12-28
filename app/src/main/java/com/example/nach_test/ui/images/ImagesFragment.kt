package com.example.nach_test.ui.images

import android.Manifest
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.nach_test.R
import com.example.nach_test.databinding.FragmentImagesBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ImagesFragment : Fragment() {

    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.reference
    private lateinit var bindig: FragmentImagesBinding
    private lateinit var photoFile: File
    private var mPhotoUri: Uri? = null
    var currentPhotoPath = ""
    val RESULT_LOAD_IMG_GALLERY = 1111
    val RESULT_LOAD_IMG_CAMERA = 2222
    private val resultRequestPermission = 9999

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        bindig = DataBindingUtil.inflate(inflater, R.layout.fragment_images, container, false)
        return bindig.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissionLocation()
        bindig.fragmentImagesBtImages.setOnClickListener {
            displayPhotoDialog()
        }
    }

    private fun requestPermissionLocation() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
            }
            else -> {
                val permissionArray = arrayListOf(
                    Manifest.permission.CAMERA,  Manifest.permission.READ_EXTERNAL_STORAGE)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionArray.toTypedArray(), resultRequestPermission)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == resultRequestPermission) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                showMessageNoPermisions()
            }
        }
    }

    private fun showMessageNoPermisions() {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(requireContext()).create()
        alertDialog.setMessage(getString(R.string.fragment_images_warming_permissions))
        alertDialog.setCancelable(false)
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.fragment_images_go_settings)) { _, _ ->
            val intent = Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts(
                "package", activity?.packageName, null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            alertDialog.dismiss()
        }
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.fragment_images_cancel)) { _, _ ->
            alertDialog.dismiss()
            activity?.onBackPressed()
        }
        alertDialog.show()
    }

    private fun uploadFiles(bitmap: Bitmap, name: String) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val spaceRef = storageRef.child("images/${name}}")
        val uploadTask = spaceRef.putBytes(data)
        uploadTask.addOnFailureListener {
        }.addOnSuccessListener { taskSnapshot ->
            spaceRef.downloadUrl.addOnCompleteListener {
                showSuccessMessage(it.result.toString())
            }
        }
    }

    private fun displayPhotoDialog() {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setMessage(getString(R.string.fragment_images_load_files))
        alertDialog.setCancelable(false)
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.fragment_images_camera)) { _, _ ->
            alertDialog.dismiss()
            getImageFromCamera()
        }
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.fragment_images_gallery)) { _, _ ->
            alertDialog.dismiss()
            choosePhotoFromGallery()
        }
        alertDialog.show()

    }

    private fun getImageFromCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                }
                if (photoFile != null) {
                    mPhotoUri = context?.let {
                        FileProvider.getUriForFile(
                            it, activity?.applicationContext?.packageName
                                .toString() + ".provider", photoFile
                        )
                    }
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri)
                    startActivityForResult(takePictureIntent, RESULT_LOAD_IMG_CAMERA)
                }
            } catch (e: ActivityNotFoundException) {
                //onImageError("No fue posible tomar la imagen, agregala desde galería por favor")
            }
        } else {
            mPhotoUri = activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues())
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri)
            activity?.startActivityForResult(takePictureIntent, RESULT_LOAD_IMG_CAMERA)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        currentPhotoPath = image.absolutePath
        return image
    }

    private fun showSuccessMessage(url: String) {
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setMessage(getString(R.string.fragment_images_success_image))
        alertDialog.setCancelable(false)
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.fragment_images_explorer)) { _, _ ->
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
            alertDialog.dismiss()
        }
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.fragment_images_next)) { _, _ ->
            alertDialog.dismiss()
        }
        alertDialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMG_GALLERY && data != null) {
            data.data?.let { itData ->
                activity?.let { itActivity ->
                    val bitmap = if (Build.VERSION.SDK_INT < 28) {
                        MediaStore.Images.Media.getBitmap(
                            itActivity.contentResolver,
                            itData
                        )
                    } else {
                        val source = ImageDecoder.createSource(itActivity.contentResolver, itData)
                        ImageDecoder.decodeBitmap(source)
                    }
                    try {
                        val image = createImageFile()
                        val fOut = FileOutputStream(image)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut)
                        fOut.flush()
                        fOut.close()

                        val imageUri = FileProvider.getUriForFile(itActivity,
                            activity?.applicationContext?.packageName.toString() + ".provider", image)
                        uploadFiles(bitmap, File(imageUri.path).name)
                    } catch (e: IOException) {
                        Log.i("TAG", "Some exception $e")
                        return
                    }
                }
            }
        }
        if (requestCode == RESULT_LOAD_IMG_CAMERA) {
            if (mPhotoUri != null) {
                activity?.let { itActivity ->
                    photoFile?.let { itPhotoFile ->
                        mPhotoUri = FileProvider.getUriForFile(itActivity,
                            itActivity.applicationContext.packageName.toString() + ".provider", itPhotoFile)
                        mPhotoUri?.let {itImageUri ->
                            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(
                                    itActivity.contentResolver,
                                    itImageUri
                                )
                            } else {
                                val source = ImageDecoder.createSource(itActivity.contentResolver, itImageUri)
                                ImageDecoder.decodeBitmap(source)
                            }

                            try {
                                val fOut = FileOutputStream(itPhotoFile)
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, fOut)
                                fOut.flush()
                                fOut.close()

                                uploadFiles(bitmap, photoFile.name)

                            } catch (e: IOException) {
                                Log.i("TAG", "Some exception $e")
                                return
                            }
                        }
                    }
                }
            }
        }
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG_GALLERY)
    }

}