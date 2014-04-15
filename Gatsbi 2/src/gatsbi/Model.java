package gatsbi;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Model.java created by thigley on Apr 3, 2014 at 1:54:03 PM
 */
class Model {

    Controller c;
    private Self gatsbi;
    private Person currentPerson;
    short lastAskedQuestion = GLOBALS.START;

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

    private void getResponse(String text) {
        switch (lastAskedQuestion) {
            case GLOBALS.START:
                c.say("Hello.");
                new Timer().schedule(
                        new TimerTask() {
                    @Override
                    public void run() {
                        askName();
                    }
                }, 1000);

                break;

            case GLOBALS.QNAME:
                parseName(text);
                lastAskedQuestion = GLOBALS.NONE;
                genericResponse();

                break;
            default:
                tryToAnswer(text);
        }

    }

    private void parseName(String text) {
        char diff = 'a' - 'A';
        text = text.toLowerCase();
        text = text.replaceAll("[^a-z ]", "");
        text = text.replaceAll("im ", "");
        text = text.replaceAll("my ", "");
        text = text.replaceAll("name ", "");
        text = text.replaceAll("is ", "");
        System.out.println(text);
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
            System.out.println("Confused");
        }
    }

    void reset() {
        currentPerson = new Person();
    }

    private void genericResponse() {
        int rand = (int) (Math.random() * 10);
        switch (rand) {
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

            case 10:
                c.say("What did I ever do to deserve this..");
                break;

            default:
                System.out.println("Error");
        }


    }
     private void tryToAnswer(String text) {
        String first = text;
        String second = text;

        first = first.split(" ")[0].toLowerCase();
        first = first.replaceAll("[^a-z ]", "");
        
        if (text.split(" ").length > 1){
        second = text.split(" ")[1].toLowerCase();
        second = second.replaceAll("[^a-z ]", "");
        
        if (text.split(" ").length > 2){
        if (second.contains("the")  || second.contains("a") ||second.contains("an")){
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
                if(second.equals("you")){
                    c.say("Me? Let's not talk about me.");
                } else{
                c.say("Why are you asking about " + second +"? It's not like you actually care.");}
                break;
                
            case "is":
                c.say("Uhh... "+second +"? Who CARES?!");
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
