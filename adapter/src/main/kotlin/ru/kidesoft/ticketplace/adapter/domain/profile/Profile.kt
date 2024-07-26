package ru.kidesoft.ticketplace.adapter.domain.profile

import java.util.UUID

data class Profile(var id : UUID, var loginId : UUID, val fullName: String, val inn: Long, val avatar: String, val userName: String, val userId: Int, val roleType: RoleType) {
    constructor(id: UUID, loginId: UUID, profileInfo: ProfileInfo) : this(id, loginId, profileInfo.fullName, profileInfo.inn, profileInfo.avatar, profileInfo.userName, profileInfo.userId, profileInfo.roleType)
}

open class ProfileInfo(val fullName: String, val inn: Long, val avatar: String, val userName: String, val userId: Int, val roleType: RoleType)

class Cashier(val fullName: String, val inn: Long)