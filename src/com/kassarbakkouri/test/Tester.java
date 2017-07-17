package com.kassarbakkouri.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

import com.kassarbakkouri.trie.implementations.HybridTrie;
import com.kassarbakkouri.trie.implementations.PatriciaTrie;
import com.kassarbakkouri.trie.implementations.TrieFactory;
import com.kassarbakkouri.trie.interfaces.ITrie;

public class Tester {
	private static LinkedList<String> shakespearWords;
	private static LinkedList<String> shakespearWordsPart1;
	private static LinkedList<String> shakespearWordsPart2;
	static {
		try {
			shakespearWords = new LinkedList<String>();
			shakespearWordsPart1 = new LinkedList<String>();
			shakespearWordsPart2 = new LinkedList<String>();
			File folder = new File("Shakespeare");
			File[] listOfFiles = folder.listFiles();
			BufferedReader br;
			String word;
			for (int i = 0; i < listOfFiles.length / 2; i++) {
				if (listOfFiles[i].isFile()) {
					br = new BufferedReader(new FileReader(listOfFiles[i]));
					while ((word = br.readLine()) != null) {
						shakespearWords.add(word);
						shakespearWordsPart1.add(word);
					}
					// System.err.println(listOfFiles[i].getName());
					br.close();
				}
			}

			for (int i = listOfFiles.length / 2; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					br = new BufferedReader(new FileReader(listOfFiles[i]));
					while ((word = br.readLine()) != null) {
						shakespearWords.add(word);
						shakespearWordsPart2.add(word);
					}
					// System.err.println(listOfFiles[i].getName());
					br.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static int iterationNember = 50;
	private static ITrie hybrid, hybridAutoBalenced, patriciaTrieSimpleArray,
			patriciaTrieTreeMap;
	static {
		hybrid = TrieFactory.newHybridTrie();
		hybridAutoBalenced = TrieFactory.newHybridTrieAutoBalanced();
		patriciaTrieSimpleArray = TrieFactory.newPatriciaTrieSimpleArray();
		patriciaTrieTreeMap = TrieFactory.newPatriciaTrieTreeMap();
	}
	private static long startTime, endTime;
	private static double sum;

	public static void setIterationNember(int iterationNember) {
		hybrid.clear();
		hybridAutoBalenced.clear();
		patriciaTrieSimpleArray.clear();
		patriciaTrieTreeMap.clear();
		Tester.iterationNember = iterationNember;
	}

	public static void lunchTestOnBasicSample() {
		hybrid.clear();
		hybridAutoBalenced.clear();
		patriciaTrieSimpleArray.clear();
		patriciaTrieTreeMap.clear();

		String text = "a quel genial professeur de dactylographie sommes nous "
				+ "redevables de la superbe phrase ci dessus un modele du genre ,"
				+ " que toute dactylo connait par coeur puisque elle fait appel "
				+ "a chacune des touches du clavier de la machine a ecrire ?";

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("\tDictionaire de l'exemple de base");

		for (String word : text.split(" ")) {
			hybrid.addWord(word);
			hybridAutoBalenced.addWord(word);
			patriciaTrieSimpleArray.addWord(word);
			patriciaTrieTreeMap.addWord(word);
		}
		System.out.println(" - Les mots du texte :");
		System.out.println("\t- Trie Hybrid : \t\t\t" + hybrid.getWords());
		System.out.println("\t- Trie Hybrid avec equilibrage : \t"
				+ hybridAutoBalenced.getWords());
		System.out.println("\t- Patricia-Trie (noeuds vecteur) : \t"
				+ patriciaTrieSimpleArray.getWords());
		System.out.println("\t- Patricia-Trie (noeuds TreeMap) : \t"
				+ patriciaTrieTreeMap.getWords());

		System.out.println(" - Nombre de mots prefixe's par dactylo :");
		System.out.println("\t- Trie Hybrid : \t\t\t"
				+ hybrid.countPrefix("dactylo"));
		System.out.println("\t- Trie Hybrid avec equilibrage : \t"
				+ hybridAutoBalenced.countPrefix("dactylo"));
		System.out.println("\t- Patricia-Trie (noeuds vecteur) : \t"
				+ patriciaTrieSimpleArray.countPrefix("dactylo"));
		System.out.println("\t- Patricia-Trie (noeuds TreeMap) : \t"
				+ patriciaTrieTreeMap.countPrefix("dactylo"));

		System.out.println(" - Les mots prefixe's par dactylo :");
		System.out.println("\t- Trie Hybrid : \t\t\t"
				+ hybrid.getWordsPrefixedBy("dactylo"));
		System.out.println("\t- Trie Hybrid avec equilibrage : \t"
				+ hybridAutoBalenced.getWordsPrefixedBy("dactylo"));
		System.out.println("\t- Patricia-Trie (noeuds vecteur) : \t"
				+ patriciaTrieSimpleArray.getWordsPrefixedBy("dactylo"));
		System.out.println("\t- Patricia-Trie (noeuds TreeMap) : \t"
				+ patriciaTrieTreeMap.getWordsPrefixedBy("dactylo"));
	}

	public static void lunchTestOnShakspearBooks() {
		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Construction du dictionaire de Shakspear : (le nombre total d'ajouts = "
						+ shakespearWords.size() + " mots)");

		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			hybrid.clear();
			startTime = System.nanoTime();
			for (String word : shakespearWords) {
				hybrid.addWord(word);
			}
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			hybridAutoBalenced.clear();
			startTime = System.nanoTime();
			for (String word : shakespearWords) {
				hybridAutoBalenced.addWord(word);
			}
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			patriciaTrieSimpleArray.clear();
			startTime = System.nanoTime();
			for (String word : shakespearWords) {
				patriciaTrieSimpleArray.addWord(word);
			}
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			patriciaTrieTreeMap.clear();
			startTime = System.nanoTime();
			for (String word : shakespearWords) {
				patriciaTrieTreeMap.addWord(word);
			}
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Comptage de mots : (parcours de toute la structure)");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.countWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.countWords());

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.countWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out
				.println("\t\t resultat : " + hybridAutoBalenced.countWords());

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.countWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.countWords());

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.countWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.countWords());

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Comptage de pointeurs vers null :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.countNullPointors();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.countNullPointors());

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.countNullPointors();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.countNullPointors());

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.countNullPointors();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.countNullPointors());

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.countNullPointors();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.countNullPointors());

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Lister les mots par ordre croissant :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.getWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : Liste de mots");

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.getWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : Liste de mots");

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.getWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : Liste de mots");

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.getWords();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : Liste de mots");

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Comptage de feuilles :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.countLeafNodes();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.countLeafNodes());

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.countLeafNodes();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.countLeafNodes());

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.countLeafNodes();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.countLeafNodes());

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.countLeafNodes();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.countLeafNodes());

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Calcul de profondeur :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.getDepth();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.getDepth());

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.getDepth();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybridAutoBalenced.getDepth());

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.getDepth();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.getDepth());

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.getDepth();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + patriciaTrieTreeMap.getDepth());

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Calcul de profondeur moyenne:");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.getDepthAverage();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.getDepthAverage());

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.getDepthAverage();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.getDepthAverage());

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.getDepthAverage();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.getDepthAverage());

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.getDepthAverage();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.getDepthAverage());

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Calculer le nombre de mots comancant par \"reach\":");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.countPrefix("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.countPrefix("reach"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.countPrefix("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.countPrefix("reach"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.countPrefix("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.countPrefix("reach"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.countPrefix("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.countPrefix("reach"));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Lister les mots comancant par \"reach\":");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.getWordsPrefixedBy("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybrid.getWordsPrefixedBy("reach"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.getWordsPrefixedBy("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.getWordsPrefixedBy("reach"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.getWordsPrefixedBy("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.getWordsPrefixedBy("reach"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.getWordsPrefixedBy("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.getWordsPrefixedBy("reach"));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Recherche d'un mot existant \"reach\":");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.search("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.search("reach"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.search("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.search("reach"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.search("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.search("reach"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.search("reach");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.search("reach"));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Recherche d'un mot inexistant \"ordinateur\":");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.search("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.search("ordinateur"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.search("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.search("ordinateur"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.search("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.search("ordinateur"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.search("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.search("ordinateur"));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Obtention de nombre de mots :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.size();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.size());

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.size();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.size());

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.size();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.size());

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.size();
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.size());

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("L'ajout d'un nouveau mot \"ordinateur\" :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.addWord("ordinateur");
			endTime = System.nanoTime();
			hybrid.removeWord("ordinateur");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.addWord("ordinateur"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.addWord("ordinateur");
			endTime = System.nanoTime();
			hybridAutoBalenced.removeWord("ordinateur");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.addWord("ordinateur"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.addWord("ordinateur");
			endTime = System.nanoTime();
			patriciaTrieSimpleArray.removeWord("ordinateur");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.addWord("ordinateur"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.addWord("ordinateur");
			endTime = System.nanoTime();
			patriciaTrieTreeMap.removeWord("ordinateur");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.addWord("ordinateur"));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("L'ajout d'un mot deja existant \"ordinateur\" :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.addWord("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.addWord("ordinateur"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.addWord("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.addWord("ordinateur"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.addWord("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.addWord("ordinateur"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.addWord("ordinateur");
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.addWord("ordinateur"));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Suppression des mots [reach, reaches, reacheth, reaching] :");
		System.out.print("");
		System.out.print("\t- Trie Hybrid : \t\t");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybrid.removeWord("reach");
			hybrid.removeWord("reaches");
			hybrid.removeWord("reacheth");
			hybrid.removeWord("reaching");
			endTime = System.nanoTime();
			hybrid.addWord("reach");
			hybrid.addWord("reaches");
			hybrid.addWord("reacheth");
			hybrid.addWord("reaching");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : " + hybrid.removeWord("reach")
				+ " " + hybrid.removeWord("reaches") + " "
				+ hybrid.removeWord("reacheth") + " "
				+ hybrid.removeWord("reaching"));

		System.out.print("\t- Trie Hybrid avec equilibrage : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			hybridAutoBalenced.removeWord("reach");
			hybridAutoBalenced.removeWord("reaches");
			hybridAutoBalenced.removeWord("reacheth");
			hybridAutoBalenced.removeWord("reaching");
			endTime = System.nanoTime();
			hybridAutoBalenced.addWord("reach");
			hybridAutoBalenced.addWord("reaches");
			hybridAutoBalenced.addWord("reacheth");
			hybridAutoBalenced.addWord("reaching");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ hybridAutoBalenced.removeWord("reach") + " "
				+ hybridAutoBalenced.removeWord("reaches") + " "
				+ hybridAutoBalenced.removeWord("reacheth") + " "
				+ hybridAutoBalenced.removeWord("reaching"));

		System.out.print("\t- Patricia-Trie (noeuds vecteur) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieSimpleArray.removeWord("reach");
			patriciaTrieSimpleArray.removeWord("reaches");
			patriciaTrieSimpleArray.removeWord("reacheth");
			patriciaTrieSimpleArray.removeWord("reaching");
			endTime = System.nanoTime();
			patriciaTrieSimpleArray.addWord("reach");
			patriciaTrieSimpleArray.addWord("reaches");
			patriciaTrieSimpleArray.addWord("reacheth");
			patriciaTrieSimpleArray.addWord("reaching");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieSimpleArray.removeWord("reach") + " "
				+ patriciaTrieSimpleArray.removeWord("reaches") + " "
				+ patriciaTrieSimpleArray.removeWord("reacheth") + " "
				+ patriciaTrieSimpleArray.removeWord("reaching"));

		System.out.print("\t- Patricia-Trie (noeuds TreeMap) : ");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			patriciaTrieTreeMap.removeWord("reach");
			patriciaTrieTreeMap.removeWord("reaches");
			patriciaTrieTreeMap.removeWord("reacheth");
			patriciaTrieTreeMap.removeWord("reaching");
			endTime = System.nanoTime();
			patriciaTrieTreeMap.addWord("reach");
			patriciaTrieTreeMap.addWord("reaches");
			patriciaTrieTreeMap.addWord("reacheth");
			patriciaTrieTreeMap.addWord("reaching");
			sum += endTime - startTime;
		}
		System.out.println(nanosToStr(sum / iterationNember));
		System.out.println("\t\t resultat : "
				+ patriciaTrieTreeMap.removeWord("reach") + " "
				+ patriciaTrieTreeMap.removeWord("reaches") + " "
				+ patriciaTrieTreeMap.removeWord("reacheth") + " "
				+ patriciaTrieTreeMap.removeWord("reaching"));

	}

	public static void lunchTestMerging() {
		PatriciaTrie shakespearPart1 = TrieFactory.newPatriciaTrieSimpleArray();
		PatriciaTrie shakespearPart2 = TrieFactory.newPatriciaTrieSimpleArray();
		PatriciaTrie newTrie = null;
		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println(" - Construction de deux trie chaqu'un contient la moitier des mots des ouvrages:");
		shakespearWordsPart1.stream().forEach(word -> {
			shakespearPart1.addWord(word);
		});
		System.out.println("\tNombre de mots dans le premier Trie : "
				+ shakespearPart1.size());
		shakespearWordsPart2.stream().forEach(word -> {
			shakespearPart2.addWord(word);
		});
		System.out.println("\tNombre de mots dans le deuxieme Trie : "
				+ shakespearPart2.size());

		System.out
				.println("------------------------------------------------------------------------------------------------");

		System.out
				.println(" - Fusion des deux trie en un seul trie (PatriciaTrieSimpleArray), en mode parallèle :");

		sum = 0;
		for (int i = 0; i < iterationNember; i++) {

			startTime = System.nanoTime();
			newTrie = PatriciaTrie.mergeIntoPatriciaTrieSimpleArray(
					shakespearPart1, shakespearPart2, true);
			endTime = System.nanoTime();
			sum += endTime - startTime;

			shakespearWordsPart1.stream().forEach(word -> {
				shakespearPart1.addWord(word);
			});
			shakespearWordsPart2.stream().forEach(word -> {
				shakespearPart2.addWord(word);
			});
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.println("  Nombre de mots dans le nouveau Trie : "
				+ newTrie.size());
		System.out
				.println("------------------------------------------------------------------------------------------------");

		System.out
				.println(" - Fusion des deux trie en un seul trie (PatriciaTrieSimpleArray), en mode normal :");

		sum = 0;
		for (int i = 0; i < iterationNember; i++) {

			startTime = System.nanoTime();
			newTrie = PatriciaTrie.mergeIntoPatriciaTrieSimpleArray(
					shakespearPart1, shakespearPart2, false);
			endTime = System.nanoTime();
			sum += endTime - startTime;

			shakespearWordsPart1.stream().forEach(word -> {
				shakespearPart1.addWord(word);
			});
			shakespearWordsPart2.stream().forEach(word -> {
				shakespearPart2.addWord(word);
			});
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.println("  Nombre de mots dans le nouveau Trie : "
				+ newTrie.size());
		System.out
				.println("------------------------------------------------------------------------------------------------");

		System.out
				.println(" - Fusion des deux trie en un seul trie (PatriciaTrieTreeMap), en mode parallèle :");

		sum = 0;
		for (int i = 0; i < iterationNember; i++) {

			startTime = System.nanoTime();
			newTrie = PatriciaTrie.mergeIntoPatriciaTrieTreeMap(
					shakespearPart1, shakespearPart2, true);
			endTime = System.nanoTime();
			sum += endTime - startTime;

			shakespearWordsPart1.stream().forEach(word -> {
				shakespearPart1.addWord(word);
			});
			shakespearWordsPart2.stream().forEach(word -> {
				shakespearPart2.addWord(word);
			});
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.println("  Nombre de mots dans le nouveau Trie : "
				+ newTrie.size());
		System.out
				.println("------------------------------------------------------------------------------------------------");

		System.out
				.println(" - Fusion des deux trie en un seul trie (PatriciaTrieTreeMap), en mode normal :");

		sum = 0;
		for (int i = 0; i < iterationNember; i++) {

			startTime = System.nanoTime();
			newTrie = PatriciaTrie.mergeIntoPatriciaTrieTreeMap(
					shakespearPart1, shakespearPart2, false);
			endTime = System.nanoTime();
			sum += endTime - startTime;

			shakespearWordsPart1.stream().forEach(word -> {
				shakespearPart1.addWord(word);
			});
			shakespearWordsPart2.stream().forEach(word -> {
				shakespearPart2.addWord(word);
			});
		}
		System.out.println(nanosToStr(sum / iterationNember));

		System.out.println("  Nombre de mots dans le nouveau Trie : "
				+ newTrie.size());

	}

	public static void lunchTestConvertion() {
		ITrie trieTemp = null;
		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out.println("Construction du dictionaire de Shakspear");

		hybridAutoBalenced.clear();
		patriciaTrieSimpleArray.clear();
		patriciaTrieTreeMap.clear();
		hybrid.clear();
		for (String word : shakespearWords) {
			hybrid.addWord(word);
			patriciaTrieTreeMap.addWord(word);
			hybridAutoBalenced.addWord(word);
			patriciaTrieSimpleArray.addWord(word);
		}

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Conversion du trie hybrid vers PatriciaTrieSimpleArray :");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			trieTemp = PatriciaTrie
					.hybridToPatriciaTrieSimpleArray((HybridTrie) hybrid);
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}

		System.out.println("  Nombre de mots dans le Trie obtenu: "
				+ trieTemp.size());
		System.out.println(nanosToStr(sum / iterationNember));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Conversion du trie hybrid vers PatriciaTrieTrieMap :");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			trieTemp = PatriciaTrie
					.hybridToPatriciaTrieTreeMap((HybridTrie) hybrid);
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}

		System.out.println("  Nombre de mots dans le Trie obtenu: "
				+ trieTemp.size());
		System.out.println(nanosToStr(sum / iterationNember));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Conversion du trie hybrid equilibre' vers PatriciaTrieSimpleArray :");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			trieTemp = PatriciaTrie
					.hybridToPatriciaTrieSimpleArray((HybridTrie) hybridAutoBalenced);
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}

		System.out.println("  Nombre de mots dans le Trie obtenu: "
				+ trieTemp.size());
		System.out.println(nanosToStr(sum / iterationNember));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Conversion du trie hybrid equilibre' vers PatriciaTrieTrieMap :");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			trieTemp = PatriciaTrie
					.hybridToPatriciaTrieTreeMap((HybridTrie) hybridAutoBalenced);
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}

		System.out.println("  Nombre de mots dans le Trie obtenu: "
				+ trieTemp.size());
		System.out.println(nanosToStr(sum / iterationNember));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Conversion du patriciaTrieSimpleArray vers trie hybrid:");
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			trieTemp = HybridTrie
					.patriciaToHybridTrie((PatriciaTrie) patriciaTrieSimpleArray);
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}

		System.out.println("  Nombre de mots dans le Trie obtenu: "
				+ trieTemp.size());
		System.out.println(nanosToStr(sum / iterationNember));

		System.out
				.println("------------------------------------------------------------------------------------------------");
		System.out
				.println("Conversion du PatriciaTrieTrieMap vers trie hybrid:"
						+ patriciaTrieTreeMap.countWords());
		sum = 0;
		for (int i = 0; i < iterationNember; i++) {
			startTime = System.nanoTime();
			trieTemp = HybridTrie
					.patriciaToHybridTrie((PatriciaTrie) patriciaTrieTreeMap);
			endTime = System.nanoTime();
			sum += endTime - startTime;
		}

		System.out.println("  Nombre de mots dans le Trie obtenu: "
				+ trieTemp.size());
		System.out.println(nanosToStr(sum / iterationNember));
	}

	private static String nanosToStr(double nanos) {
		String s = "\tLe temps moyen de cette operation pour "
				+ iterationNember + " executions : ";
		if (nanos < 1000) {
			s += nanos + " nanosecondes";
		} else if (nanos < 1000000) {
			s += nanos / 1000 + " microsecondes";
		} else {
			s += nanos / 1000000 + " millisecondes";
		}
		return s;
	}

}
