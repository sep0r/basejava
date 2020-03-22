package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    AbstractArrayStorageTest(Storage storage) {
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
        storage.clear();
        storage.save(RESUME_1);
        Assert.assertSame(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void update() throws Exception {
        storage.update(new Resume(UUID_3));
        Assert.assertEquals(RESUME_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(3, resumes.length);
        Assert.assertSame(RESUME_1, resumes[0]);
        Assert.assertSame(RESUME_2, resumes[1]);
        Assert.assertSame(RESUME_3, resumes[2]);
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

    @Test(expected = StorageException.class)
    public void overflowStorageTest() throws Exception {
        try {
            storage.clear();
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Exception thrown");
        }
        storage.save(new Resume());
    }
}