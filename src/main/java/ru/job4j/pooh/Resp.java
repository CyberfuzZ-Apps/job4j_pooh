package ru.job4j.pooh;

/**
 * Класс Resp формирует ответ от сервера.
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class Resp {
    private final String text;
    private final int status;

    public Resp(String text, int status) {
        if (text == null) {
            text = "Empty queue!!!";
        }
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public int status() {
        return status;
    }

}
