package org.wit.sites.models.json.users

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import org.jetbrains.anko.AnkoLogger
import org.wit.sites.helpers.exists
import org.wit.sites.helpers.read
import org.wit.sites.helpers.write
import org.wit.sites.models.SiteModel

import org.wit.sites.models.UserModel
import org.wit.sites.models.UserStore
import java.util.*

val JSON_FILE = "users.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class UserJSONStore :UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun create(user: UserModel) : UserModel {
        if(!exists(user.email)) {
            user.id = generateRandomId()
            users.add(user)
            serialize()
        }
        return user
    }

    override fun changeEmail(id: Long, NewEmail: String) : Boolean {
        if(exists(NewEmail))
            return false
        var foundUser = users.find {p -> p.id == id}
        foundUser?.email = NewEmail
        serialize()
        return true
    }

    override fun changePassword(id: Long, password: String) : Boolean {
        var foundUser = users.find {p -> p.id == id}
        foundUser?.password = password
        serialize()
        return true
    }

    override fun findById(id: Long): UserModel? {
        return users.find { it.id == id }
    }

    override fun findByEmailPassword(email: String, password: String): UserModel? {
        var foundUser = users.find {p -> p.email == email}
        if(foundUser?.password == password)
            return foundUser
        return null
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(users, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        users = Gson().fromJson(jsonString, listType)
    }

    private fun exists(email: String) : Boolean {
        return  users.any{ user -> user.email == email }
    }
}