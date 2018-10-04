package tests;

import code.Location;
import code.Person;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Nicolas Barrios
 */

public class LocationTests {

    Location instance = new Location();

    @Test
    public void validPerson() {
        Person result = instance.getPerson();
        assertTrue("Location should have one of the four valid Person types",
                result == Person.B_AGENT || result == Person.R_AGENT || result == Person.BYSTANDER
                        || result == Person.ASSASSIN || result == Person.NONE);
    }

    // what else can there be

}
