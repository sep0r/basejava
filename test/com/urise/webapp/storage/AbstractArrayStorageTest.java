package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static com.urise.webapp.storage.ResumeTestData.RESUME_4;
import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void overflowStorageTest() throws Exception {
        try {
            storage.clear();
            for (int i = 0; i < 10_000; i++) {
                storage.save(new Resume("Lex"));
            }
        } catch (StorageException e) {
            fail("Exception thrown");
        }
        storage.save(RESUME_4);
    }
}
