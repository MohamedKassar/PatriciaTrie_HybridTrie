package com.kassarbakkouri.trie.implementations;

import java.util.ArrayList;
import java.util.TreeMap;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;

class PatriciaTrieNodeTreeMap extends PatriciaTrieNode {

	TreeMap<Character, NodeElement> elements = new TreeMap<>();

	PatriciaTrieNodeTreeMap() {
	}

	/*
	 * dans cette implementation le nombre de pointeurs vers null = nombre de
	 * mot (methode jamais executer voir PatriciaTrie countNullPointors())
	 */
	@Override
	public long countNullPointors() {
		return countWords();
	}

	@Override
	protected ArrayList<NodeElement> getElements() {
		ArrayList<NodeElement> temp = new ArrayList<>(elements.values());
		return temp;
	}

	@Override
	protected boolean containEndOfWordChar() {
		return elements.containsKey(ASCII128Word.END_OF_WORD_CHAR);
	}

	@Override
	protected NodeElement getElement(char c) {
		return elements.containsKey(c) ? elements.get(c) : null;
	}

	@Override
	protected void setElement(char c, NodeElement element) {
		if (element != null)
			elements.put(c, element);
		else
			elements.remove(c);

	}

	@Override
	protected int countElemsInNode() {
		return elements.size();
	}


}
