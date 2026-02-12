package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    private static final Faker faker = new Faker(new Locale("en"));
    private static final Random random = new Random();

    // -----------------------------
    // Карты
    // -----------------------------

    public static String getApprovedCard() {
        return "1111 2222 3333 4444";
    }

    public static String getDeclinedCard() {
        return "5555 6666 7777 8888";
    }

    public static String getRandomCard() {
        return faker.finance().creditCard().replaceAll("-", " ");
    }

    public static String getShortCardNumber() {
        return faker.number().digits(15); // 15 цифр вместо 16
    }

    public static String getLongCardNumber() {
        return faker.number().digits(20); // слишком длинный номер
    }

    public static String getCardLetters() {
        return faker.letterify("???? ???? ???? ????").toUpperCase();
    }

    public static String getCardSymbols() {
        return "@@@@ #### **** !!!!";
    }

    public static String getZerosCard() {
        return "0000 0000 0000 0000";
    }

    // -----------------------------
    // Месяц
    // -----------------------------

    public static String getValidMonth() {
        return LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonth00() {
        return "00";
    }

    public static String getInvalidMonth13() {
        return "13";
    }

    public static String getOneDigitMonth() {
        return String.valueOf(random.nextInt(9) + 1); // 1–9
    }

    public static String getMonthLetters() {
        return faker.letterify("??").toUpperCase();
    }

    public static String getMonthSymbols() {
        return "@#";
    }

    // -----------------------------
    // Год
    // -----------------------------

    public static String getValidYear() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getExpiredYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getTooFarYear() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearLetters() {
        return faker.letterify("??").toUpperCase();
    }

    public static String getYearSymbols() {
        return "@@";
    }

    // -----------------------------
    // Владелец
    // -----------------------------

    public static String getValidOwner() {
        return faker.name().fullName().toUpperCase();
    }

    public static String getInvalidOwnerCyrillic() {
        return faker.name().fullName().replaceAll("[A-Za-z]", "И"); // генерируем кириллицу
    }

    public static String getInvalidOwnerDigits() {
        return faker.number().digits(5);
    }

    public static String getInvalidOwnerSymbols() {
        return "@@@###";
    }

    public static String getOneLetterOwner() {
        return faker.letterify("?").toUpperCase();
    }

    // -----------------------------
    // CVC
    // -----------------------------

    public static String getValidCVC() {
        return faker.number().digits(3);
    }

    public static String getInvalidCVC1Digit() {
        return faker.number().digits(1);
    }

    public static String getInvalidCVC2Digits() {
        return faker.number().digits(2);
    }

    public static String getInvalidCVC000() {
        return "000";
    }
}


