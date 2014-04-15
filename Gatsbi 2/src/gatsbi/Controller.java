package gatsbi;

/**
 * Controller.java created by thigley on Apr 3, 2014 at 1:53:01 PM
 */
class Controller{

    ViewFrame vf;
    Model m;

    Controller() {
        vf = new ViewFrame(this);
        m = new Model(this);
    }

    void parse(String text) {
        m.parse(text);
    }

    String getFirstName() {
        if (m.hasName()) {
            return m.getName();
        } else {
            return "You";
        }
    }

    void say(String output) {
        vf.say(output);
    }

    void reset() {
        m.reset();
    }
}
