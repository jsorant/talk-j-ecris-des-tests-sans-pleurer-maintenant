package com.jsorant.library.domain;

public class BookFixture {

    public static String idOfABookThatDoesNotExist() {
        return "1234567891";
    }

    public static Book theHobbit() {
        return new Book("1234567890", "The Hobbit", "JRR Tolkien", BookType.NOVEL);
    }

    public static Book lordOfTheRings() {
        return new Book("3214515512", "The Lord of the Rings", "JRR Tolkien", BookType.NOVEL);
    }

    public static Book harryPotter() {
        return new Book("4083U14844", "Harry Potter and the Philosopher's Stone", "JK Rowling", BookType.NOVEL);
    }

    public static Book theTwoTowers() {
        return new Book("5341343136", "The Two Towers", "JRR Tolkien", BookType.NOVEL);
    }

    public static Book theFellowshipOfTheRing() {
        return new Book("2534646466", "The Fellowship of the Ring", "JRR Tolkien", BookType.NOVEL);
    }

    public static Book theReturnOfTheKing() {
        return new Book("6453424356", "The Return of the King", "JRR Tolkien", BookType.NOVEL);
    }
}
