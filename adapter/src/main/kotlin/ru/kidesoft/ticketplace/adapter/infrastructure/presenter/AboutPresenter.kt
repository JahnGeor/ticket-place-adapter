package ru.kidesoft.ticketplace.adapter.infrastructure.presenter

import ru.kidesoft.ticketplace.adapter.application.port.PropertiesPort

interface AboutViewOutput {
    fun setVersion(version: String)
}

interface AboutView : ViewManager, AboutViewOutput {

}

class AboutPresenter(private val propertiesPort : PropertiesPort) : Presenter() {
    fun update(view: AboutViewOutput) {
        propertiesPort.getVersion()?.let {
            view.setVersion(it)
        }
    }
}