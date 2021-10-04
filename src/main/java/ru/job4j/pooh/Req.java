package ru.job4j.pooh;

import java.util.Map;

/**
 * Req - класс служит для парсинга входящего сообщения.
 *
 * method - GET или POST. Он указывает на тип запроса.
 *
 * mode - указывает на режим работы: queue или topic.
 *
 * queue - имя очереди.
 *
 * text - содержимое запроса.
 */
public class Req {
    private final String method;
    private final String mode;
    private final String queue;
    private final Map<String, String> params;

    public Req(String method, String mode, String queue, Map<String, String> params) {
        this.method = method;
        this.mode = mode;
        this.queue = queue;
        this.params = params;
    }

    public static Req of(String content) {
        String[] lines = content.split(System.lineSeparator());
        String[] methodMode = lines[0].split(" ");
        String method = methodMode[0];
        String[] modeQueue = methodMode[1].split("/");
        String mode = modeQueue[1];
        String queueName = modeQueue[2];
        Map<String, String> map = null;
        if ("POST".equals(method)) {
            String[] text = lines[lines.length - 1].split("=");
            map = Map.of(text[0], text[1]);
        }
        return new Req(method, mode, queueName, map);
    }

    public String method() {
        return method;
    }

    public String mode() {
        return mode;
    }

    public String queue() {
        return queue;
    }

    public String param(String key) {
        return params.get(key);
    }
}
