package com.ryanpodell;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.FileVisitResult.*;

public class Main {
    static Path MASTER_SOURCE_ROOT_PATH = Path.of("C:\\Users\\rpode\\OneDrive\\Desktop\\TestFrom");
    static Path MASTER_DESTINATION_ROOT_PATH = Path.of("C:\\Users\\rpode\\OneDrive\\Desktop\\TestTo\\");

    public static void main(String[] args){
        manageDirectory(MASTER_SOURCE_ROOT_PATH);
        System.out.println("\nProgram complete.");
    }

    public static void manageDirectory(Path dir){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                AnalyzeCurrentPath analyze = new AnalyzeCurrentPath();
                analyze.visitFile(file, attr);
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }
    }

    //Need to return and build directories first, then files
    public static class AnalyzeCurrentPath extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
            if (attr.isDirectory()) {
                manageDirectory(file);
                shipDirectoryPath(file);
            } else {
                shipFilePath(file);
            }
            return CONTINUE;
        }
    }

    public static void shipFilePath(Path pathToShip) throws IOException {
        String parsedPath = String.valueOf(pathToShip).substring(41);
        Path targetFilePath = Path.of(MASTER_DESTINATION_ROOT_PATH + "\\" + parsedPath);

        if (!Files.exists(targetFilePath)){
            Files.copy(pathToShip, targetFilePath);
        } else {
            System.out.println("File " + pathToShip.getFileName() + " already exists. Skipping to next file.");
        }
    }

    public static void shipDirectoryPath(Path pathToShip) throws IOException {
        String parsedPath = String.valueOf(pathToShip).substring(41);
        Path dir = Path.of(MASTER_DESTINATION_ROOT_PATH + "\\" + parsedPath);
        Files.createDirectories(dir);
    }
}
