package com.utechia.tdf.reservation

import androidx.lifecycle.MutableLiveData
import com.utechia.domain.model.profile.ProfileModel

object InvitationListener {
    val addInvitationListener = MutableLiveData<ProfileModel?>()
    val removeInvitationListener = MutableLiveData<ProfileModel>()
    val addGuestListener = MutableLiveData<ProfileModel?>()
    val removeGuestListener = MutableLiveData<ProfileModel>()
}