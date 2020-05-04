package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public abstract class AbstractStorageTest {
    protected Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = new ResumeTestData().addResume(UUID_1, "name1",
                "890828", "skype1", "111@mail.ru", "http://111.ru/", "https://github.com/1", "https://stackoverflow.com/1",
                "RESUME_1 personal", "RESUME_1 objective",
                "RESUME_1 achievement1", "RESUME_1 qualification1",
                Arrays.asList(new Organization("RESUME_1 header", "RESUME_1 link", "http://111.ru/",
                        new Organization.Position("RESUME_1 title.", YearMonth.of(2013, 10),
                                YearMonth.of(2005, 1), "RESUME_1 experience"))),
                Arrays.asList(new Organization("RESUME_1 header", "RESUME_1 link", "http://111.ru/",
                        new Organization.Position("RESUME_1 title.", YearMonth.of(2013, 10),
                                YearMonth.of(2005, 1), "RESUME_1 experience"))));
        RESUME_2 = new ResumeTestData().addResume(UUID_2, "name2",
                "89082812", "skype2", "222@mail.ru", "http://222.ru/", "https://github.com/2", "https://stackoverflow.com/2",
                "RESUME_2 personal", "RESUME_2 objective",
                "RESUME_2 achievement1", "RESUME_2 qualification1",
                Arrays.asList(new Organization("RESUME_2 header", "RESUME_2 link", "http://222.ru/",
                        new Organization.Position("RESUME_2 title.", YearMonth.of(2013, 10),
                                YearMonth.of(2005, 1), "RESUME_1 experience"))),
                Arrays.asList(new Organization("RESUME_1 header", "RESUME_2 link", "http://222.ru/",
                        new Organization.Position("RESUME_2 title.", YearMonth.of(2013, 10),
                                YearMonth.of(2005, 1), "RESUME_1 experience"))));
        RESUME_3 = new ResumeTestData().addResume(UUID_3, "name3",
                "89083813", "skype3", "333@mail.ru", "http://333.ru/", "https://github.com/3", "https://stackoverflow.com/3",
                "RESUME_3 personal", "RESUME_3 objective",
                "RESUME_3 achievement1", "RESUME_3 qualification1",
                Arrays.asList(new Organization("RESUME_3 header", "RESUME_3 link", "http://333.ru/",
                        new Organization.Position("RESUME_3 title.", YearMonth.of(3013, 10),
                                YearMonth.of(3005, 1), "RESUME_1 experience"))),
                Arrays.asList(new Organization("RESUME_1 header", "RESUME_3 link", "http://333.ru/",
                        new Organization.Position("RESUME_3 title.", YearMonth.of(3013, 10),
                                YearMonth.of(3005, 1), "RESUME_1 experience"))));
        RESUME_4 = new ResumeTestData().addResume(UUID_4, "name4",
                "89084814", "skype4", "444@mail.ru", "http://444.ru/", "https://github.com/4", "https://stackoverflow.com/4",
                "RESUME_4 personal", "RESUME_4 objective",
                "RESUME_4 achievement1", "RESUME_4 qualification1",
                Arrays.asList(new Organization("RESUME_4 header", "RESUME_4 link", "http://444.ru/",
                        new Organization.Position("RESUME_4 title.", YearMonth.of(4014, 10),
                                YearMonth.of(4005, 1), "RESUME_1 experience"))),
                Arrays.asList(new Organization("RESUME_1 header", "RESUME_4 link", "http://444.ru/",
                        new Organization.Position("RESUME_4 title.", YearMonth.of(4014, 10),
                                YearMonth.of(4005, 1), "RESUME_1 experience"))));
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
        Assert.assertSame(RESUME_4, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveAlreadyExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test
    public void update() throws Exception {
        Resume RESUME_3 = new Resume(UUID_3, "name33");
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