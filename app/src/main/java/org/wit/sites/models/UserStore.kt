package org.wit.sites.models

interface UserStore {
    fun create(user: UserModel) : UserModel
    fun changeEmail(id: Long, email: String)
    fun changePassword(id: Long, password: String)
    fun findById(id:Long) : UserModel?
    fun findByEmailPassword(username: String, password: String) : UserModel?
}