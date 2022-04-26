package io.brewdict.application.apis.brewdict

import io.brewdict.application.apis.brewdict.models.ReadingTypeEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


object ReadingTypeSerializer: KSerializer<ReadingTypeEnum> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("ReadingType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ReadingTypeEnum {
        return try {
            val key = decoder.decodeString()
            ReadingTypeEnum.findByKey(key)
        } catch (e: IllegalArgumentException) {
            ReadingTypeEnum.NONE
        }
    }

    override fun serialize(encoder: Encoder, value: ReadingTypeEnum) {
        encoder.encodeString(value.key)
    }
}