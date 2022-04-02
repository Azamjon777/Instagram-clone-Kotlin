package com.example.buttonnavigation.activities

import android.content.Intent
import android.os.Bundle
import com.example.buttonnavigation.R
import com.example.buttonnavigation.models.FeedPost
import com.example.buttonnavigation.models.User
import com.example.buttonnavigation.utils.CameraHelper
import com.example.buttonnavigation.utils.FirebaseHelper
import com.example.buttonnavigation.utils.GlideApp
import com.example.buttonnavigation.utils.ValueEventListenerAdapter
import kotlinx.android.synthetic.main.activity_share.*

class ShareActivity : BaseActivity(2) {

    private lateinit var mCamera: CameraHelper
    private lateinit var mFirebase: FirebaseHelper
    private lateinit var mUser: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        mFirebase = FirebaseHelper(this)
        mCamera = CameraHelper(this)
        mCamera.takeCameraPicture()

        back_image.setOnClickListener { finish() }
        share_text.setOnClickListener {
            share()
            finish()
        }
        mFirebase.currentUserReference().addValueEventListener(ValueEventListenerAdapter{
            mUser = it.asUser()!!
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == mCamera.REQUEST_CODE){
            if (resultCode == RESULT_OK){
                GlideApp.with(this).load(mCamera.imageUri).centerCrop().into(post_image)
            }else{
                finish()
            }
        }
    }


    private fun share() {
        val imageUri = mCamera.imageUri
        if (imageUri != null){
            val uid = mFirebase.currentUid()!!
            mFirebase.storage.child("users").child(uid).child("images")
                .child(imageUri.lastPathSegment!!).putFile(imageUri).addOnCompleteListener {
                    if (it.isSuccessful){
                        val imageDownloadUrl = it.result!!.storage.downloadUrl.toString()
                        mFirebase.database.child("images").child(uid).push()
                            .setValue(imageDownloadUrl)
                            .addOnCompleteListener {
                                if (it.isSuccessful){
                                    mFirebase.database.child("feed-posts").child(uid)
                                        .push()
                                        .setValue(makeFeedPosts(uid, imageDownloadUrl))
                                        .addOnCompleteListener {
                                            if (it.isSuccessful){
                                                startActivity(Intent(this, ProfileActivity::class.java))
                                                finish()
                                            }
                                        }
                                }else{
                                    showToast(it.exception!!.message!!)
                                }
                            }
                    }else{
                        showToast(it.exception!!.message!!)
                    }
                }
        }
    }
    private fun makeFeedPosts(uid: String, imageDownloadUrl: String): FeedPost {
        return FeedPost(
            uid = uid,
            username = mUser.username,
            image = imageDownloadUrl,
            caption = caption_input.text.toString(),
            photo = mUser.photo
        )
    }
}


