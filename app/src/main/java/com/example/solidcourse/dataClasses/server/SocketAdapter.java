package com.example.solidcourse.dataClasses.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketAdapter implements Closeable {
    private final Socket socket;
    private final BufferedWriter writer;
    private final BufferedReader reader;

    public SocketAdapter(Socket socket) {
        this.socket = socket;
        this.reader = getReader();
        this.writer = getWriter();
    }

    private BufferedReader getReader() {
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedWriter getWriter() {
        try {
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    public void write(String message) throws IOException {
        writer.write(message);
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
