package gatsbi;

import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Model.java created by thigley on Apr 3, 2014 at 1:54:03 PM
 */
class Model {

    Controller c;
    private HashMap<String, String[]> responses = new HashMap<>();
    private Self gatsbi;
    private Person currentPerson;
    private Person friend;
    short lastAskedQuestion = GLOBALS.START;
    boolean personIsNew = false;
    MyReader mr = new MyReader();
    MyWriter mw;

    Model(Controller c) {
        this.c = c;
        loadResponses();
        currentPerson = new Person();
        gatsbi = new Self();
    }

    public String toString() {
        String returnMe = "I am a Model, please fill in my variables so I can be debugged.";

        return returnMe;
    }

    boolean hasName() {
        return currentPerson.getName().length() != 0;
    }

    String getName() {
        return currentPerson.getName();
    }

    void parse(String text) {
        //save      
        currentPerson.inputs.add(text);
        getResponse(text);
        //probe/continue

    }

    private void askName() {
        lastAskedQuestion = GLOBALS.QNAME;
        c.say("What is your name?");
    }

    private void askFriend() {
        lastAskedQuestion = GLOBALS.QFRIEND;
        c.say("What's your friend's name?");
    }

    private void getResponse(String text) {
        switch (lastAskedQuestion) {
            case GLOBALS.START:
                c.say("Hello."); //Change it up a bit. Hello, hey, what's up..
                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                askName();

                            }
                        }, 1000);

                break;

            case GLOBALS.QNAME: //we should ask about a friend during a conversation instead of right after he asks for your name... like "I'm bored of this conversation, let's talk about something else. Do you have any friends?"
                parseName(text);
                if (personIsNew) {
                    c.say("Oh, I haven't met you before...");
                    askMidName();
                    break;
                }
                askFriend();
                lastAskedQuestion = GLOBALS.QFRIEND;
//                genericResponse();

                break;

            case GLOBALS.QMIDNAME:
                currentPerson.setMidName(text);
                askLastName();
                break;

            case GLOBALS.QLASTNAME:
                currentPerson.setLastName(text);
                askAge();
                break;

            case GLOBALS.QAGE:
                currentPerson.setAge((short) Integer.parseInt(text));
                askGender();
                break;

            case GLOBALS.QGENDER:
                currentPerson.setGenderM(Integer.parseInt(text) == 1);

                askOccupation();
                break;

            case GLOBALS.QOCCUPATION:
                currentPerson.setOccupation((short) Integer.parseInt(text));

                askHometown();
                break;

            case GLOBALS.QHOMETOWN:
                currentPerson.setHometown(text);

                askMajor();
                break;

            case GLOBALS.QMAJOR:
                currentPerson.setMajor((short) Integer.parseInt(text));

                askLikes();
                break;

            case GLOBALS.QLIKES:
                currentPerson.setLikes(text);
                printPerson();
                c.say("Now I know all about you! Let's talk about your friends now.");
                askFriend();
                break;

            case GLOBALS.QFRIEND:
                if (personExists(text)) {
                    foundFriend(getPerson(text));
                } else {
                    //genericResponse(); //okay... now add the new person to the list, create a file, and ask questions about that person.
                    lastAskedQuestion = GLOBALS.NONE;

                }
                break;

            default:
                tryToAnswer(text);
        }

    }

    private void parseName(String text) { //sets name to name... if the name exists in "users", spits out "HEY I KNOW YOU!"
        char diff = 'a' - 'A';
        text = cleanse(text);
        text = text.replaceAll("im ", "");
        text = text.replaceAll("my ", "");
        text = text.replaceAll("name ", "");
        text = text.replaceAll("is ", "");
//        System.out.println(text);
        if (personExists(text)) {
            foundSelf(getPerson(text));
        }
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = (char) (words[i].charAt(0) - diff) + words[i].substring(1);
        }
        if (words.length == 1) {
            currentPerson.setName(words[0]);
//            System.out.println("1");
        } else if (words.length == 2) {
            currentPerson.setName(words[0]);
            currentPerson.setLastName(words[1]);
//            System.out.println("2");
        } else if (words.length == 3) {
            currentPerson.setName(words[0]);
            currentPerson.setMidName(words[1]);
            currentPerson.setLastName(words[2]);
//            System.out.println("3");
        } else {
            System.out.println("I am Confused");
        }

        if (personExists(text)) {
            foundSelf(getPerson(text));
            personIsNew = false;
        } else {
            personIsNew = true;
            createNewPerson(text);
        }
    }

    String cleanse(String string) { //makes strings all lower case, gets rid of punctuation (does not affect spaces)
        string = string.toLowerCase();
        string = string.replaceAll("[^a-z ]", "");
        return string;
    }

    File getPerson(String name) { //load the file of the person if s/he exists.
        name = cleanse(name);
        for (File next : mr.files) {
            if (next != null) {
                if (!next.isHidden() && next.getName().equals(name)) {
                    System.out.println("OKAY");

                    return next;
                }
            }

        }
        System.out.println("SHIT!!!!");
        return null;
    }

    boolean personExists(String name) { //does the person exist in the file "users"?
        boolean returnMe = false;
        name = cleanse(name);

        for (File next : mr.files) {

            if (next != null) {

                System.out.println("~" + next.getName());
                if (!next.isHidden() && next.getName().equals(name)) {
                    returnMe = true;
                }
            }
        }
        System.out.println("personExists = " + returnMe);
        return returnMe;

    }

    private void foundSelf(File file) {
        MyReader self = new MyReader(file);
        currentPerson = new Person(self);
        c.say("Oh, you again. You like " + currentPerson.getLikes() + ", if I remember correctly.");
    }

    private void foundFriend(File next) { //spit out some relevent info about the person, or ask more questions about the person to fill in variables.

        MyReader friendFile = new MyReader(next);
        friend = new Person(friendFile);
        if (friend.getName().equals(currentPerson.getName())) {
            c.say("I guess it's nice that you're your own friend... but don't you have any other friends?");
            lastAskedQuestion = GLOBALS.QFRIEND;
            return;
        }
        c.say("Oh hey, I know him! That's the one that likes " + friend.getLikes() + ", right?");
        lastAskedQuestion = GLOBALS.NONE;

    }

    private void tryToAnswer(String text) { //uses the first word of a question sentence to determine a generic answer.
        text = text.toLowerCase();
        text = text.replaceAll("'", "");
        text = text.replaceAll("[^a-z ]", " ");
        text = " " + text + " ";
        
        if(text.contains("your") && text.contains("name")){
            c.say("My name is " + gatsbi.getName());
            return;
        }
        
        for (String next : responses.keySet()) {
            if (text.contains(" " + next + " ")) {
                String[] choices = responses.get(next);
                c.say(choices[(int) (Math.random() * choices.length)]);
                return;
            }
        }
        String[] choices = responses.get("NOKEYFOUND");
        c.say(choices[(int) (Math.random() * choices.length)]);
    }

    private void loadResponses() {
        MyReader rr = new MyReader("responses");
        String nextKey;
        String[] nextResponses;
        while (rr.hasMoreData()) {
            String input = rr.giveMeTheNextLine();
            nextKey = input.substring(1);
            int ln = Integer.parseInt(rr.giveMeTheNextLine());
            nextResponses = new String[ln];
            for (int i = 0; i < ln; i++) {
                nextResponses[i] = rr.giveMeTheNextLine();
            }
            rr.giveMeTheNextLine();
            String[] allKeys = nextKey.split(";");
            for (int i = 0; i < allKeys.length; i++) {
                responses.put(allKeys[i], nextResponses);
            }
        }
    }

    private void createNewPerson(String name) { //create a new person with name
        mw = new MyWriter(name);
        currentPerson = new Person();
        currentPerson.setName(name);
        //            mw.close();
        System.out.println("hello");

    }

    private void askMidName() {

        c.say("What's your middle name?");
        lastAskedQuestion = GLOBALS.QMIDNAME;
    }

    private void askLastName() {

        c.say("What's your last name?");
        lastAskedQuestion = GLOBALS.QLASTNAME;
    }

    private void askGender() {

        c.say("Are you male of female?");
        lastAskedQuestion = GLOBALS.QGENDER;
    }

    private void askOccupation() {

        c.say("What's your occupation?");
        lastAskedQuestion = GLOBALS.QOCCUPATION;
    }

    private void askHometown() {

        c.say("What's your hometown?");
        lastAskedQuestion = GLOBALS.QHOMETOWN;
    }

    private void askMajor() {

        c.say("What's your major?");
        lastAskedQuestion = GLOBALS.QMAJOR;
    }

    private void askAge() {

        c.say("What's your age?");
        lastAskedQuestion = GLOBALS.QAGE;
    }

    private void askLikes() {

        c.say("What do you like?");
        lastAskedQuestion = GLOBALS.QLIKES;
    }

    private void printPerson() {
mw.println(currentPerson.getName());
mw.println(currentPerson.getMidName());
mw.println(currentPerson.getLastName());
mw.println(""+(currentPerson.getGenderM()?1:0));
mw.println(""+currentPerson.getOccupation());
mw.println(currentPerson.getHometown());
mw.println(""+currentPerson.getMajor());
mw.println(""+currentPerson.getAge());
mw.println(currentPerson.getLikes());
mw.close();
    
    }
}
