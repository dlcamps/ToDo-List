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

// TODO: Add selection to show full ToDoList or just urgent items
// TODO: Highlight urgent items to replace "[!!!]" tag

public class GUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private JButton removeButton;
    private JTextField itemName;

    private DefaultListModel listUrgentModel;
    private JList listUrgent;
    private Integer numberOfColumns = 15;
    private ArrayList<String> itemNameImportList;
    private Observer autoSave;
    private ToDoList myList;
    private Boolean startFromScratch;
    String[] listStrings = { "All", "Urgent" };
    private static final String quitString = "Quit";
    private static final String regularString = "Regular";
    private static final String urgentString = "Urgent";
    private JComboBox listList;
    private JButton addButton;
    private JButton quitButton;
    private JRadioButton regularButton;
    private JRadioButton urgentButton;
    private Integer borderSideSize = 30;
    private Integer fontSize = 16;
    private Font myFont = new Font("Lucidia Grande", Font.PLAIN, fontSize);
    private Font urgentFont = new Font("Lucidie Grande", Font.BOLD, fontSize);

    public GUI() throws IOException {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        listUrgentModel = new DefaultListModel();

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

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        setBorder(BorderFactory.createEmptyBorder(borderSideSize, borderSideSize, borderSideSize, borderSideSize));
        list.setFont(myFont);

        listUrgent = new JList(listUrgentModel);
        listUrgent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listUrgent.setSelectedIndex(0);
        listUrgent.addListSelectionListener(this);
        listUrgent.setVisibleRowCount(5);
        JScrollPane listUrgentScrollPane = new JScrollPane(listUrgent);
        listUrgent.setFont(urgentFont);

        listList = new JComboBox(listStrings);
        listList.setSelectedIndex(0);
        listList.addActionListener(new ListListener());

        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitListener());

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
        add(radioPanel, BorderLayout.LINE_START);

        itemName = new JTextField(numberOfColumns);
        itemName.addActionListener(addListener);
        itemName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        if (startFromScratch) {
            addButton.setEnabled(true);
            removeButton.setEnabled(false);
            listModel.remove(0);
        } else if (!startFromScratch) {
            addButton.setEnabled(false);
            removeButton.setEnabled(true);
        }

        //Create a panel that uses BoxLayout.
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

        add(listList, BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);

    }

    public void updateList(String s) {
        if (s.equals("All")) {
            removeButton.setEnabled(true);
            addButton.setEnabled(true);
            regularButton.setEnabled(true);
            urgentButton.setEnabled(true);
            itemName.setEnabled(true);
            itemName.setBackground(Color.white);
        } else if (s.equals("Urgent")) {
            removeButton.setEnabled(false);
            addButton.setEnabled(false);
            regularButton.setEnabled(false);
            urgentButton.setEnabled(false);
            itemName.setEnabled(false);
            itemName.setBackground(Color.lightGray);
        }
    }

    class ListListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            String listName = (String)cb.getSelectedItem();
            updateList(listName);
        }
    }

    class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
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

    //This listener is shared by the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        public void addToAll(Boolean b, String s, Integer i) {
            if (b == true) {
                listModel.insertElementAt(s, i);
                myList.createItem(1, s);
            } else if (b == false) {
                listModel.insertElementAt("[!!!] " + s, i);
                listUrgentModel.insertElementAt(s, i);
                myList.createItem(2, s);
            }
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = itemName.getText();

            //User didn't type in a unique name...
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

            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(itemName.getText());
            addToAll(regularButton.isSelected(), itemName.getText(), index);

            //Reset the text field.
            itemName.requestFocusInWindow();
            itemName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
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

    //This method is required by ListSelectionListener.
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

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException, RemoveOnEmptyListException {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
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