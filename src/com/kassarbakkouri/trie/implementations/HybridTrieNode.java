package com.kassarbakkouri.trie.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.kassarbakkouri.ASCII128Word.ASCII128Word;
import com.kassarbakkouri.trie.interfaces.INode;

class HybridTrieNode implements INode {

	protected static long counter = 0;

	protected Long isWord = null;
	protected Character content = null;

	protected HybridTrieNode rightChild = null;
	protected HybridTrieNode leftChild = null;
	protected HybridTrieNode middleChild = null;

	HybridTrieNode() {
	}

	@Override
	public boolean addWord(ASCII128Word word) {
		ASCII128Word temp;
		if (content == null) {
			if (word.getNext() == null) {
				content = word.getFirst();
				isWord = counter++;
				return true;
			} else {
				temp = word.troncAt(1);
				content = word.getFirst();
				middleChild = new HybridTrieNode();
				return middleChild.addWord(temp);
			}
		} else {
			if (word.getFirst() > content) {
				if (rightChild == null)
					rightChild = new HybridTrieNode();
				return rightChild.addWord(word);
			} else if (word.getFirst() < content) {
				if (leftChild == null)
					leftChild = new HybridTrieNode();

				return leftChild.addWord(word);
			} else if (word.getFirst() == content) {
				if (word.getNext() == null) {
					if (isWord != null)
						return false;
					isWord = counter++;
					return true;
				} else {
					if (middleChild == null)
						middleChild = new HybridTrieNode();
					return middleChild.addWord(word.troncAt(1));
				}
			}
		}
		return false;
	}

	protected boolean addWord(ASCII128Word word, Stack<Pair> stack) {
		ASCII128Word temp;
		if (content == null) {
			if (word.getNext() == null) {
				content = word.getFirst();
				isWord = counter++;
				return true;
			} else {
				temp = word.troncAt(1);
				content = word.getFirst();
				middleChild = new HybridTrieNode();
				return middleChild.addWord(temp);
			}
		} else {
			if (word.getFirst() > content) {
				if (rightChild == null) {
					rightChild = new HybridTrieNode();
					return rightChild.addWord(word);
				}
				stack.push(new Pair(this, Pair.RIGHT_CHILD));
				return rightChild.addWord(word, stack);
			} else if (word.getFirst() < content) {
				if (leftChild == null) {
					leftChild = new HybridTrieNode();
					return leftChild.addWord(word);
				}
				stack.push(new Pair(this, Pair.LEFT_CHILD));
				return leftChild.addWord(word, stack);
			} else if (word.getFirst() == content) {
				if (word.getNext() == null) {
					if (isWord != null)
						return false;
					isWord = counter++;
					return true;
				} else {
					if (middleChild == null) {
						middleChild = new HybridTrieNode();
						return middleChild.addWord(word.troncAt(1));
					}
					stack.clear();
					stack.push(new Pair(this, Pair.MIDDLE_CHILD));
					return middleChild.addWord(word.troncAt(1), stack);
				}
			}
		}
		return false;
	}

	protected int getRightDepth() {
		if (rightChild != null)
			return 1 + rightChild.getDepthLR();
		return 0;
	}

	protected int getLeftDepth() {
		if (leftChild != null)
			return 1 + leftChild.getDepthLR();
		return 0;
	}

	protected int getDepthLR() {
		if (leftChild != null && rightChild != null) {
			return Math.max(1 + leftChild.getDepthLR(),
					1 + rightChild.getDepthLR());
		} else if (rightChild != null) {
			return 1 + rightChild.getDepthLR();
		} else if (leftChild != null) {
			return 1 + leftChild.getDepthLR();
		} else {
			return 0;
		}
	}

	@Override
	public boolean search(String word, String prefix) {
		if (word.charAt(prefix.length()) == content) {
			if (word.length() > prefix.length() + 1) {
				if (middleChild != null)
					return middleChild.search(word, prefix + content);
			} else {
				// TODO remove FilsM.
				if (isWord != null) {
					return true;
				} else {
					return false;
				}
			}
		} else if (word.charAt(prefix.length()) < content) {
			if (leftChild != null)
				return leftChild.search(word, prefix);
		} else if (word.charAt(prefix.length()) > content) {
			if (rightChild != null)
				return rightChild.search(word, prefix);
		}
		return false;
	}

	@Override
	public long countNullPointors() {
		int temp = 0;
		if (leftChild != null) {
			temp += leftChild.countNullPointors();
		} else {
			temp++;
		}
		if (middleChild != null) {
			temp += middleChild.countNullPointors();
		} else {
			temp++;
		}
		if (rightChild != null) {
			temp += rightChild.countNullPointors();
		} else {
			temp++;
		}
		return temp;
	}

	@Override
	public int getDepth() {
		int temp = 0;
		if (leftChild != null) {
			temp = 1 + leftChild.getDepth();
		}
		if (middleChild != null) {
			temp = Math.max(temp, 1 + middleChild.getDepth());
		}
		if (rightChild != null) {
			temp = Math.max(temp, 1 + rightChild.getDepth());
		}
		return temp;
	}

	@Override
	public void getWords(ArrayList<String> vector, String prefix) {
		if (leftChild != null) {
			leftChild.getWords(vector, prefix);
		}
		if (isWord != null) {
			vector.add(prefix + content);
		}
		if (middleChild != null) {
			middleChild.getWords(vector, prefix + content);
		}
		if (rightChild != null) {
			rightChild.getWords(vector, prefix);
		}
	}

	@Override
	public void getWordsPrefixedBy(String prefix, ArrayList<String> vector,
			String interPrefix) {
		if (prefix.charAt(interPrefix.length()) == content) {
			if (prefix.length() > interPrefix.length() + 1) {
				if (middleChild != null)
					middleChild.getWordsPrefixedBy(prefix, vector, interPrefix
							+ content);
			} else {
				if (isWord()) {
					vector.add(prefix);
				}
				middleChild.getWords(vector, prefix);
			}
		} else if (prefix.charAt(interPrefix.length()) < content) {
			if (leftChild != null)
				leftChild.getWordsPrefixedBy(prefix, vector, interPrefix);
		} else if (prefix.charAt(interPrefix.length()) > content) {
			if (rightChild != null)
				rightChild.getWordsPrefixedBy(prefix, vector, interPrefix);
		}
	}

	@Override
	public long countWords() {
		long temp = 0;
		if (leftChild != null) {
			temp += leftChild.countWords();
		}
		if (isWord != null) {
			temp++;
		}
		if (middleChild != null) {
			temp += middleChild.countWords();
		}
		if (rightChild != null) {
			temp += rightChild.countWords();
		}
		return temp;
	}

	@Override
	public long countPrefix(String prefix, String interPrefix) {
		if (prefix.charAt(interPrefix.length()) == content) {
			if (prefix.length() > interPrefix.length() + 1) {
				if (middleChild != null)
					return middleChild.countPrefix(prefix, interPrefix
							+ content);
			} else {
				if (isWord()) {
					return 1 + middleChild.countWords();
				}
				return middleChild.countWords();
			}
		} else if (prefix.charAt(interPrefix.length()) < content) {
			if (leftChild != null)
				return leftChild.countPrefix(prefix, interPrefix);
		} else if (prefix.charAt(interPrefix.length()) > content) {
			if (rightChild != null)
				return rightChild.countPrefix(prefix, interPrefix);
		}
		return 0;
	}

	@Override
	public long countLeafNodes() {
		long temp = 0;
		if (rightChild == null && leftChild == null && middleChild == null) {
			return 1;
		}
		if (leftChild != null)
			temp += leftChild.countLeafNodes();
		if (middleChild != null)
			temp += middleChild.countLeafNodes();
		if (rightChild != null)
			temp += rightChild.countLeafNodes();

		return temp;
	}

	@Override
	public void getLeafNodesDepths(ArrayList<Integer> vector, int currentDepth) {
		if (rightChild == null && leftChild == null && middleChild == null) {
			vector.add(currentDepth);
		}
		if (leftChild != null)
			leftChild.getLeafNodesDepths(vector, currentDepth + 1);
		if (middleChild != null)
			middleChild.getLeafNodesDepths(vector, currentDepth + 1);
		if (rightChild != null)
			rightChild.getLeafNodesDepths(vector, currentDepth + 1);
	}

	/*
	 * cette methode retourne vrai si on veut supprimer le predecesseur
	 * (suppresion a la remonte'). sinn return faux si le mot n'existe pas
	 * jetter une exception
	 */
	@Override
	public boolean removeWord(String word, String prefix) throws Exception {
		if (word.charAt(prefix.length()) == content) {
			if (word.length() > prefix.length() + 1) {
				if (middleChild != null)
					if (middleChild.removeWord(word, prefix + content)) {
						if (middleChild.rightChild == null
								&& middleChild.leftChild == null) {
							middleChild = null;
							if (isWord == null) {
								return true;
							}
						} else if (middleChild.rightChild == null) {
							middleChild = middleChild.leftChild;
						} else if (middleChild.leftChild == null) {
							middleChild = middleChild.rightChild;
						} else {// tous les fils non null
							HybridTrieNode RChild = middleChild.rightChild, temp;
							temp = middleChild = middleChild.leftChild;
							while (temp.rightChild != null) {
								temp = temp.rightChild;
							}
							temp.rightChild = RChild;
						}
						return false;
					} else {
						return false;
					}
			} else {
				if (isWord != null) {
					if (middleChild != null) {
						isWord = null;
						return false;
					}
					return true;
				} else {
					throw new Exception("not existant word");
				}
			}
		} else if (word.charAt(prefix.length()) < content) {
			if (leftChild != null)
				if (leftChild.removeWord(word, prefix)) {
					if (leftChild.rightChild == null
							&& leftChild.leftChild == null) {
						leftChild = null;
						/*
						 * test inutile
						 * 
						 * if (isWord == null && FilsM == null && FilsD == null)
						 * {// TODO // : // && // FilsM // == // null // && //
						 * FilsD // == // null // to // check
						 * System.err.println("\nICIC"); return true; }
						 */
					} else if (leftChild.rightChild == null) {
						leftChild = leftChild.leftChild;
					} else if (leftChild.leftChild == null) {
						leftChild = leftChild.rightChild;
					} else {// les fils non null
						HybridTrieNode RChild = leftChild.rightChild, temp;
						temp = leftChild = leftChild.leftChild;
						while (temp.rightChild != null) {
							temp = temp.rightChild;
						}
						temp.rightChild = RChild;
					}
					return false;
				} else {
					return false;
				}
		} else if (word.charAt(prefix.length()) > content) {
			if (rightChild != null)
				if (rightChild.removeWord(word, prefix)) {
					if (rightChild.rightChild == null
							&& rightChild.leftChild == null) {
						rightChild = null;
						/*
						 * test inutile
						 * 
						 * if (isWord == null && FilsM == null && FilsG == null)
						 * {// TODO // : // && // FilsM // == // null // && //
						 * FilsG // == // null // to // check
						 * System.err.println("\nICIC"); return true; }
						 */
					} else if (rightChild.rightChild == null) {
						rightChild = rightChild.leftChild;
					} else if (rightChild.leftChild == null) {
						rightChild = rightChild.rightChild;
					} else {// les fils non null
						HybridTrieNode RChild = rightChild.rightChild, temp;
						temp = rightChild = rightChild.leftChild;
						while (temp.rightChild != null) {
							temp = temp.rightChild;
						}
						temp.rightChild = RChild;
					}
					return false;
				} else {
					return false;
				}
		}

		throw new Exception("not existant word");
	}

	public boolean isWord() {
		return isWord != null;
	}

	protected Character getContent() {
		return content;
	}

	protected HybridTrieNode getRightChild() {
		return rightChild;
	}

	protected HybridTrieNode getLeftChild() {
		return leftChild;
	}

	protected HybridTrieNode getMiddleChild() {
		return middleChild;
	}

	/*
	 * algorithme a optimiser
	 */
	private static void sortElements(List<NodeElement> list,
			ArrayList<NodeElement> sortedElements) {
		if (!list.isEmpty()) {
			sortedElements.add(list.remove(list.size() / 2));
			List<NodeElement> list1 = new ArrayList<NodeElement>();
			List<NodeElement> list2 = new ArrayList<NodeElement>();
			for (int i = 0; i < list.size() / 2; i++) {
				list1.add(list.get(i));
			}
			for (int i = list.size() / 2; i < list.size(); i++) {
				list2.add(list.get(i));
			}
			sortElements(list1, sortedElements);
			sortElements(list2, sortedElements);
		}
	}

	protected static void patriciaToHybridTrie(HybridTrieNode hybridNode,
			PatriciaTrieNode patriciaNode) {
		ArrayList<NodeElement> patriciaNodeElements = patriciaNode
				.getElements();

		if (patriciaNodeElements.get(0).content.getFirst() == ASCII128Word.END_OF_WORD_CHAR) {
			patriciaNodeElements.remove(0);
		}
		ArrayList<NodeElement> sortedElements = new ArrayList<NodeElement>();

		sortElements(patriciaNodeElements, sortedElements);

		sortedElements.stream().forEach(node -> {
			patriciaToHybridTrie(hybridNode, node);
		});
	}

	protected static void patriciaToHybridTrie(HybridTrieNode hybridNode,
			NodeElement patriciaNodeElem) {
		if (hybridNode.content == null) {
			ASCII128Word temp = patriciaNodeElem.content;
			while (temp.getNext() != null
					&& temp.getNext().getContent() != ASCII128Word.END_OF_WORD_CHAR) {
				hybridNode.content = temp.getFirst();
				hybridNode.middleChild = new HybridTrieNode();
				hybridNode = hybridNode.middleChild;
				temp = temp.getNext();
			}
			hybridNode.content = temp.getFirst();
			if (temp.getNext() == null) {
				if (patriciaNodeElem.next.containEndOfWordChar()) {
					hybridNode.isWord = counter++;
				}
				patriciaToHybridTrie(
						hybridNode.middleChild = new HybridTrieNode(),
						patriciaNodeElem.next);
			} else { // end of word
				hybridNode.isWord = counter++;
			}
		} else {
			if (patriciaNodeElem.content.getFirst() < hybridNode.content) {
				if (hybridNode.leftChild != null) {
					patriciaToHybridTrie(hybridNode.leftChild, patriciaNodeElem);
				} else {
					patriciaToHybridTrie(
							hybridNode.leftChild = new HybridTrieNode(),
							patriciaNodeElem);
				}
			} else if (patriciaNodeElem.content.getFirst() > hybridNode.content) {
				if (hybridNode.rightChild != null) {
					patriciaToHybridTrie(hybridNode.rightChild,
							patriciaNodeElem);
				} else {
					patriciaToHybridTrie(
							hybridNode.rightChild = new HybridTrieNode(),
							patriciaNodeElem);
				}
			}
		}
	}
}
