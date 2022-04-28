package Task3;

import Set.DoubleHashSet;
import Stack.QueuedBoundedStack;

import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        CommandShellWithRollbacks shell = new CommandShellWithRollbacks();
        shell.initAndExecuteCommands();
    }
}

/**
 * Command Shell Simulator which support "NEW", "REMOVE", "LIST" and "UNDO" commands
 */
class CommandShellWithRollbacks {
    int numberOfCommands, numberOfFilesOrDirectories;
    DoubleHashSet<Object> filesAndDirectories;
    QueuedBoundedStack<Object[]> states;

    public void initAndExecuteCommands() {
        Scanner input = new Scanner(System.in);

        String[] numbers = input.nextLine().split(" ");
        numberOfCommands = Integer.parseInt(numbers[0]);
        numberOfFilesOrDirectories = Integer.parseInt(numbers[1]);

        states = new QueuedBoundedStack<>(numberOfFilesOrDirectories);
        filesAndDirectories = new DoubleHashSet<>(50021);

        Object[] emptyState = {" "};
        states.push(emptyState);

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
                if (errorCheckNewCommand(command[1], commandLine)) {
                    states.push(filesAndDirectories.toArray());
                    executeNewCommand(command[1]);
                }
                break;
            case "REMOVE":
                if (errorCheckRemoveCommand(command[1], commandLine)) {
                    states.push(filesAndDirectories.toArray());
                    executeRemoveCommand(command[1]);
                }
                break;
            case "LIST":
                executeListCommand();
                break;
            case "UNDO":
                int undoCount = command.length > 1 ? Integer.parseInt(command[1]) : 1;
                if (errorCheckUndoCommand(undoCount, commandLine)) {
                    executeUndoCommand(undoCount);
                }
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

    private boolean errorCheckNewCommand(String fileOrDirectory, String commandLine) {
        String fileName = fileOrDirectory;
        String directoryName = fileOrDirectory;

        if (fileOrDirectory.contains("/")) {
            fileName = fileOrDirectory.substring(0, fileOrDirectory.length() - 1);
        } else {
            directoryName = fileOrDirectory + "/";
        }

        if (filesAndDirectories.contains(fileName) || filesAndDirectories.contains(directoryName)) {
            printError(commandLine);
            return false;
        }
        return true;
    }

    private void executeNewCommand(String fileOrDirectory) {
        filesAndDirectories.add(fileOrDirectory);
    }

    private boolean errorCheckRemoveCommand(String fileOrDirectory, String commandLine) {
        if (!filesAndDirectories.contains(fileOrDirectory)) {
            printError("REMOVE " + fileOrDirectory);
            return false;
        }
        return true;
    }

    private void executeRemoveCommand(String fileOrDirectory) {
        filesAndDirectories.remove(fileOrDirectory);
    }

    private boolean errorCheckUndoCommand(int undoCount, String commandLine) {
        if (undoCount >= states.size()) {
            printError(commandLine);
            return false;
        }
        return true;
    }

    private void executeUndoCommand(int commandsCount) {
        Object[] lastState = {};
        for (int i = 0; i < commandsCount; i++) {
            lastState = states.pop();
        }
        filesAndDirectories.removeAllItems();
        filesAndDirectories.addItems(lastState);
    }
}
