package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DataChangeTest {

    String setMeetingDate(int daysFromToday) {
        $("[placeholder=\"Дата встречи\"]").doubleClick().sendKeys(Keys.DELETE);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar dataToday = Calendar.getInstance();
        dataToday.add(Calendar.DAY_OF_YEAR, daysFromToday);
        return dateFormat.format(dataToday.getTime());
    }


    @BeforeEach
    void openLocalhost() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendValidData() {
        $("[data-test-id=\"city\"] input").setValue(FakerGenerator.Registration.generateByCard().getCity());
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(3));
        $("[data-test-id=\"name\"] input").setValue(FakerGenerator.Registration.generateByCard().getName());
        $("[data-test-id=\"phone\"] input").setValue(FakerGenerator.Registration.generateByCard().getPhone());
        $("[data-test-id=\"agreement\"]").click();
        $(withText("Запланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
        $(withText("Запланировать")).click();
        $("[data-test-id=\"date\"] input").setValue(setMeetingDate(7));
        $(withText("Перепланировать")).click();
        $(withText("Успешно!")).shouldBe(Condition.visible, Duration.ofMillis(15000));
    }
}
