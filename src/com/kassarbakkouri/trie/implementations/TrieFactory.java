package com.kassarbakkouri.trie.implementations;


public abstract class TrieFactory {

	public static PatriciaTrie newPatriciaTrieSimpleArray() {
		return new PatriciaTrie(new PatriciaTrieNodeSimpleArray());
	}

	public static PatriciaTrie newPatriciaTrieTreeMap() {
		return new PatriciaTrie(new PatriciaTrieNodeTreeMap());
	}

	public static HybridTrie newHybridTrie() {
		return new HybridTrie();
	}
	
	public static HybridTrie newHybridTrieAutoBalanced() {
		return new HybridTrieAutoBalanced();
	}
}
