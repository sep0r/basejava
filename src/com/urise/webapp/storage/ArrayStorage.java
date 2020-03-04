package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {

    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void save(Resume resume) {
        if (size >= storage.length) {
            System.out.println("Storage is full");
        } else if (size != 0) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(resume.getUuid())) {
                    System.out.println("Resume with " + storage[i].getUuid() + "is already exist!");
                    break;
                } else {
                    storage[size] = resume;
                    size++;
                    break;
                }
            }
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int i = checkResume(uuid);
        if (i != -1) {
            return storage[i];
        }
        return null;
    }

    private int checkResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i] != null) {
                if (storage[i].getUuid().equals(uuid)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void delete(String uuid) {
        int i = 0;
        do {
            if (storage[i].getUuid().equals(uuid)) {
                for (; i < size-1; i++) {
                    storage[i] = storage[i + 1];
                }
                size--;
                return;
            }
            i++;
        } while (i < size);
        System.out.println("Resume with this uuid not found.");
    }

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }
}