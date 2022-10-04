package id.co.mka.teraskill

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun spannableString(view: TextView, textUnClickable: String, textClickable: String, onClick: () -> Unit) {
    val spannableString = SpannableString(textUnClickable + textClickable)
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            onClick.invoke()
        }
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
        }
    }
    spannableString.setSpan(clickableSpan, textUnClickable.length, textUnClickable.length + textClickable.length,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    view.text = spannableString
    view.movementMethod = LinkMovementMethod.getInstance()
}

