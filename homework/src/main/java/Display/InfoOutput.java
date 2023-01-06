package Display;
import Display.Cell;
import Game.GameSet;

import java.util.Set;
import java.util.stream.Collectors;

public class InfoOutput {

    public void ableMovesOutput(Set<Cell> moves) {
        System.out.println(String.format("""
            На данный момент достпны следующие ходы: %s """,
                moves.stream().toList()
                        .stream().map(Cell::positionInfo).sorted()
                        .collect(Collectors.joining(" "))));
        System.out.println("Введите ход в указаном формате:");
    }

    public void noAbleMoves() {
        System.out.println("""
                К сожалению, доступных ходов нет!
                Ходит следующий игрок. """);
    }
    public void congratulations(String winner) {
        System.out.println(String.format("""
            Победил - %s 
            Пожалуйста, введите свое имя для протокола: """, winner));
    }


    public void programStart() {
        String startInfo = """
                Приветствую!
                Это игра Реверси, в ней тебе предстоит сразиться на поле боя с игроком или компьютером.
                C правилами ты можешь ознакомиться по ссылке - https://drive.google.com/file/d/18WzMWH6D0GnBnBvc7DpxP15lWPaary1U/view
                Желаю успехов!
                """;
        System.out.println(startInfo);
    }

    public void showCommands() {
        String commandsInfo = """
                Список команд:
                /bot - начало игры игрок против бота
                /player - начало игры игрок против игрока
                /best - вывод лучшего счета
                /exit - завершение программы
                            
                Введите команду: """;
        System.out.println(commandsInfo);
    }

    public void incorrectInput() {
        String incorrectAttention = """
                Произведен некорректный ввод!
                Пожалуйста, повторите ввод нужной команды: """;
        System.out.println(incorrectAttention);
    }

    public void goodbyeWords() {
        String goodbye = """
                Было очень приятно с вами играть!
                До скорого запуска :) """;
        System.out.println(goodbye);
    }

    public void botCongratulations() {
        String botCongratulationsWords = """
                Поприветствуем победителя!
                К сожалению, победил ботик. """;
        System.out.println(botCongratulationsWords); }

    public void bestPlayer() { System.out.println(String.format("""
            Лучший результат - %s
            """, GameSet.bestScore != 0 ? GameSet.bestScore + " имеет игрок " + GameSet.bestPlayer : "пока не выявлен!"));
    }

    public void botMoveInfo(Cell bestCell) {
        System.out.println(String.format("Ботик сходил на %s", bestCell.positionInfo()));
    }

    public void firstPlayerMoveInfo() {
        System.out.println("Ходит первый игрок (красный цвет):");
    }
    public void secondPlayerMoveInfo() {
        System.out.println("Ходит второй игрок (зеленый цвет):");
    }




}
