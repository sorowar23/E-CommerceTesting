package uipages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utilities.UIElementUtility;
import utilities.WaitUtility;

public class ProductDetailsPage
{
    private static final Logger LOG = LoggerFactory.getLogger(ProductDetailsPage.class);
    private final WebDriver driver;
    private final UIElementUtility elementUtility;
    private final WaitUtility waitUtils;
    /*
     * WebElement of Product Title
     */
    @FindBy(xpath = "//span[@class='base']")
    private WebElement productTitle;
    /*
     * WebElement of Product Price
     */
    @FindBy(css = ".product-info-price span[id*='product-price-'] span[class='price']")
    private WebElement productPrice;

    //div[@class='swatch-option text'][contains(text(), 'XL')]
    /*
     * WebElement of Product Size - XS
     */
    @FindBy(xpath = "//div[@class='swatch-option text'][(text()= 'XS')]")
    private WebElement productSizeXL;
    /*
     * WebElement of Product Color - Orange
     */
    @FindBy(xpath = "//div[@id='option-label-color-93-item-56']")
    private WebElement productColorOrange;
    /*
	  WebElement of product Details page Add to Card button
	 */
    @FindBy(css="#product-addtocart-button")
    private WebElement addToCardButton;
    /*
     * WebElement of Add to Card Sucess Message
     */
    @FindBy(xpath = "//div[@data-bind='html: $parent.prepareMessageForHtml(message.text)']")
    private WebElement addToCardSuccessMsg;

    public ProductDetailsPage(final WebDriver driver)
    {
        this.driver = driver;
        this.elementUtility = new UIElementUtility(this.driver);
        this.waitUtils = new WaitUtility(this.driver);
        PageFactory.initElements(this.driver, this);
    }

    public String getTitle()
    {
        return driver.getTitle();
    }

    public void productAddtoCard()
    {
        waitUtils.waitForElementVisible(productSizeXL);
        productSizeXL.click();
        waitUtils.waitForElementVisible(productColorOrange);
        productColorOrange.click();
        waitUtils.waitForElementVisible(addToCardButton);
        addToCardButton.click();
    }
    /*
     * Method to validate contact us link
     */
    public Boolean successMsgCheckAfterAddtoCard()
    {
        boolean isDisplayed = addToCardSuccessMsg.isDisplayed();
        return isDisplayed;
    }
}
