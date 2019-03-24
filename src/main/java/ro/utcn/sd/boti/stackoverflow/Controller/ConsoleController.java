package ro.utcn.sd.boti.stackoverflow.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.entity.Tag;
import ro.utcn.sd.boti.stackoverflow.entity.User;
import ro.utcn.sd.boti.stackoverflow.exception.QuestionNotFoundException;
import ro.utcn.sd.boti.stackoverflow.service.StackoverflowService;

import java.util.Scanner;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ConsoleController implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final StackoverflowService stackoverflowService;
    private User user;

    @Override
    public void run(String... args) {
        print("Welcome to stackoverflow.");
        scanner.useDelimiter(Pattern.compile("[\\r\\n;]+"));

        boolean loggedIn = false;
        while (!loggedIn) {
            print("Enter username: ");
            String username = scanner.next().trim();
            print("Enter password: ");
            String password = scanner.next().trim();
            user = stackoverflowService.findByUserName(username).orElse(null);
            if (user == null){
                print("Username not found!");
                continue;
            }
            if (password.equals(user.getPassword())){
                print("You are logged in!");
                loggedIn = true;
            }
            else{
                print("Wrong password!");
            }
        }

        boolean done = false;
        while (!done) {
            print("Enter a command: ");
            String command = scanner.next().trim();
            try {
                done = handleCommand(command);
            } catch (QuestionNotFoundException questionNotFoundException) {
                print("The question with the given ID was not found!");
            }
        }
    }

    private void print(String value) {
        System.out.println(value);
    }

    private boolean handleCommand(String command) {
        switch (command) {
            case "listquestions":
                listQuestions();
                return false;
            case "listtags":
                listTags();
                return false;
            case "addquestion":
                addQuestion();
                return false;
            case "removequestion":
                removeQuestion();
                return false;
            case "searchbytag":
                searchByTag();
                return  false;
            case "searchbytext":
                searchByText();
                return  false;
            case "exit":
                return true;
            default:
                print("Unknown command. Try again.");
                return false;
        }
    }

    private void listQuestions() {
        for (Question q : stackoverflowService.listQuestions()) { print(q.toString()); }
    }
    private void listTags() {for (Tag t : stackoverflowService.listTags()) { print(t.toString()); } }

    private void addQuestion() {
        print("Question title:");
        String title = scanner.next().trim();
        print("Question text:");
        String text = scanner.next().trim() ;
        print("Add tags:");
        String input = scanner.next().trim() ;
        String[] arrayOfTags = input.split("\\s+");
        Question question = stackoverflowService.addQuestion(user, title, text, arrayOfTags);
        print("Created question: " + question + ".");
    }

    private void removeQuestion() {
        print("Question ID:");
        int id = scanner.nextInt();
        stackoverflowService.removeQuestion(id);
    }

    private void searchByTag(){
        print("Tag ID:");
        int id = scanner.nextInt();
        for (Question q : stackoverflowService.searchByTag(id)) { print(q.toString()); }
    }

    private void searchByText(){
        print("Text:");
        String s = scanner.next().trim();
        if (!s.isEmpty()) {
            for (Question q : stackoverflowService.searchInTitle(s)) { print(q.toString()); }
        }
    }
}
