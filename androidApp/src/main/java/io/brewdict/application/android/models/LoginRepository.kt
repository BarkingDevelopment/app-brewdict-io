package io.brewdict.application.android.models

import io.brewdict.application.android.Result

class LoginRepository(val dataSource: LoginDataSource) {
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init{
        user = null
    }

    fun login(identity: String, password: String): Result<LoggedInUser>{
        val result = dataSource.login(identity, password)

        if(result is Result.Success) setLoggedInUser(result.data)

        return result
    }

    fun logout(){
        user = null
        dataSource.logout()
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser){
        this.user = loggedInUser
    }
}
