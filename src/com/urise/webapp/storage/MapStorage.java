package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void preSave(Object index, Resume resume) {
        map.put((String) index, resume);
    }

    @Override
    protected void preUpdate(Object index, Resume resume) {
        map.put((String) index, resume);
    }

    @Override
    protected void preDelete(Object index) {
        map.remove((String) index);
    }

    @Override
    protected Resume preGet(Object index) {
        return map.get((String) index);
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object index) {
        return map.containsKey((String) index);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Resume[] getAll() {
        ArrayList<Resume> resumes = new ArrayList<>(map.values());
        return resumes.toArray(new Resume[map.size()]);
    }

    @Override
    public void clear() {
        map.clear();
    }
}
