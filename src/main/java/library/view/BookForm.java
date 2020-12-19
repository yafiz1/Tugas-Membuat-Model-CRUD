/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import library.database.Database;
import library.model.Book;

/**
 *
 * @author user
 */
public class BookForm extends JFrame {
    
    private Book book;
    private JButton jButtonSave;
    
    private JTextField jTextFieldBookName;
    private JTextField jTextFieldBookAuthor;
    private JTextField jTextFieldBookPage;
    private JTextField jTextFieldPublisher;
    
    private JLabel jLabelBookForm;
    private JLabel jLabelBookName;
    private JLabel jLabelBookAuthor;
    private JLabel jLabelBookPage;
    private JLabel jLabelPublisher;
    
    private Database database;
    
    public BookForm() {
        initComponent();
        setLayout(null);
        setTitle("Form Buku");
        setSize(300,300);
        setVisible(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void updateBook(int id){
        ArrayList<Book> result = database.searchData(id);
        
        book = new Book();
        book.setBookId(id);
        jTextFieldBookName.setText(result.get(0).getBookName());
        jTextFieldBookAuthor.setText(result.get(0).getBookAuthor());
        jTextFieldBookPage.setText(String.valueOf(result.get(0).getBookPage()));
        jTextFieldPublisher.setText(result.get(0).getBookPublisher());
    }
    
    public void deleteBook(int id){
        database.delete(id);
        BookData.updateData();
    }
    
    private void initComponent(){
        database = new Database();
        
        jButtonSave = new JButton();

        jTextFieldBookName = new JTextField();
        jTextFieldBookAuthor = new JTextField();
        jTextFieldBookPage = new JTextField();
        jTextFieldPublisher = new JTextField();

        jLabelBookForm = new JLabel();
        jLabelBookName = new JLabel();
        jLabelBookAuthor = new JLabel();
        jLabelBookPage = new JLabel();
        jLabelPublisher = new JLabel();
        
        jButtonSave.setText("Simpan");
        jButtonSave.addActionListener((ActionEvent arg0) -> {
            if (book == null) {
                book = new Book();
                book.setBookName(jTextFieldBookName.getText());
                book.setBookAuthor(jTextFieldBookAuthor.getText());
                book.setBookPage(Integer.parseInt(jTextFieldBookPage.getText()));
                book.setBookPublisher(jTextFieldPublisher.getText());

                if(database.create(book)){
                    System.out.println("Success");
                    BookData.updateData();
                }else System.out.println("Failed");  
            }else{
                book.setBookName(jTextFieldBookName.getText());
                book.setBookAuthor(jTextFieldBookAuthor.getText());
                book.setBookPage(Integer.parseInt(jTextFieldBookPage.getText()));
                book.setBookPublisher(jTextFieldPublisher.getText());
                if(database.update(book)){
                    System.out.println("Success");
                    BookData.updateData();
                }else System.out.println("Failed");
            }
            
        });
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent evt) {
                jTextFieldBookName.setText("");
                jTextFieldBookAuthor.setText("");
                jTextFieldBookPage.setText("");
                jTextFieldPublisher.setText("");
                book = null;
            }
        });
        
        jLabelBookForm.setText("FORM BUKU");
        jLabelBookForm.setFont(new Font("Tahoma", Font.BOLD, 18));
        
        jLabelBookName.setText("Nama Buku");
        jLabelBookAuthor.setText("Penulis");
        jLabelBookPage.setText("Halaman");
        jLabelPublisher.setText("Penerbit");
    
        jTextFieldBookName.setBounds(135, 76, 125, 25);
        jTextFieldBookAuthor.setBounds(135, 113, 125, 25);
        jTextFieldBookPage.setBounds(135, 150, 125, 25);
        jTextFieldPublisher.setBounds(135, 187, 125, 25);
        
        jLabelBookForm.setBounds(95, 27, 110, 21);
        jLabelBookName.setBounds(39, 81, 125, 15);
        jLabelBookAuthor.setBounds(39, 118, 125, 15);
        jLabelBookPage.setBounds(39, 155, 125, 15);
        jLabelPublisher.setBounds(39, 192, 125, 15);
        
        jButtonSave.setBounds(135,220,125,30);
        
        add(jButtonSave);
        add(jLabelBookAuthor);
        add(jLabelBookName);
        add(jLabelBookPage);
        add(jLabelPublisher);
        add(jLabelBookForm);
        add(jTextFieldBookAuthor);
        add(jTextFieldBookName);
        add(jTextFieldBookPage);
        add(jTextFieldPublisher);
    }
    
}
