package gatsbi;

import java.util.Stack;

/**
 * Person.java created by thigley on Apr 3, 2014 at 2:05:20 PM
 */
class Person extends AbstractPerson{
    Stack<String> inputs = new Stack<String>();
    
    public String toString() {
        String returnMe = "I am a Person, please fill in my variables so I can be debugged.";

        return returnMe;
    }


}
