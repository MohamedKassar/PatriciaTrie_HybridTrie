package com.kassarbakkouri.ASCII128Word;

public class ASCII128Word {

	public final static char END_OF_WORD_CHAR = '#';
	public final static String END_OF_WORD_CHAR_PRINTING = "#";/*
																 * il doit etre
																 * un seul car
																 * sinon la
																 * recherche ne
																 * marche pas
																 */
	private ASCII128Word last = this;
	private char content;
	private ASCII128Word next = null;

	private ASCII128Word() {
	}

	public ASCII128Word(String word, boolean isWord) throws Exception {

		if (word.equals("")) {
			content = END_OF_WORD_CHAR;
			return;
		}

		int lengthTemp = word.length(), index = 0;
		char charTemp;

		for (; index < lengthTemp - 1; index++) {
			charTemp = word.charAt(index);
			/*
			 * si cette caractere n'est pas en ASCII 128 jete une exception
			 */
			isASCII128(charTemp);

			last.next = new ASCII128Word();
			last.content = charTemp;
			last = last.next;
		}
		last.content = word.charAt(index);
		if (isWord) {
			last.next = new ASCII128Word();
			last = last.next;
			last.content = END_OF_WORD_CHAR;
		}
	}

	private ASCII128Word(char content, ASCII128Word next, ASCII128Word last) {
		super();
		this.last = last;
		this.content = content;
		this.next = next;
	}

	public char getContent() {
		return content;
	}

	public ASCII128Word getNext() {
		return next;
	}

	public boolean isWord() {
		return last.content == END_OF_WORD_CHAR;
	}

	public void setWord(boolean b) {
		if (b) {
			if (last.content != END_OF_WORD_CHAR) {
				last.next = new ASCII128Word();
				last = last.next;
				last.content = END_OF_WORD_CHAR;
			} else {
				System.err.println("Alrady word : " + toString() + " !");
			}
		} else {
			if (last.content == END_OF_WORD_CHAR) {
				ASCII128Word temp = this;
				while (temp.next != last) {
					temp = temp.next;
				}
				last = temp;
				last.next = null;
			} else {
				System.err.println("Alrady not word : " + toString() + " !");
			}
		}
	}

	/*
	 * Unused method
	 */
	public ASCII128Word getLast() {
		return last;
	}

	public char getFirst() {
		return content;
	}
	

	public ASCII128Word troncAt(int index) {
		ASCII128Word temp = this;
		if (index < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (index == 0) {
			ASCII128Word temp1 = new ASCII128Word(content, next, last);
			/*
			 * TODO: A tester !!
			 */
			content = END_OF_WORD_CHAR;
			next = null;
			last = this;
			return temp1;
		}
		for (int i = 0; i < index - 1 && temp != null; i++) {
			temp = temp.next;
		}

		if (temp == null || temp.next == null) {
			throw new IndexOutOfBoundsException();
		}

		ASCII128Word temp1 = temp.next;
		temp1.last = last;
		
		last = temp;
		temp.next = null;
		// temp1.setWord(false);
		return temp1;
	}
	
	public void append(ASCII128Word word) throws Exception{
		if(isWord()){
			throw new Exception("can not append to a word");
		}
		last.next = word;
		last = word.last;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof String) {
			return ((String) obj).concat(END_OF_WORD_CHAR_PRINTING).equals(
					toString());
		} else if (obj instanceof ASCII128Word) {
			ASCII128Word temp = (ASCII128Word) obj;
			if (temp.isWord() && isWord() || !temp.isWord() && !isWord()) {
				return temp.toString().equals(toString());
			}
		}
		return false;
	}

	public static void isASCII128(char character) throws Exception {
		if (character < 0 || character > 127) {
			throw new Exception("not a ASCII128 Character");
		}
	}

	@Override
	public String toString() {
		if (content != ASCII128Word.END_OF_WORD_CHAR) {
			if (next == null) {
				return Character.toString(content);
			} else {
				return content + next.toString();
			}
		}
		return ASCII128Word.END_OF_WORD_CHAR_PRINTING;
	}
	
	public String toStringWithoutEndChar() {
		if (content != ASCII128Word.END_OF_WORD_CHAR) {
			if (next == null) {
				return Character.toString(content);
			} else {
				return content + next.toStringWithoutEndChar();
			}
		}
		return "";
	}
}
