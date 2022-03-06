package com.example.javaproject.Classes.ScramblerClasses;

public class CaesarScrambler  implements  Scrambler{
    private final int shift;

    private interface CharChanger{
        public char changeChar(char ch);
    }

    public CaesarScrambler(int shift){
        this.shift=shift;
    }

    @Override
    public String encode(String str) {
        return changeStringChars(str,(char ch)->encodeChar(ch));
    }

    @Override
    public String decode(String str) {
        return changeStringChars(str,(char ch)->decodeChar(ch));
    }


    private char encodeChar(char ch){
        return (char)((ch+shift)%Character.MAX_VALUE);
    }

    private char decodeChar(char ch){
        return (char)((Character.MAX_VALUE+ch-shift)%Character.MAX_VALUE);
    }

    private String changeStringChars(String str,CharChanger changer){
        return str.chars().map(ch->changer.changeChar((char)ch)).collect(StringBuilder::new,
                StringBuilder::appendCodePoint,
                StringBuilder::append)
                .toString();
    }
}
