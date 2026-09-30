package com.app.oving4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FriendViewModel : ViewModel() {

    var friends by mutableStateOf(listOf<Friend>())
        private set

    var name by mutableStateOf("")
        private set

    var birthday by mutableStateOf("")
        private set

    fun changeName(newName: String) {
        name = newName
    }

    fun changeBirthday(newBirthday: String) {
        birthday = newBirthday
    }

    fun addFriend() {
        friends = friends + Friend(name = name, birthday = birthday)
        name = ""
        birthday = ""
    }

    fun startEdit(index: Int) {
        val friend = friends[index]
        name = friend.name
        birthday = friend.birthday
    }

    fun saveEdit(index: Int) {
        val updated = friends.toMutableList()
        updated[index] = Friend(name = name, birthday = birthday)
        friends = updated
        name = ""
        birthday = ""
    }

    fun clearForm() {
        name = ""
        birthday = ""
    }

}