package ru.job4j.pooh;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        var queueService = new QueueService();
        Map<String, String> params = new HashMap<>();
        params.put("temperature", "18");
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(
                new Req("POST", "queue", "weather", params)
        );
        /* Забираем данные из очереди weather. Режим queue */
        var result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("18"));
    }

    @Test
    public void whenPostThenGetQueue2() {
        var queueService = new QueueService();
        /* Добавляем данные в очередь weather. Режим queue */
        queueService.process(new Req(
                "POST",
                "queue",
                "weather",
                        Map.of("temperature", "18")));
        queueService.process(new Req(
                "POST",
                "queue",
                "weather",
                        Map.of("temperature", "25")));
        /* Забираем данные из очереди weather. Режим queue */
        Req req = new Req("GET", "queue", "weather", null);
        var result = queueService.process(req);
        assertThat(result.text(), is("18"));
        result = queueService.process(req);
        assertThat(result.text(), is("25"));
        result = queueService.process(req);
        assertThat(result.text(), is("Empty queue!!!"));
    }
}