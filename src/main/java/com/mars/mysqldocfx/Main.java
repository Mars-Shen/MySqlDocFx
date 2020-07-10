package com.mars.mysqldocfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        primaryStage.getIcons().add(new Image(classLoader.getResourceAsStream("images/logo.png")));
        URL resource = classLoader.getResource("layout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Mysql文档生成器");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        Controller controller = fxmlLoader.getController();
        controller.init();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
