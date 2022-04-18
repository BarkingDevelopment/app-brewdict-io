package io.brewdict.application.api_consumption.serializers

import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.SimpleDateFormat
import java.util.*

@kotlin.OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
object DateSerializer: KSerializer<Date> {
    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString(value.time.toString())
    }

    override fun deserialize(decoder: Decoder): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val decoded = decoder.decodeString()
        return formatter.parse(decoded) as Date
    }
}