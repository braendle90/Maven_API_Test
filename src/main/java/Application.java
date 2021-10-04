
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpServer;

class Application {

    public static void main(String[] args) throws IOException {

        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/hello/v2", (exchange -> {

            if (exchange.getRequestURI().getQuery() == null) {

                if ("POST".equals(exchange.getRequestMethod())) {
                    Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());

                    CreateJsonFromMysql load = new CreateJsonFromMysql();
                    String respText = load.getMysqlContent();

                    String encoding = "UTF-8";

                    exchange.getResponseHeaders().set("Content-Type", "application/json; charset=" + encoding);


                    //String respText = String.format("Hello %s!", name + " EiNgabe");
                    exchange.sendResponseHeaders(200, respText.getBytes().length);

                    OutputStream output = exchange.getResponseBody();
                    output.write(respText.getBytes());
                    output.flush();
                }
            }



            if ("POST".equals(exchange.getRequestMethod())) {

                Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
                String noNameText = exchange.getRequestURI().getRawQuery();

                String SplitQuery = noNameText.substring(3);

                int id = Integer.parseInt(SplitQuery);

                System.out.println(exchange.getRequestMethod());


                System.out.println(exchange.getRequestURI().getRawQuery());
                //int name = params.getOrDefault("id", com.sun.tools.javac.util.List.of(noNameText)).stream().findFirst().orElse(noNameText);


                CreateJsonFromMysql load = new CreateJsonFromMysql();
                String respText = load.getMysqlContent(id);




                //String respText = String.format("Hello %s!", name + " EiNgabe");
                exchange.sendResponseHeaders(200, respText.getBytes().length);

                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            }

            else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public static Map<String, List<String>> splitQuery(String query) {
        if (query == null || "".equals(query)) {
            return Collections.emptyMap();
        }

        return Pattern.compile("&").splitAsStream(query)
                .map(s -> Arrays.copyOf(s.split("="), 2))
                .collect(groupingBy(s -> decode(s[0]), mapping(s -> decode(s[1]), toList())));

    }

    private static String decode(final String encoded) {
        try {
            return encoded == null ? null : URLDecoder.decode(encoded, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 is a required encoding", e);
        }
    }
}