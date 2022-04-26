package io.brewdict.application.apis.brewdict.models

enum class ReadingTypeEnum(val key: String, val title: String, val unit: String) {
    SG( "density","Specific Gravity","SG"),
    TEMP("temperature", "Temperature","C"),
    NONE("", "","");

    companion object {
        fun findByKey(
            key: String,
            default: ReadingTypeEnum = NONE
        ): ReadingTypeEnum {
            return values().find { it.key == key } ?: default
        }
    }
}