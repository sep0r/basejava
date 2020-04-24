package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void preSave(String searchKey, Resume resume) {
        map.put(searchKey, resume);
    }

    @Override
    protected void preUpdate(String searchKey, Resume resume) {
        map.put(searchKey, resume);
    }

    @Override
    protected void preDelete(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected Resume preGet(String searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void clear() {
        map.clear();
    }
}
