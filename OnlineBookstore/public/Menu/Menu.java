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
    System.out.println("1. Enter you email :");
    this.loginEmail = in.nextLine();
    System.out.println("2. Enter password :");
    this.loginPassword = in.nextLine();
  }

  public void adminLogin(){
    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
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

  public void postBookPage(){
    try {
      System.out.println("");
      System.out.println("");
      System.out.println("************** Welcome to AASTU Bookstore **************");
      System.out.println("1. Enter the book name :");
      this.bookName = in.nextLine();
      System.out.println("2. Enter the author name :");
      this.authorName = in.nextLine();
      System.out.println("3. Enter the book's description :");
      this.description = in.nextLine();
      System.out.println("4. Enter the book's published year :");
      this.publishedYear = in.nextLine();
      System.out.println("5. Enter the book's rating it gets so far(only numerical values like 4.4) :");
      this.rating = in.nextDouble();
      in.nextLine();
      System.out.println("6. Enter the book's price you want to be sold( only numerical values like 99.99 ) :");
      this.price = in.nextDouble();
      in.nextLine();
      System.out.println("7. Enter the book's genre :");
      this.genere = in.nextLine();

    } catch (InputMismatchException e) {
      System.err.println("Invalid input! Please enter a valid double value.");
    }
  }

  public void signup(){

    System.out.println("");
    System.out.println("");
    System.out.println("************** Welcome to AASTU Bookstore **************");
    System.out.println("1. Enter your fullname(fname and lname separeted by whitespace.):");
    this.userFullName = in.nextLine();
    System.out.println("2. Enter your email :");
    this.userEmail = in.nextLine();
    System.out.println("3. Enter a password :");
    this.userPassword = in.nextLine();
    System.out.println("4. Enter your Mobile NO :");
    this.userMobile = in.nextLine();
    System.out.println("5. Enter your Telegram handler :");
    this.userTelegramHandler = in.nextLine();
    System.out.println("5. Go back to main menu :");
    this.mainMenu();
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
