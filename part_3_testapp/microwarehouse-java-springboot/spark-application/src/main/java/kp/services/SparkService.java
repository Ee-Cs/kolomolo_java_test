package kp.services;

import kp.models.FoodHubOrder;
import kp.tools.DataLoader;
import kp.tools.ProductSender;
import kp.tools.Utilities;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.List;

/**
 * The Spark service.
 */
public class SparkService {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static final Path DATA_FILE =
            Path.of("/opt/bitnami/spark/data/foodhub_order_no_header.csv");
    private static final String QUERY_STR =
            "SELECT COUNT(restaurantName) restaurantCount, cuisineType " +
            "FROM food_hub_order " +
            "GROUP BY cuisineType ORDER BY restaurantCount DESC";

    /**
     * Processes.
     * Gets 10 Top cuisine types along with the number of restaurants serving them
     * (right now it gets the list with all 14 cuisine types).
     */
    public void process() {

        final List<FoodHubOrder> foodHubOrderList = DataLoader.readDataFile(DATA_FILE);
        Utilities.report(foodHubOrderList);
        try (SparkSession sparkSession = SparkSession.builder().appName("Application").getOrCreate()) {
            final Dataset<FoodHubOrder> dataset = sparkSession.createDataset(
                    foodHubOrderList, Encoders.bean(FoodHubOrder.class));
            dataset.createOrReplaceTempView("food_hub_order");
            final Dataset<Row> cuisineTypeDF = sparkSession.sql(QUERY_STR);
            cuisineTypeDF.show();
            ProductSender.sendSparkProduct(cuisineTypeDF);
        } catch (Exception e) {
            logger.error("process(): exception[{}] with Dataset", e.getMessage());
            return;
        }
        logger.info("process(): foodHubOrder list size[{}]", foodHubOrderList.size());
    }

}
