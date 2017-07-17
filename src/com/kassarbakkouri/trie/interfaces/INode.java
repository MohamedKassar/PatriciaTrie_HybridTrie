package com.kassarbakkouri.trie.interfaces;

import java.util.ArrayList;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;

public interface INode {

	public long countPrefix(String prefix, String interPrefix);

	public long countWords();

	public void getWordsPrefixedBy(String prefix, ArrayList<String> vector,
			String interPrefix);

	public void getWords(ArrayList<String> vector, String prefix);

	public int getDepth();

	public long countNullPointors();

	public boolean search(String word, String prefix);

	public boolean addWord(ASCII128Word word);

	public long countLeafNodes();

	public void getLeafNodesDepths(ArrayList<Integer> vector, int currentDepth);

	/*
	 * cette methode retourne vrai si on veut supprimer le predecesseur (suppresion a la remonte').
	 * sinn return faux
	 * si le mot n'existe pas jetter une exception
	 */
	public boolean removeWord(String word, String prefix) throws Exception ;
}