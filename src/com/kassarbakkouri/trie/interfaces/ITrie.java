package com.kassarbakkouri.trie.interfaces;

import java.util.ArrayList;

public interface ITrie {

	public boolean addWord(String word);

	public boolean removeWord(String word);

	public ArrayList<String> getWords();

	public ArrayList<String> getWordsPrefixedBy(String prefix);

	public boolean search(String word);

	public long size();

	public long countPrefix(String prefix);

	public String toString();

	public double getDepthAverage();

	public long countLeafNodes();

	public int getDepth();

	public long countNullPointors();

	public long countWords();

	public boolean isEmpty();

	public void clear();

}