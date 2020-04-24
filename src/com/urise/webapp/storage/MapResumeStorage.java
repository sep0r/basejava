package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void preSave(Resume r, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void preUpdate(Resume r, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void preDelete(Resume r) {
        map.remove(r.getUuid());
    }

    @Override
    protected Resume preGet(Resume r) {
        return r;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
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
