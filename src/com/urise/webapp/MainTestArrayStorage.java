package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapResumeStorage;
import com.urise.webapp.storage.Storage;

public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {

        Resume r1 = new Resume("uuid3", "3");
        Resume r2 = new Resume("uuid2", "3");
        Resume r3 = new Resume("uuid1", "1");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        ARRAY_STORAGE.update(r2);

        printAll();
        System.out.println("Get: " + ARRAY_STORAGE.get("uuid3"));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.delete("uuid1");
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get deleted resume: " + ARRAY_STORAGE.get("uuid2"));
        printAll();

        ARRAY_STORAGE.clear();
        System.out.println("Size: " + ARRAY_STORAGE.size());
        printAll();
    }

    private static void printAll() {
        System.out.println("----------------------------");
        if (ARRAY_STORAGE.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : ARRAY_STORAGE.getAllSorted()) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}