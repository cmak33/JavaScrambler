package com.example.javaproject.Classes.Translator;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;

public abstract class WebDriverCreatorWithOptions<T extends AbstractDriverOptions> extends WebDriverCreator{
    protected T options;

    protected WebDriverCreatorWithOptions(String webDriverPath) {
        super(webDriverPath);
        initializeDriverOptions();
    }

    protected abstract void initializeDriverOptions();


}
