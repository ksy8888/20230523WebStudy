	package com.sist.data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*import com.sist.dao.mangoVO;
import com.sist.dao.mangoDAO;*/

public class temp1 {
    static StringTokenizer st;
    private WebDriver driver;
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\chromedriver-win32\\chromedriver.exe";
   // mangoDAO dao = mangoDAO.newInstance();
    public static void main(String[] args) throws Exception{
        // Selenium 웹 드라이버 설정
        String fulllink = null;
		/* String fulllink1 = null; */
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless"); // 브라우저 창 숨기기
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://o2o.ohou.se/remodeling/home/discovery");
//        WebElement webElement = null;
        
        List<String> fulllinks = new ArrayList<>();
        
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        //스크롤 횟수만큼 만큼
        int scrollCount=20; 
        
        // 페이지 번호
        try {
            // 메인 웹 페이지 로드
            for(int i=1; i<scrollCount;i++) {
            	jsExecutor.executeScript("window.scrollTo(0,document.body.scrollHeight);");
                Thread.sleep(500);
            }
            for(int i =1; i < 100; i++){
            													//*[@id="__next"]/div[1]/main/div[2]/div/div[8]/div[2]/div/div/div/div[5]/div/div[3]/div/article/div/div[2]/header/div[1]
            													//*[@id="__next"]/div[1]/main/div[2]/div/div[8]/div[2]/div/div/div/div[1]/div/div[2]/div/article/div/div[2]/header/div[1]
            													//*[@id="__next"]/div[1]/main/div[2]/div/div[8]/div[2]/div/div/div/div[5]/div/div[3]/div/article/div/div[2]/header/div[1]
            	WebElement title = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/main/div[2]/div/div[8]/div[2]/div/div/div/div[1]/div/div["+i+"]/div/article/div/div[2]/header/div[1]"));
//                WebElement link = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[1]/main/div[2]/div/div[8]/div[2]/div/div/div/div["+i+"]/div/div[1]/div/article/div/div[1]/div[1]/div[1]/ul/li/a/div/img"));
//                fulllink = link.getAttribute("href");
                System.out.println("no: "+i);
                System.out.println("제목: "+title.getText());
//                System.out.println("사진: "+link.getAttribute("src").indexOf(".jpg"));
//                System.out.println("카테고리: "+fulllink);
//                number = categoriNum(title.getText());
                System.out.println("=====================================================");
                
                fulllinks.add(fulllink);
            }
            
            //세부 웹페이지 로드
            try {

                
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                driver.quit();
            }
//            System.out.println("메인링크 : "+fulllinks);
//            System.out.println("세부링크 : "+fulllinks1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // WebDriver 종료
            driver.quit();
        }
    }

}
