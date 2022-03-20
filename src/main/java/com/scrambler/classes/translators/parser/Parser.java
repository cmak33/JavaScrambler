package com.scrambler.classes.translators.parser;

import com.scrambler.classes.translators.web_drivers.WebDriverCreator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public record Parser(WebDriverCreator webDriverCreator,
                     int maxPageWaitTime) {

    public List<Element> findElementsByXPath(String url, String xPath) {
        Document document = receiveDocumentByUrlWhenXPathIsFound(url, xPath);
        return document.selectXpath(xPath);
    }

    public List<String> findElementsTextByXPath(String url, String xPath) {
        return findElementsByXPath(url, xPath).stream().map(Element::text).toList();
    }


    private Document receiveDocumentByUrlWhenXPathIsFound(String url, String xPath) {
        WebDriver driver = webDriverCreator.createWebDriver();
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(maxPageWaitTime));
        wait.until(dr -> dr.findElement(By.xpath(xPath)));
        Document document = Jsoup.parse(driver.getPageSource());
        driver.quit();
        return document;
    }
}
