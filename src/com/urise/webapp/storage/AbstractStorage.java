package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void preSave(Integer uuid, Resume resume);

    protected abstract void preUpdate(String uuid, Resume resume);

    protected abstract Integer getKey(String uuid);

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

    private Object NotExistStorageException(String uuid) {
        Integer index = getKey(uuid);
        if (index == null || index < 0) {
            return index;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    Object ExistStorageException(String uuid) {
        Integer index = getKey(uuid);
        if (index == null || index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return index;
        }
    }
}
