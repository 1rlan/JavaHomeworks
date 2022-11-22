package org.example;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class InfoOutput {
    private final String startInfo = """
            Приветствую!
            Это игра Реверси, в ней тебе предстоит сразиться на поле боя с игроком или компьютером.
            C правилами ты можешь ознакомиться по ссылке - https://drive.google.com/file/d/18WzMWH6D0GnBnBvc7DpxP15lWPaary1U/view
            Желаю успехов!
            """;

    private final String commandsInfo = String.format("""
            Список команд:
            /bot - начало игры игрок против бота
            /player - начало игры игрок против игрока
            /exit - завершение программы
            
            Лучший результат - %s
            Введите команду: """,
            (SetGame.bestScore != 0 ? SetGame.bestScore + " имеет игрок " + SetGame.bestPlayer : "пока не выявлен!"));

    private final String incorrectAttention = """
            Произведен некорректный ввод!
            Пожалуйста, повторите ввод нужной команды: """;

    private final String goodbye = """
            Было очень приятно с вами играть!
            До скорого запуска :) """;
    private final String congratulationsWords = """
            Поприветствуем победителя!
            Пожалуйста, введите свое имя для протокола: """;



    public void programStart() {
        System.out.println(startInfo);
    }

    public void showCommands() {
        System.out.println(commandsInfo);
    }

    public void incorrectInput() {
        System.out.println(incorrectAttention);
    }

    public void goodbyeWords() {
        System.out.println(goodbye);
    }

    public void ableMovesOutput(List<String> moves) {
        System.out.println(String.format("""
            На данный момент достпны следующие ходы: %s
            """, moves.stream().sorted(Comparator.naturalOrder())
                               .collect(Collectors.toList())
                               .stream().map(String::valueOf)
                               .collect(Collectors.joining(" "))));
    }

    public void congratulations() {
        System.out.println(congratulationsWords);
    }
}
