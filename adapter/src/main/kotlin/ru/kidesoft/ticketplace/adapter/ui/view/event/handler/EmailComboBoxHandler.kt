package ru.kidesoft.ticketplace.adapter.ui.view.event.handler

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

class ComboBoxAutoComplete<T>(private val comboBox: ComboBox<T>) : EventHandler<KeyEvent> {
    private val items : ObservableList<T> = FXCollections.observableArrayList()
    private var oldIndex : Number = comboBox.selectionModel.selectedIndex
    private var savedText: String? = null

    init {
        comboBox.visibleRowCount = 12

        comboBox.items.forEach {
            items.add(it)
        }

        comboBox.editor.onKeyReleased = this

    }



    override fun handle(event: KeyEvent?) {
        if (event == null) {
            return
        }

        if (event.code == KeyCode.ENTER) {
            enterHandler()
        }

        if (event.code == KeyCode.UP) {
            upArrowHandler()
        }

        if (event.code == KeyCode.DOWN) {
            downArrowHandler()
        }

        if (event.text.matches(Regex("^[a-zA-Z0-9._%+-@]+$"))) {
            emailEnterHandler()
            return
        }

        if (event.code == KeyCode.BACK_SPACE) {
            val text = comboBox.editor.text
            updateList(text)
        }

        if (event.code == KeyCode.ESCAPE) {

        }

        if (event.code == KeyCode.ENTER) {
            comboBox.editor.end()
        }

    }

    private fun upArrowHandler() {
        comboBox.visibleRowCount = 12

        if ((comboBox.selectionModel.selectedIndex == oldIndex && comboBox.selectionModel.selectedIndex == 0) || oldIndex == -1) {
            oldIndex = -1
            comboBox.selectionModel.select(oldIndex as Int)
            comboBox.hide()
        }

        oldIndex = comboBox.selectionModel.selectedIndex

        moveCaret(comboBox.editor.text.length)
    }

    private fun downArrowHandler() {
        comboBox.visibleRowCount = 12

        if (oldIndex == -1) {
            comboBox.selectionModel.select(0)
        }

        oldIndex = comboBox.selectionModel.selectedIndex

        if (!comboBox.isShowing) {
            comboBox.selectionModel.select(oldIndex as Int)
            comboBox.show()
        }

        moveCaret(comboBox.editor.text.length)
    }

    private fun emailEnterHandler() {
        val text = comboBox.editor.text
        updateList(text)
    }

    private fun enterHandler() {

    }

    private fun updateList(text: String) {
        comboBox.editor.text = text

        val filtered = FXCollections.observableArrayList(items.filtered{comboBox.converter.toString(it).lowercase().contains(text)})

        if (filtered.isEmpty()) {
            comboBox.items.clear()
            comboBox.placeholder = Label("Не обнаружено")
        } else {
            comboBox.items.setAll(filtered)
        }

        comboBox.visibleRowCount = 12
        comboBox.editor.text = text
        moveCaret(text.length)

        if (oldIndex.toInt() > comboBox.items.size) {
            oldIndex = 0
        }
    }


    private fun moveCaret(length: Int?) {
        if (length != null) {
            comboBox.editor.positionCaret(length)
        } else {
            comboBox.editor.positionCaret(0)
        }
    }
}
//import javafx.application.Platform
//import javafx.collections.FXCollections
//import javafx.collections.ObservableList
//import javafx.event.EventHandler
//import javafx.scene.control.ComboBox
//import javafx.scene.control.ListCell
//import javafx.scene.control.ListView
//import javafx.scene.control.PopupControl
//import javafx.scene.input.KeyCode
//import javafx.scene.input.KeyEvent
//import javafx.scene.input.MouseButton
//import javafx.util.Callback
//
//
//class AutoCompleteBox<T>(private val comboBox: ComboBox<T>, private val sid: Int? = null) : EventHandler<KeyEvent> {
//    private val data: ObservableList<T> = comboBox.items
//
//    init {
//        doAutoCompleteBox()
//    }
//
//
//    private fun doAutoCompleteBox() {
//        comboBox.isEditable = true
//
////        comboBox.editor.focusedProperty().addListener { _, _, newValue ->
////            if (newValue) {
////                comboBox.show()
////            }
////        }
//
//        comboBox.editor.setOnMouseClicked { event ->
//            if (event.button == MouseButton.PRIMARY) {
//                if (event.clickCount == 2) {
//                    return@setOnMouseClicked
//                }
//            }
//            comboBox.show()
//        }
//
////        comboBox.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
////            println("selectedIndex: $newValue")
////            moveCaret(comboBox.editor.text.length)
////        }
//
//
//        comboBox.setOnKeyPressed {
//            println("Нажата клавиша: ${it.text}")
//            it.consume()
//        }
//
//        comboBox.onKeyReleased = this
//
//        if (sid != null) {
//            comboBox.selectionModel.select(sid)
//        }
//
//        comboBox.visibleRowCount = 12
//    }
//
//    override fun handle(event: KeyEvent) {
//
//        println("Отжата клавиша: ${event.text}")
//
//        if (event.code == KeyCode.ENTER) {
//            if (comboBox.selectionModel.selectedIndex == -1) {
//
//                comboBox.selectionModel.select(-1)
//
//                Platform.runLater {
//                    comboBox.scene.root.requestFocus()
//                    comboBox.hide()
//                }
//
//                return
//            }
//        }
//
//        if (event.code == KeyCode.RIGHT || event.code == KeyCode.LEFT || event.code == KeyCode.HOME || event.code == KeyCode.END || event.code == KeyCode.TAB
//        ) {
//            event.consume()
//            return
//        }
//
//        if (event.code == KeyCode.ESCAPE) {
//            Platform.runLater {comboBox.scene.root.requestFocus()}
//        }
//
//        if (event.code == KeyCode.DOWN || event.code == KeyCode.UP) {
//            if (comboBox.selectionModel.selectedIndex == -1) {
//                comboBox.selectionModel.select(0)
//            }
//
//            return
//        }
//
//
//        if (event.code == KeyCode.BACK_SPACE) {
//
//            val str = comboBox.editor.text
//            comboBox.selectionModel.clearSelection()
//            comboBox.editor.text = str
//
//            if (str!= null) {
//                comboBox.editor.text = str
//                moveCaret(str.length)
//            }
//
//        }
//
////        if (event.code == KeyCode.ENTER) { //
////            event.consume()
////            comboBox.hide()
////            return
////        }
//
//        setItems()
//    }
//
//    private fun setItems() {
//        val list = FXCollections.observableArrayList<T>()
//
//        for (datum in data) {
//            val s = comboBox.editor.text.lowercase()
//            if (datum.toString().lowercase().contains(s)) {
//                list.add(datum)
//            }
//        }
//
//        if (list.isEmpty()) {comboBox.hide(); return}
//
//        comboBox.items = list
//
//
//
//        comboBox.show()
//    }
//
//    private fun moveCaret(textLength: Int) {
//        comboBox.editor.positionCaret(textLength)
//    }
//}
//
//
///**
// *
// * // Licensed under the Apache License, Version 2.0 (the "License");
// * import javafx.collections.FXCollections;
// * import javafx.collections.ObservableList;
// * import javafx.event.EventHandler;
// * import javafx.scene.control.ComboBox;
// * import javafx.scene.input.KeyCode;
// * import javafx.scene.input.KeyEvent;
// *
// * public class Main{
// *     public static <T> void autoCompleteComboBox(ComboBox<T> comboBox, AutoCompleteMode mode) {
// *     final ObservableList<T> data = comboBox.getItems();
// *
// *     comboBox.setEditable(true);/*w  w  w. ja v a2 s .  c  om*/
// *     comboBox.getEditor().focusedProperty().addListener(observable -> {
// *         if (0 > comboBox.getSelectionModel().getSelectedIndex()) {
// *             comboBox.getEditor().setText(null);
// *         }
// *     });
// *     comboBox.addEventHandler(KeyEvent.KEY_PRESSED, t -> comboBox.hide());
// *     comboBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
// *
// *         private boolean moveCaretToPos = false;
// *         private int caretPos;
// *
// *         @Override
// *         public void handle(KeyEvent event) {
// *             switch (event.getCode()) {
// *                 case DOWN:
// *                     if (!comboBox.isShowing()) {
// *                         comboBox.show();
// *                     }
// *                 case UP:
// *                     caretPos = -1;
// *                     moveCaret(comboBox.getEditor().getText().length());
// *                     return;
// *                 case BACK_SPACE:
// *                 case DELETE:
// *                     moveCaretToPos = true;
// *                     caretPos = comboBox.getEditor().getCaretPosition();
// *                     break;
// *             }
// *
// *             if (KeyCode.RIGHT == event.getCode() || KeyCode.LEFT == event.getCode()
// *                     || event.isControlDown() || KeyCode.HOME == event.getCode()
// *                     || KeyCode.END == event.getCode() || KeyCode.TAB == event.getCode()) {
// *                 return;
// *             }
// *
// *             final ObservableList<T> list = FXCollections.observableArrayList();
// *             for (T aData : data) {
// *                 if (shouldDataBeAddedToInput(aData)) {
// *                     list.add(aData);
// *                 }
// *             }
// *             final String text = comboBox.getEditor().getText();
// *
// *             comboBox.setItems(list);
// *             comboBox.getEditor().setText(text);
// *             if (!moveCaretToPos) {
// *                 caretPos = -1;
// *             }
// *             moveCaret(text.length());
// *             if (!list.isEmpty()) {
// *                 comboBox.show();
// *             }
// *         }
// *
// *         private boolean shouldDataBeAddedToInput(T aData) {
// *             return mode.equals(AutoCompleteMode.STARTS_WITH) && inputStartsWith(aData)
// *                     || mode.equals(AutoCompleteMode.CONTAINING) && inputContains(aData);
// *         }
// *
// *         private boolean inputStartsWith(T aData) {
// *             final String dataValue = aData.toString().toLowerCase();
// *             final String inputValue = comboBox.getEditor().getText().toLowerCase();
// *             return dataValue.startsWith(inputValue);
// *         }
// *
// *         private boolean inputContains(T aData) {
// *             final String dataValue = aData.toString().toLowerCase();
// *             final String inputValue = comboBox.getEditor().getText().toLowerCase();
// *             return dataValue.contains(inputValue);
// *         }
// *
// *         private void moveCaret(int textLength) {
// *             if (-1 == caretPos) {
// *                 comboBox.getEditor().positionCaret(textLength);
// *             } else {
// *                 comboBox.getEditor().positionCaret(caretPos);
// *             }
// *             moveCaretToPos = false;
// *         }
// *     });
// * }
// * }
// *
// */

