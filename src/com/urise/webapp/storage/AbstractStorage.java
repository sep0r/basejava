package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void preSave(Integer uuid, Resume resume);

    protected abstract void preUpdate(String uuid, Resume resume);

    protected abstract void preDelete(int index);

    protected abstract Resume preGet(Integer index);

    protected abstract Integer getKey(String uuid);

    protected abstract boolean checkIndex(Object index);

    @Override
    public void save(Resume resume) {
        Integer key = (Integer) NotExistStorageException(resume.getUuid());
        preSave(key, resume);
    }

    @Override
    public void update(Resume resume) {
        String key = resume.getUuid();
        preUpdate(key, resume);
    }

    @Override
    public void delete(String uuid) {
        int index = (int) ExistStorageException(uuid);
        preDelete(index);
    }

    @Override
    public Resume get(String uuid) {
        Integer index = (Integer) ExistStorageException(uuid);
        return preGet(index);
    }

    private Object NotExistStorageException(String uuid) {
        Integer index = getKey(uuid);
        if (checkIndex(index)) {
            throw new ExistStorageException(uuid);
        } else {
            return index;
        }
    }

    Object ExistStorageException(String uuid) {
        Integer index = getKey(uuid);
        if (!checkIndex(index)) {
            throw new NotExistStorageException(uuid);
        } else {
            return index;
        }
    }
}
