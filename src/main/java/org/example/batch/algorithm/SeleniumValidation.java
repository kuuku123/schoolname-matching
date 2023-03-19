package org.example.batch.algorithm;

import com.beust.ah.A;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeleniumValidation implements ValidationAlgorithm{

    @Override
    public HashMap<String,Integer> verify(HashMap<String, Integer> source, int delay) {

        HashMap<String,Integer> result = new HashMap<>();
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "/home/tony/workspace/java/test/pattern-matching/chromedriver";

        System.setProperty(WEB_DRIVER_ID,WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-gpu");

        ChromeDriver chromeDriver = new ChromeDriver(options);

        chromeDriver.get("https://www.schoolinfo.go.kr/Main.do");

        try {
            for (String key : source.keySet()) {
                chromeDriver.findElement(By.xpath("//*[@id=\"SEARCH_KEYWORD\"]")).clear();
                chromeDriver.findElement(By.xpath("//*[@id=\"SEARCH_KEYWORD\"]")).sendKeys(key);
                Thread.sleep(delay);
                chromeDriver.findElement(By.xpath("//*[@id=\"headerSearchForm\"]/button")).click();
                Thread.sleep(delay);


                String elementSchool = "//*[@id=\"contents\"]/div/ul/li[2]/a";
                String middleSchool = "//*[@id=\"contents\"]/div/ul/li[3]/a";
                String highSchool = "//*[@id=\"contents\"]/div/ul/li[4]/a";

                if (verifySchoolName(chromeDriver,elementSchool,result,source,key,delay)) {
                    continue;
                }
                if(verifySchoolName(chromeDriver,middleSchool,result,source,key,delay)) {
                    continue;
                }
                verifySchoolName(chromeDriver,highSchool,result,source,key,delay);

            }

            return result;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            chromeDriver.close();
        }
        return null;
    }

    private boolean verifySchoolName(ChromeDriver chromeDriver, String path, HashMap<String,Integer> result, HashMap<String,Integer> source, String key,int delay) throws InterruptedException {

        WebElement element = chromeDriver.findElement(By.xpath(path));
        String text = element.getText();
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            String totalResult = matcher.group();
            String number = totalResult.substring(1, totalResult.length() - 1);
            int value = Integer.parseInt(number);
            if (value == 0) {
                return false;
            }
            element.click();
            Thread.sleep(delay+1500);
            if (value > 5) {
                value = 5;
            }
            for (int i = 1; i <= value; i++) {
                WebElement schoolElement = chromeDriver.findElement(By.xpath(String.format("//*[@id=\"contents\"]/div/div[2]/div[%d]/div[1]/p/a", i)));
                String target = schoolElement.getText();
                String candidate1 = key + "학교";
                String candidate2 = key + "등학교";
//                System.out.println("target = " + target + " candidate1 = " + candidate1 + " candidate2 = " + candidate2);
                if (target.equals(key)) {
                    result.put(target, source.get(key));
                    return true;
                } else if (target.equals(candidate1)) {
                    result.put(candidate1, source.get(key));
                    return true;
                } else if (target.equals(candidate2)) {
                    result.put(candidate2, source.get(key));
                    return true;
                }
            }
        }
        return false;
    }
}
