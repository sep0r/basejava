package com.urise.webapp;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
//        String filePath = "C:\\Users\\Dem\\IdeaProjects\\basejava";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }

        File dir = new File("C:\\Users\\Dem\\IdeaProjects\\basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        bypass(dir, "");

//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    private static void bypass(File dir, String string) {
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(string + "d: " + file.getName());
                bypass(file, string + "  ");
            } else if (file.isFile()) {
                System.out.println(string + "f: " + file.getName());
            }
        }
    }
}