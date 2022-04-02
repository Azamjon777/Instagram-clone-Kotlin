package com.example.buttonnavigation.activities

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bumptech.glide.Glide
import com.example.buttonnavigation.R
import com.example.buttonnavigation.models.FeedPost
import com.example.buttonnavigation.models.User
import com.example.buttonnavigation.utils.GlideApp
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

fun Context.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    text?.let{Toast.makeText(this, it, duration).show()}
}

fun coordinateBtnAndInputs(btn: Button, vararg inputs: EditText) {
    val watcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            btn.isEnabled = inputs.all { it.text.isNotEmpty() }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    }
    inputs.forEach { it.addTextChangedListener(watcher) }
    btn.isEnabled = inputs.all { it.text.isNotEmpty() }
}

fun Editable.toStringOrNull(): String? {
    val str = toString()
    return if (str.isEmpty()) null else str
}

fun ImageView.loadUserPhoto(photoUrl: String?) =
    ifNotDestroyed {
        GlideApp.with(this).load(photoUrl).fallback(R.drawable.person).into(this)
    }

fun ImageView.loadImage(image: String?) =
    ifNotDestroyed {
        GlideApp.with(this).load(image).centerCrop().into(this)
    }

private fun View.ifNotDestroyed(block: () -> Unit) {
    if (!(context as Activity).isDestroyed) {
        block()
    }
}

fun <T> task(block: (TaskCompletionSource<T>) -> Unit): Task<T> {
    val taskSource = TaskCompletionSource<T>()
    block(taskSource)
    return taskSource.task
}

fun DataSnapshot.asUser(): User? =
    getValue(User::class.java)?.copy(uid = key!!)

fun DataSnapshot.asFeedPost(): FeedPost? =
    getValue(FeedPost::class.java)?.copy(id = key!!)

fun DatabaseReference.setValueTrueOrRemove(value: Boolean) =
    if (value) setValue(true) else removeValue()

fun <A, B> LiveData<A>.map(f: (A) -> B): LiveData<B> =
    Transformations.map(this, f)