package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void addResume(Resume resume, Integer index) {
        int i = -index - 1;
        System.arraycopy(storage, i, storage, i + 1, size - i);
        storage[i] = resume;
    }

    @Override
    protected void deleteResume(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }

    @Override
    protected Integer getKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
