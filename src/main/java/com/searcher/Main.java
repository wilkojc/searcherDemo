package com.searcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Index mainIndex = Index.getInstance();
    private static Map<String, String> documentsMap;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        boolean loop = true;

        while(loop) {
            System.out.println("Add documents one by one - type \"1\".");
            System.out.println("Use the predefined document set from the example - type \"2\".");
            String input = sc.nextLine();
            switch (input) {
                case "1": {
                    documentsMap = new HashMap<>();
                    boolean running = true;
                    int i = 1;
                    System.out.println("Please input the document's content. Type \"//\" to finish.");
                    while (running) {
                        String doc = sc.nextLine();
                        if (doc.equals("//"))
                            running = false;
                        else {
                            String processed = Searcher.processContent(doc);
                            if (!processed.isBlank()) {
                                documentsMap.put("document " + i, doc);
                                System.out.println("Added. Input next document's content or type \"//\" to finish.");
                                i++;
                            } else {
                                System.out.println("This document contains no valid tokens and it will result in a blank document content field after processing.\n" +
                                        "Do you wish to add it anyway? Type \"YES\" to proceed, or any other character to skip.");
                                String userInput = sc.nextLine();
                                if (userInput.toUpperCase().equals("YES")) {
                                    documentsMap.put("document " + i, doc);
                                    System.out.println("Added. Input next document's content or type \"//\" to finish.");
                                    i++;
                                } else
                                    System.out.println("Skipped. Input next document's content or type \"//\" to finish.");
                            }
                        }
                    }
                    loop = false;
                    System.out.println("-------------------------------------");
                    break;
                }
                case "2": {
                    System.out.println("Using the predefined set.");
                    documentsMap = Map.of(
                            "document 1", "the brown fox jumped over the brown dog",
                            "document 2", "the lazy brown dog sat in the corner",
                            "document 3", "the red fox bit the lazy dog");
                    System.out.println("-------------------------------------");
                    loop = false;
                    break;
                }
                default:
                    System.out.println("Unrecognized input.");
                    System.out.println();
                    break;
            }
        }


        Searcher searcher = new Searcher();

        documentsMap.forEach((k, v) -> searcher.indexDocument(k, v));

        String query = "";
        boolean running = true;
        while (running) {
            System.out.println("Please input a query. Type \"//\" to finish.");
            query = sc.nextLine();
            if (query.equals("//"))
                running = false;
            else {
                System.out.println("-------------- Results --------------");
                searcher.search(query).forEach(indexEntry -> {
                    System.out.println(indexEntry.toString() + "  ||  content: " + mainIndex.getDocumentByID(indexEntry.getId()).getContent());
                });
                System.out.println("-------------------------------------");
                System.out.println();
            }
        }
    }
}
