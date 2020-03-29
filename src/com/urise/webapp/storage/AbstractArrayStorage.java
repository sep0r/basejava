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

    public void preUpdate(String uuid, Resume resume) {
        Integer index = (Integer) ExistStorageException(uuid);
        storage[index] = resume;
    }

    public void delete(String uuid) {
        Integer index = (Integer) ExistStorageException(uuid);
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

    public Resume get(String uuid) {
        Integer index = (Integer) ExistStorageException(uuid);
        return storage[index];
    }

    protected abstract Integer getKey(String uuid);

    protected abstract void addResume(Resume resume, Integer index);

    protected abstract void deleteResume(int index);
}
