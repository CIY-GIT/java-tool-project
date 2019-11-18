package cy.learn.spi;

import java.util.Iterator;

public class SpiTest {

    /**
     * @param args
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws InstantiationException, IllegalAccessException,
                                          ClassNotFoundException {
        // TODO Auto-generated method stub
        Iterator it = sun.misc.Service.providers(SPIService.class);
        while (it.hasNext()) {
            SPIService service = (SPIService) it.next();
            service.test();
        }
    }
}
