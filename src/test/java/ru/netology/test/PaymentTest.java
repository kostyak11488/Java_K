package ru.netology.test;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.util.DbHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTest {

    @Test
    void shouldApprovePayment() {
        var mainPage = new MainPage();
        var paymentPage = mainPage.buyWithCard();

        paymentPage.fillForm(
                DataHelper.getApprovedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOwner(),
                DataHelper.getCVC()
        );

        var status = DbHelper.getPaymentStatus();
        assertEquals("APPROVED", status);
    }

    @Test
    void shouldDeclinePayment() {
        var mainPage = new MainPage();
        var paymentPage = mainPage.buyWithCard();

        paymentPage.fillForm(
                DataHelper.getDeclinedCard(),
                DataHelper.getValidMonth(),
                DataHelper.getValidYear(),
                DataHelper.getOwner(),
                DataHelper.getCVC()
        );

        var status = DbHelper.getPaymentStatus();
        assertEquals("DECLINED", status);
    }
}
