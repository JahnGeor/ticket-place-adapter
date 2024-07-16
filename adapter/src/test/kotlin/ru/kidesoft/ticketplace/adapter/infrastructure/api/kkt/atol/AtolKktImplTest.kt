package ru.kidesoft.ticketplace.adapter.infrastructure.api.kkt.atol

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

import ru.atol.drivers10.fptr.IFptr

class AtolKktImplTest {


    val repo = AtolKktImpl()

    @Test()
    @DisplayName("ВыполнитьПодключение")
    fun setConnection() {
        repo.setConnection()
    }

    @Test
    fun getConnection() {

    }

    @Test
    fun openConnection() {
    }

    @Test
    fun closeConnection() {
    }

    @Test
    fun openShift() {
    }

    @Test
    fun closeShift() {
    }

    @Test
    fun getShiftState() {
    }
}