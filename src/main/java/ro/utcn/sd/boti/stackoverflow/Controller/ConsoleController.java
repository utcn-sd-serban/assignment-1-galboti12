package ro.utcn.sd.boti.stackoverflow.Controller;

import javassist.bytecode.ExceptionsAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.exception.QuestionNotFoundException;
import ro.utcn.sd.boti.stackoverflow.service.QuestionManagementService;

import java.util.Scanner;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class ConsoleController implements CommandLineRunner {
    private final Scanner scanner = new Scanner(System.in);
    private final QuestionManagementService questionManagementService;

    @Override
    public void run(String... args) {
        print("Welcome to stackoverflow.");
        scanner.useDelimiter(Pattern.compile("[\\r\\n;]+"));
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

    private boolean handleCommand(String command) {
        switch (command) {
            case "list":
                handleList();
                return false;
            case "add":
                handleAdd();
                return false;
            case "update-text":
                handleUpdateText();
                return false;
            case "upadte-title":
                handleUpdateTitle();
                return false;
            case "remove":
                handleRemove();
                return false;
            case "exit":
                return true;
            default:
                print("Unknown command. Try again.");
                return false;
        }
    }

    private void handleList() {
        questionManagementService.listQuestions().forEach(s -> print(s.toString()));
    }

    private void handleAdd() {
        print("Title:");
        String title = scanner.next().trim();
        print("Text:");
        String text = scanner.next().trim() ;
        Question question = questionManagementService.addQuestion(title, text);
        print("Created quesion: " + question + ".");
    }

    private void handleUpdateTitle() {
        print("Question ID:");
        int id = scanner.nextInt();
        print("Title:");
        String title = scanner.next().trim();
        questionManagementService.updateTitle(id, title);
    }

    private void handleUpdateText() {
        print("Question ID:");
        int id = scanner.nextInt();
        print("Text:");
        String text = scanner.next().trim();
        questionManagementService.updateTitle(id, text);
    }

    private void handleRemove() {
        print("Question ID:");
        int id = scanner.nextInt();
        questionManagementService.removeQuestion(id);
    }

    private void print(String value) {
        System.out.println(value);
    }
}
