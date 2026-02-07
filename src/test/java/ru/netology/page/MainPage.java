package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement buyButton = $("button.button_size_m");
    private final SelenideElement buyCreditButton = $$("button.button_size_m").get(1);

    public PaymentPage buyWithCard() {
        buyButton.click();
        return new PaymentPage();
    }
}