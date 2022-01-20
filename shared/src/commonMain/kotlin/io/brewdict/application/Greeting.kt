package io.brewdict.application

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}