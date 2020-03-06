package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

public class MainTestArrayStorage {
    private final static ArrayStorage arrayStorage1 = new ArrayStorage();

    public static void main(String[] args) {

        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid4");
        Resume r5 = new Resume("uuid5");

        arrayStorage1.save(r1);
        arrayStorage1.save(r2);
        arrayStorage1.save(r3);
        arrayStorage1.save(r4);
        arrayStorage1.save(r5);

        arrayStorage1.update(r2);

        printAll();
        System.out.println("Get: " + arrayStorage1.get("uuid2"));
        System.out.println("Size: " + arrayStorage1.size());

        arrayStorage1.delete("uuid4");
        System.out.println("Get deleted resume: " + arrayStorage1.get("uuid4"));
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
