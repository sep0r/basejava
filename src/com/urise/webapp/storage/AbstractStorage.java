package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void preSave(Integer index, Resume resume);

    protected abstract void preUpdate(int index, Resume resume);

    protected abstract void preDelete(int index);

    protected abstract Resume preGet(int index);

    protected abstract Integer getKey(String uuid);

    protected abstract boolean isExist(Integer index);

    @Override
    public void save(Resume resume) {
        Integer index = (Integer) NotExistStorageException(resume.getUuid());
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

    private Integer NotExistStorageException(String uuid) {
        Integer index = getKey(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Integer ExistStorageException(String uuid) {
        Integer index = getKey(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }
}
