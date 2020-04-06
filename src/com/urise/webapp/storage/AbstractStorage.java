package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void preSave(Object searchKey, Resume resume);

    protected abstract void preUpdate(Object searchKey, Resume resume);

    protected abstract void preDelete(Object searchKey);

    protected abstract Resume preGet(Object searchKey);

    protected abstract Object getSearchKey (String uuid);

    protected abstract boolean isExist(Object searchKey);

    @Override
    public void save(Resume resume) {
        Object searchKey = NotExistStorageException(resume.getUuid());
        preSave(searchKey, resume);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = ExistStorageException(resume.getUuid());
        preUpdate(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = ExistStorageException(uuid);
        preDelete(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = ExistStorageException(uuid);
        return preGet(searchKey);
    }

    private Object NotExistStorageException(String uuid) {
        Object searchKey = getSearchKey (uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object ExistStorageException(String uuid) {
        Object searchKey = getSearchKey (uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
