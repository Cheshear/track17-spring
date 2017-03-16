package track.lessons.lesson1;

import org.junit.Ignore;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

/**
 *
 */
public class CountWordsTest {

    static File file;

    @BeforeClass
    public static void init() {
        file = new File("words.txt");
    }


    @Test
    @Ignore
    public void countNumbers() throws Exception {
        CountWords countWords = new CountWords();
        Assert.assertEquals(42, countWords.countNumbers(file));
    }

    @Test
    @Ignore
    public void concatWords() throws Exception {
        CountWords countWords = new CountWords();
        Assert.assertEquals("hello world !", countWords.concatWords(file));
    }

}