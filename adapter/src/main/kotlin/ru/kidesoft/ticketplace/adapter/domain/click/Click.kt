package ru.kidesoft.ticketplace.adapter.domain.click

import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.ClickRepository.ClickTable.integer
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.ClickRepository.ClickTable.references
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.ClickRepository.ClickTable.uuid
import ru.kidesoft.ticketplace.adapter.infrastructure.repository.database.h2.LoginRepository
import java.util.UUID
import kotlin.properties.Delegates

class Click: ClickExposed {
    var orderId by Delegates.notNull<Int>()
    lateinit var sourceType: SourceType

    constructor() {

    }

    constructor(clickExposed: ClickExposed) {
        clickId = clickExposed.clickId
    }

    constructor(clickExposed: ClickExposed, orderId: Int, sourceType: SourceType) : this(clickExposed) {
        this.orderId = orderId
        this.sourceType = sourceType
    }
}

open class ClickDatabase: ClickExposed {
    lateinit var id: UUID
    lateinit var loginId : UUID

    constructor(clickExposed: ClickExposed) {
        clickId = clickExposed.clickId
    }

    constructor(id: UUID, loginId : UUID, clickId: Int) {
        this.id = id
        this.loginId = loginId
        this.clickId = clickId
    }

    constructor(id : UUID, loginId : UUID, clickExposed: ClickExposed) : this(clickExposed) {
        this.id = id
        this.loginId = loginId
    }
}

open class ClickExposed {
    var clickId by Delegates.notNull<Int>()
}