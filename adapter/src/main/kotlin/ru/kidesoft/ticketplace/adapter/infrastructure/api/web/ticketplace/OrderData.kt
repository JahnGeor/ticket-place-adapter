package ru.kidesoft.ticketplace.adapter.infrastructure.api.web.ticketplace

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.kidesoft.ticketplace.adapter.application.dto.Mapper
import ru.kidesoft.ticketplace.adapter.domain.order.OrderExposed
import ru.kidesoft.ticketplace.adapter.domain.order.PaymentType
import ru.kidesoft.ticketplace.adapter.domain.order.StatusType
import ru.kidesoft.ticketplace.adapter.domain.order.Ticket
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.properties.Delegates
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

val formatterForeign = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
val longFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
val shortFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
val zoneId = ZoneId.of("Europe/Moscow")

class OrderData: Mapper<OrderExposed> {
    var id by Delegates.notNull<Int>()
    var kassirName: String? = null
    var clientName: String? = null
    var userName: String? = null
    var paymentType: String? = null
    lateinit var createdAt: String
    var tickets = mutableListOf<TicketData>()

    override fun mapToEntity(): OrderExposed {
        val ticketsEntityMap = mutableListOf<Ticket>()

        for (ticket in tickets) {

            var ticketExposed = Ticket(
                id = ticket.id, zone = ticket.zone,
                amount = ticket.amount, number = ticket.number,
                status = StatusType.findByValue(ticket.status),
                rowSector = ticket.rowSector,
                seatNumber = ticket.seatNumber, ageLimit = ticket.ageLimit,
                showName = ticket.showName,
                dateTime = LocalDateTime.parse(ticket.dateTime, formatterForeign).atZone(zoneId)
            )

            ticketsEntityMap.add(ticketExposed)
        }

        val createdAt = kotlin.runCatching { LocalDateTime.parse(createdAt, shortFormatter).atZone(zoneId) }.getOrElse {
            run { LocalDateTime.parse(createdAt, formatterForeign).atZone(zoneId) }
        }

        val paymentType = paymentType?.let {
            PaymentType.findByValue(it)
        } ?: PaymentType.UNDEFINED

        val cashierName = kassirName?.let { it } ?: clientName?.let { it }?: userName?.let { it } ?: null

        return OrderExposed(this.id, paymentType, createdAt, ticketsEntityMap, cashierName)
    }
}

class TicketData {
    var id by Delegates.notNull<Int>()
    var amount by Delegates.notNull<Float>()
    lateinit var number: String
    var seatNumber by Delegates.notNull<Int>()
    var rowSector by Delegates.notNull<Int>()
    lateinit var zone: String
    lateinit var status: String
    lateinit var ageLimit: String
    lateinit var showName: String
    lateinit var dateTime: String
}

class OrderAdapter : JsonDeserializer<OrderData> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): OrderData {
        val jsonObject = json.asJsonObject

        var orderData = OrderData()

        val dataJsonObject = jsonObject.get("data").asJsonObject
        val ticketsJsonArray = dataJsonObject.get("tickets").asJsonArray

        for (ticket in ticketsJsonArray) {
            val ticket = TicketData().apply {
                id = ticket.asJsonObject.get("id").asInt
                amount = ticket.asJsonObject.get("amount").asFloat
                number = ticket.asJsonObject.get("number").asString
                seatNumber = ticket.asJsonObject.get("seat_number").asInt
                rowSector = ticket.asJsonObject.get("row_sector").asInt
                zone = ticket.asJsonObject.get("zona").asString
                status = ticket.asJsonObject.get("status").asString
                dateTime = ticket.asJsonObject.get("event").asJsonObject.get("date_time").asString
                ageLimit = ticket.asJsonObject.get("event").asJsonObject.get("show").asJsonObject.get("age_limit").asString
                showName = ticket.asJsonObject.get("event").asJsonObject.get("show").asJsonObject.get("name").asString
            }

            orderData.tickets.add(ticket)
        }

        orderData.id = dataJsonObject.get("id").asInt

        orderData.paymentType = if (dataJsonObject.has("payment_type") and dataJsonObject.get("payment_type").asString.isNotEmpty()) {
            dataJsonObject.get("payment_type").asString
        } else {
            null
        }

        orderData.kassirName = if (dataJsonObject.has("kassir_name") and dataJsonObject.get("kassir_name").asString.isNotEmpty()) {
            dataJsonObject.get("kassir_name").asString
        } else {
            null
        }

        orderData.clientName = if (dataJsonObject.has("client_name") and dataJsonObject.get("client_name").asString.isNotEmpty()) {
            dataJsonObject.get("client_name").asString
        } else {
            null
        }

        orderData.userName = if (dataJsonObject.has("user_name") and dataJsonObject.get("user_name").asString.isNotEmpty()) {
            dataJsonObject.get("user_name").asString
        } else {
            null
        }

        orderData.createdAt = dataJsonObject.get("date_time").asString

        return orderData
    }
}