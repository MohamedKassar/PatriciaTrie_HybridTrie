package com.kassarbakkouri.trie.implementations;

public class Pair {
	protected final static int RIGHT_CHILD = 0;
	protected final static int LEFT_CHILD = 1;
	protected final static int MIDDLE_CHILD = 2;
	
	private final HybridTrieNode node;
	private final int direction;
	
	public Pair(HybridTrieNode node, int direction) {
		this.node = node;
		this.direction = direction;
	}
	
	public HybridTrieNode getNode() {
		return node;
	}
	
	public int getDirection() {
		return direction;
	}
	
}
