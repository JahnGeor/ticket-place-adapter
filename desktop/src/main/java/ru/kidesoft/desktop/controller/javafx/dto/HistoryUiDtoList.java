package ru.kidesoft.desktop.controller.javafx.dto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class HistoryUiDtoList {
    public ObservableList<HistoryUiDto> list = FXCollections.observableList(new ArrayList<>());
}
