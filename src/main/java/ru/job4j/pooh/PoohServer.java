package ru.job4j.pooh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Класс PoohServer запускает сервер, читает входящие запросы от клиента
 * и отсылает обработанные ответы обратно клиенту.
 *
 * @author Evgeniy Zaytsev
 * @version 1.0
 */
public class PoohServer {
    private final HashMap<String, Service> modes = new HashMap<>();

    /**
     * Запускает сервер.
     */
    public void start() {
        modes.put("queue", new QueueService());
        modes.put("topic", new TopicService());
        ExecutorService pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                pool.execute(() -> {
                    try (OutputStream out = socket.getOutputStream();
                         InputStream input = socket.getInputStream()) {
                        byte[] buff = new byte[1_000_000];
                        var total = input.read(buff);
                        var content = new String(Arrays.copyOfRange(buff, 0, total), StandardCharsets.UTF_8);
                        System.out.println(content);
                        var req = Req.of(content);
                        var resp = modes.get(req.mode()).process(req);
                        out.write(("HTTP/1.1 " + resp.status() + " OK\r\n\r\n").getBytes());
                        out.write(resp.text().getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PoohServer().start();
    }
}
