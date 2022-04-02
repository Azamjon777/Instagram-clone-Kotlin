package com.example.buttonnavigation.activities.addfriends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buttonnavigation.R
import com.example.buttonnavigation.activities.ViewModelFactory
import com.example.buttonnavigation.activities.showToast
import com.example.buttonnavigation.models.User
import kotlinx.android.synthetic.main.activity_add_friends.*

class AddFriendsActivity : AppCompatActivity(), FriendsAdapter.Listener {
    private lateinit var mUser: User
    private lateinit var mUsers: List<User>
    private lateinit var mAdapter: FriendsAdapter
    private lateinit var mViewModel: AddFriendsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friends)

        mAdapter = FriendsAdapter(this)

        mViewModel = ViewModelProviders.of(this, ViewModelFactory())
            .get(AddFriendsViewModel::class.java)

        back_image.setOnClickListener { finish() }

        add_friends_recycler.adapter = mAdapter
        add_friends_recycler.layoutManager = LinearLayoutManager(this)

        mViewModel.userAndFriends.observe(this, Observer {
            it?.let { (user, otherUsers) ->
                mUser = user
                mUsers = otherUsers

                mAdapter.update(mUsers, mUser.follows)
            }
        })
    }

    override fun follow(uid: String) {
        setFollow(uid, true) {
            mAdapter.followed(uid)
        }
    }

    override fun unfollow(uid: String) {
        setFollow(uid, false) {
            mAdapter.unfollowed(uid)
        }
    }

    private fun setFollow(uid: String, follow: Boolean, onSuccess: () -> Unit) {
        mViewModel.setFollow(mUser.uid, uid, follow)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message!!) }
    }
}