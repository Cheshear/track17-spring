package track.lessons.lesson4;

import org.junit.Test;
import track.container.Container;
import track.container.beans.Car;
import track.container.beans.Engine;
import track.container.beans.Gear;
import track.container.config.InvalidConfigurationException;

public class ContainerTest {

    @Test
    public void getObjectById() throws InvalidConfigurationException {
        Container container = new Container("src/main/resources/config.json");
        Car car = (Car) container.getById("carBean");
        System.out.println(car);

        Gear gear = (Gear) container.getById("gearBean");
        System.out.println(gear);

        Engine engine = (Engine) container.getById("engineBean");
        System.out.println(engine);
    }

    @Test
    public void getObjectByClassName() throws InvalidConfigurationException {
        Container container = new Container("src/main/resources/config.json");

        Car car = (Car) container.getByClass("track.container.beans.Car");
        System.out.println(car);

        Gear gear = (Gear) container.getByClass("track.container.beans.Gear");
        System.out.println(gear);

        Engine engine = (Engine) container.getByClass("track.container.beans.Engine");
        System.out.println(engine);}

}