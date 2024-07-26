package ru.kidesoft.ticketplace.adapter.ui

import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Hyperlink
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.image.Image
import javafx.scene.layout.BorderPane
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority
import javafx.scene.layout.StackPane
import javafx.stage.Modality
import javafx.stage.Stage
import org.controlsfx.control.Notifications
import org.controlsfx.dialog.ExceptionDialog
import org.kordamp.ikonli.javafx.FontIcon
import ru.kidesoft.ticketplace.adapter.application.presenter.*
import ru.kidesoft.ticketplace.adapter.ui.view.FxmlView
import ru.kidesoft.ticketplace.adapter.ui.view.ViewController
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.reflect.KClass
import kotlin.system.exitProcess


class StageManager(var stage: Stage, baseViewController: ViewController) : SceneManager, ApplicationManager,
    Notification, ru.kidesoft.ticketplace.adapter.application.presenter.Alert {
    private val viewControllers: MutableList<ViewController> = mutableListOf()

    override fun <P : Presenter> getPresenter(presenter: KClass<P>): P? {
        return viewControllers.firstOrNull { presenter.java.isAssignableFrom(it.javaClass) }?.let {
            presenter.java.cast(it)
        }
    }

    init {
        baseViewController.stageManager = this
        val root = getRoot(baseViewController)
        stage.apply {
            scene = javafx.scene.Scene(root as Parent?, 600.0, 415.0)
        }
        stage.setOnCloseRequest {
            closeApplication(0)
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

    override fun closeApplication(code: Int) {
        stage.close()
        Platform.exit()
        exitProcess(code)
    }

    override fun showNotification(notificationType: NotificationType, title: String, message: String) {
        Platform.runLater {
            val path =
                javaClass.getResource("/ru/kidesoft/ticketplace/adapter/assets/css/notification.css").toExternalForm()
            stage.scene.stylesheets.removeAll(path)
            stage.scene.stylesheets.add(path)

            val notification = Notifications.create().title(title).text(message).position(Pos.TOP_RIGHT)

            when (notificationType) {
                NotificationType.INFO -> notification.show()
                NotificationType.WARN -> notification.showWarning()
                NotificationType.ERROR -> notification.showError()
            }
        }

    }

    override fun showAlert(alertType: AlertType, title: String, message: String, exception: Throwable?) {
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
        fun showErrorAlert(message: String, title: String, exception: Throwable) {
            val dialog = ExceptionDialog(exception)
            dialog.contentText = message
            dialog.headerText = title
            dialog.initModality(Modality.APPLICATION_MODAL)

            setOwnerProperties(dialog.dialogPane.scene.window as Stage)

            dialog.showAndWait()
        }


        fun defaultAlert(type: Alert.AlertType, message: String, title: String) {
            val alert = Alert(type)
            alert.headerText = title
            alert.contentText = message
            alert.initModality(Modality.APPLICATION_MODAL)

            setOwnerProperties(alert.dialogPane.scene.window as Stage)

            alert.showAndWait()
        }

        fun setOwnerProperties(owner: Stage) {
            owner.icons.add(Image("/ru/kidesoft/ticketplace/adapter/assets/icon.png"))
            owner.title = "Ticket Place"
        }
    }
}