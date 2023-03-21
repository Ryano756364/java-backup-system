package com.ryanpodell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

        for(Object file : filesToCopy){
            System.out.println(file);
        }

        //PASTE
        String toFolder = "C:\\Users\\rpode\\OneDrive\\Desktop\\TestTo\\";
        String fileName = "";
        for(Object file : filesToCopy){
            fileName = file.toString().substring(file.toString().lastIndexOf('\\') + 1);
            System.out.println(fileName);
        }


        System.out.println("Program complete.");

    }
}


//  "C:\\Users\\rpode\\OneDrive\\Desktop\\TestFrom\\"
//  "C:\\Users\\rpode\\OneDrive\\Desktop\\TestTo\\"
