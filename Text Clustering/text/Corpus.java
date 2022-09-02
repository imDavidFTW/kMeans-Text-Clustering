package text;

import library.Book;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Files;

import cluster.Group;
import vector.VectorOperations;

public class Corpus extends ArrayList<Document> implements Group
{
    private HashMap<String, Integer> documentFrequency; // document frequency table: stores the number of documents that contain a given term
    private double[][] similarityMatrix;
    public ArrayList<Book> books;

    public Corpus(String path) { // The path to the folder that contains all documents.
        this.documentFrequency = new HashMap<String, Integer>();
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
            this.add(doc);
        }
        this.similarityMatrix = similarityMatrix();
    }

    @Override
    public boolean add(Document doc) {
        // update the document frequency table.
        for(String term : doc.getTermSet()) {
            Integer freq = this.documentFrequency.get(term);
            if(freq == null) {
                this.documentFrequency.put(term, 1);
            }
            else {
                this.documentFrequency.put(term, freq + 1);
            }
        }
        boolean result = super.add(doc); // call the function add from the super class ArrayList
        if(this.similarityMatrix != null) {
            this.similarityMatrix = similarityMatrix();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\ndocuments:\n");
        for(int i = 0; i < this.size(); i++) {
            builder.append(i + ": " + this.get(i).getName() + "\n");
        }
        builder.append("\nsimilarity matrix:\n");
        builder.append("  ");
        for(int j = 0; j < this.size(); j++) {
            builder.append("   " + j + " ");
        }
        builder.append("\n");
        for(int i = 0; i < this.size(); i++) {
            builder.append(i + " ");
            for(int j = 0; j < this.size(); j++) {
                builder.append(String.format("%.2f ", similarityMatrix[i][j]));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public double similarity(int i, int j) {
        return similarityMatrix[i][j];
    }

    private double inverseDocumentFrequency(String term) {
        if(this.documentFrequency.containsKey(term)) { // term is in the corpus
            return Math.log((double) this.size() / this.documentFrequency.get(term));
        }
        else {
            return 0;
        }
    }

    // term frequency-inverse document frequency for one term
    // This quantity measures how relevant a term is in a document.
    private double tfidf(String term, int docIndex) {
        return this.get(docIndex).termFrequency(term) * this.inverseDocumentFrequency(term);
    }

    // term frequency-inverse document frequency for all terms in the corpus
    private double[] tfidf(int docIndex) {
        double[] t = new double[this.documentFrequency.size()];
        int i = 0;
        for(String term : this.documentFrequency.keySet()) {
            t[i] = this.tfidf(term, docIndex);
            i++;
        }
        return t;
    }

    // calculate the similarity between every pair of document in the corpus
    private double[][] similarityMatrix() {
        // TODO: use sparse representation to reduce space usage
        double[][] tfidf = new double[this.size()][this.documentFrequency.size()];
        for(int i = 0; i < this.size(); i++) { // for each document in corpus
            tfidf[i] = this.tfidf(i);
        }
        double[] norm = new double[this.size()];
        for(int i = 0; i < this.size(); i++) {
            norm[i] = VectorOperations.norm(tfidf[i]);
        }
        double[][] cosineSimilarity = new double[this.size()][this.size()];
        for(int i = 0; i < this.size(); i++) {
            cosineSimilarity[i][i] = 1.0;
            for(int j = i + 1; j < this.size(); j++) {
                cosineSimilarity[i][j] = VectorOperations.cosineSimilarity(tfidf[i], tfidf[j], norm[i], norm[j]);
                cosineSimilarity[j][i] = cosineSimilarity[i][j];
            }
        }
        return cosineSimilarity;
    }
}
