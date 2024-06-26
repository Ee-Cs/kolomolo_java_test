package kp.models;

import java.io.Serializable;

/**
 * The food hub order.
 */
public class FoodHubOrder implements Serializable {
    public static final int ROW_ITEMS_NUMBER = 9;

    /**
     * The constructor
     */
    public FoodHubOrder() {
    }

    /**
     * The constructor
     *
     * @param orderId             the order id
     * @param customerId          the customer id
     * @param restaurantName      the restaurant name
     * @param cuisineType         the cuisine type
     * @param costOfTheOrder      the cost of the order
     * @param dayOfTheWeek        the day of the week
     * @param rating              the rating
     * @param foodPreparationTime the food preparation time
     * @param deliveryTime        the delivery time
     */
    public FoodHubOrder(int orderId, int customerId, String restaurantName,
                        CuisineType cuisineType, double costOfTheOrder,
                        DayOfTheWeek dayOfTheWeek, Rating rating,
                        int foodPreparationTime, int deliveryTime) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.restaurantName = restaurantName;
        this.cuisineType = cuisineType;
        this.costOfTheOrder = costOfTheOrder;
        this.dayOfTheWeek = dayOfTheWeek;
        this.rating = rating;
        this.foodPreparationTime = foodPreparationTime;
        this.deliveryTime = deliveryTime;
    }

    private int orderId;
    private int customerId;
    private String restaurantName;
    private CuisineType cuisineType;
    private double costOfTheOrder;
    private DayOfTheWeek dayOfTheWeek;
    private Rating rating;
    private int foodPreparationTime;
    private int deliveryTime;

    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public double getCostOfTheOrder() {
        return costOfTheOrder;
    }

    public DayOfTheWeek getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public Rating getRating() {
        return rating;
    }

    public int getFoodPreparationTime() {
        return foodPreparationTime;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}