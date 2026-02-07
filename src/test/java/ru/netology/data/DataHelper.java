package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));

    public static String getApprovedCard() {
        return "1111 2222 3333 4444";
    }

    public static String getDeclinedCard() {
        return "5555 6666 7777 8888";
    }

    public static String getRandomCard() {
        return faker.finance().creditCard().replaceAll("-", " ");
    }

    public static String getValidMonth() {
        return LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonth00() {
        return "00";
    }

    public static String getInvalidMonth13() {
        return "13";
    }

    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getExpiredYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getOwner() {
        return faker.name().fullName().toUpperCase();
    }

    public static String getInvalidOwnerCyrillic() {
        return "ИВАН ИВАНОВ";
    }

    public static String getInvalidOwnerDigits() {
        return "12345";
    }

    public static String getInvalidOwnerSymbols() {
        return "@@@###";
    }

    public static String getCVC() {
        return faker.number().digits(3);
    }

    public static String getInvalidCVC1Digit() {
        return "1";
    }

    public static String getInvalidCVC2Digits() {
        return "12";
    }

    public static String getInvalidCVC000() {
        return "000";
    }
}

