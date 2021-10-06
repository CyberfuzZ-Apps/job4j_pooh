package ru.job4j.pooh;

import java.util.Map;

/**
 * Класс Req служит для парсинга входящего сообщения от клиента.
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class Req {
    private final String method;
    private final String mode;
    private final String queue;
    private final Map<String, String> params;

    /**
     * Конструктор Req.
     *
     * @param method - GET или POST. Он указывает на тип запроса.
     * @param mode   - указывает на режим работы: queue или topic.
     * @param queue  - имя очереди.
     * @param params - содержимое запроса.
     */
    public Req(String method, String mode, String queue, Map<String, String> params) {
        this.method = method;
        this.mode = mode;
        this.queue = queue;
        this.params = params;
    }

    public static Req of(String content) {
        String[] lines = content.split(System.lineSeparator());
        String[] methodAndMode = lines[0].split(" ");
        String method = methodAndMode[0];
        String[] modeAndQueue = methodAndMode[1].split("/");
        String mode = modeAndQueue[1];
        String queueName = modeAndQueue[2];
        Map<String, String> map = null;
        if ("POST".equals(method)) {
            String[] text = lines[lines.length - 1].split("=");
            map = Map.of(text[0], text[1]);
        } else if ("GET".equals(method) && "queue".equals(mode)) {
            map = Map.of();
        } else if ("GET".equals(method) && "topic".equals(mode)) {
            if (modeAndQueue.length > 3) {
                map = Map.of("UserId", modeAndQueue[3]);
            }
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

    public Map<String, String> getParams() {
        return params;
    }
}
