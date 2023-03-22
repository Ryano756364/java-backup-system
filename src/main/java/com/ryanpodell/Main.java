package com.ryanpodell;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.FileVisitResult.*;

public class Main {
    static Path MASTER_SOURCE_ROOT_PATH = Path.of("C:\\Users\\rpode\\OneDrive\\Desktop\\TestFrom");  //41
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

    public static class AnalyzeCurrentPath extends SimpleFileVisitor<Path> {
        // Print information about each type of file.
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attr) throws IOException {
            //System.out.println(file);
            if (attr.isDirectory()) {
                //System.out.println("Directory");
                //System.out.println(String.valueOf(file).substring(41));
                manageDirectory(file);
                shipDirectoryPath(file);
            } else {
                //System.out.println("Not Directory");
                //shipFilePath(file);
            }
            System.out.println("");
            return CONTINUE;
        }
    }

    public static void shipFilePath(Path pathToShip) throws IOException {

        String targetString = String.valueOf(MASTER_DESTINATION_ROOT_PATH) + String.valueOf(pathToShip).substring(41);
        Path targetFilePath = Path.of(targetString);
        if (!Files.exists(targetFilePath)){
            Files.copy(pathToShip, targetFilePath);
        } else {
            //Future releases of this software to allow for option to copy file or skip
            //Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
            //System.out.println("Replaced " + fileName + " with an updated file");
            System.out.println("File " + pathToShip.getFileName() + " already exists. Skipping to next file.");
        }
    }

    public static void shipDirectoryPath(Path pathToShip) throws IOException {
        String parsedPath = String.valueOf(pathToShip).substring(41);
        Path dir = Path.of(MASTER_DESTINATION_ROOT_PATH + "\\" + parsedPath);
        Files.createDirectories(dir);
    }
}

/*
        //COPY
        ArrayList<Object> filesToCopy = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathToShip)) {
            for(Path file: stream){
                filesToCopy.add(file);
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }

        //PASTE
        String fileName = "";
        for(Object file : filesToCopy){
            fileName = file.toString().substring(file.toString().lastIndexOf('\\') + 1);
            Path sourceFilePath = Path.of(file.toString());
            Path targetFilePath = Path.of(destinationPath + fileName);

            if (!Files.exists(targetFilePath)){
                Files.copy(sourceFilePath, targetFilePath);
            } else {
                //Future releases of this software to allow for option to copy file or skip
                //Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                //System.out.println("Replaced " + fileName + " with an updated file");
                System.out.println("File " + fileName + " already exists. Skipping to next file.");
            }
        }
        */
