package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void preSave(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected void preUpdate(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected void preDelete(Object searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected Resume preGet(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
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
