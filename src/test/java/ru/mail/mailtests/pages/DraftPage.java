package ru.mail.mailtests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import ru.mail.mailtests.model.Letter;

import java.util.List;

public class DraftPage {
  private WebDriver driver;

  @FindBys(@FindBy(css = "a[href*=drafts] div[class=b-datalist__item__addr]"))
  private List<WebElement> recepientOfLetters;

  @FindBys(@FindBy(css = "a[href*=drafts] div[class=b-datalist__item__subj]"))
  private List<WebElement> subjectOfLetters;

  @FindBys(@FindBy(css = "a[href*=drafts] span[class=b-datalist__item__subj__snippet]"))
  private List<WebElement> bodyOfLetters;

  @FindBys(@FindBy(css = "a[href*=drafts] div[class=b-checkbox__box]"))
  private List<WebElement> selectMail;

  @FindBys(@FindBy(css = "div[data-name=remove]"))
  private List<WebElement> bRemove;

  public DraftPage(WebDriver driver) throws InterruptedException {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    Thread.sleep(5000); // задержка, необходимая для инициализации страницы
  }

  public boolean draftIsExist(Letter let) {
    boolean res = false;
    for (int i = 0; i < recepientOfLetters.size(); i++) {
      if (recepientOfLetters.get(i).getText().equals(let.getRecipient()) &&
              subjectOfLetters.get(i).getText().contains(let.getSubject()) &&
              bodyOfLetters.get(i).getText().contains(let.getBody())) {
        res = true;
        selectMail.get(i).click();
        bRemove.get(1).click();
        break;
       }
    }
    return res;

  }

}
