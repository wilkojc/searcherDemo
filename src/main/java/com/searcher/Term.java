package com.searcher;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Term.
 * Term represents a single token alongside it's occurrences in all the indexed documents
 */
public class Term {
    private String value = "";
    private Set<String> occurrences = new LinkedHashSet<>();  // doc 1, doc 2

    /**
     * Instantiates a new Term.
     *
     * @param value representing the associated token eg. dog
     */
    public Term(String value) {
        setValue(value);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Add occurrence.
     *
     * @param id the id of the document to be added
     */
    public void addOccurrence(String id) {
        this.occurrences.add(id);
    }

    public Set<String> getOccurrences() {
        return this.occurrences;
    }

    @Override
    public String toString() {
        return "ID : " + this.value + " || Wystąpienia : " + this.occurrences;
    }
}
