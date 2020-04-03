package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void preSave(int index, Resume resume);

    protected abstract void preUpdate(int index, Resume resume);

    protected abstract void preDelete(int index);

    protected abstract Resume preGet(int index);

    protected abstract int getKey(String uuid);

    protected abstract boolean isExist(int index);

    @Override
    public void save(Resume resume) {
        int index = NotExistStorageException(resume.getUuid());
        preSave(index, resume);
    }

    @Override
    public void update(Resume resume) {
        int index = ExistStorageException(resume.getUuid());
        preUpdate(index, resume);
    }

    @Override
    public void delete(String uuid) {
        int index = ExistStorageException(uuid);
        preDelete(index);
    }

    @Override
    public Resume get(String uuid) {
        int index = ExistStorageException(uuid);
        return preGet(index);
    }

    private int NotExistStorageException(String uuid) {
        int index = getKey(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private int ExistStorageException(String uuid) {
        int index = getKey(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}
