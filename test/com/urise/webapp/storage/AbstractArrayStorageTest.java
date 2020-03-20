package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class AbstractArrayStorageTest {
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";


    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void save() throws Exception {
        Assert.assertEquals(new Resume(UUID_1), storage.get("uuid1"));
    }

    @Test
    public void update() throws Exception {
        storage.update(new Resume("uuid3"));
        Assert.assertEquals(new Resume(UUID_3), storage.get("uuid3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        storage.get("uuid2");
    }

    @Test
    public void getAll() throws Exception {
        Assert.assertEquals(new Resume(UUID_1), storage.get("uuid1"));
        Assert.assertEquals(new Resume(UUID_2), storage.get("uuid2"));
        Assert.assertEquals(new Resume(UUID_3), storage.get("uuid3"));
        Assert.assertEquals(3, storage.size());
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
        Assert.assertEquals(new Resume("uuid2"), storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void overloadStorageTest() throws Exception {
        try {
            setUp();
        } catch (StorageException e) {
            fail("Exception thrown");
        }
        storage.save(new Resume("uuid4"));
    }
}