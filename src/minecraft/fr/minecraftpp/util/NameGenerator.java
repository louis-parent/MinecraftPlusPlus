package fr.minecraftpp.util;

import java.util.Random;

public class NameGenerator {

	public static Random rand = new Random();

	public static String generateName() {

		int nbChars = 3 + rand.nextInt(4);

		return startRecursiveGeneration(nbChars);
	}

	private static String startRecursiveGeneration(int nbChars) {
		int type = rand.nextInt(2);
		return generateRecursively(nbChars, type);
	}

	private static String generateRecursively(int nbChars, int typeOfLastChar) {
		if (nbChars > 0) {
			int newType = (typeOfLastChar + 1) % 2;
			return selectChar(newType) + generateRecursively(nbChars - 1, newType);
		} else {
			return "";
		}
	}

	private static String selectChar(int typeOfChar) {
		if (typeOfChar == 0) {
			String letters = getConsonant();
			if (rand.nextInt(2) == 0) {
				letters += getConsonant();
			}
			return letters;
		} else {
			return getVowel();
		}
	}

	private static String getConsonant() {
		int choix = rand.nextInt(20);
		switch (choix) {
		case 0:
			return "b";
		case 1:
			return "c";
		case 2:
			return "d";
		case 3:
			return "f";
		case 4:
			return "g";
		case 5:
			return "h";
		case 6:
			return "j";
		case 7:
			return "k";
		case 8:
			return "l";
		case 9:
			return "m";
		case 10:
			return "n";
		case 11:
			return "p";
		case 12:
			return "q";
		case 13:
			return "r";
		case 14:
			return "s";
		case 15:
			return "t";
		case 16:
			return "v";
		case 17:
			return "w";
		case 18:
			return "x";
		case 19:
			return "z";
		default:
			return "";
		}
	}

	private static String getVowel() {
		int choix = rand.nextInt(14);
		switch (choix) {
		case 0:
			return "a";
		case 1:
			return "e";
		case 2:
			return "i";
		case 3:
			return "o";
		case 4:
			return "u";
		case 13:
			return "y";
		case 5:
			return "au";
		case 6:
			return "eu";
		case 7:
			return "ou";
		case 8:
			return "ai";
		case 9:
			return "ei";
		case 10:
			return "oi";
		case 11:
			return "ee";
		case 12:
			return "oo";
		default:
			return "";
		}
	}
}
