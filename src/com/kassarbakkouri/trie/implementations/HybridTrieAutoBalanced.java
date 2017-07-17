package com.kassarbakkouri.trie.implementations;

import java.util.Stack;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;

class HybridTrieAutoBalanced extends HybridTrie {
	
	@Override
	public boolean addWord(String word){
		return addWordWithBalancing(word);
	}

	protected boolean addWordWithBalancing(String word){
		if (word.length() == 0) {
			return false;
		}
		HybridTrieNode.counter = wordNember;
		Stack<Pair> stack = new Stack<Pair>();
		try {
			if (((HybridTrieNode) root).addWord(new ASCII128Word(word, false),
					stack)) {
				wordNember++;

				Pair temp = null;
				while (!stack.isEmpty()) {
					temp = stack.pop();
					if (temp.getDirection() == Pair.LEFT_CHILD) {
						temp.getNode().leftChild = balance(temp.getNode().leftChild);
					} else if (temp.getDirection() == Pair.RIGHT_CHILD) {
						temp.getNode().rightChild = balance(temp.getNode().rightChild);
					} else {
						temp.getNode().middleChild = balance(temp.getNode().middleChild);
					}
				}
				if (temp != null && temp.getNode() == root) {
					root = balance((HybridTrieNode) root);
				}
				return true;
			}
		} catch (Exception e) {
			System.err.println("Incompatible word : "+ word);
		}
		return false;
	}

	private static HybridTrieNode rightRotate(HybridTrieNode node) {
		HybridTrieNode racine = node.leftChild;
		HybridTrieNode temp1 = node.leftChild.rightChild;
		racine.rightChild = node;
		node.leftChild = temp1;
		return racine;
	}

	private static HybridTrieNode leftRotate(HybridTrieNode node) {
		HybridTrieNode racine = node.rightChild;
		HybridTrieNode temp1 = node.rightChild.leftChild;
		racine.leftChild = node;
		node.rightChild = temp1;
		return racine;
	}

	private static HybridTrieNode balance(HybridTrieNode node) {
		int rightDepth = node.getRightDepth();
		int leftDepth = node.getLeftDepth();
		int dif = rightDepth - leftDepth;
		if (dif < -1 || dif > 1) {
			if (leftDepth > rightDepth) {
				if (node.leftChild.getRightDepth() > node.leftChild.getLeftDepth()) {
					// RGD
					node.leftChild = leftRotate(node.leftChild);
					return rightRotate(node);
				} else {
					// RD
					return rightRotate(node);
				}
			} else if (leftDepth < rightDepth) {
				if (node.rightChild.getRightDepth() > node.rightChild.getLeftDepth()) {
					// RG
					return leftRotate(node);

				} else {
					// RGD
					node.rightChild = rightRotate(node.rightChild);
					return leftRotate(node);
				}
			}
		}
		return node;
	}

}
