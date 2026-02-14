package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.netology.data.DataHelper;
import ru.netology.helpers.AllureListener;
import ru.netology.page.MainPage;
import ru.netology.util.DbHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("AllureSelenide",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
                        .includeSelenideSteps(true)
        );
    }


    @BeforeEach
    void setup() {
        DbHelper.clearTables();
        open("http://localhost:8080");
    }

    // -----------------------------
    // 1. Позитивные сценарии
    // -----------------------------

    @Test
    void shouldApprovePayment() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowSuccessNotification();
        assertEquals("APPROVED", DbHelper.getPaymentStatus());
    }

    @Test
    void shouldDeclinePayment() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getDeclinedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowErrorNotification();
        assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    // -----------------------------
    // 2. Негативные сценарии — Номер карты
    // -----------------------------

    @Test
    void shouldShowErrorIfCardEmpty() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                "",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCardShort() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getShortCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCardLong() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getLongCardNumber(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCardLetters() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getCardLetters(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCardSymbols() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getCardSymbols(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCardNotFromTestSet() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getRandomCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCardZeros() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getZerosCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowCardNumberError("Неверный формат");
    }

    // -----------------------------
    // 3. Месяц
    // -----------------------------

    @Test
    void shouldShowErrorIfMonthEmpty() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "",
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowMonthError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfMonth00() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getInvalidMonth00(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowMonthError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfMonthMoreThan12() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getInvalidMonth13(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowMonthError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfMonthOneDigit() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getOneDigitMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowMonthError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfMonthLetters() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getMonthLetters(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowMonthError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfMonthSymbols() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getMonthSymbols(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowMonthError("Неверный формат");
    }

    // -----------------------------
    // 4. Год
    // -----------------------------

    @Test
    void shouldShowErrorIfYearEmpty() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                "",
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowYearError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfYearExpired() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getExpiredYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowYearError("Истёк срок действия карты");
    }

    @Test
    void shouldShowErrorIfYearTooFar() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getTooFarYear(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowYearError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfYearLetters() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getYearLetters(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowYearError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfYearSymbols() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getYearSymbols(),
                DataHelper.getValidOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowYearError("Неверный формат");
    }

    // -----------------------------
    // 5. Владелец
    // -----------------------------

    @Test
    void shouldShowErrorIfOwnerEmpty() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "",
                DataHelper.getValidCVC()
        );

        payment.shouldShowOwnerError("Поле обязательно для заполнения");
    }

    @Test
    void shouldShowErrorIfOwnerCyrillic() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getInvalidOwnerCyrillic(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowOwnerError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfOwnerDigits() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getInvalidOwnerDigits(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowOwnerError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfOwnerSymbols() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getInvalidOwnerSymbols(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowOwnerError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfOwnerOneLetter() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOneLetterOwner(),
                DataHelper.getValidCVC()
        );

        payment.shouldShowOwnerError("Неверный формат");
    }

    // -----------------------------
    // 6. CVC
    // -----------------------------

    @Test
    void shouldShowErrorIfCvcEmpty() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                ""
        );

        payment.shouldShowCvcError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCvcOneDigit() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getInvalidCVC1Digit()
        );

        payment.shouldShowCvcError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCvcTwoDigits() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getInvalidCVC2Digits()
        );

        payment.shouldShowCvcError("Неверный формат");
    }

    @Test
    void shouldShowErrorIfCvcZeros() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getValidOwner(),
                DataHelper.getInvalidCVC000()
        );

        payment.shouldShowCvcError("Неверный формат");
    }

    // -----------------------------
    // 7. Все поля пустые
    // -----------------------------

    @Test
    void shouldShowErrorsIfAllFieldsEmpty() {
        var payment = new MainPage().buyWithCard();

        payment.fillForm("", "", "", "", "");

        payment.shouldShowCardNumberError("Неверный формат");
        payment.shouldShowMonthError("Неверный формат");
        payment.shouldShowYearError("Неверный формат");
        payment.shouldShowOwnerError("Поле обязательно для заполнения");
        payment.shouldShowCvcError("Неверный формат");
    }
}

