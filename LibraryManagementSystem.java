import java.util.ArrayList;
import java.util.Scanner;

// Book class
class Book {
    private int id;
    private String title;
    private String author;
    private boolean issued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public int getId() {
        return id;
    }

    public boolean isIssued() {
        return issued;
    }

    public void issue() {
        issued = true;
    }

    public void returnBook() {
        issued = false;
    }

    public void display() {
        System.out.println(id + " " + title + " " + author + " Issued: " + issued);
    }
}

// Library class
class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book b) {
        books.add(b);
        System.out.println("Book added");
    }

    public void showBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available");
            return;
        }

        for (Book b : books) {
            b.display();
        }
    }

    public void issueBook(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                if (!b.isIssued()) {
                    b.issue();
                    System.out.println("Book issued");
                } else {
                    System.out.println("Already issued");
                }
                return;
            }
        }
        System.out.println("Book not found");
    }

    public void returnBook(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                if (b.isIssued()) {
                    b.returnBook();
                    System.out.println("Book returned");
                } else {
                    System.out.println("Book was not issued");
                }
                return;
            }
        }
        System.out.println("Book not found");
    }
}

// Main class
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library lib = new Library();

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Show Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter id: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter title: ");
                String title = sc.nextLine();

                System.out.print("Enter author: ");
                String author = sc.nextLine();

                lib.addBook(new Book(id, title, author));
            }

            else if (choice == 2) {
                lib.showBooks();
            }

            else if (choice == 3) {
                System.out.print("Enter book id: ");
                lib.issueBook(sc.nextInt());
            }

            else if (choice == 4) {
                System.out.print("Enter book id: ");
                lib.returnBook(sc.nextInt());
            }

            else if (choice == 5) {
                System.out.println("Exit");
                break;
            }

            else {
                System.out.println("Invalid choice");
            }
        }

        sc.close();
    }
}