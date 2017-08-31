package ru.mail.mailtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainMenu {

  private WebDriver driver;

  @FindBy(css = "a[href='/messages/drafts/']")
  private WebElement toDraft;

  @FindBy(css = "a[href='/messages/inbox/']")
  private WebElement toInbox;

  @FindBy(css = "a[href='/messages/sent/']")
  private WebElement toSent;

  @FindBy(css = "a[id='PH_logoutLink']")
  private WebElement logout;

  public MainMenu(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
  }

  public DraftPage moveToDraft(WebDriver driver) throws InterruptedException {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    Thread.sleep(3000);
    toDraft.click();
    return new DraftPage(driver);
  }

  public InboxPage moveToInbox() throws InterruptedException {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    toInbox.click();
    return new InboxPage(driver);
  }

  public SentPage moveToSent(WebDriver driver) throws InterruptedException {
    Thread.sleep(13000); // задержка, необходимая для появления письма в директории "Отправленные"
    PageFactory.initElements(driver, this);
    this.driver = driver;
    toSent.click();
    return new SentPage(driver);
  }

  public StartPage logout() {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    logout.click();
    return new StartPage(driver);
  }
}
