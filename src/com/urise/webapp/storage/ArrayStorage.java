package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addObject(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void deleteObject(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    protected int getIndex(String uuid) {

        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}