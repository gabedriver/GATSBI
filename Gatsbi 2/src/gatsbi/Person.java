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
        String next = mr.giveMeTheNextLine();
        if (next.charAt(0) == '-') {
            setOccupation(GLOBALS.NULL);
        } else {
            setOccupation((short) Short.parseShort(mr.giveMeTheNextLine()));
        }
        setHometown(mr.giveMeTheNextLine());
        next = mr.giveMeTheNextLine();
        if (next.charAt(0) == '-') {
            setAge((short)0);
        } else {
            setAge((short) Short.parseShort(mr.giveMeTheNextLine()));
        }
        setLikes(mr.giveMeTheNextLine());

    }
}
