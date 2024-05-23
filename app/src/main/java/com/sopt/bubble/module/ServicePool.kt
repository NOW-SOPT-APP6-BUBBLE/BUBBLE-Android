package com.sopt.bubble.module

import com.sopt.bubble.data.service.FriendsDetailService
import com.sopt.bubble.data.service.FriendsService

object ServicePool {
    val friendService = ApiFactory.create<FriendsService>()
    val friendDetailService = ApiFactory.create<FriendsDetailService>()
}