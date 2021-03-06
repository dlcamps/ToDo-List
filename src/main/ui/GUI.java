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

import model.Item;
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
    private JList listUrgent;
    private DefaultListModel listModel;
    private DefaultListModel listUrgentModel;
    private JComboBox listComboBox;
    private JScrollPane listScrollPane;
    private JScrollPane listUrgentScrollPane;
    private JButton addButton;
    private JButton removeButton;
    private JRadioButton regularButton;
    private JRadioButton urgentButton;
    private JTextField itemName;
    private JButton quitButton;

    private String[] listStrings = { "All", "Urgent" };
    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String regularString = "Regular";
    private static final String urgentString = "Urgent";
    private static final String quitString = "Quit";

    private ArrayList<String> itemNameImportList;
    private Observer autoSave;
    private ToDoList myList;
    private Boolean startFromScratch;

    private Integer numberOfColumns = 15;
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
                if ((s.length() >= 5) && (s.substring(0, 5).equals("[!!!]"))) {
                    listUrgentModel.addElement(s.substring(6));
                }
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

        listUrgent = new JList(listUrgentModel);
        listUrgent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listUrgent.setVisibleRowCount(5);
        listUrgentScrollPane = new JScrollPane(listUrgent);
        listUrgent.setFont(urgentFont);

        listComboBox = new JComboBox(listStrings);
        listComboBox.setSelectedIndex(0);
        listComboBox.addActionListener(new ListListener());

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

        if (startFromScratch) {
            addButton.setEnabled(true);
            removeButton.setEnabled(false);
            listModel.remove(0);
        } else if (!startFromScratch) {
            addButton.setEnabled(false);
            removeButton.setEnabled(true);
        }

        itemName = new JTextField(numberOfColumns);
        itemName.addActionListener(addListener);
        itemName.getDocument().addDocumentListener(addListener);

        quitButton = new JButton(quitString);
        quitButton.setActionCommand(quitString);
        quitButton.addActionListener(new QuitListener());

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

        add(listComboBox, BorderLayout.PAGE_START);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
        add(radioPanel, BorderLayout.LINE_START);

    }

    // REQUIRES: Current scroll pane name
    // MODIFIES: This
    // EFFECTS: Switches the active/inactive scroll panes, enables/disables user controls
    public void updateList(String s) {
        if (s.equals("All")) {
            swapAndRefresh(listUrgentScrollPane, listScrollPane);
            userControlsAccessible(true);
        } else if (s.equals("Urgent")) {
            swapAndRefresh(listScrollPane, listUrgentScrollPane);
            userControlsAccessible(false);
        }
    }

    // REQUIRES: 2 scroll panes
    // MODIFIES: This
    // EFFECTS: Switches the active/inactive scroll panes
    public void swapAndRefresh(JScrollPane out, JScrollPane in) {
        this.remove(out);
        this.add(in, BorderLayout.CENTER);
        this.updateUI();
    }

    // REQUIRES: Boolean to enable or disable user controls
    // MODIFIES: This
    // EFFECTS: Enables/disables all user controls, fades text input box
    public void userControlsAccessible(Boolean b) {
        removeButton.setEnabled(b);
        addButton.setEnabled(b);
        regularButton.setEnabled(b);
        urgentButton.setEnabled(b);
        itemName.setEnabled(b);
        if (b == true) {
            itemName.setBackground(Color.white);
        } else if (b == false) {
            itemName.setBackground(Color.lightGray);
        }
    }

    class ListListener implements ActionListener {
        // REQUIRES: JComboBox
        // MODIFIES: This
        // EFFECTS: Calls helper methods to change current scroll pane
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            String listName = (String)cb.getSelectedItem();
            updateList(listName);
        }
    }

    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        // REQUIRES: User input into text box
        // MODIFIES: This
        // EFFECTS: Adds text input into listModel or both listModel and listUrgentModel
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

            addToLists(regularButton.isSelected(), itemName.getText());

            itemName.requestFocusInWindow();
            itemName.setText("");

            list.setSelectedIndex(listModel.getSize() - 1);
            list.ensureIndexIsVisible(index);
        }

        // REQUIRES: Boolean to decide item's type, string of item's name
        // MODIFIES: This
        // EFFECTS: Creates item from text and type, adds into 1 or 2 lists
        public void addToLists(Boolean regular, String name) {
            if (regular == true) {
                listModel.addElement(name);
                myList.createItem(1, name);
            } else if (regular == false) {
                listModel.addElement("[!!!] " + name);
                listUrgentModel.addElement(name);
                myList.createItem(2, name);
            }
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


    class RemoveListener implements ActionListener {
        // REQUIRES: Valid index selected
        // MODIFIES: This
        // EFFECTS: Removes the selected item from 1 or 2 lists
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);
            tryRemoveFromUrgent(index);
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

    // REQUIRES: Valid index selected
    // MODIFIES: This
    // EFFECTS: Removes selected item from the urgent list if it's an urgent item
    public void tryRemoveFromUrgent(Integer position) {
        if (position >= 0) {
            String s = getStringAtIndex(position);
            if ((s.length() >= 5) && (s.substring(0, 5).equals("[!!!]"))) {
                listUrgentModel.removeElement(s.substring(6));
            }
        }
    }

    public String getStringAtIndex(Integer position) {
        Item i = myList.getItem(position);
        String name = i.getName();
        return name;
    }

    class QuitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    // REQUIRES: N/A
    // MODIFIES: This
    // EFFECTS: Disables button on empty list
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