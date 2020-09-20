package advisor.server;

import advisor.config.Config;
import advisor.config.CurrentUser;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;

public class Server {

    private static Server instance = null;
    private HttpServer server;

    private Server() {
        init();
    }

    private void init() {
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(Config.REDIRECT_URI_PORT), 0);
            server.createContext("/",
                    exchange -> {
                        String code = exchange.getRequestURI().getQuery();
                        if (code != null && code.startsWith("code")) {
                            CurrentUser.getInstance().setCode(code);
                        }
                        String response = "Not found authorization code. Try again.";
                        if (Objects.isNull(CurrentUser.getInstance().getCode()) || ( code != null && code.startsWith("error"))) {
                            exchange.sendResponseHeaders(200, response.length());
                            exchange.getResponseBody().write(response.getBytes());
                            exchange.getResponseBody().close();
                        } else {
                            response = "Got the code. Return back to your program.";
                            exchange.sendResponseHeaders(200, response.length());
                            exchange.getResponseBody().write(response.getBytes());
                            exchange.getResponseBody().close();
                        }
                        synchronized (System.out) {
                            System.out.notifyAll();
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        server.stop(1);
    }

    public void start() {
        server.start();
    }

    public static Server getInstance() {
        if (Objects.isNull(instance)) instance = new Server();
        return instance;
    }
}
