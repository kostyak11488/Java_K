package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class PaymentPage {

    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("[placeholder='08']");
    private final SelenideElement year = $("[placeholder='22']");
    private final SelenideElement owner = $$(".input__control").get(3);
    private final SelenideElement cvc = $("[placeholder='999']");
    private final SelenideElement continueButton = $$("button.button").findBy(text("Продолжить"));

    public void fillForm(String card, String mm, String yy, String name, String code) {
        cardNumber.setValue(card);
        month.setValue(mm);
        year.setValue(yy);
        owner.setValue(name);
        cvc.setValue(code);
        continueButton.click();
    }
}

