package com.example.buttonnavigation.utils

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference

class FirebaseLiveData(private val reference: DatabaseReference): LiveData<DataSnapshot>(){
    private val listener = ValueEventListenerAdapter {

    }
    override fun onActive() {
        super.onActive()
        reference.addValueEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        reference.removeEventListener(listener)
    }
}