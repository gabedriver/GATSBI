package gatsbi;

/**
 * AbstractPerson.java created by thigley on Apr 3, 2014 at 1:29:34 PM
 */
abstract class AbstractPerson {

    private String firstName = "";
    private String middleName;
    private String lastName;
    private boolean genderM;
    private short occupation;
    private String hometown;
    private short major;
    private short age;
    private String interestingFact;

    public String getName() {
        return firstName;
    }

    public String getMidName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getGenderM() {
        return genderM;
    }

    public short getOccupation() {
        return occupation;
    }

    public String getHometown() {
        return hometown;
    }

    public short getMajor() {
        return major;
    }

    public short getAge() {
        return age;
    }

    public String getInterestingFact() {
        return interestingFact;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
    }

    public void setMidName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGenderM(boolean genderM) {
        this.genderM = genderM;
    }

    public void setOccupation(short occupation) {
        this.occupation = occupation;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setMajor(short major) {
        this.major = major;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public void setInterestingFact(String interestingFact) {
        this.interestingFact = interestingFact;
    }

    public String toString() {
        String returnMe = "I am a Foo: ";
        returnMe += "\tfirstName=" + getName();
        returnMe += "\tmiddleName=" + getMidName();
        returnMe += "\tlastName=" + getLastName();
        returnMe += "\tgenderM=" + getGenderM();
        returnMe += "\toccupation=" + getOccupation();
        returnMe += "\thometown=" + getHometown();
        returnMe += "\tmajor=" + getMajor();
        returnMe += "\tage=" + getAge();
        returnMe += "\tinterestingFact=" + getInterestingFact();
        return returnMe;
    } // toString()
}
