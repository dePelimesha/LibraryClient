package ru.javarush.client;

import ru.javarush.ws.Library;
import ru.javarush.ws.LibraryImplService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class LibraryClient extends JFrame implements ListSelectionListener {

    private Library lib;
    private JList booksList;
    private JTextArea outputArea;
    private JScrollPane pane;

    LibraryClient() {

        LibraryImplService libService = new LibraryImplService();
        lib = libService.getLibraryImplPort();//подключ к порту с сервисом

        booksList = new JList(lib.getBooksList().getItem().toArray());
        booksList.setLayoutOrientation(JList.VERTICAL);

        outputArea = new JTextArea(50,144);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        pane = new JScrollPane(outputArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.setTitle("Library client");
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        add(pane, BorderLayout.WEST);
        add(booksList, BorderLayout.EAST);

        booksList.addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {

        byte[] book = lib.getBook(booksList.getSelectedValue().toString());
        String bookText = new String(book);
        outputArea.setText(bookText);
        outputArea.setCaretPosition(0);
    }

    public static void main(String [] args) {

        new LibraryClient();
    }
}

