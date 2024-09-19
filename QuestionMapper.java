import java.util.HashMap;
import java.util.Map;

public class QuestionMapper
 {
    private static Map<Integer, Question> questionMap;

    public QuestionMapper() {
        this.questionMap = new HashMap<>();
    }

    public void addQuestion(int index, Question question) {
        this.questionMap.put(index, question);
    }

    public static Map<Integer, Question> getQuestions() {
        return questionMap;
    }
    public void printQuestions() {
        //Scanner scanner = new Scanner(System.in);
        System.out.println("Running");
        for (Map.Entry<Integer, Question> entry : this.getQuestions().entrySet()) {
            int index = entry.getKey();
            Question question = entry.getValue();

            System.out.println((index + 1) + ". " + question.getQuestion());
            for (int i = 0; i < question.getOptions().size(); i++) {
                System.out.println("   " + i + ". " + question.getOptions().get(i));
            }
            
        }
    }
}