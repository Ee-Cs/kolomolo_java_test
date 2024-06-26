package kp;

import kp.services.SparkService;

/**
 * The application.
 */
public class Application {
    /**
     * The main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        new SparkService().process();
    }

}
