package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {

    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void save(Resume resume) {
        int index = checkResume(resume.getUuid());
        if (size >= storage.length) {
            System.out.println("Storage is full");
        } else if (index == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Resume with " + storage[index].getUuid() + " is already exist!");
        }
    }

    public void update(Resume resume) {
        int index = checkResume(resume.getUuid());
        if (index == -1) {
            System.out.println("Resume with this uuid not exist!");
        } else {
            storage[index] = resume;
        }
    }

    public Resume get(String uuid) {
        int index = checkResume(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Resume with this uuid not found. ");
        return null;
    }

    public void delete(String uuid) {
        int index = checkResume(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        } else {
            System.out.println("Failed to delete: resume with this uuid not found.");
        }
    }

    private int checkResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }
}