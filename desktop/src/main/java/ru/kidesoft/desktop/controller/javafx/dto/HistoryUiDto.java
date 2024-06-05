package ru.kidesoft.desktop.controller.javafx.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.kidesoft.desktop.domain.entity.history.History;

import java.util.List;

@Getter
@Setter
@Builder
public class HistoryUiDto {
    SimpleBooleanProperty check;
    History history;
}
