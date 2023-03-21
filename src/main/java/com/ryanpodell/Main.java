package com.ryanpodell;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        //String fromFolder = "C:\\Users\\rpode\\OneDrive\\Desktop\\TestFrom";
        //String toFolder = "C:\\Users\\rpode\\OneDrive\\Desktop\\TestTo\\";
        //Files.copy(Path.of(fromFolder), Path.of(toFolder));

        //COPY
        ArrayList filesToCopy = new ArrayList<>();

        Path dir = Path.of("C:\\Users\\rpode\\OneDrive\\Desktop\\TestFrom");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for(Path file: stream){
                filesToCopy.add(file);
            }
        } catch (IOException | DirectoryIteratorException x) {
            System.err.println(x);
        }

        //PASTE
        String toFolder = "C:\\Users\\rpode\\OneDrive\\Desktop\\TestTo\\";
        String fileName = "";
        for(Object file : filesToCopy){
            fileName = file.toString().substring(file.toString().lastIndexOf('\\') + 1);
            Path sourceFilePath = Path.of(file.toString());
            Path targetFilePath = Path.of(toFolder + fileName);

            if (!Files.exists(targetFilePath)){
                Files.copy(sourceFilePath, targetFilePath);
            } else {
                //Future releases of this software to allow for option to copy file or skip
                //Files.copy(sourceFilePath, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
                //System.out.println("Replaced " + fileName + " with an updated file");
                System.out.println("File " + fileName + " already exists. Skipping to next file.");
            }
        }

        System.out.println("\nProgram complete.");
    }
}

//  "C:\\Users\\rpode\\OneDrive\\Desktop\\TestFrom\\"
//  "C:\\Users\\rpode\\OneDrive\\Desktop\\TestTo\\"
