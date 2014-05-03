package gatsbi;

/**
 * AbstractPerson.java created by thigley on Apr 3, 2014 at 1:29:34 PM
 */
abstract class AbstractPerson {

    private String firstName = "";
    private String lastName= "-";
    private String gender= "-";
    private short occupation = GLOBALS.NULL;
    private String hometown= "-";
    private short age=0;
    private String likes= "-";

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
                return getLastName();

            case 2:
                cycleNum++;
                return getGender();
            case 3:
                cycleNum++;
                return getOccupation();

            case 4:
                cycleNum++;
                return getHometown();
            
            case 5:
                cycleNum++;
                return getAge();
            case 6:
                cycleNum = 0;
                return getLikes();
        }
        return '-';
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

    

    public short getAge() {
        return age;
    }

    public String getLikes() {
        return likes;
    }

    public void setName(String firstName) {
        this.firstName = firstName;
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

   

    public void setAge(short age) {
        this.age = age;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String toString() {
        String returnMe = "I am a Person: ";
        returnMe += "\nfirstName=" + getName();
        returnMe += "\nlastName=" + getLastName();
        returnMe += "\ngenderM=" + getGender();
        returnMe += "\noccupation=" + getOccupation();
        returnMe += "\nhometown=" + getHometown();
        returnMe += "\nage=" + getAge();
        returnMe += "\ninterestingFact=" + getLikes();
        return returnMe;
    } // toString()
}
