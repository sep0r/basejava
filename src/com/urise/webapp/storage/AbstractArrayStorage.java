package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void preSave(Integer uuid, Resume resume) {
        if (size >= storage.length) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            addResume(resume, uuid);
            size++;
        }
    }

    public void preUpdate(int index, Resume resume) {
        storage[index] = resume;
    }

    public void preDelete(int index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public Resume preGet(int index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract Integer getKey(String uuid);

    protected abstract void addResume(Resume resume, Integer index);

    protected abstract void deleteResume(Integer index);
}
