package id.co.mka.teraskill.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


fun setError(
    textInputLayout: TextInputLayout, editText: TextInputEditText, message: String, context: Context
) {
    textInputLayout.error = message
    editText.background = AppCompatResources.getDrawable(context, R.drawable.bg_textinput_error)
}

fun removeError(textInputLayout: TextInputLayout, editText: TextInputEditText, context: Context) {
    textInputLayout.error = null
    editText.background = AppCompatResources.getDrawable(context, R.drawable.bg_textinput)
    textInputLayout.isErrorEnabled = false
}


fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun isErrorOrEmpty(
    textInputLayout: TextInputLayout, editText: TextInputEditText
): Boolean {
    when {
        editText.text.toString().isEmpty() -> {
            editText.requestFocus()
            setError(textInputLayout, editText, "Kolom tidak boleh kosong", editText.context)
            if (textInputLayout.error != null) {
                editText.afterTextChanged {
                    removeError(textInputLayout, editText, editText.context)
                }
            }
        }
        textInputLayout.error != null -> {
            editText.requestFocus()
        }
    }
    return editText.text.toString().isEmpty() || textInputLayout.error != null
}

fun isEmailValid(email: CharSequence?): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
}

fun spannableString(
    view: TextView, text: String, spanStart: Int, spanEnd: Int, color: Int, onClick: () -> Unit
) {
    val ss = SpannableString(text)
    val clickableSpan: ClickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = ContextCompat.getColor(view.context, color)
            ds.isUnderlineText = false
        }
    }
    ss.setSpan(clickableSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = ss
    view.movementMethod = LinkMovementMethod.getInstance()
}

fun File.toMultipartBody(name: String, type: String): MultipartBody.Part =
    if (type == "image") {
        MultipartBody.Part.createFormData(
            name, this.name, this.asRequestBody("image/jpeg".toMediaTypeOrNull())
        )
    } else {
        MultipartBody.Part.createFormData(
            name, this.name, this.asRequestBody("application/pdf".toMediaTypeOrNull())
        )
    }


fun uriToFile(selectedFile: Uri, context: Context, type: String): File {
    val contentResolver: ContentResolver = context.contentResolver

    val myFile: File = when (type) {
        "pdf" -> {
            createCustomTempFilePDF(context)
        }
        else -> {
            createCustomTempFileImage(context)
        }
    }

    val inputStream = contentResolver.openInputStream(selectedFile) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.flush()
    outputStream.close()
    inputStream.close()

    return myFile

}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT, Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFilePDF(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    return File.createTempFile(timeStamp, ".pdf", storageDir)
}

fun createCustomTempFileImage(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun showLoading(isLoading: Boolean, context: Context) {
    if (isLoading) {
        val loading = (context as Activity).findViewById<ConstraintLayout>(R.id.dialog_loading)
        loading.visibility = View.VISIBLE
    } else {
        val loading = (context as Activity).findViewById<ConstraintLayout>(R.id.dialog_loading)
        loading.visibility = View.GONE
    }
}

fun saveBitmapToFile(file: File): File? {
    return try {

        // BitmapFactory options to downsize the image
        val o = BitmapFactory.Options()
        o.inJustDecodeBounds = true
        o.inSampleSize = 6
        // factor of downsizing the image
        var inputStream = FileInputStream(file)
        //Bitmap selectedBitmap = null;
        BitmapFactory.decodeStream(inputStream, null, o)
        inputStream.close()

        // The new size we want to scale to
        val REQUIRED_SIZE = 75

        // Find the correct scale value. It should be the power of 2.
        var scale = 1
        while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
            o.outHeight / scale / 2 >= REQUIRED_SIZE
        ) {
            scale *= 2
        }
        val o2 = BitmapFactory.Options()
        o2.inSampleSize = scale
        inputStream = FileInputStream(file)
        val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
        inputStream.close()

        // here i override the original image file
        file.createNewFile()
        val outputStream = FileOutputStream(file)
        selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        file
    } catch (e: Exception) {
        null
    }
}

