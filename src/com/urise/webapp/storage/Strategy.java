package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Strategy {

    abstract void doWrite(Resume r, OutputStream os) throws IOException;

    abstract Resume doRead(InputStream is) throws IOException;
}
