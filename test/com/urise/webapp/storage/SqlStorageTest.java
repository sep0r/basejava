package com.urise.webapp.storage;

import com.urise.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.getINSTANCE().getDbUrl(),
                Config.getINSTANCE().getDbUser(),
                Config.getINSTANCE().getDbPassword()));
    }
}
