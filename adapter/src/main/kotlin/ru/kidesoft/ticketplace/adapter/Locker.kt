package ru.kidesoft.ticketplace.adapter

import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.net.ServerSocket
import java.nio.channels.FileChannel
import java.nio.channels.FileLock
import java.nio.channels.OverlappingFileLockException


class SocketLock(val port: Int) {

    private var socket: java.net.ServerSocket? = null

    /**
     * Returns true if this [SocketLock] is currently locked and listening on the port.
     */
    fun isLocked() = socket != null

    /**
     * Attempts to acquire a lock on the port. If the port is already in use, this method will block until the port is
     * available.
     */
    fun lock() = synchronized(this) {
        if (isLocked()) {
            error("Already locked")
        }

        while (true) {
            try {
                socket = ServerSocket(port)
                break
            } catch (e: IOException) {
                Thread.sleep(1000)
            }
        }
    }

    /**
     * Attempts to acquire a lock on the port. If the port is already in use, this method will return false and the
     * [SocketLock] will remain unlocked.
     */
    fun tryLock(): Boolean = synchronized(this) {
        if (isLocked()) {
            error("Already locked")
        }

        socket = try {
            ServerSocket(port)
        } catch (e: IOException) {
            null
        }

        return socket != null
    }

    /**
     * Executes the given function while holding a lock on the port. If the port is already in use, this method will
     * block until the port is available. The lock will be released after the function [f] returns.
     */
    fun <T> withLock(f: () -> T): T = synchronized(this) {
        lock()
        try {
            return f()
        } finally {
            unlock()
        }
    }

    fun unlock() = synchronized(this) {
        runCatching { socket?.close() }
    }

}