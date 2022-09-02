package library;
import text.Document;

public class Book {
    public String title;
    public Document content;

    public Book(String title, Document content){
        this.title = title;
        this.content = content;
    }
}
