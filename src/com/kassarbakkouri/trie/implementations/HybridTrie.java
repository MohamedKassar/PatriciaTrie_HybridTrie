package com.kassarbakkouri.trie.implementations;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;

public class HybridTrie extends Trie {

	HybridTrie() {
		super(new HybridTrieNode());
	}

	private HybridTrie(HybridTrieNode root) {
		super(root);
	}

	@Override
	public boolean addWord(String word) {

		if (word.length() == 0) {
			return false;
		}
		HybridTrieNode.counter = wordNember;
		try {
			if (root.addWord(new ASCII128Word(word, false))) {
				wordNember++;
				return true;
			}
		} catch (Exception e) {
			System.err.println("Incompatible word : " + word);
		}
		return false;

	}

	@Override
	public boolean removeWord(String word) {
		if (!isEmpty())
			try {
				if (root.removeWord(word, "")) {
					((HybridTrieNode) root).content = null;
					((HybridTrieNode) root).isWord = null;
				}
				wordNember--;
				return true;
			} catch (Exception e) {
				if (e.getMessage().equals("not existant word")) {
					return false;
				}
				e.printStackTrace();
			}
		return false;
	}

	@Override
	public boolean search(String word) {
		if (word.isEmpty() || isEmpty())
			return false;
		return super.search(word);
	}

	protected HybridTrieNode getRoot() {
		return (HybridTrieNode) root;
	}

	public static HybridTrie patriciaToHybridTrie(PatriciaTrie patriciaTrie) {
		if (patriciaTrie.isEmpty()) {
			return TrieFactory.newHybridTrie();
		}
		HybridTrieNode root = new HybridTrieNode();
		HybridTrieNode.counter = 0;
		HybridTrieNode.patriciaToHybridTrie(root, patriciaTrie.getRoot());
		HybridTrie temp = new HybridTrie(root);
		temp.wordNember = HybridTrieNode.counter;
		return temp;
	}

	/*
	 * @Override public void clear() { super.clear(); //root = new
	 * HybridTrieNode(); }
	 */
}
