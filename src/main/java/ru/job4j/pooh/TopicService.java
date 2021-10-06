package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Класс TopicService реализует сервис топиков.
 *
 * Отправитель посылает сообщение с указанием темы.
 * Получатель читает первое сообщение и удаляет его из очереди.
 * Для каждого получателя уникальная очередь потребления.
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> users
            = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        int status = 200;
        if ("POST".equals(req.method())) {
            text = getPostText(req);
        } else if ("GET".equals(req.method())) {
            text = getGetText(req);
        }
        return new Resp(text, status);
    }

    private String getPostText(Req req) {
        String text = "";
        queues.putIfAbsent(req.queue(), new ConcurrentLinkedQueue<>());
        for (String str : req.getParams().keySet()) {
            if (!"UserId".equals(str)) {
                text = req.param(str);
            }
        }
        queues.get(req.queue()).offer(text);
        if (users.size() > 0) {
            for (String str : users.keySet()) {
                users.get(str).get(req.queue()).offer(text);
            }
        }
        return text;
    }

    private String getGetText(Req req) {
        String userId = req.param("UserId");
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> userMap = new ConcurrentHashMap<>();
        for (String q : queues.keySet()) {
            ConcurrentLinkedQueue<String> userQueue = new ConcurrentLinkedQueue<>(queues.get(q));
            userMap.put(q, userQueue);
        }
        users.putIfAbsent(userId, userMap);
        return users.get(userId).get(req.queue()).poll();
    }

}
