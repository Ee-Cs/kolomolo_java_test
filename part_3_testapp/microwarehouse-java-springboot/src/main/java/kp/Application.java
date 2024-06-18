package kp;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * The application.
 */
//@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        StringBuilder strBld = new StringBuilder();
        JavaSparkSQLExample.execute(strBld);
        strBld.append("### ".repeat(25)).append("\n");
        countWords(strBld);
        strBld.append("### ".repeat(25)).append("\n");
        System.out.println(strBld);

//        if (true) {
//        } else {
//            SpringApplication.run(Application.class, args);
//        }
    }

    /**
     * Counts the words.
     *
     * @param strBld the string builder
     */
    static void countWords(StringBuilder strBld) {

        final File dataFile = new File("data.txt");
        strBld.append(String.format("START spa <<<<<<<%n"));
        try (SparkSession sparkSession = SparkSession.builder().appName("Application").getOrCreate()) {
            // lines JavaRDD object
            final JavaRDD<String> lines = sparkSession.read().textFile(dataFile.getName()).javaRDD();
            //  JavaRDD object words, space-separated words
            final JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(SPACE.split(s)).iterator());
            JavaPairRDD<String, Integer> counts;
            List<Tuple2<String, Integer>> output;
            // ONES
            final JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
            counts = ones.reduceByKey(Integer::sum);
            output = counts.collect();
            for (Tuple2<?, ?> tuple : output) {
                strBld.append(String.format("ones tuple 1[%s], tuple 2[%s]%n", tuple._1(), tuple._2()));
            }
            // TWOS
            final JavaPairRDD<String, Integer> twos = words.mapToPair(s -> new Tuple2<>(s, 2));
            counts = twos.reduceByKey(Integer::sum);
            output = counts.collect();
            for (Tuple2<?, ?> tuple : output) {
                strBld.append(String.format("twos tuple 1[%s], tuple 2[%s]%n", tuple._1(), tuple._2()));
            }
            // THIRDS
            final JavaPairRDD<String, Integer> thirds = words.mapToPair(s -> new Tuple2<>(s, 3));
            counts = thirds.reduceByKey(Integer::sum);
            output = counts.collect();
            for (Tuple2<?, ?> tuple : output) {
                strBld.append(String.format("thirds tuple 1[%s], tuple 2[%s]%n", tuple._1(), tuple._2()));
            }
            // FOURTHS
            final JavaPairRDD<String, Integer> fourths = words.mapToPair(s -> new Tuple2<>(s, 4));
            counts = fourths.reduceByKey(Integer::sum);
            output = counts.collect();
            for (Tuple2<?, ?> tuple : output) {
                strBld.append(String.format("fourths tuple 1[%s], tuple 2[%s]%n", tuple._1(), tuple._2()));
            }
            sparkSession.stop();
        } catch (Exception e) {
            strBld.append(String.format("exception[%s]%n", e.getMessage()));
            System.out.println(strBld);
            System.exit(1);
        }
    }
    static void alternative(File dataFile, StringBuilder strBld) {

        final List<Integer> DATA = Arrays.asList(1, 2, 3, 4, 5);

        final SparkConf sparkConf = new SparkConf().setAppName("Application").setMaster("local");
        try (JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf)) {
            final JavaRDD<String> lines = javaSparkContext.textFile(dataFile.getName(), 1);
            JavaRDD<Integer> lineLengths = lines.map(String::length);
            int totalLength = lineLengths.reduce(Integer::sum);

            final JavaRDD<Integer> distData = javaSparkContext.parallelize(DATA);
            final int count = distData.reduce(Integer::sum);
            final JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2<>(s, 1));
            final JavaPairRDD<String, Integer> counts = pairs.reduceByKey(Integer::sum);


            javaSparkContext.stop();
        } catch (Exception e) {
            strBld.append(String.format("exception[%s]%n", e.getMessage()));
            System.out.println(strBld);
            System.exit(1);
        }
    }
}
