package ru.mail.mailtests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.mail.mailtests.model.Letter;

import java.util.List;

public class NewLetterPage {

  private WebDriver driver;

  @FindBy(css = "textarea[tabindex='4']")
  private WebElement frecipient;

  @FindBy(css = "input[tabindex='7']")
  private WebElement fsubject;

  @FindBy(css = "body[id='tinymce']")
  private WebElement fbody;

  @FindBy(css = "div[data-name='send']")
  private WebElement bSend;

  @FindBy(css = "div[data-name='saveDraft']")
  private WebElement bSaveDraft;

  @FindBy(css = "a[href='/messages/drafts']")
  private WebElement messageSaveDraft;

  @FindBy(css ="div[class='message-sent__title']")
  private WebElement messageSent;


  public NewLetterPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
    this.driver = driver;

  }

  public void typeAndSendMail(Letter letter) throws InterruptedException {
    //Заполнение полей нового письма
    typeData(letter);
    //Отправка письма
    bSend.click();
  }

  public void typeAndSaveDraft(Letter letter) throws InterruptedException {
    //Заполнение полей нового письма
    typeData(letter);
    //Сохранение письма в черновиках
    bSaveDraft.click();
    Thread.sleep(5000);
  }

  private void typeData(Letter letter) throws InterruptedException {
    //Заполнение полей нового письма
    frecipient.sendKeys(letter.getRecipient());
    fsubject.sendKeys(letter.getSubject());
    String nameFrame = driver.findElement(By.cssSelector("iframe")).getAttribute("id");
    driver.switchTo().frame(nameFrame); // переход на окно встроенного редактора текста
    fbody.sendKeys(letter.getBody());
    driver.switchTo().defaultContent(); // возврат к основному фрейму
  }

  public boolean checkPageIsOpened() {
    if(frecipient.isDisplayed() && fsubject.isDisplayed()){
      return true;
    }
    else
      return false;
  }

  public boolean checkSaveMailToDraft() {
    if (messageSaveDraft.getText().contains("черновиках")) {
      return true;
    }
    else
      return false;
  }

  public boolean checkSendMail() {
    if (messageSent.getText().contains("отправлено")) {
      return true;
    }
    else
      return false;
  }

}




