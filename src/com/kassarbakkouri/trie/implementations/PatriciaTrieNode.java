package com.kassarbakkouri.trie.implementations;

import java.util.ArrayList;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;
import com.kassarbakkouri.trie.interfaces.INode;

abstract class PatriciaTrieNode implements INode {

	protected abstract NodeElement getElement(char c);

	protected abstract void setElement(char c, NodeElement element);

	protected abstract int countElemsInNode();

	protected abstract ArrayList<NodeElement> getElements();

	protected abstract boolean containEndOfWordChar();

	public abstract long countNullPointors();

	public boolean addWord(ASCII128Word word) {
		char positionTemp = word.getFirst();
		NodeElement currentElement;
		if ((currentElement = getElement(positionTemp)) == null) {
			setElement(positionTemp, new NodeElement(word));
		} else {
			ASCII128Word currentWordTemp = currentElement.content, wordTemp = word;
			int i = 0;

			while (currentWordTemp != null && wordTemp != null) {
				if (currentWordTemp.getContent() != wordTemp.getContent()) {
					break;
				}
				i++;
				currentWordTemp = currentWordTemp.getNext();
				wordTemp = wordTemp.getNext();
			}

			if (currentWordTemp == null && wordTemp == null) {
				return false;
			} else {
				if (currentWordTemp != null) {
					PatriciaTrieNodeSimpleArray trieTemp = new PatriciaTrieNodeSimpleArray();
					ASCII128Word toAdd = currentElement.content.troncAt(i);
					trieTemp.addWord(toAdd);
					trieTemp.elements[toAdd.getFirst()].next = currentElement.next;
					currentElement.next = trieTemp;
				}
				return currentElement.next.addWord(word.troncAt(i));
			}

		}
		return true;
	}

	/*
	 * cette methode retourne vrai si on veut supprimer le predecesseur
	 * (suppresion a la remonte'). sinn return faux si le mot n'existe pas
	 * jetter une exception
	 */
	@Override
	public boolean removeWord(String word, String prefix) throws Exception {
		char i;
		try {
			i = getIndexOfRestOfPrefix(word, prefix);
		} catch (Exception e) {
			// TODO: marche mais a revoir
			i = ASCII128Word.END_OF_WORD_CHAR;
		}
		NodeElement temp;
		if ((temp = getElement(i)) != null) {
			if (temp.content.isWord()) {
				if ((prefix + temp.content.toString()).equals(word
						.concat(ASCII128Word.END_OF_WORD_CHAR_PRINTING))) {
					setElement(i, null);
					if (countElemsInNode() <= 1) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				if (temp.next
						.removeWord(word, prefix + temp.content.toString())) {
					for (NodeElement element : temp.next.getElements()) {
						/*
						 * cette boucle fait au plus une seul iteration
						 */
						temp.content.append(element.content);
					}
					temp.next = null;

					if (countElemsInNode() == 1) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		throw new Exception("not existant word");
	}

	@Override
	public void getWords(ArrayList<String> vector, String prefix) {
		for (NodeElement element : getElements()) {
			if (element.content.isWord()) {
				vector.add(prefix + element.content.toStringWithoutEndChar());
			} else {
				element.next.getWords(vector,
						prefix + element.content.toString());
			}
		}
	}

	@Override
	public void getWordsPrefixedBy(String prefix, ArrayList<String> vector,
			String interPrefix) {
		String stringTemp;
		NodeElement element;
		char i;
		try {
			i = getIndexOfRestOfPrefix(prefix, interPrefix);
		} catch (Exception e) {
			// ici dans le cas d'une chaine vide
			i = ASCII128Word.END_OF_WORD_CHAR;
		}

		if ((element = getElement(i)) != null) {
			if (element.next != null) {
				stringTemp = interPrefix + element.content.toString();
				if (isPrefixOf(prefix, stringTemp)) {
					element.next.getWords(vector, stringTemp);
				} else {
					if (isPrefixOf(stringTemp, prefix)) {
						element.next.getWordsPrefixedBy(prefix, vector,
								stringTemp);
					}
				}
			} else {
				if (isPrefixOf(prefix, interPrefix + element.content.toString())) {
					vector.add(interPrefix + element.content.toString());
				}
			}
		}
	}

	@Override
	public boolean search(String word, String prefix) {
		char i;
		try {
			i = getIndexOfRestOfPrefix(word, prefix);
		} catch (Exception e) {
			return false;
		}
		NodeElement element;
		if ((element = getElement(i)) != null) {
			if (element.content.isWord()) {
				if ((prefix + element.content.toString()).equals(word)) {
					return true;
				}
			} else {
				if (element.next.search(word,
						prefix + element.content.toString())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public long countWords() {
		long longTemp = 0;

		for (NodeElement element : getElements()) {
			if (element.content.isWord()) {
				longTemp++;
			} else {
				longTemp += element.next.countWords();
			}
		}
		return longTemp;
	}

	@Override
	public long countPrefix(String prefix, String interPrefix) {
		String stringTemp;
		char i;

		try {
			i = getIndexOfRestOfPrefix(prefix, interPrefix);
		} catch (Exception e) {
			System.err.println("\n" + prefix + interPrefix);
			// ici dans le cas d'une chaine vide
			i = ASCII128Word.END_OF_WORD_CHAR;
		}

		NodeElement temp;
		if ((temp = getElement(i)) != null) {
			if (temp.next != null) {
				stringTemp = interPrefix + temp.content.toString();
				if (isPrefixOf(prefix, stringTemp)) {
					return temp.next.countWords();
				} else {
					if (isPrefixOf(stringTemp, prefix)) {
						return temp.next.countPrefix(prefix, stringTemp);
					}
				}
			} else {
				if (isPrefixOf(prefix, interPrefix + temp.content.toString())) {
					return 1;
				}
			}
		}

		return 0;
	}

	@Override
	public void getLeafNodesDepths(ArrayList<Integer> vectorTemp,
			int currentDepth) {
		boolean b = true;

		for (NodeElement element : getElements()) {
			if (!element.content.isWord()) {
				b = false;
				element.next.getLeafNodesDepths(vectorTemp, currentDepth + 1);
			}
		}
		if (b) {
			vectorTemp.add(currentDepth);
		}
	}

	@Override
	public long countLeafNodes() {
		long longTemp = 0;
		boolean b = true;
		for (NodeElement element : getElements()) {
			if (!element.content.isWord()) {
				b = false;
				longTemp += element.next.countLeafNodes();
			}
		}
		if (b) {
			return longTemp + 1;
		} else {
			return longTemp;
		}

	}

	@Override
	public int getDepth() {
		int intTemp = 0;
		for (NodeElement element : getElements()) {
			if (!element.content.isWord()) {
				intTemp = Math.max(intTemp, 1 + element.next.getDepth());
			}
		}
		return intTemp;
	}

	protected static boolean isPrefixOf(String a, String b) {
		int i = 0;
		for (; i < a.length() && i < b.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return false;
			}
		}
		if (i < a.length()) {
			return false;
		}
		return true;
	}

	protected static char getIndexOfRestOfPrefix(String word, String prefix)
			throws Exception {
		if (word.length() < (prefix.length() + 1))
			throw new Exception();
		char i = word.charAt(prefix.length());
		i = (word.charAt(prefix.length()) == ASCII128Word.END_OF_WORD_CHAR_PRINTING
				.charAt(0)) ? ASCII128Word.END_OF_WORD_CHAR : i;
		return i;
	}

}