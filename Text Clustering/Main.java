import cluster.ClusteringAlgorithm;
import library.Library;
import library.User;
import text.Corpus;

class Main
{
    public static void main(String[] args) {
        /*Corpus corpus = new Corpus("documents/");
        System.out.println(corpus);*/

        Library library = new Library("documents/");
        System.out.println(library.getLibrary());
        System.out.println(library.findMostSimilar().title);

        Library library2 = new Library("documents2/");
        System.out.println(library.getLibrary());
        System.out.println(library2.findMostSimilar().title);

        User user1 = new User("Mark", library);
        User user2 = new User("Samantha", library2);
        System.out.println( "User1 should get the book: " + library.libraryRecommendation(user1, user2).title);

        /*int numCluster = 3;
        int maxIteration = 6;
        ClusteringAlgorithm clustering = new ClusteringAlgorithm(numCluster, corpus, maxIteration);
        for(int i = 0; i < numCluster; i++) {
            System.out.println("cluster " + i + ": " + clustering.getCluster(i));
        }*/
    }
}

/*
    Library
        - List of Books
         - calculate a libraries similarity

*/
