package com.hotmail.bess410.demo.services;

import com.hotmail.bess410.demo.web.model.TaskTwoModel;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskTwoService {
    public String getPathToResult(TaskTwoModel model) {
        URL input = TaskTwoService.class.getClassLoader().getResource(model.getPathToInputFile());

        List<String> strings = new ArrayList<>();
        try {
            if (input == null) {
                throw new FileNotFoundException();
            } else {
                strings = Files.readAllLines(Path.of(input.toURI()));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        StringBuilder stringBuilder = new StringBuilder();
        strings.forEach(string -> stringBuilder.append(handleString(string)).append("\n"));
        Path fileName = Path.of(model.getPathToOutputFile());
        try {
            Files.writeString(fileName, stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(fileName.toString()).getAbsolutePath();
    }

    private static String handleString(String string) {
        // Находим знак
        String[] tokens = string.split("[+\\-/*]");
        if (string.length() == 0) {
            return "Empty line";
        }
        if (tokens.length == 1) {
            return "No sign for calculation";
        }
        if (tokens.length > 2) {
            return "More than one sign for calculation";
        }

        // Получаем первое число
        String firstArg = tokens[0];
        if (!isDigit(firstArg)) {
            return "First argument has wrong format";
        }
        int first = Integer.parseInt(firstArg);
        // Получаем второе число
        String secondArg = tokens[1];
        if (!isDigit(secondArg)) {
            return "Second argument has wrong format";
        }
        int second = Integer.parseInt(secondArg);

        //Получаем знак
        String sign = string.substring(firstArg.length(), firstArg.length() + 1);
        switch (sign) {
            case "*":
                return Integer.toString(first * second);
            case "-":
                return Integer.toString(first - second);
            case "+":
                return Integer.toString(first + second);
            case "/":
                if (second == 0) {
                    return "Divided by zero error";
                }
                return Double.toString((double) first / second);
            default:
                return string;
        }
    }

    private static boolean isDigit(String firstArg) {
        return firstArg.chars().allMatch(Character::isDigit);
    }
}
