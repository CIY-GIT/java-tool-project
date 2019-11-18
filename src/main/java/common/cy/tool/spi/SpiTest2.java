package cy.learn.spi;

import java.util.ServiceLoader;

public class SpiTest2 {

    /**
     * @param args
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
                                          ClassNotFoundException {
        ServiceLoader<SPIService> loader = ServiceLoader.load(SPIService.class);
        for (SPIService service : loader) {
            service.test();
        }

    }
}
