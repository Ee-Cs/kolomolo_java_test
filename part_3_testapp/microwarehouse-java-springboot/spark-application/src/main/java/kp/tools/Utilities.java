package kp.tools;

import kp.models.FoodHubOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * The utilities.
 */
public class Utilities {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Reports the list to console log.
     *
     * @param foodHubOrderList the food hub order list
     */
    public static void report(List<FoodHubOrder> foodHubOrderList) {

        FoodHubOrder foodHubOrder = foodHubOrderList.get(0);
        logger.info("report(): first in list \n\t" +
                    "orderId[{}], customerId[{}], restaurantName[{}], \n\t" +
                    "cuisineType[{}], costOfTheOrder[{}], dayOfTheWeek[{}], \n\t" +
                    "rating[{}], foodPreparationTime[{}], deliveryTime[{}]",
                foodHubOrder.getOrderId(), foodHubOrder.getCustomerId(),
                foodHubOrder.getRestaurantName(), foodHubOrder.getCuisineType(),
                foodHubOrder.getCostOfTheOrder(), foodHubOrder.getDayOfTheWeek(),
                foodHubOrder.getRating(), foodHubOrder.getFoodPreparationTime(),
                foodHubOrder.getDeliveryTime());
        foodHubOrder = foodHubOrderList.get(foodHubOrderList.size() - 1);
        logger.info("report(): last in list \n\t" +
                    "orderId[{}], customerId[{}], restaurantName[{}], \n\t" +
                    "cuisineType[{}], costOfTheOrder[{}], dayOfTheWeek[{}], \n\t" +
                    "rating[{}], foodPreparationTime[{}], deliveryTime[{}]",
                foodHubOrder.getOrderId(), foodHubOrder.getCustomerId(),
                foodHubOrder.getRestaurantName(), foodHubOrder.getCuisineType(),
                foodHubOrder.getCostOfTheOrder(), foodHubOrder.getDayOfTheWeek(),
                foodHubOrder.getRating(), foodHubOrder.getFoodPreparationTime(),
                foodHubOrder.getDeliveryTime());
    }

}
