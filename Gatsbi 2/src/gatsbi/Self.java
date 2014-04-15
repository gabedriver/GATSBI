package gatsbi;

/**
 * Self.java created by thigley on Apr 3, 2014 at 2:02:14 PM
 */
class Self extends AbstractPerson{
    
    Self(){
        setName("GATSBI");
        setMidName("the");
        setLastName("Robot");
        setGenderM(true);
        setOccupation(GLOBALS.MACHINE);
        setHometown("Computer");
        setMajor(GLOBALS.CS);
        setAge((short) 1);
        setInterestingFact("I am a professional break dancer");
    }

    public String toString() {
        String returnMe = "Hello, I am GATSBI\n";
        returnMe+= "My name is " + getName() + " " + getMidName() + " " + getLastName() + "\n"; 
        returnMe+= "My hometown is a " + getHometown() + "\n";
        returnMe += "My age is " + getAge() +  " Months";
        returnMe += "And one interesting fact about myself is that " + getInterestingFact();
        return returnMe;
    }
}
