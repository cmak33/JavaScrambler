package com.example.javaproject.classes.scramblers;

public class CaesarScrambler  implements  Scrambler{
    private final int shift;

    private interface CharChanger{
        char changeChar(char ch);
    }

    public CaesarScrambler(int shift){
        this.shift=shift;
    }

    @Override
    public String encode(String str) {
        return changeStringChars(str, this::encodeChar);
    }

    @Override
    public String decode(String str) {
        return changeStringChars(str,this::decodeChar);
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
