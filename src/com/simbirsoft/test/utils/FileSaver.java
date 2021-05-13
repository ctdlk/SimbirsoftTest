package com.simbirsoft.test.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileSaver {
    private static FileSaver INSTANCE;
    private static final String DB = "db\\%s.txt";
    private static final String DOWNLOADS = "downloads\\%s.txt";

    private FileSaver() {
    }

    public static FileSaver getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FileSaver();
        }
        return INSTANCE;
    }

    public boolean create(String filename) {
        try {
            File file = new File(filename);
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void save(Path path, String data) {
        try {
            byte[] strToBytes = data.getBytes();
            Files.write(path, strToBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveWebpage(String url, String data) {
        try {
            String filename = getFilename(DOWNLOADS, url);
            create(filename);
            Path path = Paths.get(filename);
            save(path, data);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean saveDictionary(String url, String data) {
        try {
            String filename = getFilename(DB, url);
            create(filename);
            Path path = Paths.get(filename);
            save(path, data);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            return false;
        }
        return true;
    }


    public String getWebpage(String url) {
        try {
            Path path = Paths.get(getFilename(DOWNLOADS, url));
            Stream<String> lines = Files.lines(path);
            String data = lines.collect(Collectors.joining("\n"));
            lines.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String cleanURL(String URL) {
        try {
            return URL.split("://")[1].replace("/", "#");
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getFilename(String mode, String url) {
        return String.format(mode, cleanURL(url));
    }
}
