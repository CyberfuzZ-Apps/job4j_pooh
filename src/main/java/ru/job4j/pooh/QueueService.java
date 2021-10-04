package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Для реализации QueueService и TopicService нужно использовать многопоточные коллекции.
 *
 * Важно. Для реализации QueueService и TopicService вам хватит методов putIfAbsent и get.
 *
 * В коде не надо писать проверку на if (map.contains()) - это не атомарная операция.
 *
 * Для выбора метода GET или POST используйте константы "GET".equals(req.method()),
 * а не на оборот. req.method.equals("GET").
 */
public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> concurrentMap
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        if ("POST".equals(req.method())) {
            concurrentMap.putIfAbsent(req.queue(), new ConcurrentLinkedQueue<>());

        }
        return null;
    }
}
