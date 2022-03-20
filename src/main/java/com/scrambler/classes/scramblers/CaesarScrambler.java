package com.scrambler.classes.scramblers;

public class CaesarScrambler  implements  Scrambler{
    private final int shift;

    private interface CharModifier {
        char modify(char ch);
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
        int charCode = (ch+shift)%Character.MAX_VALUE;
        return (char)charCode;
    }

    private char decodeChar(char ch){
        int charCode = (Character.MAX_VALUE+ch-shift)%Character.MAX_VALUE;
        return (char)charCode;
    }

    private String changeStringChars(String str, CharModifier modifier){
        return str.chars().map(ch->modifier.modify((char)ch))
                .collect(StringBuilder::new,
                StringBuilder::appendCodePoint,
                StringBuilder::append)
                .toString();
    }
}
