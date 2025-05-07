package com.example.praktikum_4;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BooksDataSource {
    private static BooksDataSource instance;
    private final List<Book> books;

    private BooksDataSource(Context context) {
        books = new ArrayList<>();
        initDummyData(context);
    }

    public static synchronized BooksDataSource getInstance(Context context) {
        if (instance == null) {
            instance = new BooksDataSource(context);
        }
        return instance;
    }

    private void initDummyData(Context context) {
        String packageName = context.getPackageName();
        Uri defaultCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.default_book_cover);

        Uri hp1CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover1);
        Uri hp2CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover2);
        Uri hp3CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover3);
        Uri hp4CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover4);
        Uri hp5CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover5);
        Uri tsitp1CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover6);
        Uri tsitp2CoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover7);
        Uri annabelleCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover8);
        Uri widCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover9);
        Uri dwCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover10);
        Uri insidiousCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover11);
        Uri iewuCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover12);
        Uri iswuCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover13);
        Uri nsCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover14);
        Uri shCoverUri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.cover15);

        // Book 1
        books.add(new Book(
                "1",
                "Harry Potter and the Order of the Phoenix",
                "J.K. Rowling",
                2003,
                "Harry Potter and the Order of the Phoenix is the fifth book in the globally acclaimed Harry Potter series by J.K. Rowling. In this installment, Harry returns to Hogwarts School of Witchcraft and Wizardry for his fifth year, only to find the magical world in denial about Lord Voldemort’s return. The Ministry of Magic interferes at Hogwarts by installing Dolores Umbridge as the new Defense Against the Dark Arts teacher, whose authoritarian rule sparks resistance among students.",
                hp1CoverUri,
                "fantasy",
                3.5f,
                "Harry Potter and the Order of the Phoenix is darker and more emotionally complex than its predecessors. Rowling skillfully captures the frustrations and confusion of adolescence, particularly through Harry’s internal struggles, grief, and growing defiance."
        ));

        // Book 2
        books.add(new Book(
                "2",
                "Harry Potter and the Chamber of Secrets",
                "J.K. Rowling",
                1998,
                "Harry returns to Hogwarts for his second year but is met with strange occurrences and the legend of the Chamber of Secrets. Students are being petrified, and Harry is blamed as whispers of a hidden heir to Slytherin spread.",
                hp2CoverUri,
                "Fantasy",
                4.5f,
                "Darker and more mysterious than the first, this sequel builds on the magical universe with suspenseful pacing and deeper mythology. The book explores themes of identity, fear, and prejudice."
        ));

        // Book 3
        books.add(new Book(
                "3",
                "Harry Potter and the Prisoner of Azkaban",
                "J.K. Rowling",
                2006,
                "A dystopian novel set in a totalitarian society where individualism and independent thinking are persecuted as 'thoughtcrimes'.",
                hp3CoverUri,
                "Fantasy",
                3.5f,
                "Orwell's masterpiece on surveillance and authoritarian control."
        ));

        // Book 4
        books.add(new Book(
                "4",
                "Harry Potter and the Deathly Hallows",
                "J.K. Rowling",
                2007,
                "A fantasy novel about the adventures of hobbit Bilbo Baggins, who embarks on a quest to reclaim the Lonely Mountain from the dragon Smaug.",
                hp4CoverUri,
                "Fantasy",
                2.5f,
                "A beloved adventure that established the fantasy genre as we know it today."
        ));

        // Book 5
        books.add(new Book(
                "5",
                "Harry Potter and the Half-Blood Prince",
                "J.K. Rowling",
                2005,
                "The tale of the spirited Elizabeth Bennet and her complicated relationship with the proud Mr. Darcy set in rural England at the turn of the 19th century.",
                hp5CoverUri,
                "fantasy",
                5.0f,
                "Austen's masterpiece of wit, romance, and social commentary."
        ));

        // Book 6
        books.add(new Book(
                "6",
                "The Summer I Turned Pretty",
                "Jenny Han",
                2009,
                "The Summer I Turned Pretty is the first book in Jenny Han’s beloved coming-of-age trilogy. The story follows Isabel “Belly” Conklin, who spends every summer at Cousins Beach with her mother, her brother, and her mom’s best friend Susannah — along with Susannah’s two sons, Conrad and Jeremiah.",
                tsitp1CoverUri,
                "romance",
                3.0f,
                "This novel beautifully captures the bittersweet experience of growing up, first love, and the complexities of relationships. Jenny Han’s writing is simple yet emotionally resonant, making it easy for readers to connect with Belly’s internal struggles."
        ));

        // Book 7
        books.add(new Book(
                "7",
                "It’s Not Summer Without You",
                "Jenny Han",
                2010,
                "In It’s Not Summer Without You, the story picks up with Belly facing a summer that feels all wrong. Susannah, the boys' mother and a central figure in their summer traditions, has passed away, and everything has changed.",
                tsitp2CoverUri,
                "romance",
                4.0f,
                "This sequel deepens the emotional layers introduced in the first book. Jenny Han sensitively explores themes of grief, growing apart, and the emotional aftermath of death and heartbreak."
        ));

        // Book 8
        books.add(new Book(
                "8",
                "Annabelle",
                "John R. Leonetti",
                2005,
                "An epic high-fantasy novel that follows the quest to destroy the One Ring, which was created by the Dark Lord Sauron.",
                annabelleCoverUri,
                "horror",
                4.5f,
                "The definitive fantasy epic that influenced generations of literature and media."
        ));

        // Book 9
        books.add(new Book(
                "9",
                "A Walk in the Dark",
                "Jane Godwin",
                2022,
                "A Walk in the Dark is a gripping and suspenseful rite-of-passage novel that follows five Year Nine students from Otway Community School as they embark on an overnight hike through the rainforest of Victoria's Otway Ranges.",
                widCoverUri,
                "science fiction",
                2.5f,
                "A Walk in the Dark has been praised for its authentic portrayal of adolescent experiences and its ability to engage readers with a suspenseful narrative. "
        ));

        // Book 10
        books.add(new Book(
                "10",
                "Terror Tower (Dread Wood)",
                "Jennifer Killick",
                2006,
                "Terror Tower is the thrilling conclusion to Jennifer Killick's Dread Wood series, blending humor, horror, and science fiction. The story follows Angelo and his friends—members of \"Club Loser\"—as they face their most terrifying challenge yet.",
                dwCoverUri,
                "thriller",
                3.5f,
                "A spiritual journey that inspires readers to follow their dreams."
        ));

        // Book 11
        books.add(new Book(
                "11",
                "Insidious: The Last Key ",
                "Adam Robitel",
                2008,
                "Insidious: The Last Key is the fourth installment in the Insidious franchise and serves as a prequel to the first two films. Set in 1953 and 2010, the story follows parapsychologist Dr. Elise Rainier as she confronts haunting memories from her childhood in Five Keys, New Mexico.",
                insidiousCoverUri,
                "horror",
                4.5f,
                "A gripping tale of survival, sacrifice, and rebellion against oppression."
        ));

        // Book 12
        books.add(new Book(
                "12",
                "It Ends with Us",
                "Colleen Hoover",
                2016,
                "It Ends with Us is a poignant romance novel that delves into the complexities of love, trauma, and personal strength. The story follows Lily Bloom, a young woman who moves to Boston to start her own flower shop after graduating college.",
                iewuCoverUri,
                "romance",
                2.5f,
                "The beginning of the beloved magical journey that captivated a generation."
        ));

        // Book 13
        books.add(new Book(
                "13",
                "It Starts with Us",
                "Colleen Hoover",
                2022,
                "It Starts with Us is the highly anticipated sequel to Colleen Hoover's bestselling novel It Ends with Us. This romantic narrative picks up where the first book left off, focusing on Lily Bloom's journey as she navigates life after her tumultuous marriage to Ryle Kincaid.",
                iswuCoverUri,
                "romance",
                4.0f,
                "A controversial bestseller that blends fact and fiction into a global treasure hunt."
        ));

        // Book 14
        books.add(new Book(
                "14",
                "Night Shift",
                "Annie Crown",
                2023,
                "Night Shift is a contemporary new adult romance that follows Kendall Holiday, a self-proclaimed bookworm who works the graveyard shift at her college library. Her quiet Fridays are disrupted when Vincent Knight, the university's star basketball player, seeks refuge in the library to finish an urgent assignment.",
                nsCoverUri,
                "romance",
                4.5f,
                "Critics have praised Night Shift for its engaging and lighthearted narrative. A review on Romance by the Book describes it as a \"steamy and fun new adult romance,\" highlighting the protagonist's love for romance novels and how it influences her real-life interactions."
        ));

        // Book 15
        books.add(new Book(
                "15",
                "Sherlock Holmes",
                "Sir Arthur Conan Doyle",
                1927,
                "Sherlock Holmes is a brilliant, eccentric, and often aloof detective known for his keen observational skills, logical reasoning, and extraordinary ability to solve complex cases. His primary method of solving crimes is through deduction and his ability to notice details that others overlook.",
                shCoverUri,
                "thriller",
                3.5f,
                "Sherlock Holmes has had a profound impact on the detective genre and has become a cultural icon. "
        ));
        sortByNewest();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public void sortByNewest() {
        Collections.sort(books, (b1, b2) -> Integer.compare(b2.getPublishYear(), b1.getPublishYear()));
    }

    public List<Book> getLikedBooks() {
        List<Book> likedBooks = books.stream()
                .filter(Book::isLiked)
                .collect(Collectors.toList());
        return likedBooks;
    }

    public List<Book> getBooksByGenre(String genre) {
        if (genre == null || genre.isEmpty() || genre.equalsIgnoreCase("All")) {
            return getAllBooks();
        }

        return books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public void addBook(Book book) {
        books.add(0, book);
    }

    public void toggleLikeStatus(String bookId) {
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                boolean newStatus = !book.isLiked();
                book.setLiked(newStatus);
                break;
            }
        }
    }
}