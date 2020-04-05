package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void preSave(Object index, Resume resume);

    protected abstract void preUpdate(Object index, Resume resume);

    protected abstract void preDelete(Object index);

    protected abstract Resume preGet(Object index);

    protected abstract Object getKey(String uuid);

    protected abstract boolean isExist(Object index);

    @Override
    public void save(Resume resume) {
        Object index = NotExistStorageException(resume.getUuid());
        preSave(index, resume);
    }

    @Override
    public void update(Resume resume) {
        Object index = ExistStorageException(resume.getUuid());
        preUpdate(index, resume);
    }

    @Override
    public void delete(String uuid) {
        Object index = ExistStorageException(uuid);
        preDelete(index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = ExistStorageException(uuid);
        return preGet(index);
    }

    private Object NotExistStorageException(String uuid) {
        Object index = getKey(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Object ExistStorageException(String uuid) {
        Object index = getKey(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}
