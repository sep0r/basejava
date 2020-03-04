package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

public class MainTestArrayStorage {
    private final static ArrayStorage arrayStorage1 = new ArrayStorage();

    public static void main(String[] args) {

        arrayStorage1.save(new Resume("3be6912a-5275-11ea-8d77-2e728ce88121"));
        arrayStorage1.save(new Resume("3be693d2-5275-11ea-8d77-2e728ce88122"));
        arrayStorage1.save(new Resume("3be69526-5275-11ea-8d77-2e728ce88123"));
        arrayStorage1.save(new Resume("3be6965c-5275-11ea-8d77-2e728ce88124"));
        arrayStorage1.save(new Resume("3be69792-5275-11ea-8d77-2e728ce88125"));

        System.out.println("Get: " + arrayStorage1.get("3be693d2-5275-11ea-8d77-2e728ce88122"));
        System.out.println("Size: " + arrayStorage1.size());
        printAll();

        arrayStorage1.delete("3be6965c-5275-11ea-8d77-2e728ce88124");
        System.out.println("Get deleted resume: " + arrayStorage1.get("3be6965c-5275-11ea-8d77-2e728ce88124"));
        System.out.println("Size: " + arrayStorage1.size());
        printAll();

        arrayStorage1.clear();
        System.out.println("Size: " + arrayStorage1.size());
        printAll();
    }

    private static void printAll() {
        System.out.println("----------------------------");
        if (arrayStorage1.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : arrayStorage1.getAll()) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}
