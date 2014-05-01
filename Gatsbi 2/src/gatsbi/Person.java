package gatsbi;

import java.io.File;
import java.util.Stack;

/**
 * Person.java created by thigley on Apr 3, 2014 at 2:05:20 PM
 */
class Person extends AbstractPerson {

    Stack<String> inputs = new Stack<String>();
    

    public Person() {

    }

    public Person(MyReader mr) {
        setName(mr.giveMeTheNextLine());
        setLastName(mr.giveMeTheNextLine());
        setGender(mr.giveMeTheNextLine());
        setOccupation((short) Short.parseShort(mr.giveMeTheNextLine()));
        setHometown(mr.giveMeTheNextLine());
        setAge((short) Short.parseShort(mr.giveMeTheNextLine()));
        setLikes(mr.giveMeTheNextLine());

    }

}
