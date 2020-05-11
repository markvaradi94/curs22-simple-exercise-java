package org.fasttrackit.simpleexercise.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class VacationReader {

    private final String fileLocation;
    private final Resource file;

    public VacationReader(@Value("${file.location:default.txt}") String fileLocation) {
        this.fileLocation = fileLocation;
        this.file = new ClassPathResource(fileLocation);
        if (!file.exists()) {
            throw new RuntimeException("Could not find file in classpath " + fileLocation);
        }
    }

    public List<Vacation> read() {
        List<Vacation> result = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("[|]");
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split("[|]");
                result.add(new Vacation(tokens[0], Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2])));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
