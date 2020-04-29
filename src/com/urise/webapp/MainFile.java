package com.urise.webapp;

import java.io.File;

public class MainFile {
    static File dir1;

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Dem\\IdeaProjects\\basejava";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }
//
//        File dir = new File("./src/ru/javawebinar/basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }

//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        dir1 = new File(filePath);
        if (dir1.isDirectory()) {
            bypass();
        }

    }

    private static void bypass() {
        File[] files = dir1.listFiles();
        for (int i = 0; i < files.length; i++) {
            File stroca = files[i];
            dir1 = new File(stroca.getAbsolutePath());
            boolean catalog1 = dir1.isDirectory();
            if (catalog1) {
                bypass();
            }
        }
        if (files != null) {
            System.out.println(dir1.getParentFile());
            System.out.println("--------------------------------------------");
            for (File name : files) {

                System.out.println(name.getName());
            }
            System.out.println("--------------------------------------------");
        }
    }
}