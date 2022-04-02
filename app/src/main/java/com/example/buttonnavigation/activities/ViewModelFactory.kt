package com.example.buttonnavigation.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.buttonnavigation.activities.addfriends.AddFriendsViewModel
import com.example.buttonnavigation.activities.addfriends.FirebaseAddFriendsRepository
import com.example.buttonnavigation.activities.editprofile.EditProfileViewModel
import com.example.buttonnavigation.activities.editprofile.FirebaseEditProfileRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFriendsViewModel::class.java)){
                return AddFriendsViewModel(FirebaseAddFriendsRepository()) as T
        }else if(modelClass.isAssignableFrom(EditProfileViewModel::class.java)){
            return EditProfileViewModel(FirebaseEditProfileRepository()) as T
        }else{
            error("Unknown view model class $modelClass")
        }
    }
}