import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class quiz {
    private static String filename = "./src/main/resources/users.json";

    public static void main(String[] args) throws IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your Username");
        String username = scanner.nextLine();
        System.out.println("Username : " + username);
        System.out.println("Enter your Password");
        String password = scanner.nextLine();
        System.out.println("Password : " + password);

        JSONParser jsonParser = new JSONParser();
        JSONArray userarry = (JSONArray) jsonParser.parse(new FileReader(filename));
        JSONObject adminobj = (JSONObject) userarry.get(0);
        JSONObject studentobj = (JSONObject) userarry.get(1);


        if (adminobj.get("username").equals(username) && adminobj.get("password").equals(password)) {
            System.out.println("System:> Welcome admin! Please create new questions in the question bank.");
            addquiz();
        } else if (studentobj.get("username").equals(username) && studentobj.get("password").equals(password)) {
            System.out.println("System:> Welcome salman to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.\n");
            takeQuiz();
        } else {
            System.out.println("Invalid user! Please Enter valid credaintial!");
        }
    }

    private  static void addquiz() throws IOException, ParseException {

        while (true) {
            System.out.println("System:> Input your question");
            Scanner scanner = new Scanner(System.in);
        String  question = scanner.nextLine();
            String[] option = new String[4];
            for (int i = 0; i < option.length; i++) {
                System.out.println("System: Input option" + (i + 1) + ":");
                option[i] = scanner.nextLine();
            }
            System.out.println("System: What is the answer key?");
            int answer = scanner.nextInt();

            savequiz(question, option, answer);



            System.out.println("System:> Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
          scanner = new Scanner(System.in);
            String response =  scanner.nextLine();
            if(response.equals("q")){
                break;
            }


        }



    }
     private  static void savequiz(String question, String[] option, int answer) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONArray  quizarry = (JSONArray) jsonParser.parse(new FileReader(filename));

        JSONObject quizobj = new JSONObject();
        quizobj.put("question",question);


         quizobj.put("option",option[0]);
         quizobj.put("option",option [1]);
         quizobj.put("option",option[2]);
         quizobj.put("option",option[3]);

         quizobj.put("answer",answer);

         quizarry.add(quizobj);

         FileWriter fw = new FileWriter(filename);
         fw.write(quizarry.toJSONString());
        fw.flush();
        fw.close();
     }
    private static void takeQuiz() throws IOException, ParseException {
        String Filename= "./src/main/resources/quiz.json";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String startQuiz = scanner.nextLine();
            System.out.println("Student:>"+startQuiz);
            System.out.println("system:>");
            if (startQuiz.equals("s")) {
                break;
            }
        }

        int score = 0;
        int totalQuestions = 0;

        JSONParser jsonParser = new JSONParser();
        JSONArray quizArray = (JSONArray) jsonParser.parse(new FileReader(Filename));

        for (int i = 0; i < 10 && i < quizArray.size(); i++) {

            int randomIndex = (int) (Math.random() * quizArray.size());
            JSONObject question = (JSONObject) quizArray.get(randomIndex);

            System.out.println("[Question " + (totalQuestions + 1) + "] " + question.get("question"));
            for (int j = 1; j <= 4; j++) {
                System.out.println(j + ". " + question.get("option " + j));
            }

            System.out.print("Answer: ");
            int userAnswer;
            userAnswer = scanner.nextInt();

            int correctAnswer = ((Long) question.get("answerKey")).intValue();
            if (userAnswer == correctAnswer) {
                score++;
            }

            totalQuestions++;
        }

        System.out.println("Quiz Completed!");
        System.out.println(" Your Score: " + score + " out of " + totalQuestions);

        if (score >= 8) {
            System.out.println(" Excellent! You have got " + score + " out of " + totalQuestions);
        } else if (score >= 5) {
            System.out.println("Good. You have got " + score + " out of " + totalQuestions);
        } else if (score >= 2) {
            System.out.println("Very poor! You have got " + score + " out of " + totalQuestions);
        } else {
            System.out.println("Very sorry, you are failed. You have got " + score + " out of " + totalQuestions);
        }

    }
}