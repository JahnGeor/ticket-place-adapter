package ru.kidesoft.ticketplace.adapter.domain.profile

import java.util.UUID
import kotlin.properties.Delegates

class Profile {
    lateinit var id : UUID
    lateinit var loginId : UUID
    var cashier : Cashier = Cashier()
    lateinit var avatar : String
    lateinit var userName : String
    var userId by Delegates.notNull<Long>()
    lateinit var roleType : RoleType
}

class Cashier {
    lateinit var fullName : String
    var inn by Delegates.notNull<Long>()
}


class ProfileExposed() {
    lateinit var loginId : UUID
    lateinit var cashier : Cashier
    lateinit var avatar : String
    lateinit var userName : String
    var userId by Delegates.notNull<Long>()
    lateinit var roleType : RoleType
}