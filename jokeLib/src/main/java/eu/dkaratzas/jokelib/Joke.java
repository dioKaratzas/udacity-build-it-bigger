package eu.dkaratzas.jokelib;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joke {
    private List<String> jokes;
    private int previousIndex;

    public Joke() {
        this.jokes = new ArrayList<>();
        this.jokes.add("Q: What is a programmer's favourite hangout place?\n\nA: Foo Bar");
        this.jokes.add("Q: What do computers and air conditioners have in common?\n\nA: They both become useless when you open windows.");
        this.jokes.add("3 Database SQL walked into a NoSQL bar.\n\nA little while later...\nthey walked out\n\nBecause they couldn't find a table.");
        this.jokes.add("Chuck Norris can take a screenshot of his blue screen");
        this.jokes.add("Programmer (noun.)\nA machine that turns coffee into code");
    }

    public String tellMeAJoke() {
        Random random = new Random();
        return jokes.get(random.nextInt(jokes.size() - 1));
    }

}
