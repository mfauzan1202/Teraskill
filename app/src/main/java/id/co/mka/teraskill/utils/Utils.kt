package id.co.mka.teraskill.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import id.co.mka.teraskill.R
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

fun setError(
    textInputLayout: TextInputLayout,
    editText: TextInputEditText,
    message: String,
    context: Context
) {
    textInputLayout.error = message
    editText.background =
        AppCompatResources.getDrawable(context, R.drawable.bg_textinput_error)
}

fun removeError(textInputLayout: TextInputLayout, editText: TextInputEditText, context: Context) {
    textInputLayout.error = null
    editText.background =
        AppCompatResources.getDrawable(context, R.drawable.bg_textinput)
}

fun isErrorOrEmpty(
    textInputLayout: TextInputLayout,
    editText: TextInputEditText
): Boolean {
    return editText.text.toString().isEmpty() || textInputLayout.error != null
}

fun isEmailValid(email: CharSequence?): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
}

fun spannableString(
    view: TextView,
    text: String,
    spanStart: Int,
    spanEnd: Int,
    color: Int,
    onClick: () -> Unit
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

fun File.toMultipartBody(name: String): MultipartBody.Part = MultipartBody.Part.createFormData(
    name,
    this.name,
    this.asRequestBody("application/pdf".toMediaTypeOrNull())
)

fun uriToFile(selectedFile: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedFile) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (
        inputStream.read(buf).also { len = it } > 0)
        outputStream.write(buf, 0, len)
    outputStream.flush()
    outputStream.close()
    inputStream.close()

    return myFile

}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    return File.createTempFile(timeStamp, ".pdf", storageDir)
}

fun showLoading(isLoading: Boolean, context: Context) {
    if (isLoading) {
        val loading = (context as Activity).findViewById<ProgressBar>(R.id.progressBar)
        loading.visibility = View.VISIBLE
    } else {
        val loading = (context as Activity).findViewById<ProgressBar>(R.id.progressBar)
        loading.visibility = View.GONE
    }
}