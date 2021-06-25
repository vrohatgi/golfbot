package hello;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class LeaderTest {

    @Test
    public void testParseAndAdd() {
        System.out.println("Testing parseAndAdd() function!");
        Leader l = new Leader();
        String p = "someGame vanyar 8";
        l.parseAndAdd(p);
        assertTrue(l.gameData.containsKey("someGame"));
        assertTrue(l.gameData.get("someGame").containsKey("vanyar"));
        assertTrue(Integer.toString(l.gameData.get("someGame").get("vanyar")).equals("8"));
    }

}
