package com.jsorant.library.domain;

public class BookFixture {

    public static Book lordOfTheRings() {
        return new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL);
    }

    public static Book harryPotter() {
        return new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL);
    }

    public static Book theTwoTowers() {
        return new Book("5341343136", "The Two Towers", "JRR Tolkien", BookType.NOVEL);
    }

    public static Book theHobbit() {
        return new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL);
    }

    public static String idOfABookThatDoesNotExist() {
        return "1234567891";
    }
}
