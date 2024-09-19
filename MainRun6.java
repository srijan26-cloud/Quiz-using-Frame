import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer; 
import java.util.TimerTask;

public class MainRun6 extends Frame implements ActionListener, ItemListener {
    private Frame fr;
    private Label questionLabel;
    private Label timerLabel;
    private Button nextButton;
    private Button backButton;
    private Button submitButton;
    private int currentQuestionIndex;
    private Map<Integer, Question> questionMap;
    private Timer timer;
    private int secondsRemaining;
    private CheckboxGroup cbg;
    private Checkbox[] cb = new Checkbox[4];
    private HashMap<Integer, int[]> answers; // HashMap to store questionId -> [selectedAnswerIndex, correctAnswerIndex]
    private final int PASSING_PERCENTAGE = 50;
    private final Assign assign;
    //private final QuestionMapper questionMapper;
    public MainRun6() {
        fr = new Frame();
        fr.setTitle("Quiz Game");
        fr.setSize(700, 700);
        fr.setLayout(null);
        
        currentQuestionIndex = 0;
        
        assign = new Assign();
        assign.assignValues();
        //questionMapper = new QuestionMapper();
        questionMap = QuestionMapper.getQuestions();
        //System.out.println(questionMap);
        //questionMapper.printQuestions();
        
        questionLabel = new Label();
        questionLabel.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 16)); // Bold and Italic
        questionLabel.setBounds(50, 200, 600, 30); // Adjusted position
        fr.add(questionLabel);
        
        timerLabel = new Label("Time Remaining: 10");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Bold
        timerLabel.setBounds(50, 50, 200, 30);
        timerLabel.setForeground(Color.RED); // Set timer label color to red
        fr.add(timerLabel);
        
        cbg = new CheckboxGroup();
        
        nextButton = new Button("Next");
        nextButton.setFont(new Font("Arial", Font.PLAIN, 14));
        nextButton.addActionListener(this);
        nextButton.setBounds(550, 550, 80, 30);
        fr.add(nextButton);
        
        backButton = new Button("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(this);
        backButton.setBounds(450, 550, 80, 30);
        backButton.setEnabled(false); // Initially disable the Back button
        fr.add(backButton);
        
        submitButton = new Button("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        submitButton.addActionListener(this);
        submitButton.setBounds(550, 550, 80, 30);
        submitButton.setVisible(false); // Initially hide the Submit button
        fr.add(submitButton);
        
        secondsRemaining = 120; // Start countdown from 10 seconds
        startTimer();
        
        answers = new HashMap<>(); // Initialize HashMap
        fr.setVisible(true);
        fr.setResizable(false);
        
        // Display the first question
        displayQuestion(currentQuestionIndex);
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (secondsRemaining > 0) {
                    secondsRemaining--;
                    timerLabel.setText("Time Remaining: " + secondsRemaining);
                } else {
                    timer.cancel();
                    showResults();
                }
            }
        }, 0, 1000);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            saveSelectedAnswer();
            currentQuestionIndex++;
            updateQuestionDisplay();
        } else if (e.getSource() == backButton) {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                updateQuestionDisplay();
            }
        } else if (e.getSource() == submitButton) {
            saveSelectedAnswer();
            timer.cancel();
            showResults();
        }
    }

    private void saveSelectedAnswer() {
        // Get selected answer index
        int selectedAnswerIndex = getSelectedAnswerIndex();
        int correctAnswerIndex = questionMap.get(currentQuestionIndex).getCorrectAnswerIndex();

        // Store questionId, selected answer, and correct answer in the HashMap
        answers.put(currentQuestionIndex, new int[]{selectedAnswerIndex, correctAnswerIndex});
    }

    private void updateQuestionDisplay() {
        // Show the current question
        displayQuestion(currentQuestionIndex);
        
        // Clear selection in the CheckboxGroup
        cbg.setSelectedCheckbox(null);
        
        // Hide or show buttons as necessary
        if (currentQuestionIndex == questionMap.size() - 1) {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            submitButton.setVisible(false);
        }
        
        backButton.setEnabled(currentQuestionIndex > 0); // Enable back button if not on the first question
    }

    private int getSelectedAnswerIndex() {
        Checkbox selectedCheckbox = cbg.getSelectedCheckbox();
        for (int i = 0; i < cb.length; i++) {
            if (cb[i] == selectedCheckbox) {
                return i; // Return the index of the selected checkbox
            }
        }
        return -1; // Return -1 if no checkbox is selected
    }

    private void displayQuestion(int questionId) {
        Question question = questionMap.get(questionId);
        questionLabel.setText(question.getQuestion());

        for (int i = 0; i < question.getOptions().size(); i++) {
            if (cb[i] == null) {
                cb[i] = new Checkbox(question.getOptions().get(i), false, cbg);
                cb[i].setFont(new Font("Arial", Font.PLAIN, 14));
                cb[i].setBounds(100, 300 + (i * 40), 600, 30); // Align options with question
                fr.add(cb[i]);
                cb[i].addItemListener(this);
            } else {
                cb[i].setLabel(question.getOptions().get(i));
                // Restore previous selection
                cb[i].setState(answers.getOrDefault(questionId, new int[]{-1, -1})[0] == i);
            }
        }
        
        fr.repaint();
    }

    private void showResults() {
        fr.removeAll();
        fr.repaint();
        
        Label resultLabel = new Label("Quiz Completed!");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setBounds(50, 200, 300, 30);
        fr.add(resultLabel);
        
        int totalQuestions = questionMap.size();
        int correctAnswers = 0;

        // Display results
        for (Map.Entry<Integer, int[]> entry : answers.entrySet()) {
            int questionId = entry.getKey();
            int[] result = entry.getValue();
            int selectedAnswer = result[0];
            int correctAnswer = result[1];

            if (selectedAnswer == correctAnswer) {
                correctAnswers++;
            }

            Label answerLabel = new Label("Question " + questionId +
                    ": Selected = " + selectedAnswer + 
                    ", Correct = " + correctAnswer);
            answerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            answerLabel.setBounds(50, 250 + (questionId * 30), 400, 30);
            fr.add(answerLabel);
        }

        // Calculate percentage
        int percentage = (correctAnswers * 100) / totalQuestions;
        Label percentageLabel = new Label("Your Score: " + percentage + "%");
        percentageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        percentageLabel.setBounds(50, 250 + (totalQuestions * 30), 400, 30);
        fr.add(percentageLabel);

        // Pass/Fail Status
        String passFailStatus = percentage >= PASSING_PERCENTAGE ? "Passed" : "Failed";
        Label statusLabel = new Label("Status: " + passFailStatus);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBounds(50, 300 + (totalQuestions * 30), 400, 30);
        fr.add(statusLabel);

        Button exitButton = new Button("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setBounds(550, 550, 80, 30);
        exitButton.addActionListener(e -> System.exit(0));
        fr.add(exitButton);
        
        fr.repaint();
    }

    public void itemStateChanged(ItemEvent ie) {
        // Handle checkbox state changes here if needed
    }

    public static void main(String args[]) {
        
        new MainRun6();
    }
}
