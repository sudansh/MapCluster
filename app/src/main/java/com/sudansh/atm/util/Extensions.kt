package com.sudansh.atm.util

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.content.Context
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer { it ->
        it?.let {
            observer(it)
        }
    })
}

fun <X, Y> LiveData<X>.switch(transform: (x: X) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this) {
        return@switchMap transform(it)
    }
}


inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit = {}) {
    val snack = Snackbar.make(this, message, length)
    snack.action()
    snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}


fun Context.inflate(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(this).inflate(layoutRes, null)
}

fun Double?.toDistance(): String {
    return when {
        this == null -> ""
        this / 1000 > 0 -> "${this.toInt() / 1000} km"
        else -> "${this.toInt()} m"
    }
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) {
    }
    return false
}


/**
 * Make view visible or gone based on true or false
 */
fun View.visible(isTrue: Boolean?) {
    when (isTrue) {
        true -> this.visibility = View.VISIBLE
        else -> this.visibility = View.GONE
    }
}