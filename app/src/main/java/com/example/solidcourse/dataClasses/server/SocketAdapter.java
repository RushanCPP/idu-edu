package com.example.solidcourse.dataClasses.server;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketAdapter implements Closeable {
    private final Socket socket;
    final ObjectOutputStream writer;
    final ObjectInputStream reader;

    public SocketAdapter(Socket socket) {
        this.socket = socket;
        this.writer = getWriter();
        this.reader = getReader();
    }

    private ObjectInputStream getReader() {
        try {
            return new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectOutputStream getWriter() {
        try {
            return new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Object message) throws IOException {
        writer.writeObject(message);
        writer.flush();
    }

    public Object read() throws IOException, ClassNotFoundException {
        return reader.readObject();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
