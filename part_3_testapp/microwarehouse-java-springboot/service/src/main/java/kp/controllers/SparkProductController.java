package kp.controllers;

import kp.models.SparkProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * The REST controller for Spark products.
 */
@RestController
@RequestMapping("/products")
public class SparkProductController {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final List<SparkProduct> sparkProductList = new ArrayList<>();

    /**
     * The constructor.
     */
    public SparkProductController() {
        super();
    }

    /**
     * Receives the Spark products.
     *
     * @return the result
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String receiveSparkProducts(@RequestBody List<SparkProduct> sparkProductListBody) {

        sparkProductList.clear();
        sparkProductList.addAll(sparkProductListBody);
        logger.info("receiveSparkProducts(): Spark product list\n{}", extract());
        return "OK";
    }

    /**
     * Presents the Spark products.
     *
     * @return the result
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SparkProduct> presentSparkProducts() {

        logger.info("presentSparkProducts(): Spark product list\n{}", extract());
        return sparkProductList;
    }

    /**
     * Extracts the Spark products.
     *
     * @return the formated list
     */
    private String extract() {

        final StringBuilder strBld = new StringBuilder();
        sparkProductList.forEach(arg -> strBld.append(
                String.format("\trestaurantCount[%d], cuisineType[%s]%n",
                        arg.getRestaurantCount(), arg.getCuisineType())
        ));
        return strBld.toString();
    }
}