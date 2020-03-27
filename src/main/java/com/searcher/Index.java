package com.searcher;

import java.util.HashSet;
import java.util.Set;


/**
 * Index is used to store documents and terms data in the corresponding sets.
 */
public class Index {
    private Set<Term> terms = new HashSet<>();
    private Set<Document> documents = new HashSet<>();

    private static Index index;

    private Index() {
    }

    /**
     * Gets an instance of the index class.
     *
     * @return an instance of index class.
     */
    public static Index getInstance() {
        if (index == null) {
            index = new Index();
        }
        return index;
    }

    /**
     * Adds a document.
     *
     * @param id               the id of the document to add
     * @param content          original string content of the document
     * @param processedContent processed content of the document
     */
    public void addDocument(String id, String content, String processedContent) {
        documents.add(new Document(id, content, processedContent));
    }

    /**
     * Adds a term.
     *
     * @param docID     the document id the term can be found in
     * @param termValue corresponding token's string value eg. dog
     */
    public void addTerm(String docID, String termValue) {
        if (!termExists(termValue)) {
            Term buffer = new Term(termValue);
            buffer.addOccurrence(docID);
            terms.add(buffer);
        } else
            getTermByValue(termValue).addOccurrence(docID);
    }

    private boolean termExists(String value) {
        return terms.stream()
                .anyMatch(term -> term.getValue().equals(value));
    }

    /**
     * Searches for and gets a term object with the specified value.
     *
     * @param value value to search by
     * @return the term (if present)
     */
    public Term getTermByValue(String value){
        return getTerms()
                .stream()
                .filter(term -> term.getValue().equals(value.toLowerCase().strip()))
                .findAny().get();
    }

    /**
     * Searches for and gets a document object with the specified id.
     *
     * @param id the id to to search by
     * @return the document (if present)
     */
    public Document getDocumentByID(String id) {
        return documents
                .stream()
                .filter(document -> document.getId().equals(id)).findAny().get();
    }

    public Set<Term> getTerms()
    {
        return terms;
    }

    public Set<Document> getDocuments() {
        return documents;
    }
}
