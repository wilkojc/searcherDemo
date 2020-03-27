package com.searcher;

/**
 * Document is an object that stores all data related
 * to an indexed document that is further needed for search purposes.
 */
public class Document {
    private int length;
    private String id;
    private String content;
    private String processedContent;

    /**
     * Instantiates a new Document.
     *
     * @param id               id of the document
     * @param content          string content of the document
     * @param processedContent original content with all characters being lowercase, no special characters included
     *                         and the amount of whitespaces normalized
     */
    public Document (String id, String content, String processedContent){
        this.id = id;
        this.content = content;
        this.processedContent = processedContent;
        this.length = this.processedContent.split(" ").length;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getProcessedContent() {
        return processedContent;
    }

    public int getLength() {
        return length;
    }

}
