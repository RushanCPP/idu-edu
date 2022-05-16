package com.example.solidcourse.dataClasses.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketAdapter implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public SocketAdapter(Socket socket) {
        this.socket = socket;
        this.objectOutputStream = getObjectWriter();
        this.objectInputStream = getObjectReader();
        this.bufferedWriter = getBufferedWriter();
        this.bufferedReader = getBufferedReader();
    }

    private ObjectInputStream getObjectReader() {
        try {
            return new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectOutputStream getObjectWriter() {
        try {
            return new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader getBufferedReader() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedWriter getBufferedWriter() {
        try {
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeObject(Object message) throws IOException {
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    public Object readObject() throws IOException, ClassNotFoundException {
        return objectInputStream.readObject();
    }

    public void writeLine(String line) throws IOException {
        bufferedWriter.write(line);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    public void writeLong(long value) throws IOException {
        bufferedWriter.write(String.valueOf(value));
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public long readLong() throws IOException {
        return Long.parseLong(bufferedReader.readLine());
    }

    @Override
    public void close() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }
}
