package gatsbi;

/**
 * Question.java created by thigley on Apr 22, 2014 at 10:43:13 AM
 */
public class Question implements Comparable<Question>{
    String theQuestion;
    short questionNumber = 0;

    @Override
    public int compareTo(Question o) {
        
        if(o.questionNumber<questionNumber){
            return -1;
        }else if(o.questionNumber==questionNumber){
            return 0;
        }else{
            return 1;
        }
    }
    
    
}
