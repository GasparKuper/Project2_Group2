package StochasticWindModel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

public class ForceList extends JPanel implements ListSelectionListener{

    private static final String addString = "ADD", removeString = "REMOVE";

    public static HashMap<String, Force> forceMap = new HashMap<>();
    public static JList<String> list;

    private static JButton removeButton;
    private static JTextField forceField;

    private DefaultListModel<String> listModel;

    public ForceList(){
        super(null);

        listModel = new DefaultListModel<>();
        listModel.addElement("Net Force");
        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Only display one item at a time
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(JList.VERTICAL);
        list.setFont(new Font("Arial",Font.BOLD,24));
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener); // everytime the button is pressed it will go to add listener perform thing
        addButton.setEnabled(false);
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        forceField = new JTextField();
        forceField.addActionListener(addListener);
        forceField.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        JPanel buttonPane = new JPanel();
    }

    class AddListener implements ActionListener, DocumentListener{

        private JButton button;
        public AddListener(JButton button){
            this.button = button;
        }

        public void actionPerformed(ActionEvent e) { }
        public void insertUpdate(DocumentEvent e) { }
        public void removeUpdate(DocumentEvent e) { }
        public void changedUpdate(DocumentEvent e) { }
    }

    class RemoveListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
        }
    }

        public void valueChanged(ListSelectionEvent e) {

    }
}
