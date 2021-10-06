package ru.job4j.pooh;

/**
 * Интерфейс Service.
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public interface Service {
    Resp process(Req req);
}
