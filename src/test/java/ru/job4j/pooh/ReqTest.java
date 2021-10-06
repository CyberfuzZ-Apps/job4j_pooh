package ru.job4j.pooh;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ReqTest {

    @Test
    public void whenPostAndTopic() {
        var content = """
                POST /topic/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.67.0
                Accept: */*
                Content-Length: 7
                Content-Type: application/x-www-form-urlencoded
                                
                text=13
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.param("text"), is("13"));
    }

    @Test
    public void whenPostAndQueue() {
        var content = """
                POST /queue/weather HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.67.0
                Accept: */*
                Content-Length: 7
                Content-Type: application/x-www-form-urlencoded
                                
                text=13
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("POST"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.param("text"), is("13"));
    }

    @Test
    public void whenGetMethodAndQueue() {
        var content = """
                GET /queue/weather HTTP/1.1
                       Host: localhost:9000
                       User-Agent: curl/7.67.0
                       Accept: */*
                       
                
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("queue"));
        assertThat(req.queue(), is("weather"));
        assertNull(req.param(""));
    }

    @Test
    public void whenGetAndTopic() {
        var content = """
                GET /topic/weather/1 HTTP/1.1
                Host: localhost:9000
                User-Agent: curl/7.64.1
                Accept: */*
                                
                                
                """;
        var req = Req.of(content);
        assertThat(req.method(), is("GET"));
        assertThat(req.mode(), is("topic"));
        assertThat(req.queue(), is("weather"));
        assertThat(req.param("UserId"), is("1"));
    }
}