package com.scrambler.classes.translators.web_drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverCreatorWithOptions extends WebDriverCreatorWithOptions<ChromeOptions> {


    public ChromeDriverCreatorWithOptions(String webDriverPath, ChromeOptions options) {
        super(webDriverPath,options);
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
