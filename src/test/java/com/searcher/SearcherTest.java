package com.searcher;

import static org.junit.jupiter.api.Assertions.*;
import static com.searcher.Searcher.*;

class SearcherTest {

    @org.junit.jupiter.api.Test
    void shouldConvertToLowercase() {
        assertEquals("test string", processContent("Test String"));
    }

    @org.junit.jupiter.api.Test
    void shouldConvertToLowercaseAndRemoveWhitespacesFromTail() {
        assertEquals("test string", processContent("Test String   "));
    }

    @org.junit.jupiter.api.Test
    void shouldConvertToLowercaseAndStripWhitespaces() {
        assertEquals("test string", processContent("   Test String   "));
    }

    @org.junit.jupiter.api.Test
    void shouldRemoveSpecialCharacters() {
        assertEquals("test string", processContent("test string  ^%**"));
    }

    @org.junit.jupiter.api.Test
    void shouldRemoveComma() {
        assertEquals("test string", processContent("test, string"));
    }

    @org.junit.jupiter.api.Test
    void shouldBeBlank() {
        assertEquals("", processContent("[]{};'../!#@#%)=-"));
    }

    @org.junit.jupiter.api.Test
    void shouldRemoveSpecialCharactersAndConvertToLowercaseAndStripWhitespaces() {
        assertEquals("test string", processContent("         Test,         String        !@#$%^&*()_+-=[]"      ));
    }

}