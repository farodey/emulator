import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Path("/hello")
public class Servlet {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String hello() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <title>Эмулятор</title>\n" +
                " </head>\n" +
                " <body>\n" +
                " <h1>Эмулятор</h1>" +
                "  <fieldset>\n" +
                "  <legend>Генерация случайного числа в заданном диапазоне</legend>\n" +
                "  <form>\n" +
                "   <p>Введиде диапазон чисел. Например 2_15</p>\n" +
                "   <p><input placeholder=\"Введите строку\" name=\"randValue\"></p>\n" +
                "   <p><input type=\"submit\" value=\"Отправить\" \n" +
                "    formaction=\"hello\" formmethod=\"post\"></p>\n" +
                "  </form>\n" +
                "  </fieldset>\n" +
                "  <fieldset>\n" +
                "  <legend>Счетчик</legend>\n" +
                "  <form>\n" +
                "  <p>Введите значение, на которое необходимо увеличить счетчик</p>\n" +
                "   <p><input placeholder=\"Введите значение\" name=\"counterValue\"></p>\n" +
                "   <p><input type=\"submit\" value=\"Отправить\" \n" +
                "    formaction=\"hello\" formmethod=\"post\"></p>\n" +
                "  </form>\n" +
                "  </fieldset>\n" +
                "  <fieldset>\n" +
                "  <form>\n" +
                "  <legend>Запись в лог</legend>\n" +
                "  <p>Введите строку, которую необходимо записать в лог сервера</p>\n" +
                "   <p><input placeholder=\"Введите строку\" name=\"toLog\"></p>\n" +
                "   <p><input type=\"submit\" value=\"Отправить\" \n" +
                "    formaction=\"hello\" formmethod=\"post\"></p>\n" +
                "  </form>\n" +
                "  </fieldset>\n" +
                " </body>\n" +
                "</html>";
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    public String rnd_out(byte[] data) throws IOException {

        String request = new String(data, StandardCharsets.UTF_8);
        String msgType = request.substring(0, request.indexOf('='));
        String value = request.substring(msgType.length() + 1);
        String response = "";

        if (msgType.equals("randValue")) {
            int index = value.indexOf("_");
            String s = value.substring(0, index);
            int min = Integer.parseInt(value.substring(0, index));
            int max = Integer.parseInt(value.substring(index + 1));
            int rnd = (int) (Math.random() * (max - min) + min);
            response = "Случайное число: " + Integer.toString(rnd);

        } else if (msgType.equals("counterValue")) {
            Main.counterValue = Main.counterValue + Integer.parseInt(value);
            response = "Текущее значение счеткика: " + Main.counterValue;

        } else if (msgType.equals("toLog")) {
            Date date = new Date();
            FileWriter writer = new FileWriter("log.log", true);
            writer.write(date + " " + value + "\n");
            writer.flush();
            response = "В лог сервера записана строка: " +  value;
        }
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <title>Эмулятор</title>\n" +
                " </head>\n" +
                " <body>\n" +
                " <h1>Эмулятор</h1>" +
                "  <fieldset>\n" +
                "  <legend>Генерация случайного числа в заданном диапазоне</legend>\n" +
                "  <form>\n" +
                "   <p>Введиде диапазон чисел. Например 2_15</p>\n" +
                "   <p><input placeholder=\"Введите строку\" name=\"randValue\"></p>\n" +
                "   <p><input type=\"submit\" value=\"Отправить\" \n" +
                "    formaction=\"hello\" formmethod=\"post\"></p>\n" +
                "  </form>\n" +
                "  </fieldset>\n" +
                "  <fieldset>\n" +
                "  <legend>Счетчик</legend>\n" +
                "  <form>\n" +
                "  <p>Введите значение, на которое необходимо увеличить счетчик</p>\n" +
                "   <p><input placeholder=\"Введите значение\" name=\"counterValue\"></p>\n" +
                "   <p><input type=\"submit\" value=\"Отправить\" \n" +
                "    formaction=\"hello\" formmethod=\"post\"></p>\n" +
                "  </form>\n" +
                "  </fieldset>\n" +
                "  <fieldset>\n" +
                "  <form>\n" +
                "  <legend>Запись в лог</legend>\n" +
                "  <p>Введите строку, которую необходимо записать в лог сервера</p>\n" +
                "   <p><input placeholder=\"Введите строку\" name=\"toLog\"></p>\n" +
                "   <p><input type=\"submit\" value=\"Отправить\" \n" +
                "    formaction=\"hello\" formmethod=\"post\"></p>\n" +
                "  </form>\n" +
                "  </fieldset>\n" +
                " " + response +
                " </body>\n" +
                "</html>";
    }
}
