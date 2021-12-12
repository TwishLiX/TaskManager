import gui.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/sample.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        primaryStage.setTitle("Task Manager");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//import component.core.CPU;
//import component.memory.MemoryBlock;
//import component.memory.MemoryScheduler;
//import config.Configuration;
//import utils.TactGenerator;
//
//public class Main {
//    public static void programStart() {
//        CPU.initCores();
//        MemoryScheduler.add(new MemoryBlock(0, Configuration.MEMORY_VOLUME));
//    }
//    public static void main(String[] args) {
//        programStart();
//        TactGenerator timer = new TactGenerator();
//        timer.start();
//        CPU.processGenerator();
//    }
//}