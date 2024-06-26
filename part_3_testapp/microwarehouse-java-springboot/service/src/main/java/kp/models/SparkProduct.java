package kp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The result from Spark
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SparkProduct implements Serializable {
    private int restaurantCount;
    private String cuisineType;
}
