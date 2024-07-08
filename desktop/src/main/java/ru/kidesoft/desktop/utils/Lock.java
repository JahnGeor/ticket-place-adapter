package ru.kidesoft.desktop.utils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Lock {
    final static String lockPath = "./application.lock";

    public static boolean tryLock() throws IOException {
        File lockFile = new File(lockPath);

//        if (!lockFile.exists()) {
//            throw new IllegalStateException("Application lock file does not exist");
//        }

        lockFile.deleteOnExit();

        FileChannel fileChannel = FileChannel.open(lockFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        FileLock lock = fileChannel.tryLock();

        if (lock == null || !lock.isValid()) {
            return true;
        } else {
            return false;
        }

    }

}
