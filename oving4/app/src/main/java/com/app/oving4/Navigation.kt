package com.app.oving4

import kotlinx.serialization.Serializable

@Serializable
object FriendListNav

@Serializable
object FriendFormNav

@Serializable
data class FriendEditNav(val index: Int)
@Serializable
data class FriendDetailNav(val index: Int)

