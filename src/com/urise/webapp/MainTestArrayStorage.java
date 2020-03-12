package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SortedArrayStorage;
import com.urise.webapp.storage.Storage;

public class MainTestArrayStorage {
    private final static Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {

        Resume r1 = new Resume("uuid3");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid1");
        Resume r4 = new Resume("uuid5");
        Resume r5 = new Resume("uuid4");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);
        ARRAY_STORAGE.save(r5);

        ARRAY_STORAGE.update(r2);

        printAll();
        System.out.println("Get: " + ARRAY_STORAGE.get("uuid2"));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        ARRAY_STORAGE.delete("uuid4");
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get deleted resume: " + ARRAY_STORAGE.get("uuid4"));
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
            for (Resume r : ARRAY_STORAGE.getAll()) {
                System.out.println(r);
            }
        }
        System.out.println("----------------------------");
    }
}