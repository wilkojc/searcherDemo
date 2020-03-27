package com.searcher;

import com.findwise.IndexEntry;
import com.findwise.SearchEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Searcher is an object responsible for passing documents to the main index,
 * processing documents contents as well as doing and ranking searches.
 */
public class Searcher implements SearchEngine {
    /**
     * Instance of the index object used to store data regarding documents and terms
     */
    static Index mainIndex = Index.getInstance();
    private int indexSize = 0;


    /**
     * Processes and appends a document and it's contents to
     * the document list in the main index.
     *
     * @param docID id of the document to name it by
     * @param content string content of the document
     */
    public void indexDocument(String docID, String content) {
        String processedContent = processContent(content);
        mainIndex.addDocument(docID, content, processedContent);
        StringTokenizer st = new StringTokenizer(processedContent, " ");
        while (st.hasMoreTokens()) {
            mainIndex.addTerm(docID, st.nextToken());
        }
    }

    /**
     * Processes a given string.
     *
     * Processing of the document content consists of replacing duplicate whitespaces,
     * stripping all leading and trailing whitespaces,
     * removal of special characters as well as making the given string all lowercase.
     *
     * @param value string to process
     * @return processed string
     */
    public static String processContent(String value) {
        return value
                .toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", " ")
                .replaceAll(" +", " ")
                .strip();
    }

    /**
     * Searches for a given query in the document set.
     *
     * @param query string value to search for
     * @return list of found matches
     */

    public List<IndexEntry> search(String query) {
        indexSize = mainIndex.getDocuments().size();
        List<IndexEntry> results = new ArrayList<>();
        try {

            Term termObject = mainIndex.getTermByValue(query);

            List<Entry> entries = createEntries(termObject);
            entries.forEach(entry -> {
                calculateAndSetScore(entry);
                results.add(entry);
            });

            results.sort(Comparator.comparingDouble(IndexEntry::getScore).reversed());

        } catch (NoSuchElementException e) {
            System.out.println("No results found for the provided query.");
        } catch (Exception e) {
            System.out.println("Unexpected error during searching " + e.getMessage());
        }

        return results;
    }

    /**
     * Creates a list of entry objects for the occurrences of a term in the document set.
     *
     * @param termObject term object to create entries for
     * @return list of entry objects
     */

    private List<Entry> createEntries(Term termObject) {
        List<Entry> entries = new ArrayList<>();
        int totalOccurrences = termObject.getOccurrences().size();
        termObject.getOccurrences().forEach(occurrenceID -> {
            Document correspondingDoc = mainIndex.getDocumentByID(occurrenceID);
            int docLength = correspondingDoc.getLength();
            int termOccurrencesInDoc = StringUtils.countMatches(correspondingDoc.getProcessedContent(), termObject.getValue());
            entries.add(new Entry(occurrenceID, docLength, totalOccurrences, termOccurrencesInDoc));
        });
        return entries;
    }

    /**
     * Calculates and sets a score for an entry object using the calculateScore method.
     * Implemented for better code readability.
     *
     * @param entry entry to calculate the score for
     */

    private void calculateAndSetScore(Entry entry) {
        entry.setScore(calculateScore(
                entry.getDocLength(),
                entry.getTermOccurrencesInDoc(),
                entry.getTotalOccurrences(),
                indexSize));
    }

    /**
     * Calculates score using the tf-idf system
     *
     * @param docLength length of a document
     * @param termOccurrencesInDoc amount of occurrences of a term(eg. dog) in a single document
     * @param termOccurrencesTotal amount of occurrences of a term in the documents set
     * @param totalNumberOfDocuments amount of documents in the set
     * @return the score
     */

    private double calculateScore(int docLength, int termOccurrencesInDoc, int termOccurrencesTotal, int totalNumberOfDocuments) {
        double tf = (double) termOccurrencesInDoc / docLength;
        double idf = Math.log((double) totalNumberOfDocuments / termOccurrencesTotal)+1;
        return tf * idf;
    }
}

