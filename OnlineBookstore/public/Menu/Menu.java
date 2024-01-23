package Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

  private String userFullName;
  private String userEmail;
  private String userPassword;
  private String userMobile;
  private String userTelegramHandler;


  private String loginEmail;
  private String loginPassword;

  private String adminEmail;
  private String adminPassword1;
  private String adminPassword2;

  private String bookName;
  private String authorName;
  private String description;
  private double rating;
  private String publishedYear;
  private String genere;
  private double price; 

  private String buyYorN;
  

  Scanner in = new Scanner(System.in);


  public void mainMenu(){
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("1. Login");
    System.out.println("2. Signup");
    System.out.println("3. Login as Admin");
    System.out.println("4. About Us");
    System.out.println("5. Exit");
    System.out.println("");

  }

  public void login(){
    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("--------------------------------------------------");
    System.out.println("|         Demo Login email : ayele@gmail.com      |");
    System.out.println("|         Demo Login password : 12345             |");
    System.out.println("--------------------------------------------------");
    System.out.println("");
    System.out.println("1. Enter you email :");
    this.loginEmail = in.nextLine();
    System.out.println("2. Enter password :");
    this.loginPassword = in.nextLine();
  }

  public void adminLogin(){
    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("-------------------------------------------------");
    System.out.println("|     Demo Admin email : brex12@gmail.com        |");
    System.out.println("|     Demo Admin password1 : qwertyui            |");
    System.out.println("|     Demo Admin password1 : 12345678            |");
    System.out.println("-------------------------------------------------");
    System.out.println("");
    System.out.println("1. Enter admin email :");
    this.adminEmail = in.nextLine();
    System.out.println("2. Enter password 1 :");
    this.adminPassword1 = in.nextLine();
    System.out.println("3. Enter password 2 :");
    this.adminPassword2 = in.nextLine();
  }

  public void adminpage(){
    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("1. Delete User");
    System.out.println("2. Delete Book");
    System.out.println("3. Show available Books.");
    System.out.println("4. Go Back");
    System.out.println("");
    System.out.println("Enter your choice:");
  }

  public void bookPage(){
    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("1. Buy a book.");
    System.out.println("2. Post/Sell a book.");
    System.out.println("3. Search a book.");
    System.out.println("4. Go back.");
    System.out.println("");
  }

  public void buyBook(){
    System.out.print("Wanna buy a book? (Y/N) :");
    this.buyYorN = in.nextLine();
  }

  private void checker(String menu, String description, String property){
    while (true) {
      System.out.println(menu);
      property = in.nextLine();
      if(property.equals("")|| property.equals(null) || property.equals(" ")){
        System.out.println(description);
      }else{
        break;
      }
    }
  }

  public void postBookPage(){

      System.out.println("");
      System.out.println("");
      System.out.println("************** Welcome to AASTU Bookstore **************");

      while (true) {
        System.out.println("1. Enter the book name *:");
        this.bookName = in.nextLine();
        if(this.bookName.equals("")|| this.bookName.equals(null) || this.bookName.equals(" ")){
          System.out.println("Book Name is a required field.");
        }else{
          break;
        }
      }

      while (true) {
        System.out.println("2. Enter the author name *:");
        this.authorName = in.nextLine();
        if(this.authorName.equals("")|| this.authorName.equals(null) || this.authorName.equals(" ")){
          System.out.println("Author Name is a required field.");
        }else{
          break;
        }
      }

      while (true) {
        System.out.println("3. Enter the book's description *:");
        this.description = in.nextLine();
        if(this.description.equals("")|| this.description.equals(null) || this.description.equals(" ")){
          System.out.println("Book's description is a required field.");
        }else{
          break;
        }
      }
         
      checker("4. Enter the book's published year *:","Book's published year is a required field." , this.publishedYear);
      
      while (true) {
        try {
          System.out.println("5. Enter the book's rating it gets so far(only numerical values like 4.4) :");
          this.rating = in.nextDouble();
          if(this.rating < 0 ){
            System.out.println("Book's rating can not be nagative.");
          }else{
            break;
          }
        } catch (InputMismatchException e) {
          System.out.println("invalid character!");
          this.rating = 0.0;
          in.nextLine();
        }
      }
      
      
      while (true) {
        try {
          System.out.println("6. Enter the book's price you want to be sold( only numerical values like 99.99 ) :");
          this.price = in.nextDouble();
          if(this.price < 0 ){
            System.out.println("Book's price can not be nagative.");
          }else{
            in.nextLine();
            break;
          }
        } catch (InputMismatchException e) {
          System.out.println("invalid character!");
          this.price = 0.0;
          in.nextLine();
        }
      }
      
      while (true) {
        System.out.println("7. Enter the book's genre *:");
        this.genere = in.nextLine();
        if(this.genere.equals(null) || this.genere.equals("") || this.genere.equals(" ")){
          System.out.println("Book's genre is required field.");
        } else {
          break;
        }
      }



  }

  public void signup(){

    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("");
    System.out.println("");
    
    
    
    while (true) {
      System.out.println("1. Enter your fullname(fname and lname separeted by whitespace.):");
      this.userFullName = in.nextLine();
      if(this.userFullName.equals("")|| this.userFullName.equals(null) || this.userFullName.equals(" ")){
        System.out.println("Name is a required field.");
      }else{
        break;
      }
    }
    

    while (true) {
      System.out.println("2. Enter your email :");
      this.userEmail = in.nextLine();
      if(this.userEmail.equals("")|| this.userEmail.equals(null) || this.userEmail.equals(" ")){
        System.out.println("Email is a required field.");
      }else{
        break;
      }
    }
    
    

    while (true) {
      System.out.println("3. Enter a password :");
      this.userPassword = in.nextLine();
      if(this.userPassword.equals("")|| this.userPassword.equals(null) || this.userPassword.equals(" ")){
        System.out.println("Password is a required field.");
      }else{
        break;
      }
    }

    while (true) {
      System.out.println("4. Enter your Mobile NO :");
      this.userMobile = in.nextLine();
      if(this.userMobile.equals("")|| this.userMobile.equals(null) || this.userMobile.equals(" ")){
        System.out.println("Mobile no. is a required field.");
      }else{
        break;
      }
    }
  

    while (true) {
      System.out.println("5. Enter your Telegram handler (*):");
      this.userTelegramHandler = in.nextLine();
      if(this.userTelegramHandler.equals("")|| this.userTelegramHandler.equals(null) || this.userTelegramHandler.equals(" ")){
        System.out.println("Telegram handler is a required field.");
      }else{
        break;
      }
    }

    
  }

  public void printAboutUsParagraph() {
    String aboutUsParagraph =
            "Welcome to AASTU Online Bookstore, your premier destination for a world of knowledge and literary exploration. "
            + "At AASTU, we believe in the transformative power of books, and our online bookstore is designed to provide "
            + "a seamless and enriching experience for every book enthusiast. As an integral part of Addis Ababa Science "
            + "and Technology University (AASTU), our bookstore is committed to offering a diverse collection of books that "
            + "cater to various tastes and interests. Whether you're a student seeking textbooks, a professional delving into "
            + "the latest industry insights, or a leisure reader in pursuit of literary gems, AASTU Online Bookstore is your "
            + "virtual haven. Our curated selection spans genres, authors, and subjects, ensuring that you embark on a captivating "
            + "journey through the written word. Enjoy the convenience of online shopping with us, where a world of knowledge is "
            + "just a click away. Join us in fostering a culture of learning, discovery, and intellectual growth. AASTU Online "
            + "Bookstore – Your Gateway to Infinite Wisdom.";

    // Print the formatted paragraph to the console
    System.out.println(aboutUsParagraph);
  }


  public String getBuyYorN(){
    return this.buyYorN.toLowerCase();
  }

  public String getBookName(){
    return this.bookName;
  }

  public String getAuthorName(){
    return this.authorName;
  }

  public String getDescription(){
    return this.description;
  }

  public double getRating(){
    return this.rating;
  }

  public String getPublishedYear(){
    return this.publishedYear;
  }

  public String getGenere(){
    return this.genere;
  }

  public double getPrice(){
    return this.price;
  }


  public String getAdminEmail(){
    return this.adminEmail;
  }

  public String getAdminPassword1(){
    return this.adminPassword1;
  }

  public String getAdminPassword2(){
    return this.adminPassword2;
  }

  public String getLoginEmail(){
    return this.loginEmail;
  }

  public String getLoginPassword(){
    return this.loginPassword;
  }

  public String getUserFullName(){
    return this.userFullName;
  }

  public String getUserEmail(){
    return this.userEmail;
  }

  public String getUserPassword(){
    return this.userPassword;
  }

  public String getUserMobileNumber(){
    return this.userMobile;
  }

  public String getTelegramHandler(){
    return this.userTelegramHandler;
  }


}
