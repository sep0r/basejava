package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void preSave(Integer index, Resume resume) {
        if (size >= storage.length) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else {
            addResume(resume, index);
            size++;
        }
    }

    @Override
    public void preUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public void preDelete(Integer index) {
        deleteResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume preGet(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
