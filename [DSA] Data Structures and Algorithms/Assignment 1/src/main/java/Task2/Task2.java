package Task2;

import Set.DoubleHashSet;
import Stack.QueuedBoundedStack;

import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        CommandShellWithoutRollbacks shell = new CommandShellWithoutRollbacks();
        shell.initAndExecuteCommands();
    }
}

/**
 * Command Shell Simulator which support "NEW", "REMOVE" and "LIST" commands
 */
class CommandShellWithoutRollbacks {
    int numberOfCommands;
    DoubleHashSet<String> filesAndDirectories;
    QueuedBoundedStack<String> commands;

    public void initAndExecuteCommands() {
        Scanner input = new Scanner(System.in);

        numberOfCommands = Integer.parseInt(input.nextLine());

        commands = new QueuedBoundedStack<>(numberOfCommands);
        filesAndDirectories = new DoubleHashSet<>(1000003);

        String commandLine;
        for (int i = 0; i < numberOfCommands; i++) {
            commandLine = input.nextLine();
            executeCommand(commandLine);
        }
    }

    private String[] getCommandArguments(String commandLine) {
        return commandLine.split(" ");
    }

    private void executeCommand(String commandLine) {
        String[] command = getCommandArguments(commandLine);
        switch (command[0]) {
            case "NEW":
                commands.push(commandLine);
                executeNewCommand(command[1]);
                break;
            case "REMOVE":
                commands.push(commandLine);
                executeRemoveCommand(command[1]);
                break;
            case "LIST":
                executeListCommand();
                break;
            default:
                break;
        }
    }

    private void executeReversedCommand(String commandLine) {
        String[] command = getCommandArguments(commandLine);

        switch (command[0]) {
            case "NEW":
                executeRemoveCommand(command[1]);
                break;
            case "REMOVE":
                executeNewCommand(command[1]);
                break;
            default:
                break;
        }
    }

    private void printError(String commandLine) {
        System.out.println("ERROR: cannot execute " + commandLine);
    }

    private void executeListCommand() {
        Object[] array = filesAndDirectories.toArray();
        for (Object item : array) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    private void executeNewCommand(String fileOrDirectory) {
        if (filesAndDirectories.contains(fileOrDirectory)) {
            printError("NEW " + fileOrDirectory);
        }
        filesAndDirectories.add(fileOrDirectory);
    }

    private void executeRemoveCommand(String fileOrDirectory) {
        if (!filesAndDirectories.contains(fileOrDirectory)) {
            printError("REMOVE " + fileOrDirectory);
        }
        filesAndDirectories.remove(fileOrDirectory);
    }
}
