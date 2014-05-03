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
            setOccupation((short) Short.parseShort(next));
        }
        setHometown(mr.giveMeTheNextLine());
        next = mr.giveMeTheNextLine();
        if (next.charAt(0) == '-') {
            setAge((short)0);
        } else {
            setAge((short) Short.parseShort(next));
        }
        setLikes(mr.giveMeTheNextLine());

    }

    boolean qualityKnown(short lastAskedQuestion) {
        boolean returnMe = false;
        switch(lastAskedQuestion){
            case GLOBALS.QLASTNAME:
                if(!getLastName().equals("-")){
                    returnMe =   true;
                }
                break;
            case GLOBALS.QAGE:
                if(getAge() != 0){
                    returnMe =   true;
                }
                break;
            case GLOBALS.QGENDER:

                if(!getGender().equals("-")){
                    returnMe =   true;
                }
                break;
            case GLOBALS.QHOMETOWN:
                if(!getHometown().equals("-")){
                    returnMe =   true;
                }
                break;
            case GLOBALS.QLIKES:
                if(!getLikes().equals("-")){
                    returnMe =   true;
                }
                break;
            case GLOBALS.QOCCUPATION:
                if(getOccupation() != GLOBALS.NULL){
                    returnMe =  true;
                }
                break;
        }
        return returnMe;
    }
}
