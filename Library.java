package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Library {

    static Scanner sc = new Scanner(System.in);
    static Connection con = null;
    static Library obj = new Library();

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/databasename",
                    "username",
                    "Password"
            );

            System.out.println("Database connected\n");
            while (true) {

                System.out.println("\n========= LIBRARY MENU =========");
                System.out.println("1. Insert record");
                System.out.println("2. View Book by ID");
                System.out.println("3. View all Books");
                System.out.println("4. Update Book Price by ID");
                System.out.println("5. Delete Book by ID");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1: 
                	obj.insertBook(); 
                	break;
                case 2: 
                	obj.selectBook(); 
                	break;
                case 3: 
                	obj.selectAllBook(); 
                	break;
                case 4: 
                	obj.updateBookData(); 
                	break;
                case 5: 
                	obj.deleteBook(); 
                	break;
                case 6:
                    System.out.println("Thank you! Exiting program...");
                    con.close();
                    System.exit(0);
                    
                default: System.out.println("Please choose a valid option");
            }
             
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void insertBook() {
        try {
            String sql = "INSERT INTO library (book_id, book_name, book_Author, book_price) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            System.out.println("Enter Book ID:");
            pst.setInt(1, Integer.parseInt(sc.nextLine()));

            System.out.println("Enter Book Name:");
            pst.setString(2, sc.nextLine());

            System.out.println("Enter Book Author:");
            pst.setString(3, sc.nextLine());

            System.out.println("Enter Book Price:");
            pst.setInt(4, Integer.parseInt(sc.nextLine()));

            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "Record inserted successfully" : "Insert failed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void selectBook() {
        try {
            String sql = "SELECT * FROM library WHERE book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            System.out.println("Enter Book ID:");
            pst.setInt(1, Integer.parseInt(sc.nextLine()));

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
            	System.out.println("---------------------------------------------------------------");
            	System.out.printf("%-10s %-20s %-20s %-10s%n",
            	        "ID", "NAME", "AUTHOR", "PRICE");
            	System.out.println("---------------------------------------------------------------");

            	System.out.printf("%-10d %-20s %-20s %-10s%n",
            	        rs.getInt("book_id"),
            	        rs.getString("book_name"),
            	        rs.getString("book_Author"),
            	        rs.getInt("book_price"));

            	System.out.println("-----------------------------------------------");

            } else {
                System.out.println("No record found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void selectAllBook() {
        try {
            String sql = "SELECT * FROM library";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            System.out.println("---------------------------------------------------------------------------------------");
            System.out.printf("%-10s %-40s %-28s %-10s%n",
                    "ID", "NAME", "AUTHOR", "PRICE");
            System.out.println("---------------------------------------------------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.printf("%-10d %-40s %-28s %-10d%n",
                        rs.getInt("book_id"),
                        rs.getString("book_name"),
                        rs.getString("book_Author"),
                        rs.getInt("book_price"));
            }

            if (!found) {
                System.out.println("No records found");
            }

            System.out.println("---------------------------------------------------------------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void updateBookData() {
        try {
            String sql = "UPDATE library SET book_price = ? WHERE book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            System.out.println("Enter Book ID:");
            pst.setInt(2, Integer.parseInt(sc.nextLine()));

            System.out.println("Enter New Price:");
            pst.setInt(1, Integer.parseInt(sc.nextLine()));

            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "Update successful" : "Update failed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void deleteBook() {
        try {
            String sql = "DELETE FROM library WHERE book_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            System.out.println("Enter Book ID to delete:");
            pst.setInt(1, Integer.parseInt(sc.nextLine()));

            int rows = pst.executeUpdate();
            System.out.println(rows > 0 ? "Deletion successful" : "Deletion failed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


