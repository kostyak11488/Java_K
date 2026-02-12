package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {

    // --- Поля формы ---
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("[placeholder='08']");
    private final SelenideElement yearField = $("[placeholder='22']");
    private final SelenideElement ownerField = $$(".input__control").get(3);
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement continueButton = $$(".button__content").findBy(text("Продолжить"));

    // --- Ошибки под полями ---
    private final SelenideElement cardNumberError = cardNumberField.closest(".input").$(".input__sub");
    private final SelenideElement monthError = monthField.closest(".input").$(".input__sub");
    private final SelenideElement yearError = yearField.closest(".input").$(".input__sub");
    private final SelenideElement ownerError = ownerField.closest(".input").$(".input__sub");
    private final SelenideElement cvcError = cvcField.closest(".input").$(".input__sub");

    // --- Уведомления ---
    private final SelenideElement successNotification = $(".notification_status_ok .notification__content");
    private final SelenideElement errorNotification = $(".notification_status_error .notification__content");

    // --- Действия ---
    public void fillForm(String card, String month, String year, String owner, String cvc) {
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        ownerField.setValue(owner);
        cvcField.setValue(cvc);
        continueButton.click();
    }

    // --- Проверки ошибок ---
    public void shouldShowCardNumberError(String expectedText) {
        cardNumberError.shouldBe(visible).shouldHave(text(expectedText));
    }

    public void shouldShowMonthError(String expectedText) {
        monthError.shouldBe(visible).shouldHave(text(expectedText));
    }

    public void shouldShowYearError(String expectedText) {
        yearError.shouldBe(visible).shouldHave(text(expectedText));
    }

    public void shouldShowOwnerError(String expectedText) {
        ownerError.shouldBe(visible).shouldHave(text(expectedText));
    }

    public void shouldShowCvcError(String expectedText) {
        cvcError.shouldBe(visible).shouldHave(text(expectedText));
    }

    // --- Проверки уведомлений ---
    public void shouldShowSuccessNotification() {
        successNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Операция одобрена Банком."));
    }

    public void shouldShowErrorNotification() {
        errorNotification
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Ошибка! Банк отказал в проведении операции."));
    }
}




