package kp.tools;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The product sender.
 */
public class ProductSender {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final URI SERVICE_URI = URI.create("http://service01:8081/products");

    /**
     * Posts the Spark product.
     *
     * @param cuisineTypeDF the cuisine type data set
     */
    public static void sendSparkProduct(Dataset<Row> cuisineTypeDF) {

        final HttpRequest.BodyPublisher bodyPublisher = Optional.of(cuisineTypeDF).map(Dataset::toJSON)
                .map(Dataset::collectAsList)
                .map(arg -> arg.stream().collect(
                        Collectors.joining(",", "[", "]")))
                .map(HttpRequest.BodyPublishers::ofString)
                .orElse(HttpRequest.BodyPublishers.noBody());
        final HttpRequest httpRequest = HttpRequest.newBuilder(SERVICE_URI).POST(bodyPublisher)
                .header("Content-Type", "application/json").build();
        try {
            final HttpClient httpClient = HttpClient.newBuilder().build();
            final HttpResponse<String> httpResponse =
                    httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            logger.info("sendSparkProduct(): POST status code[{}]", httpResponse.statusCode());
        } catch (IOException | InterruptedException e) {
            logger.error("sendSparkProduct(): IOException[{}] with HttpClient", e.getMessage());
        }
    }
}
