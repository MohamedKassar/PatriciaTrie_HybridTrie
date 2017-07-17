package com.kassarbakkouri.trie.implementations;

import java.util.ArrayList;

import com.kassarbakkouri.trie.interfaces.ITrie;
import com.kassarbakkouri.trie.interfaces.INode;

public abstract class Trie implements ITrie {

	protected INode root;
	protected long wordNember = 0;

	public Trie(INode root) {
		this.root = root;
	}

	@Override
	public abstract boolean addWord(String word);

	@Override
	public boolean removeWord(String word){
		if (!isEmpty())
			try {
				root.removeWord(word, "");
				wordNember--;
				return true;
			} catch (Exception e) {
				if (e.getMessage() != null
						&& e.getMessage().equals("not existant word")) {
					return false;
				} else {
					e.printStackTrace();
				}

			}
		return false;
	}

	@Override
	public ArrayList<String> getWords() {
		ArrayList<String> vectorTemp = new ArrayList<>();
		if (!isEmpty())
			root.getWords(vectorTemp, "");
		return vectorTemp;
	}

	@Override
	public ArrayList<String> getWordsPrefixedBy(String prefix) {
		ArrayList<String> vector = new ArrayList<>();
		if (!isEmpty())
			root.getWordsPrefixedBy(prefix, vector, "");
		return vector;
	}

	public boolean search(String word) {
		if (!isEmpty())
			return root.search(word, "");
		return false;
	}

	@Override
	public long countPrefix(String prefix) {
		if (!isEmpty())
			return root.countPrefix(prefix, "");
		return 0;
	}

	@Override
	public double getDepthAverage() {
		ArrayList<Integer> vector = new ArrayList<>();
		if (!isEmpty())
			root.getLeafNodesDepths(vector, 0);
		int sum = 0;
		for (Integer i : vector) {
			sum += i;
		}
		return (double) sum / vector.size();
	}

	@Override
	public long countLeafNodes() {
		if (!isEmpty())
			return root.countLeafNodes();
		return 0;
	}

	@Override
	public int getDepth() {
		if (!isEmpty())
			return root.getDepth();
		return 0;
	}

	@Override
	public long countNullPointors() {
		return root.countNullPointors();
	}

	@Override
	public long countWords() {
		if (!isEmpty())
			return root.countWords();
		return 0;
	}

	@Override
	public long size() {
		return wordNember;
	}

	@Override
	public boolean isEmpty() {
		return wordNember == 0;
	}

	@Override
	public void clear() {
		wordNember = 0;
		try {
			root = root.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		String temp = "";
		ArrayList<String> vectorTemp = getWords();
		for (String w : vectorTemp) {
			temp += w + "\n";
		}
		return temp;
	}
}
