package ru.kidesoft.ticketplace.adapter.application.usecase.action

import ru.kidesoft.ticketplace.adapter.application.port.*
import ru.kidesoft.ticketplace.adapter.application.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.application.usecase.SaveHistory
import ru.kidesoft.ticketplace.adapter.application.usecase.Usecase
import ru.kidesoft.ticketplace.adapter.application.usecase.kkt.GetCurrentKktPort
import ru.kidesoft.ticketplace.adapter.application.usecase.web.GetWebPort
import ru.kidesoft.ticketplace.adapter.domain.history.*
import ru.kidesoft.ticketplace.adapter.domain.order.OperationType
import ru.kidesoft.ticketplace.adapter.domain.order.OrderExposed
import ru.kidesoft.ticketplace.adapter.domain.order.PrintType
import ru.kidesoft.ticketplace.adapter.domain.order.SourceType
import java.time.ZonedDateTime

class PrintAction(commonPort: CommonPort) :
    Usecase<PrintAction.Input, PrintAction.Output>(commonPort) {

    data class Input(
        var orderId: Int,
        var sourceType: SourceType,
        var isPrintTicket: Boolean? = null,
        var isPrintCheck: Boolean? = null
    ) : Usecase.Input {
        var operationType: OperationType = when(sourceType) {
            SourceType.ORDER -> OperationType.ORDER
            SourceType.REFUND -> OperationType.REFUND
            SourceType.UNDEFINED -> throw IllegalArgumentException("Undefined source type $sourceType")
        }

        constructor(orderId: Int, sourceType: SourceType, operationType: OperationType, isPrintCheck: Boolean? = null, isPrintTicket: Boolean? = null) : this(orderId, sourceType) {
            this.operationType = operationType
            this.isPrintTicket = isPrintTicket
            this.isPrintCheck = isPrintCheck
        }
    }

    class Output : Usecase.Output {
        var checkPrinted = false
        var ticketPrinted = false
    }

    override suspend fun invoke(input: Input?, sceneManager: SceneManager?): Output {
        val output = Output()

        validate(input)

        val currentSetting = commonPort.databasePort.getSetting().getByCurrent()
            ?: throw IllegalArgumentException("Не найдены данные настроек по текущему пользователю")

        val webPort = GetWebPort(commonPort).invoke(sceneManager = sceneManager).webPort

        var kktPort = GetCurrentKktPort(commonPort).invoke().kktPort

        val orderExposed = runCatching {
            webPort.getOrder(input!!.orderId!!, input!!.sourceType!!).mapToEntity()
        }.onFailure { e ->
            SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                this.step = Step.GET_ORDER
                this.historyPayload = HistoryPayload(
                    sourceType = input!!.sourceType,
                    createdAt = ZonedDateTime.now(),
                    statusType = ErrorStatus.ERROR,
                    operationType = input.operationType,
                    error = e.message ?: "Неизвестная ошибка"
                )
                this.orderId = input!!.orderId
            })
            logger.error("Ошибка в процессе получения данных заказа #${input!!.orderId}")
        }.getOrThrow()



        if (input?.isPrintCheck ?: currentSetting.print.isPrintingCheck) {
            val orderForCheck = runCatching { orderExposed.buildFor(PrintType.CHECK) }.onFailure { // Обработка бизнес-правил для печати чеков
                SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                    this.step = Step.PRINT_CHECK
                    this.historyPayload = HistoryPayload(
                        sourceType = input!!.sourceType,
                        createdAt = ZonedDateTime.now(),
                        statusType = ErrorStatus.SUCCESS,
                        operationType = input!!.operationType,
                        error = "${it.message}"
                    )
                    this.orderId = input!!.orderId
                })

                logger.warn("Билет ${input!!.orderId} не печатается в следствии бизнес-правила: ${it.message}")
            }.getOrNull()

            orderForCheck?.let {
                kotlin.runCatching { kktPort.print(it, input!!.operationType) }.onFailure {
                    SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                        this.step = Step.PRINT_CHECK
                        this.historyPayload = HistoryPayload(
                            sourceType = input!!.sourceType,
                            createdAt = ZonedDateTime.now(),
                            statusType = ErrorStatus.ERROR,
                            operationType = input!!.operationType,
                            error = "${it.message}"
                        )
                        this.orderId = input!!.orderId
                    })

                    logger.error("Во время печати чека ${input!!.orderId} возникла ошибка: ${it.message}")
                }.onSuccess {
                    SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                        this.step = Step.PRINT_CHECK
                        this.historyPayload = HistoryPayload(
                            sourceType = input!!.sourceType,
                            createdAt = ZonedDateTime.now(),
                            statusType = ErrorStatus.SUCCESS,
                            operationType = input!!.operationType,
                            error = ""
                        )
                        this.orderId = input!!.orderId
                    })

                    logger.info("Чек ${input!!.orderId} успешно напечатан")

                    output.checkPrinted = true
                }

            }
        }

        if (input?.isPrintTicket?: currentSetting.print.isPrintingTicket) {
            val orderForTicket = runCatching {orderExposed.buildFor(PrintType.TICKET)}
                .onFailure { // Обработка бизнес-правил для печати билетов
                    SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                        this.step = Step.PRINT_TICKET
                        this.historyPayload = HistoryPayload(
                            sourceType = input!!.sourceType,
                            createdAt = ZonedDateTime.now(),
                            statusType = ErrorStatus.SUCCESS,
                            operationType = input!!.operationType,
                            error = ""
                        )
                        this.orderId = input!!.orderId
                    })

                    logger.warn("Билет ${input!!.orderId} не печатается в следствии бизнес-правила: ${it.message}")
                }.getOrNull()

            orderForTicket?.let {
                kotlin.runCatching { commonPort.printerPort.print(currentSetting.print, orderExposed) }.onFailure {
                    SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                        this.step = Step.PRINT_TICKET
                        this.historyPayload = HistoryPayload(
                            sourceType = input!!.sourceType,
                            createdAt = ZonedDateTime.now(),
                            statusType = ErrorStatus.ERROR,
                            operationType = input!!.operationType,
                            error = it.message?: "Неизвестная ошибка"
                        )
                        this.orderId = input!!.orderId
                    })

                    logger.error("Во время печати билета ${input!!.orderId} возникла ошибка: ${it.message}")
                }.onSuccess {
                    SaveHistory(commonPort).invoke(SaveHistory.Input().apply {
                        this.step = Step.PRINT_TICKET
                        this.historyPayload = HistoryPayload(
                            sourceType = input!!.sourceType,
                            createdAt = ZonedDateTime.now(),
                            statusType = ErrorStatus.SUCCESS,
                            operationType = input!!.operationType,
                            error = ""
                        )
                        this.orderId = input!!.orderId
                    })

                    logger.info("Билет ${input!!.orderId} успешно напечатан")

                    output.ticketPrinted = true
                }
            }
        }

        return output
    }


    override fun present(output: Output, sceneManager: SceneManager) {

    }

    private fun validate(input: Input?) {
        if (input == null) throw IllegalArgumentException("${this::class.simpleName} требует наличие данных во входном наборе ${Input::class.qualifiedName}")
    }

}