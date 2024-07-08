package ru.kidesoft.ticketplace.adapter.ui

import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import ru.kidesoft.ticketplace.adapter.ui.presenter.SceneManager
import ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene
import ru.kidesoft.ticketplace.adapter.ui.view.BaseView
import ru.kidesoft.ticketplace.adapter.ui.view.FxmlView
import ru.kidesoft.ticketplace.adapter.ui.view.View

class StageManager(var stage: Stage, baseView: View) : SceneManager {
    private val views : MutableList<View> = mutableListOf()

    init {
        baseView.stageManager = this
        val root = getRoot(baseView)
        stage.scene = javafx.scene.Scene(root as Parent?, 200.0, 200.0)
    }

    fun addScene(scene: ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene, view : View) {
        view.stageManager = this
        views.add(view)
    }


    override fun openScene(scene : ru.kidesoft.ticketplace.adapter.ui.presenter.ui.Scene) {
        require(scene != Scene.BASE)

        val view = views.firstOrNull { view ->
            val fxmlViewAnnotation = view::class.annotations.find { it is FxmlView } as? FxmlView
            fxmlViewAnnotation?.scene == scene
        } ?: throw IllegalArgumentException("There is no view with fxmlView.scene = ${scene.name}")

        var root = getRoot(view)

        val borderPane  = stage.scene.root as BorderPane
        val stackPane = borderPane.center as StackPane

        stackPane.children.clear()
        stackPane.children.addAll(root)
    }

    private fun getRoot(view : View) : Node {
        val annotationFxml = view::class.annotations.find { it is FxmlView }?.let {
            it as FxmlView
        } ?: throw IllegalArgumentException("fxml view not found")

        val fxmlLoader: FXMLLoader = FXMLLoader()

        fxmlLoader.location = view.javaClass.getResource(annotationFxml.path) ?: throw IllegalArgumentException("fxml file not found")

        fxmlLoader.setController(view)

        val pane = fxmlLoader.load<Node>()

        return pane
    }


}