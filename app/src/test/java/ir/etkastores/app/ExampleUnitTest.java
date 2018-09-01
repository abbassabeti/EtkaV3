package ir.etkastores.app;

import org.junit.Test;

import ir.etkastores.app.utils.StringXORer;

import static junit.framework.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testStringXOR(){
        StringXORer xoRer = new StringXORer();
        String key = "ir.etkastores.app";
        String originalClientID = "AS1zb49R43RTIn5934dn34Prxxa34RT";
        String originalClientSecret = "AS85dwD5asd8E4ESD85";
        String originalGuestUser = "GuestUser";
        String originalGuestPassword = "Et_#usj78Se";

        String codedClientID = xoRer.encode(originalClientID,key);
        String codedClientSecret = xoRer.encode(originalClientSecret,key);
        String codedGuestUser = xoRer.encode(originalGuestUser,key);
        String codedGuestPassword = xoRer.encode(originalGuestPassword,key);

        assertEquals(originalClientID,xoRer.decode(codedClientID,key));
        assertEquals(originalClientSecret,xoRer.decode(codedClientSecret,key));
        assertEquals(originalGuestUser,xoRer.decode(codedGuestUser,key));
        assertEquals(originalGuestPassword,xoRer.decode(codedGuestPassword,key));

    }


}