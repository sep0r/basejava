package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void preSave(Object r, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void preUpdate(Object r, Resume resume) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void preDelete(Object r) {
        map.remove(((Resume) r).getUuid());
    }

    @Override
    protected Resume preGet(Object r) {
        return (Resume) r;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public List<Resume> getAll() {
        ArrayList<Resume> resumes = new ArrayList<>(map.values());
        return resumes.subList(0, size());
    }

    @Override
    public void clear() {
        map.clear();
    }
}
