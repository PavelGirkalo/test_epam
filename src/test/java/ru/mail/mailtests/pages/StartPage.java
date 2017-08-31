package ru.mail.mailtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class StartPage {
  private WebDriver driver;

  @FindBy(id = "mailbox__login")
  private WebElement loginName;

  @FindBy(id = "mailbox__password")
  private WebElement password;

  @FindBy(id = "mailbox__auth__button")
  private WebElement bAuthButton;


  public StartPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
  }

  public InboxPage authorizationSuccess(String log, String pass) throws InterruptedException {
    // успешная регистрация и переход на домашнюю страницу
    loginName.clear();
    loginName.sendKeys(log);
    password.clear();
    password.sendKeys(pass);
    bAuthButton.click();
    return new InboxPage(driver);
  }

  public StartPage authorizationError(String log, String pass) {
    // регистрация пользователя с неверно заполненными полями
    // вывод ошибки, остаемся на той же странице
    loginName.clear();
    loginName.sendKeys(log);
    password.clear();
    password.sendKeys(pass);
    bAuthButton.click();
    Assert.assertTrue(bAuthButton.isDisplayed(), "Логин и/или пароль введены неверно");
    return new StartPage(driver);
  }

  public boolean checkStartPageIsOpened() {
    if (loginName.isDisplayed() && password.isDisplayed() && bAuthButton.isDisplayed()) {
      return true;
    }
    else
      return false;
  }

}
