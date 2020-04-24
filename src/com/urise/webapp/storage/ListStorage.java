package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> list = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != -1;
    }

    @Override
    public void preSave(Integer searchKey, Resume resume) {
        list.add(resume);
    }

    @Override
    public void preUpdate(Integer searchKey, Resume resume) {
        list.set(searchKey, resume);
    }

    @Override
    public Resume preGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    public void preDelete(Integer searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
