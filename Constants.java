import java.util.*;
public class Constants
{
    
        String[] questions = {
            //"What is the capital of India?",
            "Who painted the Mona Lisa?",
            "What is the largest planet in our solar system?",
            "What is the square root of 64?",
            "What is the chemical symbol for gold?",
            "Who wrote 'Romeo and Juliet'?",
            "What is the currency of Japan?",
            "Which planet is known as the Red Planet?",
            "What is the smallest prime number?",
            "What is the main ingredient in guacamole?"
        };
        public String[] getQuestions()
        {   
            return questions;
        }
        ArrayList<ArrayList<String>> optionsList = new ArrayList<>(Arrays.asList(
            //new ArrayList<>(Arrays.asList("Mumbai", "Delhi", "Kolkata", "Chennai")),
            new ArrayList<>(Arrays.asList("Leonardo da Vinci", "Michelangelo", "Raphael", "Rembrandt")),
            new ArrayList<>(Arrays.asList("Earth", "Mars", "Jupiter", "Saturn")),
            new ArrayList<>(Arrays.asList("6", "8", "7", "9")),
            new ArrayList<>(Arrays.asList("Au", "Ag", "Pb", "Fe")),
            new ArrayList<>(Arrays.asList("Shakespeare", "Hemingway", "Twain", "Orwell")),
            new ArrayList<>(Arrays.asList("Yen", "Dollar", "Euro", "Rupee")),
            new ArrayList<>(Arrays.asList("Earth", "Mars", "Venus", "Jupiter")),
            new ArrayList<>(Arrays.asList("0", "1", "2", "3")),
            new ArrayList<>(Arrays.asList("Tomato", "Avocado", "Onion", "Pepper"))
        ));
        public ArrayList<ArrayList<String>> getAnswers()
        {   
            return optionsList;
        }
        // Define correct answers
        int[] correctAnswers = {0, 2, 1, 0, 0, 0, 1, 1, 1};
        public int[] getCorrectAnswers()
        {   
            return correctAnswers;
        }
}
