import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class Assign {
    private final QuestionMapper questionMapper;
    private final Constants constants;
    
    public Assign() {
        questionMapper = new QuestionMapper();
        constants = new Constants();
    }
    public void assignValues()
    {
        for (int i = 0; i < constants.getQuestions().length; i++) {
            //System.out.println(constants.getQuestions()[i]);
            //System.out.println(constants.getAnswers().get(i));
            //System.out.println(constants.getCorrectAnswers()[i]);
            questionMapper.addQuestion(i, new Question(constants.getQuestions()[i], constants.getAnswers().get(i), constants.getCorrectAnswers()[i]));
        }
    }
}