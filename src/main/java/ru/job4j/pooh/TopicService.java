package ru.job4j.pooh;

/**
 * Для реализации QueueService и TopicService нужно использовать многопоточные коллекции.
 *
 * Важно. Для реализации QueueService и TopicService вам хватить методов putIfAbsent и get.
 *
 * В коде не надо писать проверку на if (map.contains()) - это не атомарная операция.
 *
 * Для выбора метода GET или POST используйте константы "GET".equals(req.method()),
 * а не на оборот. req.method.equals("GET").
 */
public class TopicService implements Service {
    @Override
    public Resp process(Req req) {
        return null;
    }
}
