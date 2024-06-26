package kp.tools;

import kp.models.CuisineType;
import kp.models.DayOfTheWeek;
import kp.models.FoodHubOrder;
import kp.models.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The data loader.
 */
public class DataLoader {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Reads CVS data file.
     *
     * @param dataFile the data file
     * @return the {@link FoodHubOrder} list
     */
    public static List<FoodHubOrder> readDataFile(Path dataFile) {

        if (!dataFile.toFile().canRead()) {
            logger.error("readDataFile(): unreadable data file[{}]", dataFile);
            return List.of();
        }
        final AtomicBoolean skipHeaderFlag = new AtomicBoolean(false);
        final List<FoodHubOrder> foodHubOrderList;
        try (BufferedReader bufferedReader = Files.newBufferedReader(dataFile)) {
            foodHubOrderList = bufferedReader.lines()
                    .filter(arg -> skipHeaderFlag.getAndSet(true))
                    .map(DataLoader::readDataRow).toList();
        } catch (IOException e) {
            logger.error("readDataFile(): IOException[{}]", e.getMessage());
            return List.of();
        }
        logger.info("readDataFile(): data list size[{}]", foodHubOrderList.size());
        return foodHubOrderList;
    }

    /**
     * Reads data row.
     *
     * @param line the line
     * @return the {@link FoodHubOrder}
     */
    private static FoodHubOrder readDataRow(String line) {

        final String[] rowArr = line.split(",");
        if (FoodHubOrder.ROW_ITEMS_NUMBER != rowArr.length) {
            logger.warn("readDataRow(): row has bad number of items, expected[{}, actual[{}]",
                    FoodHubOrder.ROW_ITEMS_NUMBER, rowArr.length);
            return new FoodHubOrder();
        }
        try {
            final FoodHubOrder foodHubOrder = new FoodHubOrder(
                    Optional.ofNullable(rowArr[0]).map(Integer::parseInt).orElse(0),
                    Optional.ofNullable(rowArr[1]).map(Integer::parseInt).orElse(0),
                    Optional.ofNullable(rowArr[2]).orElse(""),
                    Optional.ofNullable(rowArr[3]).map(CuisineType::of).orElse(CuisineType.American),
                    Optional.ofNullable(rowArr[4]).map(Double::parseDouble).orElse(0d),
                    Optional.ofNullable(rowArr[5]).map(DayOfTheWeek::of).orElse(DayOfTheWeek.Weekday),
                    Optional.ofNullable(rowArr[6]).map(Rating::of).orElse(Rating.NotGiven),
                    Optional.ofNullable(rowArr[7]).map(Integer::parseInt).orElse(0),
                    Optional.ofNullable(rowArr[8]).map(Integer::parseInt).orElse(0));
            return foodHubOrder;
        } catch (Exception e) {
            logger.error("readDataRow(): IOException[{}]", e.getMessage());
            return new FoodHubOrder();
        }
    }
}
