import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 

import Menu.Menu;

class BookDataHolder {
  private int id;
  private String bookName;
  private String authorName;
  private String description;
  private double rating;
  private String publishedYear;
  private String genre;
  private double price;

  public BookDataHolder(int id, String bookName, String authorName, String description, double rating, String publishedYear, String genre, double price) {
      this.id = id;
      this.bookName = bookName;
      this.authorName = authorName;
      this.description = description;
      this.rating = rating;
      this.publishedYear = publishedYear;
      this.genre = genre;
      this.price = price;
  }

  // Constructor without ID for writing purposes
  public BookDataHolder(String bookName, String authorName, String description, double rating, String publishedYear, String genre, double price) {
      this(0, bookName, authorName, description, rating, publishedYear, genre, price);
  }

  public String getBookName(){
    return bookName;
  }

  public String getAuthorName(){
    return authorName;
  }

  public String getDescription(){
    return description;
  }

  public double getRating(){
    return rating;
  }

  public String getPublishedYear(){
    return publishedYear;
  }

  public String getGenre(){
    return genre;
  }

  public double getPrice(){
    return price;
  }
  

  public int getId() {
      return id;
  }

  public void setId(int id) {
      this.id = id;
  }

  @Override
  public String toString() {
    // Replace spaces with hyphens in authorName and description
    String formattedBookName = bookName.replace(" ", "-");
    String formattedAuthorName = authorName.replace(" ", "-");
    String formattedDescription = description.replace(" ", "-");
    String formmatedGenre = genre.replace(" ", "-");

    return id + " " + formattedBookName + " " + formattedAuthorName + " " + formattedDescription + " " + rating + " " + publishedYear + " " + formmatedGenre + " " + price;
  }

  public void printFormatted() {

    System.out.printf("%-5s %-30s %-20s %-50s %.1f      %-20s %-15s %.2f%n",
            id, bookName, authorName, description, rating, publishedYear, genre, price);
  }
}

class BookManager {

  private static boolean isInteger(String s) {
    try {
        Integer.parseInt(s);
        return true;
    } catch (NumberFormatException e) {
        return false;
    }
  }
  
  public static List<BookDataHolder> readFromFileBook(String filePath) {
      List<BookDataHolder> dataHolderList = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
          String line;
          while ((line = reader.readLine()) != null) {
              String[] parts = line.trim().split("\\s+");
              if (parts.length == 8) {
                  int id = Integer.parseInt(parts[0]);
                  String bookName = parts[1].replace("-", " ");
                  String authorName = parts[2].replace("-", " ");
                  String description = parts[3].replace("-", " ");
                  double rating = Double.parseDouble(parts[4]);
                  String publishedYear = parts[5];
                  String genre = parts[6].replace("-", " ");
                  double price = Double.parseDouble(parts[7]);

                  dataHolderList.add(new BookDataHolder(id, bookName, authorName, description, rating, publishedYear, genre, price));
              } 
          }
      } catch (IOException | NumberFormatException e) {
          e.printStackTrace();
      }

      return dataHolderList;
  }

  public static void writeToFileBook(String filePath, List<BookDataHolder> dataHolderList) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        int currentHighestId = 1;

        // Find the current highest ID
        List<BookDataHolder> existingBooks = readFromFileBook(filePath);
        for (BookDataHolder dataHolder : existingBooks) {
            currentHighestId = Math.max(currentHighestId, dataHolder.getId());
        }

        for (BookDataHolder dataHolder : dataHolderList) {
          int newId = ++currentHighestId;
          dataHolder.setId(newId);

          writer.write(dataHolder.toString());
          writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }

  public static BookDataHolder searchBookById(String filePath, int targetId) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");

            if (parts.length > 0 && isInteger(parts[0])) {
                int id = Integer.parseInt(parts[0]);

                if (id == targetId) {
                    // Found the book with the target ID, create and return a BookDataHolder object
                    return createBookFromParts(parts);
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // If the book with the target ID is not found, return null
    return null;
}

  private static BookDataHolder createBookFromParts(String[] parts) {
    // Parse the book details from the parts array
    int id = Integer.parseInt(parts[0]);
    String bookName = parts[1].replace("-", " ");
    String authorName = parts[2].replace("-", " ");
    String description = parts[3].replace("-", " ");
    double rating = Double.parseDouble(parts[4]);
    String publishedYear = parts[5];
    String genre = parts[6].replace("-", " ");
    double price = Double.parseDouble(parts[7]);

    return new BookDataHolder(id, bookName, authorName, description, rating, publishedYear, genre, price);
  }

  public static void sendEmail(BookDataHolder book, String email) {
    String to = email; // Replace with the recipient's email address
    String from = "aastutesting@gmail.com"; // Replace with your Gmail email address
    String host = "smtp.gmail.com";

    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);
    properties.setProperty("mail.smtp.port", "587");
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.starttls.enable", "true");

    Session session = Session.getDefaultInstance(properties, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("aastutesting@gmail.com", "ayadabkmjzrigfqt");
        }
    });

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Book Details");

        // Construct the email body with book details
        String emailBody = "Book Name: " + book.getBookName() + "\n" +
                "Author: " + book.getAuthorName() + "\n" +
                "Rating: " + book.getRating() + "birr" +"\n" +
                "Published Year: " + book.getPublishedYear() + "\n" +
                "Genre: " + book.getGenre() + "\n" +
                "Price: " + book.getPrice();

        message.setText(emailBody);

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully.");

    } catch (MessagingException mex) {
        mex.printStackTrace();
    }
  }

  public static List<BookDataHolder> searchByKeyWord(String filePath, String keyword) {
    List<BookDataHolder> result = new ArrayList<>();
    boolean found = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (containsAllKeywords(parts, keyword)) {
                found = true;
                int id = Integer.parseInt(parts[0]);
                String bookName = parts[1].replace("-", " ");
                String authorName = parts[2].replace("-", " ");
                String description = parts[3].replace("-", " ");
                double rating = Double.parseDouble(parts[4]);
                String publishedYear = parts[5];
                String genre = parts[6].replace("-", " ");
                double price = Double.parseDouble(parts[7]);

                result.add(new BookDataHolder(id, bookName, authorName, description, rating, publishedYear, genre, price));
            }
        }
    } catch (IOException | NumberFormatException e) {
        e.printStackTrace();
    }

    if (!found) {
        System.out.println("Book not found for keyword: " + keyword);
    }

    return result;
}

  private static boolean containsAllKeywords(String[] parts, String keyword) {
      String[] keywords = keyword.toLowerCase().split("\\s+");
      List<String> bookInfo = Arrays.asList(parts).subList(1, parts.length);

      for (String key : keywords) {
          boolean found = false;
          for (String info : bookInfo) {
              if (info.toLowerCase().contains(key)) {
                  found = true;
                  break;
              }
          }
          if (!found) {
              return false;
          }
      }
      return true;
  }

  public static void deleteBookById(String filePath, int bookId) {
    List<BookDataHolder> books = readFromFileBook(filePath);
    boolean bookFound = books.removeIf(book -> book.getId() == bookId);

    if (bookFound) {
        deleteBookWithReplacement(filePath, books);
        System.out.println("Book with ID " + bookId + " has been deleted.");
    } else {
        System.out.println("Book with ID " + bookId + " not found.");
    }
  }

  private static void deleteBookWithReplacement(String filePath, List<BookDataHolder> dataHolderList) {
    String newFilePath = "bookNew.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFilePath))) {
        int currentHighestId = 0;

        // Increment IDs starting from the current highest ID
        for (BookDataHolder dataHolder : dataHolderList) {
            int newId = ++currentHighestId;
            dataHolder.setId(newId);

            writer.write(dataHolder.toString());
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Replace the old file with the new one
    replaceFile(filePath, newFilePath);
  }

  private static void replaceFile(String oldFilePath, String newFilePath) {
    try {
        Files.move(Path.of(newFilePath), Path.of(oldFilePath), StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}

class DataHolder {
  private String userFullName;
  private String userEmail;
  private String userPassword;
  private String userMobileNo;
  private String userTelegramHandler;

  public DataHolder(String userFullName, String userEmail, String userPassword, String userMobile, String userTelegram) {
      this.userFullName = userFullName;
      this.userEmail = userEmail;
      this.userPassword = userPassword;
      this.userMobileNo = userMobile;
      this.userTelegramHandler = userTelegram;
  }

  public String getUserFullName() {
  return userFullName;
  }

  public String getUserEmail() {
      return userEmail;
  }

  public String getUserPassword() {
      return userPassword;
  }

  public String getUserMobileNo() {
      return userMobileNo;
  }

  public String getUserTelegramHandler() {
      return userTelegramHandler;
  }

  @Override
  public String toString() {
      return String.format("Full Name: %s\nEmail: %s\nMobile No: %s\nTelegram: %s", userFullName, userEmail, userMobileNo, userTelegramHandler);
  }

}

class AdminDataHolder {
  private String email;
  private String adminPassword1;
  private String adminPassword2;

  public AdminDataHolder(String email, String password1, String passsword2){
    this.email = email;
    this.adminPassword1 = password1;
    this.adminPassword2 = passsword2;
  }

  public String getAdminEmail(){
    return email;
  }

  public String getAdminPassword1(){
    return adminPassword1;
  }

  public String getAdminPassword2(){
    return adminPassword2;
  }

 
  
}

class AdminManager{
  public static List<DataHolder> readCustomersFromFile(String filePath) {
    List<DataHolder> customerList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 6) {
                String userFullName = parts[0] + " " + parts[1];
                String userEmail = parts[2];
                String userPassword = parts[3];
                String userMobileNo = parts[4];
                String userTelegramHandler = parts[5];

                // Create DataHolder object excluding the password
                DataHolder customer = new DataHolder(userFullName, userEmail, userPassword, userMobileNo, userTelegramHandler);
                customerList.add(customer);
            } 
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return customerList;
  }
}

class FileHandlerForAdmin{
  public static List<AdminDataHolder> readFromFileAdmin(String filePath) {
    List<AdminDataHolder> adminDataHolderList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
     // Print the line being read

            String[] parts = line.trim().split("\\s+");
            if (parts.length == 3) {
                String userAdminEmail = parts[0];
                String password1 = parts[1];
                String password2 = parts[2];
      
                adminDataHolderList.add(new AdminDataHolder(userAdminEmail, password1, password2));
            } 
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return adminDataHolderList;
  }

}

class FileHandler{

  public static void writeToFile(String filePath, List<DataHolder> data) {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
        for (DataHolder dataHolder : data) {
            // Write each field separated by whitespace on the same line
            writer.printf("%s %s %s %s %s%n",
                    dataHolder.getUserFullName(),
                    dataHolder.getUserEmail(),
                    dataHolder.getUserPassword(),
                    dataHolder.getUserMobileNo(),
                    dataHolder.getUserTelegramHandler());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }


  public static List<DataHolder> readFromFile(String filePath) {
    List<DataHolder> dataHolderList = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
           // System.out.println("Read line: " + line); // Print the line being read

            String[] parts = line.trim().split("\\s+");
            if (parts.length == 6) {
                String userFullName = parts[0] +" "+ parts[1];
                String userEmail = parts[2];
                String userPassword = parts[3];
                String userMobileNo = parts[4];
                String userTelegramHandler = parts[5];

                dataHolderList.add(new DataHolder(userFullName, userEmail, userPassword, userMobileNo, userTelegramHandler));
            } 
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return dataHolderList;
  }

  public static void deleteCustomerByFirstName(String filePath, String firstName) {
    List<DataHolder> customers = readFromFile(filePath);
    List<DataHolder> updatedCustomers = new ArrayList<>();

    for (DataHolder customer : customers) {
        if (!customer.getUserFullName().toLowerCase().startsWith(firstName.toLowerCase())) {
            updatedCustomers.add(customer);
        }
    }

    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, false))) {
        for (DataHolder customer : updatedCustomers) {
            writer.printf("%s %s %s %s %s%n",
                    customer.getUserFullName(),
                    customer.getUserEmail(),
                    customer.getUserPassword(),
                    customer.getUserMobileNo(),
                    customer.getUserTelegramHandler());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println("Customer information deleted successfully.");
}

}

public class MainApp{
  public static void showMenu(){

    try (Scanner in = new Scanner(System.in)) {
      Menu menu = new Menu();
      int choice = 0;

      /*
       * Replace this absolute path with yours
       */
      String filePathCustomers = "F:\\Learning-MERN\\Java-without-GUI\\OnlineBookstore\\src\\Files\\customers.txt";
      String filePathBooks = "F:\\Learning-MERN\\Java-without-GUI\\OnlineBookstore\\src\\Files\\books.txt";
      String filePathAdmin = "F:\\Learning-MERN\\Java-without-GUI\\OnlineBookstore\\src\\Files\\admin.txt";


      do{
        menu.mainMenu();
        boolean isValidInput = false;

        while (!isValidInput) {
          try {
            System.out.print("Enter your choice :");
            choice = in.nextInt();
            isValidInput = true;
            in.nextLine();
          } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            // Clear the buffer to avoid infinite loop
            in.nextLine();
          }
        }
        
        switch (choice) {
          case 1:
          menu.login();
          List<DataHolder> readCrenditials = FileHandler.readFromFile(filePathCustomers);
          boolean istrue = false;
          for (DataHolder data : readCrenditials) {
              if(data.getUserEmail().equals(menu.getLoginEmail()) && data.getUserPassword().equals(menu.getLoginPassword())){
                istrue = true;
                int innerChoice = 0;
                do{
                  menu.bookPage();
                  boolean isValidInput1 = false;
                  while (!isValidInput1) {
                    try {
                        System.out.print("Enter your choice: ");
                        innerChoice = in.nextInt();
                        isValidInput1 = true; // Break out of the loop if the input is valid
                        in.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        // Clear the buffer to avoid infinite loop
                        in.nextLine();
                    }
                  }
                  
                  
                  switch (innerChoice) {
                    case 1:
                    List<BookDataHolder> readBooks = BookManager.readFromFileBook(filePathBooks);
                    // "%-5s %-30s %-20s %-50s %.3f %-20s %-15s %.2f%n",
                    System.out.println("");
                    System.out.printf("%-5s %-30s %-20s %-50s %-8s %-20s %-15s %-8s%n",
                    "ID", "Title", "Author", "Description", "Rating", "Published Year", "Genre", "Price");
                    System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                
                    for(BookDataHolder book : readBooks){
                      book.printFormatted();
                    }

                    while (true) {
                      System.out.println("");
                      menu.buyBook();
                      System.out.println("");

                      if (menu.getBuyYorN().equals("n")) {
                          break; // Stop deleting if -1 is entered
                      } else if(menu.getBuyYorN().equals("y")){
                        System.out.print("Enter the bookId you want to buy :");
                        int id = in.nextInt();
                        BookDataHolder foundBook = BookManager.searchBookById(filePathBooks, id);
  
                        if (foundBook != null) {
                          System.out.println("Book Details:");
                          System.out.println("Book Name: " + foundBook.getBookName());
                          System.out.println("Author: " + foundBook.getAuthorName());
                          System.out.println("Rating: " + foundBook.getRating());
                          System.out.println("Published Year: " + foundBook.getPublishedYear());
                          System.out.println("Genre: " + foundBook.getGenre());
                          System.out.println("Price: " + foundBook.getPrice());
                          
                          in.nextLine();
                                  // Send an email with book details
                          System.out.print("Enter your email again to get verification:");
                          String email = in.nextLine();
                          email = email.trim();
                          BookManager.sendEmail(foundBook, email);
                        } else {
                          System.out.println("Book with ID " + id + " not found.");
                        }
                      } else {
    
                        System.out.println("Please enter Y/N!");
                        
                      }
                    }

                    boolean isValid1 = false;

                    while (!isValid1) {
                      try {
                        System.out.print("Press 1 to go back:");
                        int n = in.nextInt();
                        if(n == 1){
                          menu.bookPage();
                          isValid1 = true;
                          in.nextLine();
                        }else {
                          System.out.println("You entered invalid number.");
                        }
                      } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        // Clear the buffer to avoid infinite loop
                        in.nextLine();
                      }
                    }

                      break;
                    case 2: 
                    
                      while (true) {
                        System.out.print("Do you want to post a book (Y/N)?:");
                        String inputChar = in.nextLine();
                        if(inputChar.trim().toLowerCase().equals("y")){
                          menu.postBookPage();
                          List<BookDataHolder> books = new ArrayList<>();
                          books.add(new BookDataHolder(menu.getBookName(), menu.getAuthorName(), menu.getDescription(), menu.getRating(), menu.getPublishedYear(), menu.getGenere(), menu.getPrice()));
                          System.out.println("The book have been added successfully.");
                          BookManager.writeToFileBook(filePathBooks, books);
                          System.out.println("Book added successfully.");
                          List<BookDataHolder> readBooks1 = BookManager.readFromFileBook(filePathBooks);
                          // "%-5s %-30s %-20s %-50s %.3f %-20s %-15s %.2f%n",
                          System.out.printf("%-5s %-30s %-20s %-50s %-8s %-20s %-15s %-8s%n",
                          "ID", "Title", "Author", "Description", "Rating", "Published Year", "Genre", "Price");
                          System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                      
                          for(BookDataHolder book : readBooks1){
                            book.printFormatted();
                          }
                        } else if(inputChar.trim().toLowerCase().equals("n")) {
                          break;
                        } else {
                          System.out.println("Invalid input! please enter a valid answer (y/n):");
                        }
                      }


                    boolean isValid2 = false;

                    while (!isValid2) {
                      try {
                        System.out.print("Press 1 to go back:");
                        int n = in.nextInt();
                        if(n == 1){
                          menu.bookPage();
                          isValid2 = true;
                          in.nextLine();
                        }else {
                          System.out.println("You entered invalid number.");
                        }
                      } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        // Clear the buffer to avoid infinite loop
                        in.nextLine();
                      }
                    }

                      break;
                    case 3:
                      System.out.println("");
                      System.out.println("");
                      System.out.print("Enter a keyword to be searched:");
                      String keyword = in.nextLine();

                      List<BookDataHolder> searchResults = BookManager.searchByKeyWord(filePathBooks, keyword);
                      System.out.printf("%-5s %-30s %-20s %-50s %-8s %-20s %-15s %-8s%n",
                      "ID", "Title", "Author", "Description", "Rating", "Published Year", "Genre", "Price");
                      System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                      for (BookDataHolder book : searchResults) {
                          book.printFormatted();
                      }
                      System.out.println("");
                      System.out.println("");

                      while (true) {
                        try {
                          System.out.print("Press 1 to go back:");
                          int n = in.nextInt();
                          if(n == 1){
                            menu.bookPage();
                            in.nextLine();
                            break;
                          }else {
                            System.out.println("You entered invalid number.");
                          }
                        } catch (InputMismatchException e) {
                          System.out.println("invalid input!");
                          in.nextInt();
                        }
                      }

                      break;
                    case 4:
                      showMenu();
                      break;
                    default:
                    System.out.println("please enter a valid number.(Numerical Value Only)");
                      break;
                  }
                }while(innerChoice != 4);

                return;
              }
              
            }
            if(istrue == false){
              System.out.println("");
              System.out.println("Password or email may be incorrect. Please signup if you have no account.");
            }
            break;
          case 2:
              
                menu.signup();
                List<DataHolder> dataHolderList = new ArrayList<>();
                dataHolderList.add(new DataHolder(
                        menu.getUserFullName(),
                        menu.getUserEmail(),
                        menu.getUserPassword(),
                        menu.getUserMobileNumber(),
                        menu.getTelegramHandler()
                ));
      
                FileHandler.writeToFile(filePathCustomers, dataHolderList);
                System.out.println("");
                
                System.out.println("Data written successfully.");
                System.out.println("You have signed up succesfully. Please enter back and login.");


                while (true) {
                  try {
                    System.out.print("Press 1 to go back:");
                    int n = in.nextInt();
                    if(n == 1){
                      menu.mainMenu();
                      in.nextInt();
                      break;
                    }else {
                      System.out.println("You entered invalid number.");
                    }
                  } catch (NumberFormatException e) {
                    System.err.println("invalid input!");
                  }
                }

                System.out.println("");
              
            
            break;
          case 3:
              menu.adminLogin();
              List<AdminDataHolder> readAdminCreditials = FileHandlerForAdmin.readFromFileAdmin(filePathAdmin);
              for (AdminDataHolder data : readAdminCreditials) {
                  if(data.getAdminEmail().equals(menu.getAdminEmail()) && data.getAdminPassword1().equals(menu.getAdminPassword1()) && data.getAdminPassword2().equals(menu.getAdminPassword2())){
                    int innerChoice = 0;
                    do{
                      menu.adminpage();  
                        while (true) {
                          try {
                            innerChoice = in.nextInt(); 
                            in.nextLine();
                            break;
                          } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid integer.");
                            // Clear the buffer to avoid infinite loop
                            in.nextLine();
                          }
                        
                        }
                      switch (innerChoice) {
                        case 1:
                           // Replace with the actual file path
                          List<DataHolder> customers = AdminManager.readCustomersFromFile(filePathCustomers);
                      
                          // Print header
                          System.out.println("|----------------------|------------------------------|-------------------|----------------------|");
                          System.out.println("|   User Full Name     |          User Email          |   Mobile Number   |  Telegram Handler    |");
                          System.out.println("|----------------------|------------------------------|-------------------|----------------------|");
                      
                          // Loop through the array and print each customer's information without using toString
                          for (DataHolder customer : customers) {
                              System.out.printf("| %-20s | %-29s| %-15s   | %-20s |%n",
                                      customer.getUserFullName(),
                                      customer.getUserEmail(),
                                      customer.getUserMobileNo(),
                                      customer.getUserTelegramHandler());
                          }
                      
                          // Print footer
                          System.out.println("|----------------------|------------------------------|-------------------|----------------------|");
                          System.out.println("");
                          

                          while(true){
                            System.out.print("Do you want to delete a customer? (Y/N):");
                            String inputed = in.nextLine();
                            if(inputed.trim().toLowerCase().equals("y")){
                              System.out.print("Enter the first name of the person you want to delete: ");
                              String firstNameToDelete = in.nextLine();
                              in.nextLine();
                              FileHandler.deleteCustomerByFirstName(filePathCustomers, firstNameToDelete);
    
                               // Replace with the actual file path
                              List<DataHolder> customeres = AdminManager.readCustomersFromFile(filePathCustomers);
                          
                              // Print header
                              System.out.println("|----------------------|------------------------------|-------------------|----------------------|");
                              System.out.println("|   User Full Name     |          User Email          |   Mobile Number   |  Telegram Handler    |");
                              System.out.println("|----------------------|------------------------------|-------------------|----------------------|");
                          
                              // Loop through the array and print each customer's information without using toString
                              for (DataHolder customer : customeres) {
                                  System.out.printf("| %-20s | %-29s| %-15s   | %-20s |%n",
                                          customer.getUserFullName(),
                                          customer.getUserEmail(),
                                          customer.getUserMobileNo(),
                                          customer.getUserTelegramHandler());
                              }
                          
                              // Print footer
                              System.out.println("|----------------------|------------------------------|-------------------|----------------------|");
                              System.out.println("");
                            } else if (inputed.trim().toLowerCase().equals("n")) {
                              break;
                            } else {
                              System.out.println("Invalid value! please enter a valid input yes or no value.");
                            }
                          }

                          while (true) {
                            try {
                              System.out.print("Press 1 to go back:");
                              int n = in.nextInt();
                              if(n == 1){
                                menu.adminpage();
                                in.nextLine();
                                break;
                              }else {
                                System.out.println("You entered an invalid number.");
                              }
                            } catch (InputMismatchException e) {
                              System.out.println("invalid input!");
                              in.nextLine();
                            }
                          }

                          

                           break; 
                        case 2:
                          
                        List<BookDataHolder> readBooks = BookManager.readFromFileBook(filePathBooks);
                        // "%-5s %-30s %-20s %-50s %.3f %-20s %-15s %.2f%n",
                        System.out.printf("%-5s %-30s %-20s %-50s %-8s %-20s %-15s %-8s%n",
                        "ID", "Title", "Author", "Description", "Rating", "Published Year", "Genre", "Price");
                        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    
                        for(BookDataHolder book : readBooks){
                          book.printFormatted();
                        }
                          System.out.println("");
                          // Continuously ask for book ID to delete until the admin decides to stop
                          while (true) {
                              System.out.print("Enter the book ID to delete (or -1 to stop): ");
                              int bookIdToDelete = in.nextInt();

                              if (bookIdToDelete == -1) {
                                  in.nextLine();
                                  break; // Stop deleting if -1 is entered
                              }

                              BookManager.deleteBookById(filePathBooks, bookIdToDelete);
                              readBooks = BookManager.readFromFileBook(filePathBooks);
                              // "%-5s %-30s %-20s %-50s %.3f %-20s %-15s %.2f%n",
                              System.out.printf("%-5s %-30s %-20s %-50s %-8s %-20s %-15s %-8s%n",
                              "ID", "Title", "Author", "Description", "Rating", "Published Year", "Genre", "Price");
                              System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                          
                              for(BookDataHolder book : readBooks){
                                book.printFormatted();
                              }
                                System.out.println("");

                          }
                          
                          while (true) {
                            try {
                              System.out.print("Press 1 to go back:");
                              int n = in.nextInt();
                              if(n == 1){
                                menu.adminpage();
                                break;
                              }else {
                                System.out.println("You entered invalid number.");
                              }
                              
                            } catch (InputMismatchException e) {
                              System.out.println("invalid input!");
                              in.nextLine();
                            }
                          }


                          break;
                        case 3:
                        
                          List<BookDataHolder> readBookes = BookManager.readFromFileBook(filePathBooks);
                          // "%-5s %-30s %-20s %-50s %.3f %-20s %-15s %.2f%n",
                          System.out.printf("%-5s %-30s %-20s %-50s %-8s %-20s %-15s %-8s%n",
                          "ID", "Title", "Author", "Description", "Rating", "Published Year", "Genre", "Price");
                          System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                      
                          for(BookDataHolder book : readBookes){
                            book.printFormatted();
                          }
                            System.out.println("");

                            while (true) {
                              try {
                                System.out.print("Press 1 to go back:");
                                int n = in.nextInt();
                                if(n == 1){
                                  menu.adminpage();
                                  in.nextInt();
                                  break;
                                }else {
                                  System.out.println("You entered invalid number.");
                                }
                              } catch (InputMismatchException e) {
                                System.out.println("invalid input!");
                                in.nextLine();
                              }
                            }


                          break;
                        case 4:
                          showMenu();
                          break;
                        default:
                        System.out.println("please enter a valid number.(Numerical Value Only)");
                          break;
                      }
                    }while(innerChoice != 4);

                    return;
                  } 
                }
                System.out.println("you are not found or incorrect email/password.");
            break;
          case 4:
          System.out.println("");
            menu.printAboutUsParagraph();
            System.out.println("");

            while (true) {
              try {
                System.out.print("Press 1 to go back:");
                int n = in.nextInt();
                if(n == 1){
                  showMenu();
                  in.nextLine();
                  break;
                }else {
                  System.out.println("You entered invalid number.");
                }
              } catch (InputMismatchException e) {
                System.out.println("invalid input!");
                in.nextLine();
              }
            }

          case 5:
              break;
        
          default:
            System.out.println("");
            System.out.println("You entered invalid number.\nPlease enter a number from 1-5.");
            break;
        }
      }while(choice != 5);
    }
  }

  public static void main(String[] args) {
    System.out.println(" ______                                     ______");
    System.out.println("|      |                                   |      |");
    System.out.println("| Book | Welcome To AASTU Online Bookstore | Book |"); 
    System.out.println("|______|                                   |______|");
    showMenu();
    System.out.println(" ______                                     ______");
    System.out.println("|      |                                   |      |");
    System.out.println("| Book |        Thank you for using.       | Book |"); 
    System.out.println("|______|                                   |______|");

  }
}
