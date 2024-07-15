package ru.kidesoft.ticketplace.adapter.ui

import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.Window
import org.controlsfx.dialog.ExceptionDialog
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.ui.view.FxmlView
import ru.kidesoft.ticketplace.adapter.ui.view.ViewController
import kotlin.reflect.KClass

class StageManager(var stage: Stage, baseViewController: ViewController) : SceneManager, ApplicationManager,
    Notification, ru.kidesoft.ticketplace.adapter.application.presenter.Alert {
    private val viewControllers: MutableList<ViewController> = mutableListOf()

    fun <P : Presenter> getPresenter(presenter : KClass<P>): P? {
        return viewControllers.firstOrNull { presenter.java.isAssignableFrom(it.javaClass) } ?.let {
            presenter.java.cast(it)
        }
    }

    init {
        baseViewController.stageManager = this
        val root = getRoot(baseViewController)
        stage.apply {
            scene = javafx.scene.Scene(root as Parent?, 600.0, 415.0)
        }.also {
            setOwnerProperties(it)
        }

    }

    fun addScene(
        viewController: ViewController
    ) {
        viewController.stageManager = this
        viewControllers.add(viewController)
    }


    override fun openScene(scene: ru.kidesoft.ticketplace.adapter.application.presenter.Scene) {
        require(scene != ru.kidesoft.ticketplace.adapter.application.presenter.Scene.BASE)

        val view = viewControllers.firstOrNull { view ->
            val fxmlViewAnnotation = view::class.annotations.find { it is FxmlView } as? FxmlView
            fxmlViewAnnotation?.scene == scene
        } ?: throw IllegalArgumentException("There is no view with fxmlView.scene = ${scene.name}")

        var root = getRoot(view)

        val borderPane = stage.scene.root as BorderPane

        if (scene == Scene.AUTH) {
            borderPane.top.isVisible = false
            borderPane.top.isDisable = true
        } else {
            borderPane.top.isDisable = false
            borderPane.top.isVisible = true
        }

        val stackPane = borderPane.center as StackPane

        stackPane.children.clear()
        stackPane.children.addAll(root)


    }

    private fun getRoot(viewController: ViewController): Node {
        val annotationFxml = viewController::class.annotations.find { it is FxmlView }?.let {
            it as FxmlView
        } ?: throw IllegalArgumentException("fxml view not found")

        val fxmlLoader: FXMLLoader = FXMLLoader()

        fxmlLoader.location = viewController.javaClass.getResource(annotationFxml.path)
            ?: throw IllegalArgumentException("fxml file not found")

        fxmlLoader.setController(viewController)

        val pane = fxmlLoader.load<Node>()

        return pane
    }


    private fun setOwnerProperties(window: Window) {
        stage.icons.add(Image("/ru/kidesoft/ticketplace/adapter/assets/icon.png"))
        stage.title = "Ticket Place"
    }


    fun createNewStage(): Stage {
        return Stage().apply { centerOnScreen() }.also {
            setOwnerProperties(it)
        }

    }

    override fun closeApplication(code: Int) {
        Platform.exit()
    }

    override fun showNotification(notificationType: NotificationType, message: String, title: String) {

    }

    override fun showAlert(alertType: AlertType, message: String, title: String, exception: Exception?) {

        when (alertType) {
            AlertType.ERROR -> {
                exception?.run {
                    showErrorAlert(message, title, exception)
                } ?: run {
                    defaultAlert(Alert.AlertType.ERROR, message, title)
                }
                // TODO: подумать, нужен ли здесь throw NullPointerException(exception must be non-null with Error Alert Type (но это не всегда правда, поскольку есть кейсы, где нет Exception)
            }

            AlertType.WARN -> {
                defaultAlert(Alert.AlertType.WARNING, message, title)
            }

            AlertType.INFO -> {
                defaultAlert(Alert.AlertType.INFORMATION, message, title)
            }
        }
    }

    companion object {
        fun showErrorAlert(message: String, title: String, exception: Exception) {
            val dialog = ExceptionDialog(exception)
            dialog.contentText = message
            dialog.headerText = title
            dialog.initModality(Modality.APPLICATION_MODAL)
            dialog.showAndWait()
        }

        fun defaultAlert(type: Alert.AlertType, message: String, title: String) {
            val alert = Alert(type)
            alert.headerText = title
            alert.contentText = message
            alert.initModality(Modality.APPLICATION_MODAL)
            alert.showAndWait()
        }
    }
}