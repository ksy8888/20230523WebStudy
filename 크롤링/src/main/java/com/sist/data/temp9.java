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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/*import com.sist.dao.mangoVO;
import com.sist.dao.mangoDAO;*/

public class temp9 {
    static StringTokenizer st;
    private WebDriver driver;
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\chromedriver_win64\\chromedriver.exe";
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
		/* List<String> fulllinks1 = new ArrayList<>(); */
 //       int number=0;

        
        ///html/body/main/article/div[2]/div/div/section/div[4]/p/a[1]
        ///html/body/main/article/div[2]/div/div/section/div[4]/p/a[2]
        // 페이지 번호
        try {
            // 메인 웹 페이지 로드
//            for(int i=1; i<33;i++) {
//            	 webElement = driver.findElement(By.xpath("/html/body/footer/div/div[2]/div/a[i]"));
//                webElement.click();
//                Thread.sleep(500);
//            }
            for(int i =1; i < 34; i++){
                WebElement title = driver.findElement(By.xpath("/html/body/footer/div/div[2]/div/a["+i+"]"));
                WebElement link = driver.findElement(By.xpath("/html/body/footer/div/div[2]/div/a["+i+"]"));
                fulllink = link.getAttribute("href");
                System.out.println("no: "+i);
                System.out.println("제목: "+title.getText());
                System.out.println("카테고리: "+fulllink);
//                number = categoriNum(title.getText());
                System.out.println("=====================================================");
                
                fulllinks.add(fulllink);
            }

//            // Jsoup을 사용하여 페이지 소스 가져오기
//            String pageSource = driver.getPageSource();
//
//            // Jsoup으로 페이지 소스를 파싱하여 데이터 추출
//            Document document = Jsoup.parse(pageSource);
//            Elements elements = document.select("li.top_list_item a"); // 제목과 링크를 포함하는 요소 선택
            
            //세부 웹페이지 로드
            try {
//                for (String link : fulllinks) {
//                    driver.get(link);
////                    Thread.sleep(500);
//                    try {
//                    	for(int i=1;i<11;i++) {
//                            try {
//                                WebElement page = driver.findElement(By.xpath("/html/body/main/article/div[2]/div/div/section/div[4]/p/a["+i+"]"));
//                                page.click();
//                                Thread.sleep(500); 
//                            } catch (Exception e) {	
//                            	e.printStackTrace();
//                            }
//                            List<WebElement> elements = driver.findElements(By.cssSelector("h2.title"));
//                            for (WebElement element : elements) {                  
//                            	System.out.println(element.getText());
//                            }
//                            List<WebElement> link1 = driver.findElements(By.cssSelector("a.only-desktop_not"));
//                            for (WebElement element : link1) {
//                                System.out.println(element.getAttribute("href"));
//                                fulllink1 = element.getAttribute("href");
//                                fulllinks1.add(fulllink1);
//                            }
//                        }
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//                }
                
//                //상세 웹페이지 로드
//                try {
//                	try {
//                    	mangoDAO dao = mangoDAO.newInstance();
//                    	for (String link : fulllinks1) {
////                            driver.get(link);
////                            List<WebElement> elements = driver.findElements(By.cssSelector("h1.restaurant_name"));
//                            Document doc = Jsoup.connect(link).get();
//                            mangoVO vo=new mangoVO();
//                            vo.setFdno(vo.getFdno());
//                            Element name = doc.selectFirst("span.title h1.restaurant_name");
//        					Element score=doc.selectFirst("strong.rate-point span");
//        					double sc=0.0;
//        					if (score == null) {
//        						sc=0.0;
//        					}else {
//    							sc=Double.parseDouble(score.text());
//    						}
//        					Elements poster = doc.select("figure.restaurant-photos-item img.center-croping");
//        					Elements hit = doc.select("span.cnt.hit");
//        					int hit1=(Integer.parseInt(hit.text().replace(",", "")));
//        					String image="";
//        					if (poster.size()==1) {
//        						image = image.replace("&", "#");
//        					}else {
//        						for (int j = 0; j<poster.size(); j++) {
//            						
//            						image+=poster.get(j).attr("src")+"^";
//            					}
//            					image = image.substring(0,image.lastIndexOf("^"));
//            					image = image.replace("&", "#");
//							}
//        					Elements rday=doc.select("p.update_date");
//        					System.out.println("카테고리 번호 : "+vo.getFdno());
//        					System.out.println("업체명 : "+ name.text());
//        					System.out.println("평점 : "+ sc);
//        					System.out.println("이미지 : "+ image);
//        					System.out.println("조회수 : "+ hit1);
//        					vo.setTitle(name.text());
//        					vo.setScore(sc);
//        					vo.setPoster(image);
//        					vo.setHit(hit1);
//        					vo.setRday(rday.text());
//                            
//        					String addr="no", phone="no",type="no", price="no", parking="no",time="no",menu="no";
//        					Elements etc = doc.select("table.info tr th");
//        					for(int a=0;a<etc.size(); a++) {
//        						String ss = etc.get(a).text(); //주소 전화번호를 가지고 옴
//        						if (ss.equals("주소")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							addr=e.text();
//        						}
//        						else if (ss.equals("전화번호")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							phone=e.text();
//        						}
//        						else if (ss.equals("음식 종류")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							type=e.text();
//        						}
//        						else if (ss.equals("가격대")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							price=e.text();
//        						}
//        						else if (ss.equals("주차")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							parking=e.text();
//        						}
//        						else if (ss.equals("영업시간")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							time=e.text();
//        						}
//        						else if (ss.equals("메뉴")) {
//        							Element e = doc.select("table.info tr td").get(a);
//        							menu=e.text();
//        						}
//        					}
//        					System.out.println("주소 : "+addr);
//        					System.out.println("전화 : "+phone);
//        					System.out.println("음식종류 : "+type);
//        					System.out.println("가격대 : "+price);
//        					System.out.println("주차 : "+parking);
//        					System.out.println("영업시간 : "+time);
//        					System.out.println("메뉴 : "+menu);
//        					System.out.println("==========================================");
//        					vo.setAddress(addr);
//        					vo.setTel(phone);
//        					vo.setType(type);
//        					vo.setPrice(price);
//        					vo.setParking(parking);
//        					vo.setTime(time);
//        					vo.setMenu(menu);
//        					dao.foodDataInsert(vo);
//        					
//                        }
//    				} catch (Exception ex) {
//    					ex.printStackTrace();
//    				} finally {
//    					driver.quit();
//    				}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
                
                
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
