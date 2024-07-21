package ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.kidesoft.ticketplace.adapter.application.dto.Mapper
import ru.kidesoft.ticketplace.adapter.domain.click.Click
import ru.kidesoft.ticketplace.adapter.domain.click.ClickExposed
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.lang.reflect.Type
import java.util.*
import kotlin.properties.Delegates

class ClickData(
    var id: Int,
    var orderId: Int,
    var userId: Int,
    var type: String,
    var createdAt: String
) : Mapper<Click> {
    override fun mapToEntity() = Click().apply {
        this.clickId = this@ClickData.id
        this.orderId = this@ClickData.orderId
        this.sourceType = when(this@ClickData.type) {"refund" -> SourceType.REFUND "order" -> SourceType.ORDER else -> SourceType.UNDEFINED}
    }
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

