package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR =
            new File("C:\\Users\\Dem\\IdeaProjects\\basejava\\storage");
    protected Storage storage;

    protected static final String UUID_1 = "9668f697-3642-422b-a201-7272514dba8b";
    protected static final String UUID_2 = "b8a4d227-b9cf-491f-94d1-e33e9c3bb22f";
    protected static final String UUID_3 = "3a2443b0-1c27-4ac9-b54d-3f3982d5b118";
    protected static final String UUID_4 = "d02f12f7-f7eb-48c5-8afa-48126f230745";

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = new ResumeTestData().createResume(UUID_1, "name1");
        RESUME_2 = new ResumeTestData().createResume(UUID_2, "name2");
        RESUME_3 = new ResumeTestData().createResume(UUID_3, "name3");
        RESUME_4 = new ResumeTestData().createResume(UUID_4, "name4");
    }

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        Assert.assertEquals(RESUME_4, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void update() throws Exception {
        Resume RESUME_3 = new ResumeTestData().createResume(UUID_3,"name333");
        storage.update(RESUME_3);
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(storage.get("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}