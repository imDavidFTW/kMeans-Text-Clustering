package library;

public class User implements Comparable<User>{
    public String username;
    public Library library;
    public Book favoriteBook;
    public User(String username, Library library){
        this.username = username;
        this.library = library;
    }

    @Override
    public boolean equals(Object object)
    {
        User user = (User) object;
        return this.username.equals(user.username);
    }

    @Override
    public int hashCode()
    {
        return this.username.hashCode();
    }

    @Override
    public int compareTo(User user)
    {
        return this.username.compareTo(user.username);
    }
}
