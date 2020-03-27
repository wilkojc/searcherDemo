package com.searcher;

import com.findwise.IndexEntry;

/**
 * Entry is an object that stores data for the purposes of searching and scoring terms.
 * <p>
 * Each entry corresponds to a single token and it's occurrences in a single document and the whole document set
 * eg. dog in doc 1 and dog in doc 2 will be separate entities.
 */
public class Entry implements IndexEntry {
    private String id;
    private int docLength = 0;
    private int totalOccurrences = 0;
    private int termOccurrencesInDoc = 0;
    private double score = 0;

    /**
     * Instantiates a new Entry with a given id.
     *
     * @param id string value corresponding to a searchable token eg. dog
     */
    public Entry(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new Entry.
     *
     * @param id                   string value corresponding to a searchable token eg. dog
     * @param docLength            the amount of tokens in the document where the token(id) occurs - used for score calculation purposes
     * @param totalOccurrences     total occurrences of the id(token) in all of the documents - used for calculation purposes
     * @param termOccurrencesInDoc amount of id(token) occurrences in a single corresponding document
     */
    public Entry(String id, int docLength, int totalOccurrences, int termOccurrencesInDoc) {
        this(id);
        this.docLength = docLength;
        this.totalOccurrences = totalOccurrences;
        this.termOccurrencesInDoc = termOccurrencesInDoc;
    }

    public int getDocLength() {
        return docLength;
    }

    public int getTotalOccurrences() {
        return totalOccurrences;
    }

    public int getTermOccurrencesInDoc() {
        return termOccurrencesInDoc;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getScore() {
        return this.score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return getId() + "  ||  score: " + this.score;
    }

}
