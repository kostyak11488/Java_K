package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.util.DbHelper;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

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
                "JOHN DOE",
                "123"
        );

        $(".notification_status_ok .notification__content")
                .shouldHave(Condition.text("Операция одобрена Банком."));

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
                "JOHN DOE",
                "123"
        );

        $(".notification_status_error .notification__content")
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."));

        assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    // -----------------------------
    // 2. Негативные сценарии — Номер карты
    // -----------------------------

    @Test
    void shouldShowErrorIfCardEmpty() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCardShort() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "1111 2222 3333 444",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCardLong() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "1111 2222 3333 4444 55",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCardLetters() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "ABCD EFGH IJKL MNOP",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCardSymbols() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "1111 **** #### 4444",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCardNotFromTestSet() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "1234 5678 9012 3456",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCardZeros() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                "0000 0000 0000 0000",
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
    }

    // -----------------------------
    // 3. Месяц
    // -----------------------------

    @Test
    void shouldShowErrorIfMonthEmpty() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "",
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfMonth00() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "00",
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfMonthMoreThan12() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "13",
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfMonthOneDigit() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "5",
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfMonthLetters() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "AB",
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfMonthSymbols() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                "@#",
                DataHelper.getValidYear(),
                "JOHN DOE",
                "123"
        );

        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
    }

    // -----------------------------
    // 4. Год
    // -----------------------------

    @Test
    void shouldShowErrorIfYearEmpty() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                "",
                "JOHN DOE",
                "123"
        );

        payment.getYearError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfYearExpired() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getExpiredYear(),
                "JOHN DOE",
                "123"
        );

        payment.getYearError().shouldHave(Condition.text("Истёк срок действия карты"));
    }

    @Test
    void shouldShowErrorIfYearTooFar() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                "35",
                "JOHN DOE",
                "123"
        );

        payment.getYearError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfYearLetters() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                "AB",
                "JOHN DOE",
                "123"
        );

        payment.getYearError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfYearSymbols() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                "@@",
                "JOHN DOE",
                "123"
        );

        payment.getYearError().shouldHave(Condition.text("Неверный формат"));
    }

    // -----------------------------
    // 5. Владелец
    // -----------------------------

    @Test
    void shouldShowErrorIfOwnerEmpty() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "",
                "123"
        );

        payment.getOwnerError().shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowErrorIfOwnerCyrillic() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getInvalidOwnerCyrillic(),
                "123"
        );

        payment.getOwnerError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfOwnerDigits() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getInvalidOwnerDigits(),
                "123"
        );

        payment.getOwnerError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfOwnerSymbols() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getInvalidOwnerSymbols(),
                "123"
        );

        payment.getOwnerError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfOwnerOneLetter() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "A",
                "123"
        );

        payment.getOwnerError().shouldHave(Condition.text("Неверный формат"));
    }

    // -----------------------------
    // 6. CVC
    // -----------------------------

    @Test
    void shouldShowErrorIfCvcEmpty() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                ""
        );

        payment.getCvcError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCvcOneDigit() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                DataHelper.getInvalidCVC1Digit()
        );

        payment.getCvcError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCvcTwoDigits() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                DataHelper.getInvalidCVC2Digits()
        );

        payment.getCvcError().shouldHave(Condition.text("Неверный формат"));
    }

    @Test
    void shouldShowErrorIfCvcZeros() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                "JOHN DOE",
                DataHelper.getInvalidCVC000()
        );

        payment.getCvcError().shouldHave(Condition.text("Неверный формат"));
    }

    // -----------------------------
    // 7. Все поля пустые
    // -----------------------------

    @Test
    void shouldShowErrorsIfAllFieldsEmpty() {
        var main = new MainPage();
        var payment = main.buyWithCard();

        payment.fillForm("", "", "", "", "");

        payment.getCardNumberError().shouldHave(Condition.text("Неверный формат"));
        payment.getMonthError().shouldHave(Condition.text("Неверный формат"));
        payment.getYearError().shouldHave(Condition.text("Неверный формат"));
        payment.getOwnerError().shouldHave(Condition.text("Поле обязательно для заполнения"));
        payment.getCvcError().shouldHave(Condition.text("Неверный формат"));
    }
}
