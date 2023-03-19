
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.service.DriverService;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    @org.junit.jupiter.api.Test
    void test1(){

        String text = "\"message\"\n" +
                "\"수원, 창현고 짜장면 먹고싶어요\n" +
                "사진은 제가 먹다남은 짬뽕이네요\n" +
                "재학중은 아니지만 모교학생분들의 배를 불러드리게 하고자 이 이벤트에 참여하는 바입니다\n" +
                "(팔로워님들 좋아요 한번씩 부탁합니다..)\"\n" +
                "\"?경북 경산, 하양여자중학교?";

        // Define the regular expression pattern
        String regex1 = "\\S+(초등학교|중학교|고등학교)";
        String regex2 = "\\S+(초|중|고)(?!등학교|학교)";

        // Create a Pattern object with the regular expression
        Pattern pattern1 = Pattern.compile(regex1);
        Pattern pattern2 = Pattern.compile(regex2);

        // Create a Matcher object to search for matches
        Matcher matcher1 = pattern1.matcher(text);
        Matcher matcher2 = pattern2.matcher(text);

        // Loop through the matches and print them
        while (matcher1.find()) {
            System.out.println("Match found: " + matcher1.group());
        }
        while (matcher2.find()) {
            System.out.println("Match found: " + matcher2.group());
        }
    }

    @org.junit.jupiter.api.Test
    void test2() throws IOException, InterruptedException {
        String WEB_DRIVER_ID = "webdriver.chrome.driver";
        String WEB_DRIVER_PATH = "/home/tony/workspace/java/test/pattern-matching/chromedriver";

        System.setProperty(WEB_DRIVER_ID,WEB_DRIVER_PATH);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.addArguments("--remote-allow-origins=*");

        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);

        chromeDriver.get("https://www.schoolinfo.go.kr/Main.do");

        String[] test = {"분당고", "중앙고","하고"};
        for(int i = 0; i<test.length; i++) {
            chromeDriver.findElement(By.xpath("//*[@id=\"SEARCH_KEYWORD\"]")).clear();
            chromeDriver.findElement(By.xpath("//*[@id=\"SEARCH_KEYWORD\"]")).sendKeys(test[i]);
            Thread.sleep(500);
            chromeDriver.findElement(By.xpath("//*[@id=\"headerSearchForm\"]/button")).click();
            Thread.sleep(500);



            WebElement elementSchool = chromeDriver.findElement(By.xpath("//*[@id=\"contents\"]/div/ul/li[2]/a"));
            WebElement middleSchool = chromeDriver.findElement(By.xpath("//*[@id=\"contents\"]/div/ul/li[3]/a"));
            WebElement highSchool = chromeDriver.findElement(By.xpath("//*[@id=\"contents\"]/div/ul/li[4]/a"));

            verifySchoolName(elementSchool,chromeDriver,i);
            verifySchoolName(middleSchool,chromeDriver,i);
            verifySchoolName(highSchool,chromeDriver,i);
        }

    }

    private static void verifySchoolName(WebElement elementSchool,ChromeDriver chromeDriver,int n) {
        String[] test = {"분당고", "중앙고","하고"};
        String text = elementSchool.getText();
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String totalResult = matcher.group();
            String number = totalResult.substring(1, totalResult.length() - 1);
            int value = Integer.parseInt(number);
            if (value > 0) {
                elementSchool.click();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (value >5) {
                    value = 5;
                }
                for(int i = 1; i<=value; i++) {
                    WebElement schoolElement = chromeDriver.findElement(By.xpath(String.format("//*[@id=\"contents\"]/div/div[2]/div[%d]/div[1]/p/a",i)));
                    String target = schoolElement.getText();
                    String candidate1 = test[n] +"학교";
                    String candidate2 = test[n] +"등학교";
                    System.out.println("candidate1 = " + candidate1);
                    System.out.println("candidate2 = " + candidate2);
                    if (target.equals(candidate1)) {
                        System.out.println(candidate1);
                        break;
                    }
                    else if (target.equals(candidate2)) {
                        System.out.println(candidate2);
                        break;
                    }
                }
            }
        }
    }

    @org.junit.jupiter.api.Test
    void tes3() throws Exception {

        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream("testResult.txt"), "UTF-8");
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write("Hello" + " " + "world2");
        bw.newLine();
        bw.close();
        }
    }
