import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LibraryManagement extends JFrame implements ActionListener {
    private JLabel label1, label2, label3, label4, label5, label6, label7;
    private JTextField textField1, textField2, textField3, textField4, textField5, textField6, textField7;
    private JButton addButton, viewButton, editButton, deleteButton, clearButton, exitButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<String[]> books = new ArrayList<>();

    public LibraryManagement() {
        setTitle("Library Management System");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        label1 = new JLabel("Book ID");
        label2 = new JLabel("Book Title");
        label3 = new JLabel("Author");
        label4 = new JLabel("Publisher");
        label5 = new JLabel("Year of Publication");
        label6 = new JLabel("ISBN");
        label7 = new JLabel("Number of Copies");

        textField1 = new JTextField(10);
        textField2 = new JTextField(20);
        textField3 = new JTextField(20);
        textField4 = new JTextField(20);
        textField5 = new JTextField(10);
        textField6 = new JTextField(20);
        textField7 = new JTextField(10);

        addButton = new JButton("Add");
        viewButton = new JButton("View");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        addButton.addActionListener(this);
        viewButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        clearButton.addActionListener(this);
        exitButton.addActionListener(this);

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(label1);
        inputPanel.add(textField1);
        inputPanel.add(label2);
        inputPanel.add(textField2);
        inputPanel.add(label3);
        inputPanel.add(textField3);
        inputPanel.add(label4);
        inputPanel.add(textField4);
        inputPanel.add(label5);
        inputPanel.add(textField5);
        inputPanel.add(label6);
        inputPanel.add(textField6);
        inputPanel.add(label7);
        inputPanel.add(textField7);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Book ID", "Book Title", "Author", "Publisher", "Year of Publication", "ISBN", "Number of Copies"});
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addBook();
        } else if (e.getSource() == viewButton) {
            viewBooks();
        } else if (e.getSource() == editButton) {
            editBook();
        } else if (e.getSource() == deleteButton) {
            deleteBook();
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void addBook() {
        String bookID = textField1.getText();
        String bookTitle = textField2.getText();
        String author = textField3.getText();
        String publisher = textField4.getText();
        String yearOfPublication = textField5.getText();
        String isbn = textField6.getText();
        String numCopies = textField7.getText();

        if (bookID.isEmpty() || bookTitle.isEmpty() || author.isEmpty() || publisher.isEmpty()
                || yearOfPublication.isEmpty() || isbn.isEmpty() || numCopies.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int year = Integer.parseInt(yearOfPublication);
            int copies = Integer.parseInt(numCopies);

            if (year < 0 || copies < 0) {
                JOptionPane.showMessageDialog(this, "Year of publication and number of copies must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] book = {bookID, bookTitle, author, publisher, yearOfPublication, isbn, numCopies};
            books.add(book);
            tableModel.addRow(book);
            JOptionPane.showMessageDialog(this, "Book added successfully");
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input for year of publication or number of copies", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewBooks() {
        if (books.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books to display", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame frame = new JFrame("View Books");
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);

        JTable viewTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(viewTable);
        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void editBook() {
        String bookID = JOptionPane.showInputDialog(this, "Enter book ID to edit:");
        if (bookID == null) {
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i)[0].equals(bookID)) {
                String bookTitle = textField2.getText();
                String author = textField3.getText();
                String publisher = textField4.getText();
                String yearOfPublication = textField5.getText();
                String isbn = textField6.getText();
                String numCopies = textField7.getText();

                if (bookTitle.isEmpty() || author.isEmpty() || publisher.isEmpty()
                        || yearOfPublication.isEmpty() || isbn.isEmpty() || numCopies.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int year = Integer.parseInt(yearOfPublication);
                    int copies = Integer.parseInt(numCopies);

                    if (year < 0 || copies < 0) {
                        JOptionPane.showMessageDialog(this, "Year of publication and number of copies must be positive", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String[] book = {bookID, bookTitle, author, publisher, yearOfPublication, isbn, numCopies};
                    books.set(i, book);
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        table.setValueAt(book[j], i, j);
                    }
                    JOptionPane.showMessageDialog(this, "Book edited successfully");
                    clearFields();
                    return;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input for year of publication or number of copies", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void deleteBook() {
        String bookID = JOptionPane.showInputDialog(this, "Enter book ID to delete:");
        if (bookID == null) {
            return;
        }

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i)[0].equals(bookID)) {
                books.remove(i);
                tableModel.removeRow(i);
                JOptionPane.showMessageDialog(this, "Book deleted successfully");
                clearFields();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Book not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearFields() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        textField5.setText("");
        textField6.setText("");
        textField7.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagement());
    }
}
