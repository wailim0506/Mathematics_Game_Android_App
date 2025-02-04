package com.example.itp4501_assignment;

public class passwordHandler {
    private static final int shift = 10;
    private static final int numberOfCase = 71;

    //Caesar's cipher encryption
    public String passwordEncrypt(String pw){
        String[] pwChar = new String[pw.length()];
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            pwChar[i] = "" + c;
        }

        String[] encryptedPw = new String[pwChar.length];
        for (int i = 0; i < pwChar.length;i++){
            int convertedIndex =  (convertToIndex(pwChar[i]) + shift)%numberOfCase;
            encryptedPw[i] = convertToString(convertedIndex);
        }

        String encrypted = "";
        for (int i = 0; i < encryptedPw.length;i++){
            encrypted += encryptedPw[i];
        }

        return encrypted;
    }

    public String passwordDecrypt(String pw){
        String[] pwChar = new String[pw.length()];
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            pwChar[i] = "" + c;
        }

        String[] decryptedPw = new String[pwChar.length];
        for (int i = 0; i < pwChar.length;i++){
            int convertedIndex =  ((convertToIndex(pwChar[i]) - shift) + numberOfCase) % numberOfCase;
            decryptedPw[i] = convertToString(convertedIndex);
        }

        String decrypted = "";
        for (int i = 0; i < decryptedPw.length;i++){
            decrypted += decryptedPw[i];
        }
        return decrypted;
    }

    private int convertToIndex(String c){
        switch(c){
            case "a": return 0;
            case "A": return 1;
            case "b": return 2;
            case "B": return 3;
            case "c": return 4;
            case "C": return 5;
            case "d": return 6;
            case "D": return 7;
            case "e": return 8;
            case "E": return 9;
            case "f": return 10;
            case "F": return 11;
            case "g": return 12;
            case "G": return 13;
            case "h": return 14;
            case "H": return 15;
            case "i": return 16;
            case "I": return 17;
            case "j": return 18;
            case "J": return 19;
            case "k": return 20;
            case "K": return 21;
            case "l": return 22;
            case "L": return 23;
            case "m": return 24;
            case "M": return 25;
            case "n": return 26;
            case "N": return 27;
            case "o": return 28;
            case "O": return 29;
            case "p": return 30;
            case "P": return 31;
            case "q": return 32;
            case "Q": return 33;
            case "r": return 34;
            case "R": return 35;
            case "s": return 36;
            case "S": return 37;
            case "t": return 38;
            case "T": return 39;
            case "u": return 40;
            case "U": return 41;
            case "v": return 42;
            case "V": return 43;
            case "w": return 44;
            case "W": return 45;
            case "x": return 46;
            case "X": return 47;
            case "y": return 48;
            case "Y": return 49;
            case "z": return 50;
            case "Z": return 51;
            case "0": return 52;
            case "1": return 53;
            case "2": return 54;
            case "3": return 55;
            case "4": return 56;
            case "5": return 57;
            case "6": return 58;
            case "7": return 59;
            case "8": return 60;
            case "9": return 61;
            case "!": return 62;
            case "%": return 63;
            case "$": return 64;
            case "@": return 65;
            case "&": return 66;
            case "#": return 67;
            case "*": return 68;
            case "?": return 69;
            case "/": return 70;
            default: return 0;
        }
    }
    private String convertToString(int c){
        switch (c) {
            case 0: return "a";
            case 1: return "A";
            case 2: return "b";
            case 3: return "B";
            case 4: return "c";
            case 5: return "C";
            case 6: return "d";
            case 7: return "D";
            case 8: return "e";
            case 9: return "E";
            case 10: return "f";
            case 11: return "F";
            case 12: return "g";
            case 13: return "G";
            case 14: return "h";
            case 15: return "H";
            case 16: return "i";
            case 17: return "I";
            case 18: return "j";
            case 19: return "J";
            case 20: return "k";
            case 21: return "K";
            case 22: return "l";
            case 23: return "L";
            case 24: return "m";
            case 25: return "M";
            case 26: return "n";
            case 27: return "N";
            case 28: return "o";
            case 29: return "O";
            case 30: return "p";
            case 31: return "P";
            case 32: return "q";
            case 33: return "Q";
            case 34: return "r";
            case 35: return "R";
            case 36: return "s";
            case 37: return "S";
            case 38: return "t";
            case 39: return "T";
            case 40: return "u";
            case 41: return "U";
            case 42: return "v";
            case 43: return "V";
            case 44: return "w";
            case 45: return "W";
            case 46: return "x";
            case 47: return "X";
            case 48: return "y";
            case 49: return "Y";
            case 50: return "z";
            case 51: return "Z";
            case 52: return "0";
            case 53: return "1";
            case 54: return "2";
            case 55: return "3";
            case 56: return "4";
            case 57: return "5";
            case 58: return "6";
            case 59: return "7";
            case 60: return "8";
            case 61: return "9";
            case 62: return "!";
            case 63: return "%";
            case 64: return "$";
            case 65: return "@";
            case 66: return "&";
            case 67: return "#";
            case 68: return "*";
            case 69: return "?";
            case 70: return "/";
            default: return "a";
        }
    }

    public boolean checkPwFormat(String pw){
        String[] pwSplit = new String[pw.length()];
        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);
            pwSplit[i] = "" + c;
        }

        for (int i = 0; i < pwSplit.length;i++){
            switch(pwSplit[i]){ //have problem
                case "a":
                case "A":
                case "b":
                case "B":
                case "c":
                case "C":
                case "d":
                case "D":
                case "e":
                case "E":
                case "f":
                case "F":
                case "g":
                case "G":
                case "h":
                case "H":
                case "i":
                case "I":
                case "j":
                case "J":
                case "k":
                case "K":
                case "l":
                case "L":
                case "m":
                case "M":
                case "n":
                case "N":
                case "o":
                case "O":
                case "p":
                case "P":
                case "q":
                case "Q":
                case "r":
                case "R":
                case "s":
                case "S":
                case "t":
                case "T":
                case "u":
                case "U":
                case "v":
                case "V":
                case "w":
                case "W":
                case "x":
                case "X":
                case "y":
                case "Y":
                case "z":
                case "Z":
                case "0":
                case "1":
                case "2":
                case "3":
                case "4":
                case "5":
                case "6":
                case "7":
                case "8":
                case "9":
                case "!":
                case "%":
                case "$":
                case "@":
                case "&":
                case "#":
                case "*":
                case "?":
                case "/":
                    continue;
                default:
                    return false;
            }
        }
        return true;
    }
}
