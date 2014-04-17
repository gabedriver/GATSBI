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
        setMidName(mr.giveMeTheNextLine());
        setLastName(mr.giveMeTheNextLine());
        setGenderM(Integer.parseInt(mr.giveMeTheNextLine()) == 1);
        setOccupation((short) Integer.parseInt(mr.giveMeTheNextLine()));
        setHometown(mr.giveMeTheNextLine());
        setMajor((short) Integer.parseInt(mr.giveMeTheNextLine()));
        setAge((short) Integer.parseInt(mr.giveMeTheNextLine()));
        setLikes(mr.giveMeTheNextLine());

    }

    public String toString() {
        String returnMe = "I am a Person, please fill in my variables so I can be debugged.";

        return returnMe;
    }

}
