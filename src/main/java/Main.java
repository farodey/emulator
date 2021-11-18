import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static int counterValue = 0;
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        final ResourceConfig config = new ResourceConfig();
        config.register(Servlet.class);
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }

    public static void main(String[] args) {
        try {
            final HttpServer httpServer = startServer();

            // Обработчик завершения работы JVM
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    System.out.println("Завершение работы приложения...");
                    httpServer.shutdownNow();
                    System.out.println("Готово, выходим.");
                } catch (Exception e) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
                }
            }));

            System.out.println("Приложение запущено. Остановить работу приложения можно с помощью CTRL+C");

            // Блокируем поток и ждем сигнала завершения, например CTRL+C
            Thread.currentThread().join();

        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}