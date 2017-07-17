package com.kassarbakkouri.test.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;
import com.kassarbakkouri.test.Tester;
import com.kassarbakkouri.trie.implementations.HybridTrie;
import com.kassarbakkouri.trie.implementations.PatriciaTrie;
import com.kassarbakkouri.trie.implementations.TrieFactory;
import com.kassarbakkouri.trie.interfaces.ITrie;

public class Main {

	public static void main(String[] args) throws Exception {

		/*
		 * Remarque: la verification des caractere pour assurer qu'ils sont
		 * compris entre 0 et 127 se fait dans la classe:
		 * com.kassarbakkouri.ASCII128Word.ASCII128Word
		 */

		/*
		 * cette methode sert a initialiser le nombre d'iteration des tests,
		 * (par defaut 50)
		 */
		Tester.setIterationNember(5);
		
		//Tester.lunchTestOnBasicSample();
		Tester.lunchTestOnShakspearBooks();
		//Tester.lunchTestMerging();
		//Tester.lunchTestConvertion();

	}

	protected static void testStackHybrid() throws Exception {
		HybridTrie hybrid = TrieFactory.newHybridTrie();

		hybrid.addWord("aabcd");
		hybrid.addWord("aboubi");
		hybrid.addWord("acici");
		hybrid.addWord("adimaria");
		hybrid.addWord("aeurl");
		hybrid.addWord("afoufou");
		hybrid.addWord("agaz");
		hybrid.addWord("ahahaha");

		hybrid.addWord("aizi");
		hybrid.addWord("ajiji");
		hybrid.addWord("akaka");
		hybrid.addWord("almlight");
		hybrid.addWord("ammlight");
		hybrid.addWord("anlight");
		hybrid.addWord("aolight");
		hybrid.addWord("aplight");
		hybrid.addWord("aqlight");
		hybrid.addWord("arlight");
		hybrid.addWord("aslight");
		hybrid.addWord("atlight");
		hybrid.addWord("aulight");
		hybrid.addWord("avlight");
		hybrid.addWord("awlight");
		hybrid.addWord("axlight");
		hybrid.addWord("aylight");
		hybrid.addWord("azlight");
		System.out.println(hybrid);
	}

	protected static void removeTest() throws Exception {
		ITrie trie = TrieFactory.newHybridTrie();

		File folder = new File("Shakespeare");
		File[] listOfFiles = folder.listFiles();
		BufferedReader br;
		String line;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				br = new BufferedReader(new FileReader(listOfFiles[i]));
				while ((line = br.readLine()) != null) {
					if (!line.equals("")) {
						trie.addWord(line);
					} else
						System.err.println(line);
				}
				br.close();
			}
		}
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;

		System.out.println(elapsedTime);

		ArrayList<String> words = trie.getWords();

		words.stream().forEach(
				w -> {
					try {
						ITrie trie1 = TrieFactory.newHybridTrie();

						File folder1 = new File("Shakespeare");
						File[] listOfFiles1 = folder1.listFiles();
						BufferedReader br1;
						String line1;

						for (int i = 0; i < listOfFiles1.length; i++) {
							if (listOfFiles1[i].isFile()) {
								br1 = new BufferedReader(new FileReader(
										listOfFiles1[i]));
								while ((line1 = br1.readLine()) != null) {
									if (!line1.equals("")) {
										trie1.addWord(line1);
									} else
										System.err.println(line1);
								}
								br1.close();
							}
						}
						long i = trie1.countWords();

						if (trie1.search(w) && trie1.removeWord(w)
								&& trie1.countWords() != (i - 1)
								&& !trie1.search(w)) {
							System.err.print("\nE\n");
						} else {
							System.out.print("i");
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				});
	}

	protected static void hybridTest() throws Exception {
		// shakespearTestParalellFusion();
		ITrie t = TrieFactory.newHybridTrie();

		t.addWord("ah");

		t.addWord("ab");
		t.addWord("aa");
		t.addWord("ac");
		t.addWord("aca");
		t.addWord("acl");
		t.addWord("acd");
		System.out.println(t.getWords());
		System.out.println(PatriciaTrie.hybridToPatriciaTrieTreeMap(
				(HybridTrie) t).getWords());
		/*
		 * t.addWord("hbdil"); t.addWord("adil"); t.addWord("aail");
		 * t.addWord("aaail"); t.addWord("tarek"); t.addWord("ttarek");
		 * t.addWord("taarek");
		 */
		System.out.println(t);
		System.out.println(t.removeWord("ab"));
		System.out.println(t.getWords());
	}

	protected static void fusionTestPatricia() throws Exception {

		ITrie trie = TrieFactory.newPatriciaTrieTreeMap();
		ITrie trie1 = TrieFactory.newPatriciaTrieSimpleArray();
		ITrie trie2 = TrieFactory.newPatriciaTrieSimpleArray();

		trie.addWord("alloo");
		trie.addWord("a");
		trie.addWord("");
		trie1.addWord("zani");
		trie.addWord("zano");
		trie.addWord("zane");
		trie1.addWord("allo");
		trie1.addWord("al");
		trie.addWord("alim");
		trie1.addWord("adoulla");
		trie.addWord("adoullani");
		trie.addWord("adou");
		trie.addWord("biz");
		trie1.addWord("bizzou");
		trie.addWord("bizniu");
		trie1.addWord("biznin");
		trie.addWord("bizni");
		trie1.addWord("aaoui"); //
		trie.addWord("aanon");
		trie.addWord("zoumba");
		trie.addWord("kaki");

		trie2.addWord("alloo");
		trie2.addWord("a");
		trie2.addWord("");
		trie2.addWord("allo");
		trie2.addWord("zani");
		trie2.addWord("zano");
		trie2.addWord("zane");
		trie2.addWord("al");
		trie2.addWord("alim");
		trie2.addWord("adoulla");
		trie2.addWord("adoullani");
		trie2.addWord("adou");
		trie2.addWord("biz");
		trie2.addWord("bizzou");
		trie2.addWord("bizniu");
		trie2.addWord("biznin");
		trie2.addWord("bizni");
		trie2.addWord("aaoui");
		trie2.addWord("aanon");
		trie2.addWord("zoumba");
		trie2.addWord("kaki");

		System.out.println(trie.getWords());
		System.out.println(trie1.getWords());
		long startTime = System.currentTimeMillis();
		trie = PatriciaTrie
				.mergeIntoPatriciaTrieSimpleArray(trie, trie1, false);
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println(elapsedTime);
		System.err.println(trie.getWords());
		System.out.println(trie2.getWords());

		ASCII128Word word = new ASCII128Word("doulla", true);
		System.out.println(word.troncAt(3));
		System.out.println(word);
	}

	protected static void shakespearTestParalellFusion() throws Exception {
		ITrie t = TrieFactory.newPatriciaTrieTreeMap(), trie = TrieFactory
				.newPatriciaTrieTreeMap();
		ITrie trie1 = TrieFactory.newPatriciaTrieTreeMap();

		File folder = new File("Shakespeare");
		File[] listOfFiles = folder.listFiles();
		BufferedReader br;
		String line;

		for (int i = 0; i < listOfFiles.length / 2; i++) {
			if (listOfFiles[i].isFile()) {
				br = new BufferedReader(new FileReader(listOfFiles[i]));
				while ((line = br.readLine()) != null) {
					if (!line.equals("")) {
						trie.addWord(line);
						t.addWord(line);
					} else
						System.err.println(line);
				}
				br.close();
			}
		}

		for (int i = listOfFiles.length / 2; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				br = new BufferedReader(new FileReader(listOfFiles[i]));
				while ((line = br.readLine()) != null) {
					if (!line.equals("")) {
						trie1.addWord(line);
						t.addWord(line);
					} else
						System.err.println(line);
				}
				br.close();
			}
		}

		ITrie p = PatriciaTrie.mergeIntoPatriciaTrieSimpleArray(trie, trie1,
				true);

		System.out.println(p.getWordsPrefixedBy("reach"));
		System.out.println(t.countWords());
		System.out.println(p.countWords());
	}

	protected static void shakespearTestHybrid1() throws Exception {

		ITrie trie = TrieFactory.newHybridTrie();
		// ITrie trie1 = TrieFactory.newHybridTrie();
		trie.addWord("zallz");
		trie.addWord("zallmi");
		trie.addWord("xouxou");
		// trie.addWord("");
		trie.addWord("sousou");
		trie.addWord("ququ");
		trie.addWord("pipi");
		trie.addWord("omo");
		trie.addWord("nounou");
		trie.addWord("mimi");
		trie.addWord("loulou");
		trie.addWord("kaka");
		trie.addWord("jiji");
		//
		trie.addWord("izi");
		trie.addWord("houcin");
		trie.addWord("gogo"); //
		trie.addWord("foufou");
		// trie.addWord("zoumba");
		trie.addWord("euuu");

		System.out.println(trie.countPrefix("zall"));
		System.out.println(trie.removeWord("zallmi"));
		System.out.println(trie.countPrefix("zall"));
		System.out.println(trie);

	}

	protected static void shakespearTestPatricia() throws Exception {

		ITrie trie = TrieFactory.newPatriciaTrieTreeMap();
		ITrie trieH = TrieFactory.newHybridTrie();

		File folder = new File("Shakespeare");
		File[] listOfFiles = folder.listFiles();
		BufferedReader br;
		String line;

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				br = new BufferedReader(new FileReader(listOfFiles[i]));
				while ((line = br.readLine()) != null) {
					if (!line.equals("")) {
						trie.addWord(line);
						trieH.addWord(line);
					} else
						System.err.println(line);
				}
				br.close();
			}
		}
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;

		System.out.println(elapsedTime);
		/*
		 * trie.addWord("alloo"); trie.addWord("a"); //trie.addWord("");
		 * trie.addWord("allo"); trie.addWord("al"); trie.addWord("alim");
		 * trie.addWord("adoulla"); trie.addWord("adoullani");
		 * trie.addWord("adou"); trie.addWord("biz"); trie.addWord("bizzou");
		 * trie.addWord("bizniu"); // trie.addWord("biznin");
		 * trie.addWord("bizni"); trie.addWord("aaoui"); //
		 * trie.addWord("aanon"); // trie.addWord("zoumba");
		 * trie.addWord("kaki");
		 */
		System.err.println(trie.countWords());
		ITrie hybrid = HybridTrie.patriciaToHybridTrie((PatriciaTrie) trie);
		System.err.println(trie.countWords());
		startTime = System.currentTimeMillis();

		System.out.println(trie.getDepthAverage());
		System.out.println(trie.countWords() + "- hybrid : "
				+ hybrid.countWords() + "- hybrid original : "
				+ trieH.countWords());
		System.out.println(trie.countPrefix("reache") + "- hybrid : "
				+ hybrid.countPrefix("reache") + "- hybrid original: "
				+ trieH.countPrefix("reache"));
		System.out.println(trie.removeWord("reaches") + "- hybrid : "
				+ hybrid.removeWord("reaches") + "- hybrid original: "
				+ trieH.removeWord("reaches"));
		System.out.println(trie.countPrefix("reache") + "- hybrid : "
				+ hybrid.countPrefix("reache") + "- hybrid original: "
				+ trieH.countPrefix("reache"));
		System.out.println(trie.removeWord("reacheth") + "- hybrid : "
				+ hybrid.removeWord("reacheth") + "- hybrid original: "
				+ trieH.removeWord("reacheth"));
		System.out.println(trie.countPrefix("reache") + "- hybrid : "
				+ hybrid.countPrefix("reache") + "- hybrid original: "
				+ trieH.countPrefix("reache"));
		System.out.println(trie.countWords() + "- hybrid : "
				+ hybrid.countWords() + "- hybrid original: "
				+ trieH.countWords());
		System.out.println(trie.countWords() + "- hybrid : "
				+ hybrid.countWords() + "- hybrid original: "
				+ trieH.countWords());

		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;
		System.out.println(elapsedTime);

		// System.out.println(trie.countPrefix("lion"));
		/*
		 * ASCII128Word word = new ASCII128Word("abcdefg", false);
		 * System.out.println(word.troncAt(0)); System.out.println(word);
		 */
	}

	protected static void shakespearTestHybrid() throws Exception {

		ITrie hybrid = TrieFactory.newHybridTrie();

		File folder = new File("Shakespeare");
		File[] listOfFiles = folder.listFiles();
		BufferedReader br;
		String line;
		LinkedList<String> shakespear = new LinkedList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				br = new BufferedReader(new FileReader(listOfFiles[i]));
				while ((line = br.readLine()) != null) {
					if (!line.equals(""))
						shakespear.add(line);
					else
						System.err.println(line);
				}
				br.close();
			}
		}

		long startTime = System.nanoTime();
		for (String word : shakespear) {
			hybrid.addWord(word);
		}
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;

		System.out.println(nanoToStr(elapsedTime));

		hybrid.addWord("alloo");
		hybrid.addWord("a");
		// trie.addWord("");
		hybrid.addWord("allo");
		hybrid.addWord("al");
		hybrid.addWord("alim");
		hybrid.addWord("adoulla");
		hybrid.addWord("adoullani");
		hybrid.addWord("adou");
		hybrid.addWord("biz");
		hybrid.addWord("bizzou");
		hybrid.addWord("bizniu");
		//
		hybrid.addWord("biznin");
		hybrid.addWord("bizni");
		hybrid.addWord("aaoui"); //
		hybrid.addWord("aanon");
		// trie.addWord("zoumba");
		hybrid.addWord("kaki");

		startTime = System.nanoTime();

		System.out.println(hybrid.getDepthAverage());
		System.out.println(hybrid.countWords() /* +"  "+ trie.getWords() */);
		System.out.println(hybrid.getWordsPrefixedBy("reache"));
		System.out.println(hybrid.countPrefix("reache"));
		System.out.println(hybrid.search("reaches"));
		System.out.println(hybrid.search("reacheth") + "\n");
		// System.out.println(trie.removeWord("reaches"));
		// System.out.println(trie.removeWord("reacheth"));
		System.out.println(hybrid.countPrefix("reache"));
		System.out.println(hybrid.search("reaches"));
		System.out.println(hybrid.search("reacheth"));

		System.out.println(hybrid.countWords() /* +"  "+trie.getWords() */);
		System.out.println(hybrid.getDepthAverage());
		ITrie patriciaS = PatriciaTrie
				.hybridToPatriciaTrieSimpleArray((HybridTrie) hybrid);
		ITrie patriciaT = PatriciaTrie
				.hybridToPatriciaTrieTreeMap((HybridTrie) hybrid);
		System.out.println(" patricia :" + patriciaS.countWords());
		System.out.println(patriciaS.getWordsPrefixedBy("reach"));
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println(nanoToStr(elapsedTime));

		Vector<Long> v1 = new Vector<>(), v2 = new Vector<>(), v3 = new Vector<>();

		Runnable r1 = () -> {
			patriciaT.search("zoo");
		};

		Runnable r2 = () -> {
			patriciaS.search("zoo");
		};

		Runnable r3 = () -> {
			hybrid.search("zoo");
		};

		for (int i = 0; i < 100; i++) {
			startTime = System.nanoTime();
			// patriciaT.search("zoo");
			r1.run();
			endTime = System.nanoTime();
			v1.add(endTime - startTime);
		}
		for (int i = 0; i < 100; i++) {
			startTime = System.nanoTime();
			r2.run();
			// patriciaS.search("zoo");
			endTime = System.nanoTime();
			v2.add(endTime - startTime);

		}
		for (int i = 0; i < 100; i++) {
			startTime = System.nanoTime();
			r3.run();
			// hybrid.search("zoo");
			endTime = System.nanoTime();
			v3.add(endTime - startTime);
		}

		double sum = 0;
		for (int i = 0; i < 100; i++)
			sum += v1.get(i);
		System.out.println("Moyenne patricia Tree = " + nanoToStr(sum / 1000));

		sum = 0;
		for (int i = 0; i < 100; i++)
			sum += v2.get(i);
		System.out.println("Moyenne patricia Array = " + nanoToStr(sum / 1000));

		sum = 0;
		for (int i = 0; i < 100; i++)
			sum += v3.get(i);
		System.out.println("Moyenne Hybrid = " + nanoToStr(sum / 1000));
		// System.out.println(trie.countPrefix("lion"));
		/*
		 * ASCII128Word word = new ASCII128Word("abcdefg", false);
		 * System.out.println(word.troncAt(0)); System.out.println(word);
		 */
	}

	private static String nanoToStr(double l) {
		if (l < 1000) {
			return l + " nanos-secondes";
		} else if (l < 1000000) {
			return l / 1000 + " micros-secondes";
		} else {
			return l / 1000000 + " millis-secondes";
		}
	}

}
