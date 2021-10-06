package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс QueueService реализует сервис очереди.
 *
 * Отправитель посылает сообщение с указанием очереди.
 * Получатель читает первое сообщение и удаляет его из очереди.
 * Если приходят несколько получателей, то они читают из одной очереди.
 * Уникальное сообщение может быть прочитано, только одним получателем.
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> concurrentMap
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        int status = 200;
        if ("POST".equals(req.method())) {
            concurrentMap.putIfAbsent(req.queue(), new ConcurrentLinkedQueue<>());
            for (String str : req.getParams().keySet()) {
                 text = req.param(str);
            }
            concurrentMap.get(req.queue()).offer(text);
        } else if ("GET".equals(req.method())) {
            text = concurrentMap.get(req.queue()).poll();
        }
        return new Resp(text, status);
    }
}
