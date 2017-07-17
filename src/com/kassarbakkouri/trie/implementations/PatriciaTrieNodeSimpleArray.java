package com.kassarbakkouri.trie.implementations;

import java.util.ArrayList;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;

class PatriciaTrieNodeSimpleArray extends PatriciaTrieNode {

	NodeElement[] elements = new NodeElement[128];

	PatriciaTrieNodeSimpleArray(){
	}
	
	@Override
	public long countNullPointors() {
		long temp = 0;
		for (int i = 0; i < 128; i++) {
			if (elements[i] != null) {
				if (elements[i].content.isWord()) {
					temp++;
				} else {
					temp += elements[i].next.countNullPointors();
				}
			} else {
				temp++;
			}
		}
		return temp;
	}

	@Override
	protected int countElemsInNode() {
		int temp = 0;
		for (int i = 0; i < 128; i++)
			if (elements[i] != null)
				temp++;

		return temp;
	}

	@Override
	protected ArrayList<NodeElement> getElements() {
		ArrayList<NodeElement> temp = new ArrayList<>();
		for (int i = 0; i < 127; i++) {
			if (elements[i] != null)
				temp.add(elements[i]);
		}
		return temp;
	}

	@Override
	protected boolean containEndOfWordChar() {
		return elements[ASCII128Word.END_OF_WORD_CHAR] != null;
	}

	@Override
	protected NodeElement getElement(char c) {
		return elements[c];
	}

	@Override
	protected void setElement(char c, NodeElement element) {
		elements[c] = element;
	}
}
