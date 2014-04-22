package gatsbi;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Model.java created by thigley on Apr 3, 2014 at 1:54:03 PM
 */
class Model {

    Controller c;
    private HashMap<String, String[]> responses = new HashMap<>();
    private HashMap<String, Integer> partOfSpeech = new HashMap<>();
    PriorityQueue<Question> QQ = new PriorityQueue<>();
    // People
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
        loadQQ();
    }

    public void loadQQ() {
        MyReader qr = new MyReader("questions");
        while (qr.hasMoreData()) {
            Question next = new Question();
            next.questionNumber = (short) Integer.parseInt(qr.giveMeTheNextLine());
            next.theQuestion = qr.giveMeTheNextLine();
            qr.giveMeTheNextLine();
            QQ.add(next);
        }
    }
    boolean b = false;

    public void askQuestion() {
        if (!QQ.isEmpty()) {
            Question nextQuestion = QQ.poll();

            lastAskedQuestion = nextQuestion.questionNumber;
            c.say(nextQuestion.theQuestion);
        }
        if (b) {
            printPerson();
        }
        b = true;

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

    private void getResponse(String text) {
        switch (lastAskedQuestion) {
            case GLOBALS.START:
                c.say(responses.get("hello")[(int) (Math.random() * 10)]);
                askQuestion();
                break;

            case GLOBALS.QNAME: //we should ask about a friend during a conversation instead of right after he asks for your name... like "I'm bored of this conversation, let's talk about something else. Do you have any friends?"
                parseName(text);
                if (text.contains("you") || text.contains("your")) {
                    c.say("I'm " + gatsbi.getName() + "!");
                }
                if (personIsNew) {
                    c.say("Oh, I haven't met you before! We should get to know each other!");
                    
                    lastAskedQuestion = GLOBALS.NONE;
                    break;
                } else {
                    c.say("Oh, you again. You like " + currentPerson.getLikes() + ", if I remember correctly.");
                }
                lastAskedQuestion = GLOBALS.QFRIEND;
                break;

            case GLOBALS.QLASTNAME:
                currentPerson.setLastName(parseMidLast(text));
                c.say("Your name is cool, but not as cool as mine.");

                text = cleanse(text);

                if (text.contains("you") || text.contains("your")) {
                    c.say("Mine is " + gatsbi.getLastName() + "!");
                }
                lastAskedQuestion = GLOBALS.NONE;
                break;

            case GLOBALS.QAGE:
                currentPerson.setAge((short) parseAge(text));
                
                text = cleanse(text);

                if (text.contains("you") || text.contains("your")) {
                    c.say("I'm " + gatsbi.getAge() + ".");
                }
                lastAskedQuestion = GLOBALS.NONE;
                break;

            case GLOBALS.QGENDER:
                if (text.contains("woman") || text.contains("female") || text.contains("lady") || text.contains("girl")) {
                    currentPerson.setGender("female");
                    c.say("That's nice. I definitely relate better to females.");
                } else if (text.contains("man") || text.contains("male") || text.contains("guy") || text.contains("boy")) {
                    currentPerson.setGender("male");
                    c.say("That's nice. I definitely relate better to males.");
                }
                text = cleanse(text);

                if (text.contains("you") || text.contains("your")) {
                    c.say("I'm a machine programmed to be male.");
                }
                lastAskedQuestion = GLOBALS.NONE;
                break;

            case GLOBALS.QOCCUPATION:
                currentPerson.setOccupation(parseOccupation(text));
                text = cleanse(text);
                
                if (text.contains("you") || text.contains("your")) {
                    c.say("I'm a machine... Isn't it obvious?");
                }
                lastAskedQuestion = GLOBALS.NONE;
                break;

            case GLOBALS.QHOMETOWN:
                currentPerson.setHometown(parseHometown(text));
                text = cleanse(text);
                c.say("I've never even heard of that place.");
                if (text.contains("you") || text.contains("your")) {
                    c.say("I live in a far off, ditant land called Ford.");
                }
                lastAskedQuestion = GLOBALS.NONE;
                break;

            case GLOBALS.QLIKES:
                currentPerson.setLikes(parseLikes(text));
                text = cleanse(text);
                c.say("How cute.");
                if (text.contains("you") || text.contains("your")) {
                    c.say("Me? I like " + gatsbi.getLikes() + "!");
                }
                lastAskedQuestion = GLOBALS.NONE;
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

    private String parseLikes(String text) {
        String returnMe = text;

        returnMe = cleanse(returnMe);
        returnMe = returnMe.replaceAll("i ", "");
        returnMe = returnMe.replaceAll("am ", "");
        returnMe = returnMe.replaceAll("a ", "");
        returnMe = returnMe.replaceAll("an ", "");
        returnMe = returnMe.replaceAll("like ", "");
        returnMe = returnMe.replaceAll("im ", "");
        returnMe = returnMe.replaceAll("love ", "");
        returnMe = returnMe.replaceAll("really ", "");
        returnMe = returnMe.replaceAll("like ", "");
        returnMe = returnMe.replaceAll("addict ", "");
        returnMe = returnMe.replaceAll("fan ", "");
        returnMe = returnMe.replaceAll("what ", "");
        returnMe = returnMe.replaceAll("whats ", "");
        returnMe = returnMe.replaceAll("yours ", "");
        returnMe = returnMe.replaceAll("your ", "");
        returnMe = returnMe.replaceAll("how ", "");
        returnMe = returnMe.replaceAll("about ", "");
        returnMe = returnMe.replaceAll("you ", "");

        return returnMe;
    }

    private short parseOccupation(String text) {

        if (text.contains("machine")) {
            c.say("Wait... You're a machine too?");
            return 0;
        }
        if (text.contains("student") || text.contains("university")) {
            c.say("Hey, my creators were students too!");
            return 1;
        }
        if (text.contains("teach")) {
            c.say("That's good... Teachers play a very important role for the future.");
            return 2;
        }
        if (text.contains("parent") || text.contains("mother") || text.contains("father")) {
            c.say("I wish I had parents...");
            return 3;
        }
        if (text.contains("unemployed") || text.contains("dont") || text.contains("not")) {
            c.say("What are you, lazy?");

            return 5;
        }
        if (text.contains("wits") || text.contains("computer")) {
            c.say("You must be a genius, then..");

            return 6;
        }
        if (text.contains("secretary") || text.contains("assistant")) {
            c.say("You must enjoy helping people.");

            return 7;
        }
        if (text.contains("president") || text.contains("head") || text.contains("lead")) {
            c.say("Wow! It must be cool being a leader.");
            return 8;
        }
        c.say("Well, that sounds boring. I think I have it better than you.");
        return 4;
    }

    private String parseHometown(String text) {
        String returnMe = text;
        returnMe = cleanse(returnMe);
        returnMe = returnMe.replaceAll("i ", "");
        returnMe = returnMe.replaceAll("im ", "");
        returnMe = returnMe.replaceAll("from ", "");
        returnMe = returnMe.replaceAll("live", "");
        returnMe = returnMe.replaceAll("in ", "");
        returnMe = returnMe.replaceAll("my ", "");
        returnMe = returnMe.replaceAll("hometown", "");
        returnMe = returnMe.replaceAll("called ", "");
        returnMe = returnMe.replaceAll("it ", "");
        returnMe = returnMe.replaceAll("its ", "");
        returnMe = returnMe.replaceAll("is ", "");
        returnMe = returnMe.replaceAll("what ", "");
        returnMe = returnMe.replaceAll("whats ", "");
        returnMe = returnMe.replaceAll("yours ", "");
        returnMe = returnMe.replaceAll("your ", "");
        returnMe = returnMe.replaceAll("how ", "");
        returnMe = returnMe.replaceAll("about ", "");
        returnMe = returnMe.replaceAll("you ", "");

        return returnMe;
    }

    int parseAge(String text) { //find the number in text
        int returnMe = 0;
        String cleanText = text;

        cleanText = cleanText.replaceAll("[^0-9]", "");
        if (cleanText.isEmpty()) {
            c.say("Yeah, but how old are you?");
            lastAskedQuestion = GLOBALS.QAGE;
            return 0;

        }
        returnMe = Integer.parseInt(cleanText);
        c.say("Wow, you don't look a day older than " + (currentPerson.getAge() - 1) + "!");
        return returnMe;
    }

    private String parseMidLast(String text) {
        String returnMe = text;
        returnMe = cleanse(returnMe);
        returnMe = returnMe.replaceAll("middle ", "");
        returnMe = returnMe.replaceAll("last ", "");
        returnMe = returnMe.replaceAll("my ", "");
        returnMe = returnMe.replaceAll("names ", "");
        returnMe = returnMe.replaceAll("name", "");

        returnMe = returnMe.replaceAll("it ", "");
        returnMe = returnMe.replaceAll("its ", "");
        returnMe = returnMe.replaceAll("is ", "");
        returnMe = returnMe.replaceAll("whats ", "");

        returnMe = returnMe.replaceAll("what ", "");
        returnMe = returnMe.replaceAll("yours", "");
        returnMe = returnMe.replaceAll("your ", "");
        returnMe = returnMe.replaceAll("how ", "");
        returnMe = returnMe.replaceAll("about ", "");
        returnMe = returnMe.replaceAll("you", "");

        return returnMe;
    }

    private void parseName(String text) { //sets name to name... if the name exists in "users", spits out "HEY I KNOW YOU!"
        char diff = 'a' - 'A';
        text = cleanse(text);
        text = text.replaceAll("im ", "");
        text = text.replaceAll("my ", "");
        text = text.replaceAll("name ", "");
        text = text.replaceAll("is ", "");
        text = text.replaceAll("whats ", "");

        text = text.replaceAll("what ", "");
        text = text.replaceAll("yours", "");

        text = text.replaceAll("your ", "");
        text = text.replaceAll("how ", "");
        text = text.replaceAll("about ", "");
        text = text.replaceAll("you", "");
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
            currentPerson.setLastName(words[2]);
        } else {
            c.say("Could you be more clear? What's your name?");
            return;
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

    private void foundSelf(File file) { //now ask questions about the current user. eg "How are your CS Classes going?"
        MyReader self = new MyReader(file);
        currentPerson = new Person(self);
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
        lastAskedQuestion = GLOBALS.NONE; //<- GLOBALS.FRIENDTALK

    }

    private void tryToAnswer(String text) { //uses the first word of a question sentence to determine a generic answer.
        text = text.toLowerCase();
        text = text.replaceAll("'", "");
        text = text.replaceAll("[^a-z ]", " ");
        text = " " + text + " ";

        if (tryToUnderstand(text)) {
            return;
        }

        if ((text.contains("your") || text.contains("you")) && text.contains("name")) {
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

        if (!QQ.isEmpty()) {
            askQuestion();
            return;}
        
            String[] choices = responses.get("NOKEYFOUND");
            c.say(choices[(int) (Math.random() * choices.length)]);
        
    }

    private boolean tryToUnderstand(String text) {
        boolean returnMe = false;
        String newText = text.substring(1, text.length() - 1);
        String[] scentence = newText.split(" ");
        ArrayList<String> pos = new ArrayList<String>();
        for (String string : scentence) {
            if (partOfSpeech.containsKey(string)) {
                returnMe = true;
                pos.add("" + partOfSpeech.get(string));
            } else {
                pos.add("" + 0);
            }
        }

        return returnMe;
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

        MyReader theOtherReader = new MyReader("PartsOfSpeech");
        ArrayList<String> keys = new ArrayList<String>();
        int value = 0;
        while (theOtherReader.hasMoreData()) {
            String input = theOtherReader.giveMeTheNextLine();
            if (!input.contains("1")) {
                keys.add(input);
                continue;
            }
            value = Integer.parseInt(theOtherReader.giveMeTheNextLine());
            for (String string : keys) {
                partOfSpeech.put(string, value);
            }
            keys.clear();
        }
        System.out.println(partOfSpeech);

    }

    private void createNewPerson(String name) { //create a new person with name
        mw = new MyWriter(name);
        System.out.println("MW Initialized.");
        currentPerson = new Person();
        currentPerson.setName(name);
        //            mw.close();
        System.out.println("hello");

    }

    private void printPerson() {
//        for (int i = 0; i < 8; i++) {
//            mw.println(""+currentPerson.getNext());
//        }
        if (mw != null){

        if (currentPerson.getName().isEmpty()) {
            mw.println("-");
        } else {
            mw.println(currentPerson.getName());
        }

        if (currentPerson.getLastName().isEmpty()) {
            mw.println("-");
        } else {
            mw.println(currentPerson.getLastName());
        }
        if (currentPerson.getGender().isEmpty()) {
            mw.println("-");
        } else {
            mw.println(currentPerson.getGender());
        }

        if (currentPerson.getOccupation() == GLOBALS.NULL) {
            mw.println("-");
        } else {
            mw.println("" + currentPerson.getOccupation());
        }
        if (currentPerson.getHometown().isEmpty()) {
            mw.println("-");
        } else {
            mw.println(currentPerson.getHometown());
        }

        if (currentPerson.getAge() == 0) {
            mw.println("-");
        } else {
            mw.println("" + currentPerson.getAge());
        }
        if (currentPerson.getLikes().isEmpty()) {
            mw.println("-");
        } else {
            mw.println(currentPerson.getLikes());
        }

        mw.close();

    }}

}
