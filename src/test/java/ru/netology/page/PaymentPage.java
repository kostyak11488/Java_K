package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.text;

public class PaymentPage {

    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("[placeholder='08']");
    private final SelenideElement year = $("[placeholder='22']");
    private final SelenideElement owner = $$(".input__control").get(3);
    private final SelenideElement cvc = $("[placeholder='999']");
    private final SelenideElement continueButton = $$("button.button").findBy(text("Продолжить"));

    // --- Ошибки под полями ---
    private SelenideElement cardNumberError() {
        return cardNumber.closest(".input").$(".input__sub");
    }

    private SelenideElement monthError() {
        return month.closest(".input").$(".input__sub");
    }

    private SelenideElement yearError() {
        return year.closest(".input").$(".input__sub");
    }

    private SelenideElement ownerError() {
        return owner.closest(".input").$(".input__sub");
    }

    private SelenideElement cvcError() {
        return cvc.closest(".input").$(".input__sub");
    }

    // --- Заполнение формы ---
    public void fillForm(String card, String mm, String yy, String name, String code) {
        cardNumber.setValue(card);
        month.setValue(mm);
        year.setValue(yy);
        owner.setValue(name);
        cvc.setValue(code);
        continueButton.click();
    }

    // --- Геттеры для тестов ---
    public SelenideElement getCardNumberError() {
        return cardNumberError();
    }

    public SelenideElement getMonthError() {
        return monthError();
    }

    public SelenideElement getYearError() {
        return yearError();
    }

    public SelenideElement getOwnerError() {
        return ownerError();
    }

    public SelenideElement getCvcError() {
        return cvcError();
    }
}



