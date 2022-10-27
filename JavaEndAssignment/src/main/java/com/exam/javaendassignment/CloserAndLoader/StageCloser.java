package com.exam.javaendassignment.CloserAndLoader;

import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StageCloser {
    public void closeStageByEvent(Event event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
