package view;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainScreen implements Initializable
{
    @FXML private AnchorPane panelHomepage;
    @FXML private AnchorPane panelBooks;
    @FXML private AnchorPane panelBorrow;
    @FXML private AnchorPane panelReturn;
    @FXML private AnchorPane panelLibraryInformation;
    @FXML private AnchorPane panelAddBook;
    @FXML private AnchorPane panelExit;
    @FXML private AnchorPane panelProfile;
    @FXML private Label lblHomepage;
    @FXML private Label lblBooks;
    @FXML private Label lblBorrowBook;
    @FXML private Label lblReturnBook;
    @FXML private Label lblAddBook;
    @FXML private Label lblLibraryInformation;
    @FXML private Label lblExit;
    @FXML private TableView<Books> tableBook;
    @FXML private TableColumn<Books, String> columnBookBookName;
    @FXML private TableColumn<Books, String> columnBookAuthorName;
    @FXML private TableColumn<Books, String> columnBookNumberOfPage;
    @FXML private TableColumn<Books, String> columnBookTitle;
    @FXML private TextField textBookName;
    @FXML private TextField textAuthorName;
    @FXML private TextField textNumberOfPage;
    @FXML private ComboBox<String> comboBoxType;
    @FXML private TextField textAdminUserName;
    @FXML private PasswordField textAdminPassword;
    @FXML private TextField textBookSearch;
    @FXML private TextField textBorrowUserName;
    @FXML private TextField textBorrowBookName;
    @FXML private TextField textReturnBookName;
    @FXML private TextField textReturnUserName;
    @FXML private Label textNumberOfBook;
    @FXML private Label labelNumberOfUser;
    @FXML private Label labelName;
    @FXML private Label labelSurname;
    @FXML private Label textProfilName;
    @FXML private Label textProfilUserName;
    @FXML private Label textProfilBorrowBook;
    @FXML private Button btnProfil;
    @FXML private Button btnBorrow;
    @FXML private Button btnReturn;
    @FXML private Button btnAdd;
    @FXML private Button btnExit;

    ObservableList<Books> listBooks;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        bookNumber();                                                      // Kitap Sayısını verir.
        userNumber();                                                      // Kullanıcı sayısını verir.

        labelName.setText(Login.nowNameAndSurname);                        // İsmi ve soyismi anaekrana yazdırır.
        labelSurname.setText(Login.nowUserName);                           // Kullanıcıyı anaekrana yazdırır.

        addComboBoxType();                                                 //Kitap ekle kısmındaki combobox'a değerler atar.
        comboBoxType.setValue("Title");                                     //Combobox boş kalmaması için

        textProfilUserName.setText(Login.nowUserName);                     //Profil kısmına kullanıcı adını ekleme
        textProfilName.setText(Login.nowNameAndSurname);                   //Profil ekranına isim-soyisim yazdırma

        profilBorrowBook();                                                //Profilde ödünç alınan kitabı yazması.
        ViewTable();                                                       //Tabloya kitapları aktarır.
    }

    private int menu = 1;

    @FXML
    void menuHomePageClick(MouseEvent event)
    {
        menuControl();
      panelHomepage.setVisible(true);
        menu = 1;
    }
    @FXML
    void menuExitClick(MouseEvent event)
    {
        menuControl();
        panelExit.setVisible(true);
        menu = 2;
    }
    @FXML
    void menuReturnClick(MouseEvent event)
    {
        menuControl();
        panelReturn.setVisible(true);
        menu = 3;
    }
    @FXML
    void menuAddBookClick(MouseEvent event)
    {
        menuControl();
        panelAddBook.setVisible(true);
        menu = 4;
    }
    @FXML
    void menuBooksClick(MouseEvent event)
    {
        menuControl();
        panelBooks.setVisible(true);
        menu = 5;
    }
    @FXML
    void menuLibraryInformationClick(MouseEvent event)
    {
        menuControl();
        panelLibraryInformation.setVisible(true);
        menu = 6;
    }
    @FXML
    void menuBorrowClick(MouseEvent event)
    {
        menuControl();
        panelBorrow.setVisible(true);
        menu = 7;
    }
    @FXML
    void menuProfileClick(MouseEvent event)
    {
        menuControl();
        panelProfile.setVisible(true);
        menu = 8;
    }
    public void menuControl()
    {
        switch (menu)
        {
            case 1:
                panelHomepage.setVisible(false);
                break;
            case 2:
                panelExit.setVisible(false);
                break;
            case 3:
                panelReturn.setVisible(false);
                break;
            case 4:
                panelAddBook.setVisible(false);
                break;
            case 5:
                panelBooks.setVisible(false);
                break;
            case 6:
                panelLibraryInformation.setVisible(false);
                break;
            case 7:
                panelBorrow.setVisible(false);
                break;
            case 8:
                panelProfile.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void addComboBoxType()
    {
        ObservableList<String> list = FXCollections.observableArrayList("ROMAN","BİYOGRAFİ","HİKAYE","FELSEFE","KİŞİSEL GELİŞİM","POLİSİYE","TARİH","MASAL","ŞİİR","KURGU","MACERA");
        comboBoxType.setItems(list);
    }

    public void ViewTable()
    {
        columnBookBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        columnBookAuthorName.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        columnBookNumberOfPage.setCellValueFactory(new PropertyValueFactory<>("numberOfPage"));
        columnBookTitle.setCellValueFactory(new PropertyValueFactory<>("type"));

        try{
            BooksManager booksManager = new BooksManager();
            listBooks = booksManager.getDataBooks();
            tableBook.setItems(listBooks);
        }catch (SQLException exception)
        {
            System.out.println("error");
        }

        FilteredList<Books> filteredList = new FilteredList<>(listBooks, e->true);
        textBookSearch.setOnKeyReleased(e ->{
            textBookSearch.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredList.setPredicate((Predicate<? super Books>) Books ->{
                    if(newValue ==null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toUpperCase();
                    if(Books.getBookName().contains(lowerCaseFilter)){
                        return true;
                    }else if(Books.getAuthorName().contains(lowerCaseFilter)){
                        return true;
                    }else if(Books.getNumberOfPage().contains(lowerCaseFilter)){
                        return true;
                    }
                    else if(Books.getType().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            } );
            SortedList<Books> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tableBook.comparatorProperty());
            tableBook.setItems(sortedList);
        });
    }

    public void bookNumber()
    {
        BooksManager booksManager1 = new BooksManager();
        textNumberOfBook.setText(Integer.toString(booksManager1.numberOfBook()));
    }

    public void userNumber()
    {
        UsersManager usersManager = new UsersManager();
        labelNumberOfUser.setText(Integer.toString(usersManager.numberOfUser()));
    }

    public void profilBorrowBook()
    {
        BorrowBookManager borrowBookManager = new BorrowBookManager();
        textProfilBorrowBook.setText(borrowBookManager.userNameControl(Login.nowUserName));
    }

    @FXML
    public void clickExit(ActionEvent event)
    {
        System.exit(0);
    }

    @FXML
    public int addBookClick(ActionEvent event)
    {
        try{
            Integer.parseInt(textNumberOfPage.getText());
        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Enter the number of pages correctly");
            return 0;
        }
        if(comboBoxType.getValue().equals("Title") || textBookName.getText().equals("") || textAuthorName.getText().equals("") || textNumberOfPage.getText().equals("") || textAdminUserName.getText().equals("") || textAdminPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Missing Entry!!!");
        }
        else if(textAdminUserName.getText().equals("Admin") && textAdminPassword.getText().equals("123456"))
        {
            BooksManager booksManager = new BooksManager();
            booksManager.insertBook(textBookName.getText().toUpperCase(),textAuthorName.getText().toUpperCase(),textNumberOfPage.getText(),comboBoxType.getValue().toUpperCase());
            JOptionPane.showMessageDialog(null,"Added");
            textBookName.setText("");
            textAuthorName.setText("");
            textNumberOfPage.setText("");
            comboBoxType.setValue("Title");
            textAdminUserName.setText("");
            textAdminPassword.setText("");
            ViewTable();
            bookNumber();
            userNumber();
        }
        else {
            JOptionPane.showMessageDialog(null,"Incorrect or Incomplete Entry!!!");
        }
        return 0;
    }

    @FXML
    public void borrowBook(ActionEvent event)
    {
        BooksManager booksManager = new BooksManager();
        BorrowBookManager borrowBookManager = new BorrowBookManager();
        int control1 = booksManager.bookControl(textBorrowBookName.getText().toUpperCase());
        String control2 = borrowBookManager.userNameControl(Login.nowUserName);
        if(control1==0 && control2.equals("NO") && Login.nowUserName.equals(textBorrowUserName.getText()))
        {
            booksManager.booksData(textBorrowBookName.getText().toUpperCase(),Login.nowUserName);
            JOptionPane.showMessageDialog(null,"Book Received");
            textBorrowBookName.setText("");
            textBorrowUserName.setText("");
            bookNumber();
            userNumber();
            ViewTable();
            profilBorrowBook();
        }
        else {
            if(control1 == 1) {
                JOptionPane.showMessageDialog(null,"Such a book does not exist.");
            }
            else if(!control2.equals("NO")) {
                JOptionPane.showMessageDialog(null,"You already have a book that you are borrowing right now.");
            }
            else if(!Login.nowUserName.equals(textBorrowUserName.getText())) {
                JOptionPane.showMessageDialog(null, "Username is incorrect");
            }
        }
    }

    @FXML
    public void returnBook(ActionEvent event)
    {
        BorrowBookManager borrowBookManager = new BorrowBookManager();
        int control = borrowBookManager.borrowBookControl(textReturnBookName.getText().toUpperCase());
        if(control==0 && Login.nowUserName.equals(textReturnUserName.getText()))
        {
            borrowBookManager.borrowBookData(textReturnBookName.getText().toUpperCase());
            JOptionPane.showMessageDialog(null,"Book Returned");
            textReturnBookName.setText("");
            textReturnUserName.setText("");
            textProfilBorrowBook.setText("NO");
            bookNumber();
            userNumber();
            ViewTable();
        }
        else {
            if(control == 1){
                JOptionPane.showMessageDialog(null,"No Such Book Has Been Borrowed");
            }
            else if(!Login.nowUserName.equals(textReturnUserName.getText())) {
                JOptionPane.showMessageDialog(null,"Incorrect Username Entry");
            }
        }
    }

  //Codes written to set the background color of the buttons.
    @FXML
    public void enteredHomepage (MouseEvent event) { lblHomepage.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredBooks (MouseEvent event) { lblBooks.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredBorrowBook (MouseEvent event) { lblBorrowBook.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredReturnBook (MouseEvent event) { lblReturnBook.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredAddBook (MouseEvent event) { lblAddBook.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredLibraryInformation (MouseEvent event) { lblLibraryInformation.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void enteredExit (MouseEvent event) { lblExit.styleProperty().set("-fx-background-color: #9FA8DA"); }
    @FXML
    public void exitedHomepage (MouseEvent event) { lblHomepage.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedBooks (MouseEvent event) { lblBooks.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedBorrowBook (MouseEvent event) { lblBorrowBook.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedReturnBook (MouseEvent event) { lblReturnBook.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedAddBook (MouseEvent event) { lblAddBook.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedLibraryInformation (MouseEvent event) { lblLibraryInformation.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void exitedExit (MouseEvent event) { lblExit.styleProperty().set("-fx-background-color: #5C6BC0"); }
    @FXML
    public void btnEnteredProfile (MouseEvent event) { btnProfil.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedProfile (MouseEvent event) { btnProfil.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredBorrow (MouseEvent event) { btnBorrow.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedBorrow (MouseEvent event) { btnBorrow.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredReturn (MouseEvent event) { btnReturn.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedRetrun (MouseEvent event) { btnReturn.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredAdd (MouseEvent event) { btnAdd.styleProperty().set("-fx-background-color: #3F51B5"); }
    @FXML
    public void btnExitedAdd (MouseEvent event) { btnAdd.styleProperty().set("-fx-background-color: #283593"); }
    @FXML
    public void btnEnteredExit (MouseEvent event) { btnExit.styleProperty().set("-fx-background-color: #E57373"); }
    @FXML
    public void btnExitedExit (MouseEvent event) { btnExit.styleProperty().set("-fx-background-color:  #F44336"); }
}