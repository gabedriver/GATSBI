package gatsbi;

/**
 * AbstractPerson.java created by thigley on Apr 3, 2014 at 1:29:34 PM
 */
abstract class AbstractPerson {

    private String firstName = "";
    private String middleName;
    private String lastName;
    private String gender;
    private short occupation = GLOBALS.NULL;
    private String hometown;
    private String major;
    private short age=0;
    private String likes;

    private int cycleNum = 0;

    public String getName() {
        return firstName;
    }

    Object getNext() {
        switch (cycleNum) {
            case 0:
                cycleNum++;
                return getName();

            case 1:
                cycleNum++;
                return getMidName();

            case 2:
                cycleNum++;
                return getLastName();

            case 3:
                cycleNum++;
                return getGender();
            case 4:
                cycleNum++;
                return getOccupation();

            case 5:
                cycleNum++;
                return getHometown();
            case 6:
                cycleNum++;
                getMajor();
                break;
            case 7:
                cycleNum++;
                return getAge();
            case 8:
                cycleNum = 0;
                return getLikes();
        }
        return '-';
    }

    public String getMidName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public short getOccupation() {
        return occupation;
    }

    public String getHometown() {
        return hometown;
    }

    public String getMajor() {
        return major;
    }

    public short getAge() {
        return age;
    }

    public String getLikes() {
        return likes;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOccupation(short occupation) {
        this.occupation = occupation;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setAge(short age) {
        this.age = age;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String toString() {
        String returnMe = "I am a Foo: ";
        returnMe += "\tfirstName=" + getName();
        returnMe += "\tmiddleName=" + getMidName();
        returnMe += "\tlastName=" + getLastName();
        returnMe += "\tgenderM=" + getGender();
        returnMe += "\toccupation=" + getOccupation();
        returnMe += "\thometown=" + getHometown();
        returnMe += "\tmajor=" + getMajor();
        returnMe += "\tage=" + getAge();
        returnMe += "\tinterestingFact=" + getLikes();
        return returnMe;
    } // toString()
}
