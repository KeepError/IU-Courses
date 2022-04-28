package Task1;

import Queue.ArrayCircularBoundedQueue;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        BoundedCommandsQueue shell = new BoundedCommandsQueue();
        shell.initCommands();
        shell.printCommands();
    }
}

/**
 * Command Shell Simulator which takes a list of commands and prints last K commands
 */
class BoundedCommandsQueue {
    int numberOfCommands, numberOfOutputCommands;
    ArrayCircularBoundedQueue<String> commands;

    public void initCommands() {
        Scanner input = new Scanner(System.in);

        String[] numbers = input.nextLine().split(" ");
        numberOfCommands = Integer.parseInt(numbers[0]);
        numberOfOutputCommands = Integer.parseInt(numbers[1]);

        commands = new ArrayCircularBoundedQueue<>(numberOfOutputCommands);

        String command;
        for (int i = 0; i < numberOfCommands; i++) {
            command = input.nextLine();
            processCommand(command);
        }
    }

    public void printCommands() {
        String command;
        while (!commands.isEmpty()) {
            command = commands.poll();
            System.out.println(command);
        }
    }

    private void processCommand(String command) {
        commands.offer(command);
    }
}
