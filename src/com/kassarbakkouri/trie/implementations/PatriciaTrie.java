package com.kassarbakkouri.trie.implementations;

import java.util.LinkedList;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;
import com.kassarbakkouri.trie.interfaces.ITrie;

public class PatriciaTrie extends Trie {

	PatriciaTrie(PatriciaTrieNode root) {
		super(root);
	}

	@Override
	public boolean addWord(String word) {
		try {
			if (root.addWord(new ASCII128Word(word, true))) {
				wordNember++;
				return true;
			}
		} catch (Exception e) {
			System.err.println("Incompatible word : " + word);
		}
		return false;
	}

	@Override
	public boolean search(String word) {
		word = word.concat(ASCII128Word.END_OF_WORD_CHAR_PRINTING);
		return super.search(word);
	}

	@Override
	public long countNullPointors() {
		/*
		 * dans cette implementation le nombre de noeuds = nombre de mot
		 */
		if (root instanceof PatriciaTrieNodeTreeMap)
			return wordNember;

		return super.countNullPointors();
	}

	/*
	 * @Override public void clear() { super.clear(); if (root instanceof
	 * PatriciaTrieNodeSimpleArray) { root = new PatriciaTrieNodeSimpleArray();
	 * } else if (root instanceof PatriciaTrieNodeTreeMap) { root = new
	 * PatriciaTrieNodeTreeMap(); } else { System.err.println("Error !!"); }
	 * 
	 * }
	 */
	protected PatriciaTrieNode getRoot() {
		return (PatriciaTrieNode) root;
	}

	/*
	 * apres avoir fusuionne' 2 trie il seron inutilisable
	 */
	public static PatriciaTrie mergeIntoPatriciaTrieTreeMap(ITrie trie1,
			ITrie trie2, boolean modeParallel) {
		return merge(trie1, trie2, modeParallel, 0);
	}

	public static PatriciaTrie mergeIntoPatriciaTrieSimpleArray(ITrie trie1,
			ITrie trie2, boolean modeParallel) {
		return merge(trie1, trie2, modeParallel, 1);
	}

	/*
	 * type 0 si on veut que le resultat soit avec un PatriciaTrieTreeMap 1 si
	 * on veut que le resultat soit avec un PatriciaTrieSimpleArray
	 */
	private static PatriciaTrie merge(ITrie trie1, ITrie trie2,
			boolean modeParallel, int type) {
		PatriciaTrieNode newRoot = null;
		PatriciaTrie newTrie;
		if (trie1 instanceof PatriciaTrie && trie2 instanceof PatriciaTrie) {
			PatriciaTrie t1 = (PatriciaTrie) trie1;
			PatriciaTrie t2 = (PatriciaTrie) trie2;
			newRoot = newNode(type);
			merge((PatriciaTrieNode) t1.root, (PatriciaTrieNode) t2.root,
					newRoot, modeParallel, type);
			/*
			 * pour securise l'acces au elements du nouveau trie
			 */
			trie1.clear();
			trie2.clear();

			newTrie = new PatriciaTrie(newRoot);
			newTrie.wordNember = newRoot.countWords();
			return newTrie;
		}
		System.err.println("Incompatible Tries Type");
		return null;
	}

	private static void merge(PatriciaTrieNode trie1, PatriciaTrieNode trie2,
			PatriciaTrieNode newTrie, boolean modeParallel, int type) {
		ASCII128Word wordTemp1, wordTemp2;
		PatriciaTrieNode trieTemp, trieTemp1;
		LinkedList<Thread> threads = new LinkedList<>();
		NodeElement nodeTemp1, nodeTemp2, nodeTemp3;
		for (char i = 0; i < 127; i++) {
			nodeTemp1 = trie1.getElement(i);
			nodeTemp2 = trie2.getElement(i);
			if (nodeTemp1 != null && nodeTemp2 != null) {
				wordTemp1 = nodeTemp1.content;
				wordTemp2 = nodeTemp2.content;
				int index = 0;
				while (wordTemp1 != null && wordTemp2 != null) {
					if (wordTemp1.getContent() != wordTemp2.getContent()) {
						break;
					}
					index++;
					wordTemp1 = wordTemp1.getNext();
					wordTemp2 = wordTemp2.getNext();
				}
				if (wordTemp1 == null & wordTemp2 == null) {
					/*
					 * les deux mot sont egaux
					 */
					newTrie.setElement(i, new NodeElement(nodeTemp1.content));
					/*
					 * tester si c'est un mot. i.e : le meme mot et il n'y a pas
					 * de next
					 */
					if (!nodeTemp1.content.isWord()) {
						nodeTemp3 = newTrie.getElement(i);
						nodeTemp3.next = newNode(type);

						final PatriciaTrieNode t1 = nodeTemp1.next;
						final PatriciaTrieNode t2 = nodeTemp2.next;
						final PatriciaTrieNode t3 = nodeTemp3.next;
						if (modeParallel) {
							threads.addFirst(new Thread(() -> {
								merge(t1, t2, t3, false, type);
							}));

							threads.getFirst().start();
						} else {
							merge(t1, t2, t3, false, type);
						}
					}

				} else if (wordTemp1 != null && wordTemp2 != null) {
					wordTemp1 = nodeTemp1.content.troncAt(index);
					wordTemp2 = nodeTemp2.content.troncAt(index);
					newTrie.setElement(i, new NodeElement(nodeTemp1.content));
					newTrie.getElement(i).next = trieTemp = newNode(type);
					trieTemp.setElement(wordTemp1.getFirst(), new NodeElement(
							wordTemp1));
					trieTemp.getElement(wordTemp1.getFirst()).next = nodeTemp1.next;

					trieTemp.setElement(wordTemp2.getFirst(), new NodeElement(
							wordTemp2));
					trieTemp.getElement(wordTemp2.getFirst()).next = nodeTemp2.next;

				} else if (wordTemp1 != null /* and wordTemp2 == null */) {
					newTrie.setElement(i, new NodeElement(nodeTemp2.content));
					newTrie.getElement(i).next = trieTemp = newNode(type);

					trieTemp1 = newNode(type);
					wordTemp1 = nodeTemp1.content.troncAt(index);

					trieTemp1.setElement(wordTemp1.getFirst(), new NodeElement(
							wordTemp1));
					trieTemp1.getElement(wordTemp1.getFirst()).next = nodeTemp1.next;

					final PatriciaTrieNode t1 = trieTemp1;
					final PatriciaTrieNode t2 = nodeTemp2.next;
					final PatriciaTrieNode t3 = trieTemp;
					if (modeParallel) {
						threads.addFirst(new Thread(() -> {
							merge(t1, t2, t3, false, type);
						}));

						threads.getFirst().start();
					} else {
						merge(t1, t2, t3, false, type);
					}

				} else if (wordTemp2 != null /* and wordTemp1 == null */) {
					newTrie.setElement(i, new NodeElement(nodeTemp1.content));
					newTrie.getElement(i).next = trieTemp = newNode(type);

					trieTemp1 = newNode(type);
					wordTemp1 = nodeTemp2.content.troncAt(index);
					trieTemp1.setElement(wordTemp1.getFirst(), new NodeElement(
							wordTemp1));
					trieTemp1.getElement(wordTemp1.getFirst()).next = nodeTemp2.next;

					final PatriciaTrieNode t1 = trieTemp1;
					final PatriciaTrieNode t2 = nodeTemp1.next;
					final PatriciaTrieNode t3 = trieTemp;

					if (modeParallel) {
						threads.addFirst(new Thread(() -> {
							merge(t1, t2, t3, false, type);
						}));

						threads.getFirst().start();
					} else {
						merge(t1, t2, t3, false, type);
					}
				}
			} else if (nodeTemp1 != null) {
				newTrie.setElement(i, nodeTemp1);
			} else if (nodeTemp2 != null) {
				newTrie.setElement(i, nodeTemp2);
			}
		}
		if (modeParallel)
			for (Thread t : threads) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}

	public static PatriciaTrie hybridToPatriciaTrieSimpleArray(HybridTrie h) {
		if (h.isEmpty()) {
			return TrieFactory.newPatriciaTrieSimpleArray();
		}
		PatriciaTrieNodeSimpleArray root = new PatriciaTrieNodeSimpleArray();
		try {
			hybridToPatriciaTrie(root, h.getRoot(), 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PatriciaTrie newTrie = new PatriciaTrie(root);
		newTrie.wordNember = root.countWords();
		return newTrie;
	}

	public static PatriciaTrie hybridToPatriciaTrieTreeMap(HybridTrie h) {
		if (h.isEmpty()) {
			return TrieFactory.newPatriciaTrieTreeMap();
		}
		PatriciaTrieNodeTreeMap root = new PatriciaTrieNodeTreeMap();
		try {
			hybridToPatriciaTrie(root, h.getRoot(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		PatriciaTrie newTrie = new PatriciaTrie(root);
		newTrie.wordNember = root.countWords();
		return newTrie;
	}

	private static void hybridToPatriciaTrie(PatriciaTrieNode patriciaNode,
			HybridTrieNode hybridNode, int type) throws Exception {
		if (hybridNode.getLeftChild() != null) {
			hybridToPatriciaTrie(patriciaNode, hybridNode.getLeftChild(), type);
		}
		hybridToPatriciaTrie1(patriciaNode, hybridNode, type);
		if (hybridNode.getRightChild() != null) {
			hybridToPatriciaTrie(patriciaNode, hybridNode.getRightChild(), type);
		}
	}

	private static void hybridToPatriciaTrie1(PatriciaTrieNode patriciaNode,
			HybridTrieNode hybridNode, int type) throws Exception {
		HybridTrieNode nodeTemp = hybridNode;
		ASCII128Word wordTemp = null;
		PatriciaTrieNode temp;

		wordTemp = new ASCII128Word(nodeTemp.getContent().toString(), false);

		if (!nodeTemp.isWord())/*
								 * si le premier noeud ne represente pas un mot
								 * alors surement le fils milieu != null
								 */
			while (nodeTemp.getMiddleChild() != null) {
				nodeTemp = nodeTemp.getMiddleChild();
				if (!nodeTemp.isWord() && nodeTemp.getLeftChild() == null
						&& nodeTemp.getRightChild() == null
						&& nodeTemp.getMiddleChild() != null) {
					wordTemp.append(new ASCII128Word(nodeTemp.getContent()
							.toString(), false));
				} else {
					break;
				}
			}

		if (!nodeTemp.isWord()) {/*
								 * dans ce cas on est sur que ce n'est pas la
								 * racine, et que filsMilieu != null
								 */
			patriciaNode.setElement(wordTemp.getFirst(), new NodeElement(
					wordTemp));
			patriciaNode.getElement(wordTemp.getFirst()).next = temp = newNode(type);
			hybridToPatriciaTrie(temp, nodeTemp, type);
		} else {
			if (nodeTemp == hybridNode) {/* si la racine est un mot */
				if (nodeTemp.getMiddleChild() == null) {
					wordTemp.setWord(true);
					patriciaNode.setElement(wordTemp.getFirst(),
							new NodeElement(wordTemp));
				} else {
					patriciaNode.setElement(wordTemp.getFirst(),
							new NodeElement(wordTemp));
					patriciaNode.getElement(wordTemp.getFirst()).next = temp = newNode(type);
					temp.setElement(ASCII128Word.END_OF_WORD_CHAR,
							new NodeElement(new ASCII128Word("", true)));
					hybridToPatriciaTrie(temp, nodeTemp.getMiddleChild(), type);
				}
			} else if (nodeTemp.getLeftChild() == null
					&& nodeTemp.getRightChild() == null
					&& nodeTemp.getMiddleChild() == null) {
				wordTemp.append(new ASCII128Word(nodeTemp.getContent()
						.toString(), true));
				patriciaNode.setElement(wordTemp.getFirst(), new NodeElement(
						wordTemp));
			} else if (nodeTemp.getLeftChild() == null
					&& nodeTemp.getRightChild() == null) {
				wordTemp.append(new ASCII128Word(nodeTemp.getContent()
						.toString(), false));
				patriciaNode.setElement(wordTemp.getFirst(), new NodeElement(
						wordTemp));
				patriciaNode.getElement(wordTemp.getFirst()).next = temp = newNode(type);
				temp.setElement(ASCII128Word.END_OF_WORD_CHAR, new NodeElement(
						new ASCII128Word("", true)));
				hybridToPatriciaTrie(temp, nodeTemp.getMiddleChild(), type);
			} else {/*
					 * le fils milieu et au moins un des deux fils ne sont pas
					 * null
					 */
				patriciaNode.setElement(wordTemp.getFirst(), new NodeElement(
						wordTemp));
				patriciaNode.getElement(wordTemp.getFirst()).next = temp = newNode(type);
				hybridToPatriciaTrie(temp, nodeTemp, type);
			}
		}
	}

	private static PatriciaTrieNode newNode(int type) {
		return type == 0 ? new PatriciaTrieNodeTreeMap()
				: new PatriciaTrieNodeSimpleArray();
	}

}
