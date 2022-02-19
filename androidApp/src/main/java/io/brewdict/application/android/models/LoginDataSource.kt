package io.brewdict.application.android.models

import java.io.IOException
import io.brewdict.application.android.Result
import java.util.*

class LoginDataSource {

    fun login(identity: String, password: String): Result<LoggedInUser>{
        try {
            //TODO: Handle User Authentication
            val fakeUser = LoggedInUser(UUID.randomUUID().toString(), identity, identity, "accessToken")
            return Result.Success(fakeUser)

        } catch (e: Throwable){
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout(){
        //TODO: Revoke Authentication
    }
}
