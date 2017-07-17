package com.kassarbakkouri.trie.implementations;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;

class NodeElement {
	protected ASCII128Word content;
	protected PatriciaTrieNode next;

	public NodeElement(ASCII128Word content) {
		this.content = content;
	}

}
