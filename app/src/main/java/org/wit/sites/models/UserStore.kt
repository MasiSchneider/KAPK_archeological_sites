package org.wit.sites.models

interface UserStore {
    fun create(user: UserModel) : UserModel
    fun changeEmail(id: Long, NewEmail: String) : Boolean
    fun changePassword(id: Long, password: String) : Boolean
    fun findById(id:Long) : UserModel?
    fun findByEmailPassword(username: String, password: String) : UserModel?
}