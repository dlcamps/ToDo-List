/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package ui;

import model.ToDoList;
import model.exceptions.RemoveOnEmptyListException;
import model.observer.AutoSave;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.*;
import javax.swing.event.*;

public class GUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;
    private JScrollPane listScrollPane;
    private JButton addButton;
    private JButton removeButton;
    private JRadioButton regularButton;
    private JRadioButton urgentButton;
    private JButton quitButton;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private JTextField itemName;

    private ArrayList<String> itemNameImportList;
    private Observer autoSave;
    private ToDoList myList;
    private Boolean startFromScratch;
    private static final String quitString = "Quit";
    private static final String regularString = "Regular";
    private static final String urgentString = "Urgent";

    private Integer numberOfColumns = 15;
    private Integer borderSideSize = 30;
    private Integer fontSize = 16;
    private Font myFont = new Font("Lucidia Grande", Font.PLAIN, fontSize);

    public GUI() throws IOException {
        super(new BorderLayout());

        listModel = new DefaultListModel();

        autoSave = new AutoSave();
        myList = new ToDoList();
        myList.fileRead(autoSave);
        if (!(myList.getList().isEmpty())) {
            startFromScratch = false;
            itemNameImportList = new ArrayList<>();
            itemNameImportList = myList.convertItemListToStringList(myList);
            for (String s : itemNameImportList) {
                listModel.addElement(s);
            }
        } else if (myList.getList().isEmpty()) {
            startFromScratch = true;
            listModel.addElement("Insert Item Here");
        }

        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        listScrollPane = new JScrollPane(list);
        list.setFont(myFont);
        setBorder(BorderFactory.createEmptyBorder(borderSideSize, borderSideSize, borderSideSize, borderSideSize));

        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        regularButton = new JRadioButton(regularString);
        regularButton.setSelected(true);

        urgentButton = new JRadioButton(urgentString);
        urgentButton.setSelected(false);

        ButtonGroup group = new ButtonGroup();
        group.add(regularButton);
        group.add(urgentButton);

        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(regularButton);
        radioPanel.add(urgentButton);

        itemName = new JTextField(numberOfColumns);
        itemName.addActionListener(addListener);
        itemName.getDocument().addDocumentListener(addListener);
//        String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitListener());

        if (startFromScratch) {
            addButton.setEnabled(true);
            removeButton.setEnabled(false);
            listModel.remove(0);
        } else if (!startFromScratch) {
            addButton.setEnabled(false);
            removeButton.setEnabled(true);
        }

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(addButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(regularButton);
        buttonPane.add(urgentButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(itemName);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(quitButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        add(radioPanel, BorderLayout.LINE_START);

    }

    class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            myList.removeWithIndex(index);

            int size = listModel.getSize();

            if (size == 0) { //No items left, disable removing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        public void addToLists(Boolean regular, Integer position, String name) {
            if (regular == true) {
                listModel.insertElementAt(name, position);
                myList.createItem(1, name);
            } else if (regular == false) {
                listModel.insertElementAt("[!!!] " + name, position);
                myList.createItem(2, name);
            }
        }

        public void actionPerformed(ActionEvent e) {
            String name = itemName.getText();

            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                itemName.requestFocusInWindow();
                itemName.selectAll();
                return;
            }
            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            //listModel.addElement(itemName.getText());
            addToLists(regularButton.isSelected(), index, itemName.getText());

            itemName.requestFocusInWindow();
            itemName.setText("");

            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the remove button.
                removeButton.setEnabled(true);
            }
        }
    }

    private static void createAndShowGUI() throws IOException {
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, RemoveOnEmptyListException {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (IOException e) {
                    //
                }
            }
        });
    }
}