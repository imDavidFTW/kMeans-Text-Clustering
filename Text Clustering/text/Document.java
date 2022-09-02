package text;

import java.util.HashMap;
import java.util.Set;

public class Document
{
    private String name; // file name or title, unique identifier
    private HashMap<String, Integer> termFrequency; //  // term frequency table: stores the number of occurrences of a given term in this document.
    private int wordCount; // number of total words in this document (including repetitions).

    public Document(String name, String content) {
        this.name = name;
        preprocess(content);
    }

    private void preprocess(String content) {
        content = content.strip(); // remove leading and trailing spaces
        content = content.toLowerCase();
        content = content.replaceAll("[^a-z]+", " "); // replace every consecutive sequence of characters that are not lower case letters by a space.
        String[] termArray = content.split(" ");
        this.termFrequency = new HashMap<String, Integer>();
        for(String term : termArray) {
            if(term.length() > 2) { // ignore terms that have less than 2 characters
                Integer freq = this.termFrequency.get(term); // returns the frequency of term, or null if term is not in the term frequency table.
                if(freq == null) { // term is not in the term frequency table
                    this.termFrequency.put(term, 1);
                }
                else { // term is in the term frequency table
                    this.termFrequency.put(term, freq + 1);
                }
            }
        }
        this.wordCount = 0;
        for(String term : this.termFrequency.keySet()) {
            this.wordCount = this.wordCount + this.termFrequency.get(term);
        }
    }

    @Override
    public String toString() {
        return "\n" + this.name + "\n" + this.termFrequency;
    }

    @Override
    public boolean equals(Object obj) {
        Document doc = (Document) obj;
        return this.name.equals(doc.name);
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    // all terms in this document
    protected Set<String> getTermSet() {
        return this.termFrequency.keySet();
    }

    // frequency of a given term in this document, divided by the number of words.
    public double termFrequency(String term) {
        Integer freq = this.termFrequency.get(term); // returns the frequency of term, or null if term is not in the term frequency table.
        if(freq == null) { // term is not in the term frequency table
            return 0;
        }
        else { // term is in the term frequency table
            return (double) freq / this.wordCount;
        }
    }
}
