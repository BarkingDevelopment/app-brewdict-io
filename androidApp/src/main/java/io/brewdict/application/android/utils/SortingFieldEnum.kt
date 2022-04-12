package io.brewdict.application.android.utils

interface SortingFieldEnum<in T> {
    val acronym: String
    val field: T.() -> Any
}