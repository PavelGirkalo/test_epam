package ru.mail.mailtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class InboxPage {
  private WebDriver driver;

  @FindBy(css = "a[data-name='compose']")
  private WebElement bNewMail;

  @FindBys(@FindBy(css = "i[id='PH_user-email']"))
  private WebElement userMail;

  public InboxPage(WebDriver driver) throws InterruptedException {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    Thread.sleep(5000); // задержка, необходимая для инициализации страницы
  }

  public String getUserMail() {
    return userMail.getText();
  }

  public NewLetterPage initNewMail() throws InterruptedException {
    bNewMail.click();
    return new NewLetterPage(driver);
  }
}
