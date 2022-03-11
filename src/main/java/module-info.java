module com.example.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires  java.sql;
    requires xsoup;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.api;


    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.javaproject to javafx.fxml;
    exports com.example.javaproject;
    exports com.example.javaproject.controllers;
    opens com.example.javaproject.controllers to javafx.fxml;
}