package ru.kidesoft.ticketplace.adapter.ui

import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Alert
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Modality
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene
import ru.kidesoft.ticketplace.adapter.ui.view.FxmlView
import ru.kidesoft.ticketplace.adapter.ui.view.ViewController

class StageManager(var stage: Stage, baseViewController: ViewController) : SceneManager {
    private val viewControllers : MutableList<ViewController> = mutableListOf()

    init {
        baseViewController.stageManager = this
        val root = getRoot(baseViewController)
        stage.scene = javafx.scene.Scene(root as Parent?, 600.0, 415.0)
    }

    fun addScene(scene: ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene, viewController : ViewController) {
        viewController.stageManager = this
        viewControllers.add(viewController)
    }


    override fun openScene(scene : ru.kidesoft.ticketplace.adapter.infrastructure.presenter.ui.Scene) {
        require(scene != Scene.BASE)

        val view = viewControllers.firstOrNull { view ->
            val fxmlViewAnnotation = view::class.annotations.find { it is FxmlView } as? FxmlView
            fxmlViewAnnotation?.scene == scene
        } ?: throw IllegalArgumentException("There is no view with fxmlView.scene = ${scene.name}")

        var root = getRoot(view)

        val borderPane  = stage.scene.root as BorderPane
        val stackPane = borderPane.center as StackPane

        stackPane.children.clear()
        stackPane.children.addAll(root)
    }

    override fun closeApplication() {
        Platform.exit()
    }

    private fun getRoot(viewController : ViewController) : Node {
        val annotationFxml = viewController::class.annotations.find { it is FxmlView }?.let {
            it as FxmlView
        } ?: throw IllegalArgumentException("fxml view not found")

        val fxmlLoader: FXMLLoader = FXMLLoader()

        fxmlLoader.location = viewController.javaClass.getResource(annotationFxml.path) ?: throw IllegalArgumentException("fxml file not found")

        fxmlLoader.setController(viewController)

        val pane = fxmlLoader.load<Node>()

        return pane
    }

    override fun onError(e : Exception) {

    }

    override fun onWarning() {

    }

    override fun onErrorAlert(e : Exception) {
        val alert = Alert(Alert.AlertType.ERROR).apply {
            headerText = "Во время выполнения программы произошла ошибка"
            contentText = e.localizedMessage
        }
        alert.initOwner(stage)
        alert.initModality(Modality.APPLICATION_MODAL)
        alert.showAndWait()
    }

    override fun onWarningAlert() {
        TODO("Not yet implemented")
    }

    override fun onInformationAlert() {
        TODO("Not yet implemented")
    }
}