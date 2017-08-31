package ru.mail.mailtests.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.mail.mailtests.model.Letter;
import ru.mail.mailtests.pages.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestScenaries {

  private WebDriver driver;
  private Properties properties;


  @BeforeSuite
  public void setUp() throws Exception {
    properties = new Properties();
    properties.load(new FileReader(new File("src/test/resources/local.properties")));
    String browser = properties.getProperty("browser");
    if (browser.equals("firefox")) {
      driver = new FirefoxDriver();
    } else if (browser.equals("chrome")) {
      driver = new ChromeDriver();
    }
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

  }

  @DataProvider(name = "testData")
  public Iterator<Object[]> testData() {
    List<Object[]> list = new ArrayList<Object[]>();
    Letter letter = new Letter();
    letter.setRecipient(properties.getProperty("login") + "@mail.ru");
    letter.setSubject("test_subject");
    letter.setBody("test_body");
    list.add(new Object[] {letter});
    letter = new Letter();
    letter.setRecipient(properties.getProperty("login") + "@mail.ru");
    letter.setSubject("test2_subject");
    letter.setBody("test2_body");
    list.add(new Object[] {letter});

    return list.iterator();
  }

  //Тест создания и сохранения письма в черновике
  @Test(dataProvider = "testData", enabled = true)
  public void DraftCreationTest(Letter letter) throws IOException, InterruptedException {

    //Шаг 1. Авторизация в почтовом ящике
    driver.get(properties.getProperty("baseURL"));
    StartPage startPage = new StartPage(driver);
    Assert.assertTrue(startPage.checkStartPageIsOpened(), "Открыта некорректная стартовая страница"); // проверка, что открыта стартовая страница
    InboxPage inboxPage = startPage.authorizationSuccess(properties.getProperty("login"), properties.getProperty("password"));
    Assert.assertTrue(inboxPage.getUserMail().contains(properties.getProperty("login")), "Вход не выполнен (неверно введены логин/пароль)"); //проверка, что вход успешно выполнен

    //Шаг 2. Инициирование создания нового письма
    NewLetterPage newLetterPage = inboxPage.initNewMail();
    Assert.assertTrue(newLetterPage.checkPageIsOpened(), "Переход на окно создания нового письма не выполнен"); // проверка, что открыто окно для создания нового письма

    //Шаг 3. Заполнение полей письма тестовыми данными и сохранение черновика
    newLetterPage.typeAndSaveDraft(letter);
    Assert.assertTrue(newLetterPage.checkSaveMailToDraft(), "Сохранение письма не выполнено"); //Проверка, что письмо сохранено в черновиках

    //Шаг 4. Проверка наличия сохраненного письма в папке "Черновики" и очистка тестовых данных
    MainMenu menu = new MainMenu(driver); //инициализация бокового меню с переходами
    DraftPage draftPage = menu.moveToDraft(driver);
    Assert.assertTrue(draftPage.draftIsExist(letter), "Письмо отсутствует в черновиках"); //проверка, что письмо присутствует в черновиках

    //Шаг 5. Выход из почтового ящика
    StartPage startPage2 = menu.logout();
    Assert.assertTrue(startPage2.checkStartPageIsOpened(), "Выход из почтового ящика не выполнен"); // проверка, что выход из ящика успешно выполнен

    System.out.println("Тест создания и сохранения письма в черновике с тестовыми данными " + letter.toString() + " - Ok");

  }

  // Тест создания и отправки письма
  @Test(dataProvider = "testData", enabled = true)
  public void SendMailTest(Letter letter) throws IOException, InterruptedException {

    //Шаг 1. Авторизация в почтовом ящике
    driver.get(properties.getProperty("baseURL"));
    StartPage startPage = new StartPage(driver);
    Assert.assertTrue(startPage.checkStartPageIsOpened(), "Открыта некорректная стартовая страница"); // проверка, что открыта стартовая страница
    InboxPage inboxPage = startPage.authorizationSuccess(properties.getProperty("login"), properties.getProperty("password"));
    Assert.assertTrue(inboxPage.getUserMail().contains(properties.getProperty("login")), "Вход не выполнен (неверно введены логин/пароль)"); //проверка, что вход успешно выполнен

    //Шаг 2. Инициирование создания нового письма
    NewLetterPage newLetterPage = inboxPage.initNewMail();
    Assert.assertTrue(newLetterPage.checkPageIsOpened(), "Переход на окно создания нового письма не выполнен"); // проверка, что открыто окно для создания нового письма

    //Шаг 3. Заполнение полей письма тестовыми данными и его отправка
    newLetterPage.typeAndSendMail(letter);
    Assert.assertTrue(newLetterPage.checkSendMail(), "Сохранение письма не выполнено"); //Проверка, что письмо сохранено в черновиках

    //Шаг 4. Проверка наличия отправленного письма в папке "Отправленные" и очистка тестовых данных
    MainMenu menu = new MainMenu(driver); //инициализация бокового меню с переходами
    SentPage sentPage = menu.moveToSent(driver);
    Assert.assertTrue(sentPage.checkSentMailIsExist(letter), "Письмо отсутствует в Отправленных"); // проверка, что письмо присутствует в списке отправленных сообщений

    //Шаг 5. Выход из почтового ящика
    //menu = new MainMenu(driver); //инициализация бокового меню с переходами
    StartPage startPage2 = menu.logout();
    Assert.assertTrue(startPage2.checkStartPageIsOpened(), "Выход из почтового ящика не выполнен"); // проверка, что выход из ящика успешно выполнен

    System.out.println("Тест создания и отправки письма с тестовыми данными " + letter.toString() + " - Ok");

  }

  @AfterSuite
  public void tearDown() {
    driver.quit();
  }

}
