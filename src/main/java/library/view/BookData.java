/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.view;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import library.database.Database;
import library.model.Book;

/**
 *
 * @author user
 */
public class BookData extends JFrame {
    private static JTable jTableBooks;
    private static JScrollPane scrollPane;
    
    private JButton jButtonAdd;
    private JButton jButtonUpdate;
    private JButton jButtonDelete;
    
    private BookForm bookForm;
    
    private static int[] book_id;
    
    private static Database database;
    
    public BookData(){
        initComponent();
        setLayout(null);
        setTitle("Data Buku");
        setSize(500,400);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        updateData();
    }
    
    public static final void updateData(){
        ArrayList<Book> books = database.read();
        String[] headerTable = {"Judul Buku","Penulis","Halaman","Penerbit"};
        Object[][] data = new Object[books.size()][database.getFIELD_NAME().length];
        
        book_id = new int[books.size()];
 
        for (int i = 0; i < books.size(); i++) {
            book_id[i] = books.get(i).getBookId();
            data[i][0] = books.get(i).getBookName();
            data[i][1] = books.get(i).getBookAuthor();
            data[i][2] = books.get(i).getBookPage();
            data[i][3] = books.get(i).getBookPublisher();
        }
        
        DefaultTableModel dtm = new DefaultTableModel(data, headerTable);
        jTableBooks.setModel(dtm);
    }
    
    private void initComponent(){
        database = new Database();
        
        bookForm = new BookForm();
        
        jTableBooks = new JTable();
        jTableBooks.setFillsViewportHeight(true);
        jTableBooks.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
               jButtonUpdate.setEnabled(true);
               jButtonDelete.setEnabled(true);
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
            }
        });
        
        scrollPane = new JScrollPane(jTableBooks);
        
        jButtonAdd = new JButton();
        jButtonAdd.setText("Tambah");
        jButtonAdd.addActionListener((ActionEvent arg0) -> {
            bookForm.setVisible(true);
        });
        
        jButtonUpdate = new JButton();
        jButtonUpdate.setText("Edit");
        jButtonUpdate.setEnabled(false);
        jButtonUpdate.addActionListener((ActionEvent arg0) -> {
            bookForm.updateBook(book_id[jTableBooks.getSelectedRow()]);
            bookForm.setVisible(true);
            jButtonUpdate.setEnabled(false);
            jButtonDelete.setEnabled(false);
        });
        
        jButtonDelete = new JButton();
        jButtonDelete.setText("Hapus");
        jButtonDelete.setEnabled(false);
        jButtonDelete.addActionListener((ActionEvent arg0) -> {
            bookForm.deleteBook(book_id[jTableBooks.getSelectedRow()]);
            jButtonUpdate.setEnabled(false);
            jButtonDelete.setEnabled(false);
        });
        
        jButtonAdd.setBounds(70, 310, 100, 30);
        jButtonUpdate.setBounds(190, 310, 100, 30);
        jButtonDelete.setBounds(310, 310, 100, 30);
        scrollPane.setBounds(0,0,500,300);
        
        add(scrollPane);
        add(jButtonAdd);
        add(jButtonUpdate);
        add(jButtonDelete);
    }
}
