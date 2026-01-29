package application;

public class testDB {public static void main(String[] args) {
    if (Database.getConnection() != null) {
        System.out.println("Database connected successfully!");
    } else {
        System.out.println("Connection failed.");
    }
}
}
