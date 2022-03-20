module com.scrambler {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires  java.sql;
    requires  com.microsoft.sqlserver.jdbc;
    requires mysql.connector.java;
    requires xsoup;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.support;


    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires org.junit.jupiter.api;


    opens com.scrambler to javafx.fxml;
    exports com.scrambler;
    exports com.scrambler.tests to junit;

}