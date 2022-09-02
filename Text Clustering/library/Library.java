package library;

import text.*;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;

public class Library extends ArrayList<Book>{
    public Corpus corpus;
    public Book mostSimilar;
    public Library(String path){
        this.corpus = new Corpus(path);
        this.corpus.books = new ArrayList<Book>();
        File folder = new File(path);
        for(File file : folder.listFiles()) {
            String name = file.getName();
            String content = new String();
            try {
                content = Files.readString(file.toPath());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            Document doc = new Document(name, content);
            Book book = new Book(name.replaceAll(".txt",""), doc);
            this.add(book);
            this.corpus.books.add(book);
            System.out.println(corpus);
        }
    }

    public String getLibrary(){
        StringBuilder string = new StringBuilder();
        string.append("[");
        int count = 0;
        for(Book book : corpus.books)
        {
            if(corpus.size() - 1 > count)
            {
                string.append(book.title + ", ");
                count++;
            }
            else
                string.append(book.title);
        }
        string.append("]");
        return string.toString();  
    }

    public Book findMostSimilar()
    {
        double max = 0;
        int maxIndex = 0;
        for(int i = 0; i < this.corpus.size(); i++){
            double sum = 0;
            for(int j = 0; j < this.corpus.size(); j++)
            {
                sum = sum + corpus.similarity(i, j);
                if(sum > max)
                {
                    max = sum;
                    maxIndex = i;
                }
            }
        }
        int count = 0;
        for(Book book : this.corpus.books)
        {
            if(count == maxIndex)
            {
                this.mostSimilar = book;
                break;
            }
            count++;
        }
        return this.mostSimilar;
    }

    public Book libraryRecommendation(User user1, User user2)
    {
        double max = 0;
        int maxIndex = 0;
        user1.favoriteBook = user1.library.findMostSimilar();
        Library library = user2.library;
        library.corpus.add(user1.favoriteBook.content);
        for(int i = library.corpus.size() - 1; i < library.corpus.size(); i++)
        {
            for(int j = 0; j < user2.library.corpus.size(); j++)
            {
                if(j != i)
                {
                    double temp = user2.library.corpus.similarity(i, j);
                    if( temp > max)
                    {
                        max = temp;
                        maxIndex = j;
                    }
                }
            }
        }
        int count = 0;
        Book res = null;
        for(Book book : library.corpus.books)
        {
            if(count == maxIndex)
            {
                res = new Book(book.title, book.content);
                break;
            }
            count++;
        }
        return res;
    }

    
}
