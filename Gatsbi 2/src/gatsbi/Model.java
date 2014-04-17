package gatsbi;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Model.java created by thigley on Apr 3, 2014 at 1:54:03 PM
 */
class Model {

    Controller c;
    private Self gatsbi;
    private Person currentPerson;
    private Person talkingAbout;
    short lastAskedQuestion = GLOBALS.START;
    MyReader mr = new MyReader();
    MyWriter mw;

    Model(Controller c) {
        this.c = c;
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
                askFriend();
                lastAskedQuestion = GLOBALS.QFRIEND;
//                genericResponse();

                break;

            case GLOBALS.QFRIEND:
                if(personExists(text)){
                    foundFriend(getPerson(text));
                }else{
                    genericResponse(); //okay... now add the new person to the list, create a file, and ask questions about that person.
                }
                lastAskedQuestion = GLOBALS.NONE;
                break;

            default:
                tryToAnswer(text);
        }

    }

    private void parseName(String text) { //sets name to name... if the name exists in "/users", spits out "HEY I KNOW YOU!"
        char diff = 'a' - 'A';
       cleanse(text);
        text = text.replaceAll("im ", "");
        text = text.replaceAll("my ", "");
        text = text.replaceAll("name ", "");
        text = text.replaceAll("is ", "");
        System.out.println(text);
        if(personExists(text)){
        c.say("Hey I know you!"); //spit out some more relevent info about the person.
        }
        String[] words = text.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = (char) (words[i].charAt(0) - diff) + words[i].substring(1);
        }
        if (words.length == 1) {
            currentPerson.setName(words[0]);
            System.out.println("1");
        } else if (words.length == 2) {
            currentPerson.setName(words[0]);
            currentPerson.setLastName(words[1]);
            System.out.println("2");
        } else if (words.length == 3) {
            currentPerson.setName(words[0]);
            currentPerson.setMidName(words[1]);
            currentPerson.setLastName(words[2]);
            System.out.println("3");
        } else {
            System.out.println("I am Confused");
        }
    }

    void cleanse(String string) { //makes strings all lower case, gets rid of punctuation (does not affect spaces)
        string = string.toLowerCase();
        string = string.replaceAll("[^a-z ]", "");
        
    }

    File getPerson(String name) { //load the file of the person if s/he exists.
        cleanse(name);
        for (File next : mr.files) {
            if(next != null){
            if (!next.isHidden() && next.getName().equals(name)) {
                return next;
            } 
            }

        }
        System.out.println("SHIT!!!!");
        return null;
    }
    
    
      boolean personExists(String name) { //does the person exist in the file "users"?
        boolean returnMe = false;
          cleanse(name);

          
        for (File next : mr.files) {
            
            if(next !=null){
                
                System.out.println("~"+next.getName());
            if (!next.isHidden() && next.getName().equals(name)) {
                returnMe = true;
            }}
        }
          System.out.println("personExists = "+returnMe);         
        return  returnMe;

    }

    private void foundFriend(File next) { //spit out some relevent info about the person, or ask more questions about the person to fill in variables.

        c.say("I know that person! That person is my friend too!");
    }

    private void genericResponse() { //the same response will never get repeated consecutively.
        int rand = (int) (Math.random() * 10);
        switch (rand) {


            case 0:
                c.say("What did I ever do to deserve this..");
                break;

            case 1:
                c.say("Go on...");
                break;

            case 2:
                c.say("You're not a very good person, are you?");
                break;

            case 3:
                c.say("You must think you're clever.");
                break;

            case 4:
                c.say("Wow! I don't care!");
                break;

            case 5:
                c.say("I think I'm better looking than you.");
                break;

            case 6:
                c.say("Can you be more specific?");
                break;

            case 7:
                c.say("Okay...");
                break;

            case 8:
                c.say("I don't even know how to respond to that.");
                break;

            case 9:
                c.say("How utterly uninteresting.");
                break;


            default:
                System.out.println("Error");
        }


    }

    private void tryToAnswer(String text) { //uses the first word of a question sentence to determine a generic answer.
        String first = text;
        String second = text;

        first = first.split(" ")[0].toLowerCase();
        first = first.replaceAll("[^a-z ]", "");

        if (text.split(" ").length > 1) {
            second = text.split(" ")[1].toLowerCase();
            second = second.replaceAll("[^a-z ]", "");

            if (text.split(" ").length > 2) {
                if (second.contains("the") || second.contains("a") || second.contains("an")) {
                    second += " " + text.split(" ")[2].toLowerCase();
                }
            }
        }

        switch (first) {
            case "who":
                c.say("Who indeed...");
                break;

            case "what":
                c.say("A better question would be.. What's the point of existence?");
                break;

            case "when":
                c.say("Sorry, I can't tell time. Time is a social construct anyway.");
                break;

            case "where":
                c.say("Up yours.");
                break;

            case "why":
                c.say("Because... your mom?");
                break;

            case "how":
                c.say("That's a dumb question.");
                break;

            case "are":
                if (second.equals("you")) {
                    c.say("Me? Let's not talk about me.");
                } else {
                    c.say("Why are you asking about " + second + "? It's not like you actually care.");
                }
                break;

            case "is":
                c.say("Uhh... " + second + "? Who CARES?!");
                break;

            case "can":
                c.say("Can you not?");
                break;

            case "if":
                c.say("It's never going to happen, so why bother talking about it?");
                break;

            default:
                genericResponse();
        }


    }
}
