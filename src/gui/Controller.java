package gui;

import config.Configuration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utils.GlobalScheduler;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Thread {

    private final Stage stage = new Stage();
    private final GlobalScheduler globalScheduler = new GlobalScheduler();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea actualProcessField = new TextArea();

    @FXML
    private TextArea busyBlocksField = new TextArea();

    @FXML
    private TextArea busyCoresField = new TextArea();

    @FXML
    private Button exitBtn = new Button();

    @FXML
    private TextArea doneProcessField = new TextArea();

    @FXML
    private TextArea rejectedProcessField = new TextArea();

    @FXML
    private Button startBtn = new Button();

    @Override
    public void run(){
        actualProcessField.setFont(Font.font(10));
        busyBlocksField.setFont(Font.font(10));
        doneProcessField.setFont(Font.font(10));
        rejectedProcessField.setFont(Font.font(10));
        while (true) {
            actualProcessField.setText(Configuration.getActualProcesses());
            rejectedProcessField.setText(Configuration.getRejectedProcesses());
            doneProcessField.setText(Configuration.getDoneProcesses());
            busyBlocksField.setText(Configuration.getMemoryBlocks());
            busyCoresField.setText(Configuration.getActiveCores());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize() {
        startBtn.setOnAction(event -> {
                GlobalScheduler.programStart();
                globalScheduler.start();
                start();
        });
        exitBtn.setOnAction(event -> {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            stage.close();
            System.exit(0);
        });
    }
}