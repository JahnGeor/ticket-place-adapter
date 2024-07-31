package ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.kidesoft.ticketplace.adapter.application.dto.Mapper
import ru.kidesoft.ticketplace.adapter.domain.click.Click
import ru.kidesoft.ticketplace.adapter.domain.click.ClickInfo
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.lang.reflect.Type
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

val clickDateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_INSTANT

class ClickData(
    var id: Int,
    var orderId: Int,
    var userId: Int,
    var type: String,
    var createdAt: String
) : Mapper<ClickInfo> {
    override fun mapToEntity() =
        ClickInfo(
            this@ClickData.id,
            this@ClickData.orderId,
            when (this@ClickData.type) {
                "refund" -> SourceType.REFUND
                "order" -> SourceType.ORDER
                else -> SourceType.UNDEFINED
            },
            Instant.from(clickDateFormatter.parse(this@ClickData.createdAt)).atZone(ZoneId.of("Europe/Moscow"))
        )
}

class ClickDataAdapter : JsonDeserializer<ClickData> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ClickData {
        val jsonObject = json.asJsonObject.getAsJsonObject("data")
        val clickId = jsonObject.get("id").asInt
        val userId = jsonObject.get("user_id").asInt
        val orderId = jsonObject.get("order_id").asInt
        val type = jsonObject.get("type").asString
        val createdAt = jsonObject.get("created_at").asString

        return ClickData(clickId, orderId, userId, type, createdAt)
    }

}

