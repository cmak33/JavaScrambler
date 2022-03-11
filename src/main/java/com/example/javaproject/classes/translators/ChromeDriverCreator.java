package com.example.javaproject.classes.translators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverCreator extends WebDriverCreatorWithOptions<ChromeOptions> {


    public ChromeDriverCreator(String webDriverPath) {
        super(webDriverPath);
    }

    @Override
    protected void initializeDriverOptions() {
        options = new ChromeOptions();
        options.setHeadless(true);
    }

    @Override
    protected void setWebDriverPath(String webDriverPath) {
        System.setProperty("webdriver.chrome.driver",webDriverPath);
    }

    @Override
    public WebDriver createWebDriver() {
        return new ChromeDriver(options);
    }


}
