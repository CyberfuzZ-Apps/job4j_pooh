package ru.job4j.pooh;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TopicServiceTest {

    @Test
    public void whenPostThenGetTopic() {
        var topicService = new TopicService();
        Map<String, String> params = new HashMap<>();
        params.put("UserId", "1");
        params.put("temperature", "18");
        /* Добавляем данные в очередь weather. Режим topic */
        topicService.process(
                new Req("POST", "topic", "weather", params)
        );
        /* Забираем данные из очереди weather. Режим topic */
        var result = topicService.process(
                new Req("GET", "topic", "weather", params)
        );
        assertThat(result.text(), is("18"));
    }

    @Test
    public void whenMultiPostsAndGetsTopic() {
        var topicService = new TopicService();
        String firstValue = "18";
        String secondValue = "25";
        String thirdValue = "30";
        /* Добавляем данные в очередь weather. Режим topic */
        topicService.process(new Req(
                "POST",
                "topic",
                "weather",
                Map.of("temperature", firstValue)));
        topicService.process(new Req(
                "POST",
                "topic",
                "weather",
                Map.of("temperature", secondValue)));
        /* Забираем данные из очереди weather. Режим topic */
        var result = topicService.process(
                new Req("GET", "topic", "weather", Map.of("UserId", "1")));
        assertThat(result.text(), is(firstValue));
        /* Еще добавляем данные в очередь weather. Режим topic */
        topicService.process(new Req(
                "POST",
                "topic",
                "weather",
                Map.of("temperature", thirdValue)));
        /* Снова забираем данные из обновленной очереди weather. Режим topic */
        result = topicService.process(
                new Req("GET", "topic", "weather", Map.of("UserId", "1")));
        assertThat(result.text(), is(secondValue));
        result = topicService.process(
                new Req("GET", "topic", "weather", Map.of("UserId", "1")));
        assertThat(result.text(), is(thirdValue));
        /* Забираем данные из очереди weather. Режим topic. Другой пользователь */
        result = topicService.process(
                new Req("GET", "topic", "weather", Map.of("UserId", "2")));
        assertThat(result.text(), is(firstValue));
    }
}