package com.scrambler.classes.translators.web_drivers;

import org.openqa.selenium.remote.AbstractDriverOptions;


public abstract class WebDriverCreatorWithOptions<T extends AbstractDriverOptions<?>> extends WebDriverCreator{
    protected final T options;

    protected WebDriverCreatorWithOptions(String webDriverPath,T options) {
        super(webDriverPath);
        this.options = options;
    }

}
