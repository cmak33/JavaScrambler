package com.scrambler.tests;

import com.scrambler.classes.scramblers.CaesarScrambler;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class CaesarScramblerTest {

    @Test
    public void encodeWithPositiveShift() {
        String textToEncode = "abcz x";
        int shift = 3;
        CaesarScrambler scrambler = new CaesarScrambler(shift);

        String expected ="def}#{";
        String actual =scrambler.encode(textToEncode);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void decodeWithPositiveShift() {
        String textToDecode = "def}#{";
        int shift = 3;
        CaesarScrambler scrambler = new CaesarScrambler(shift);

        String expected ="abcz x";
        String actual =scrambler.decode(textToDecode);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void encodeWithNegativeShift(){
        String textToDecode = "test ";
        int shift = -3;
        CaesarScrambler scrambler = new CaesarScrambler(shift);

        String expected ="qbpq\u001D";
        String actual =scrambler.encode(textToDecode);

        Assertions.assertEquals(expected,actual);
    }

    @Test
    public void decodeWithNegativeShift(){
        String textToDecode = "qbpq\u001D";
        int shift = -3;
        CaesarScrambler scrambler = new CaesarScrambler(shift);

        String expected ="test ";
        String actual =scrambler.decode(textToDecode);

        Assertions.assertEquals(expected,actual);
    }


}