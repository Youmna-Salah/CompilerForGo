import java.util.Stack;
import java.lang.System;
import java.io.*;
import java_cup.runtime.Symbol;


class Lexer implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

private int Quote_count = 0;
private static String Quoted ="";
	//initialize  variables to be used by class
	private Stack<Character> stack_char = new Stack<Character>();
	boolean paran = false;
	boolean square = false;
	boolean curly = false;
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

//Add code to be executed on initialization of the lexer
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NOT_ACCEPT,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NOT_ACCEPT,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NOT_ACCEPT,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NOT_ACCEPT,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NOT_ACCEPT,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NOT_ACCEPT,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NOT_ACCEPT,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NOT_ACCEPT,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NOT_ACCEPT,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NOT_ACCEPT,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NOT_ACCEPT,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NOT_ACCEPT,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NOT_ACCEPT,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"36:8,18:2,19,36:2,48,36:18,18,36,45,36:2,43,42,36,52,55,33,31,44,32,58,35,5" +
"1:10,39,41,36,40,36:3,22,50:3,24,50,26,50:4,23,21,50:3,28,25,20,27,29,50:5," +
"53,46,56,36,30,47,2,50,3,15,6,16,5,14,13,50,4,17,37,34,38,1,50,10,9,7,11,49" +
",12,50,8,50,54,36,57,36:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,141,
"0,1,2,1:2,3,1:2,4,5,1:4,6,7,8,1:7,9,3,10,1:4,9:13,11,12,13,11,14,15,16,17,1" +
",14,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41" +
",42,43,44,45,46,39,47,48,41,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64" +
",65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89" +
",90,91,92,93,94,95,96,97,9:2,98,99,100")[0];

	private int yy_nxt[][] = unpackFromString(101,59,
"1,2,137,103,137:2,104,105,137,128,129,137:2,45,137,138,106,137,3:2,139,137:" +
"2,118,130,137,140,137:3,4,5,6,7,137,8,4,137:2,9,10,11,46,12,13,14,4,15,-1,8" +
"7,137,16,17,18,19,20,21,22,23,-1:60,137,131,137:15,-1:2,137:10,-1:4,137,-1:" +
"2,137:2,-1:10,137:2,51,-1:38,25,-1:62,26,-1:63,27,-1:19,44:18,-1,44:25,-1,5" +
"0,-1,44:11,-1,53:18,-1,53:25,-1,55,-1,53:11,-1:51,16,-1:8,137:17,-1:2,137:1" +
"0,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,26:18,-1,26:28,-1,26:10,-1,44:18," +
"-1,44:25,29,50,-1,44:11,-1,137:15,24,137,-1:2,137:10,-1:4,137,-1:2,122,137," +
"-1:10,137:2,51,-1:49,28,-1:17,53:18,-1,53:25,-1,55,30,53:11,-1,137:17,-1:2," +
"137:10,79,-1:3,137,-1:2,137:2,-1:10,137:2,51,-1:8,44:17,57,59,44:25,47,50,4" +
"4:12,-1:51,51,-1:8,137:9,31,137:7,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137" +
":2,51,-1:8,53:17,61,63,53:26,55,48,53:11,-1,137:5,32,137:11,-1:2,137:10,-1:" +
"4,137,-1:2,137:2,-1:10,137:2,51,-1:8,44:17,57,59,44:25,29,50,-1,44:11,-1,13" +
"7:5,33,137:11,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:25,59:2,-1:" +
"26,44,-1:13,137:5,34,137:11,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51," +
"-1:8,53:17,61,63,53:25,-1,55,30,53:11,-1,137:2,35,137:14,-1:2,137:10,-1:4,1" +
"37,-1:2,137:2,-1:10,137:2,51,-1:25,63:2,-1:26,53,-1:13,137:6,36,137:10,-1:2" +
",137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:31,67,-1:35,137:17,-1:2,137:1" +
"0,65,-1:3,137,-1:2,137:2,-1:10,137:2,51,-1:35,71,-1:31,137:6,37,137:10,-1:2" +
",137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:31,73,-1:35,137:13,38,137:3,-" +
"1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:36,75,-1:30,137:17,-1:2,13" +
"7:10,-1:4,39,-1:2,137:2,-1:10,137:2,51,-1:35,77,-1:31,137:6,40,137:10,-1:2," +
"137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:29,81,-1:37,137:17,-1:2,137:10" +
",69,-1:3,137,-1:2,137:2,-1:10,137:2,51,-1:36,83,-1:30,137:5,41,137:11,-1:2," +
"137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:31,85,-1:35,137:6,42,137:10,-1" +
":2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:30,52,-1:36,137:17,-1:2,137" +
":5,43,137:4,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17,-1:2,137:5,49,13" +
"7:4,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:35,86,-1:31,137,54,137:15,-1:2,13" +
"7:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:8,56,137:8,-1:2,137:10,-1:" +
"4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:8,58,137:8,-1:2,137:10,-1:4,137,-1" +
":2,137:2,-1:10,137:2,51,-1:8,60,137:16,-1:2,137:10,-1:4,137,-1:2,137:2,-1:1" +
"0,137:2,51,-1:8,137:17,-1:2,137:10,-1:4,62,-1:2,137:2,-1:10,137:2,51,-1:8,1" +
"37:8,64,137:8,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17,-1" +
":2,66,137:9,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:2,68,137:14,-1:2,13" +
"7:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:2,70,137:14,-1:2,137:10,-1" +
":4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:9,72,137:7,-1:2,137:10,-1:4,137,-" +
"1:2,137:2,-1:10,137:2,51,-1:8,137:9,74,137:7,-1:2,137:10,-1:4,137,-1:2,137:" +
"2,-1:10,137:2,51,-1:8,137:17,-1:2,137:3,76,137:6,-1:4,137,-1:2,137:2,-1:10," +
"137:2,51,-1:8,137:4,78,137:12,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,5" +
"1,-1:8,137:16,80,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17" +
",-1:2,137:4,82,137:5,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17,-1:2,13" +
"7:4,84,137:5,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137,88,137:15,-1:2,137" +
":10,-1:4,137,-1:2,137,107,-1:10,137:2,51,-1:8,137:16,89,-1:2,137:10,-1:4,13" +
"7,-1:2,137:2,-1:10,137:2,51,-1:8,137:7,90,137:9,-1:2,137:10,-1:4,137,-1:2,1" +
"37:2,-1:10,137:2,51,-1:8,137:10,91,137:6,-1:2,137:10,-1:4,137,-1:2,137,133," +
"-1:10,137:2,51,-1:8,137:17,-1:2,137:10,-1:4,92,-1:2,137:2,-1:10,137:2,51,-1" +
":8,137:17,-1:2,93,137:9,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:10,94,1" +
"37:6,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:6,95,137:10,-1" +
":2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:10,96,137:6,-1:2,137:" +
"10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17,-1:2,137:10,-1:4,137,-1:2" +
",137,97,-1:10,137:2,51,-1:8,137:17,-1:2,137:2,98,137:7,-1:4,137,-1:2,137:2," +
"-1:10,137:2,51,-1:8,137,99,137:15,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137" +
":2,51,-1:8,137:10,100,137:6,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51," +
"-1:8,137:17,-1:2,137:3,101,137:6,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,13" +
"7:17,-1:2,137:7,102,137:2,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17,-1" +
":2,137:4,108,137:5,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:9,109,137:7," +
"-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:12,110,137:4,-1:2,1" +
"37:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:6,111,137:10,-1:2,137:10," +
"-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,112,137:16,-1:2,137:10,-1:4,137,-1:" +
"2,137:2,-1:10,137:2,51,-1:8,137:17,-1:2,137:9,113,-1:4,137,-1:2,137:2,-1:10" +
",137:2,51,-1:8,137:3,114,137:13,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2" +
",51,-1:8,137,115,137:15,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8" +
",137:17,-1:2,137:3,116,137:6,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17" +
",-1:2,137:2,117,137:7,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:6,119,137" +
":4,120,137:5,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:5,121," +
"137:11,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:17,-1:2,137:" +
"8,123,137,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:2,124,137:14,-1:2,137" +
":10,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8,137:15,125,137,-1:2,137:10,-1:4" +
",137,-1:2,137:2,-1:10,137:2,51,-1:8,137:9,136,137:7,-1:2,137:10,-1:4,137,-1" +
":2,137:2,-1:10,137:2,51,-1:8,137:17,-1:2,137:2,126,137:7,-1:4,137,-1:2,137:" +
"2,-1:10,137:2,51,-1:8,137:17,-1:2,137:4,127,137:5,-1:4,137,-1:2,137:2,-1:10" +
",137:2,51,-1:8,137:5,132,137:11,-1:2,137:10,-1:4,137,-1:2,137:2,-1:10,137:2" +
",51,-1:8,137:17,-1:2,137,134,137:8,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:8," +
"137:17,-1:2,137:5,135,137:4,-1:4,137,-1:2,137:2,-1:10,137:2,51,-1:7");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

//Add code to be executed when the end of the file is reached
	{
		if(paran) {
			return (new Symbol(sym.EOF, "There is some ( that is not closed"));
		}
		if(square) {
			return (new Symbol(sym.EOF, "There is some [ that is not closed"));
		}
		if(curly) {
			return (new Symbol(sym.EOF, "There is some {} that is not closed"));
		}
		return (new Symbol(sym.EOF,"Done"));
	}
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -3:
						break;
					case 3:
						{}
					case -4:
						break;
					case 4:
						{
  return new Symbol(sym.error, "Invalid input: " + yytext());
}
					case -5:
						break;
					case 5:
						{ return (new Symbol(sym.PLUS,yytext()));}
					case -6:
						break;
					case 6:
						{ return (new Symbol(sym.MINUS,yytext()));}
					case -7:
						break;
					case 7:
						{ return (new Symbol(sym.ASTRISK,yytext()));}
					case -8:
						break;
					case 8:
						{ return (new Symbol(sym.SLASH,yytext()));}
					case -9:
						break;
					case 9:
						{ return (new Symbol(sym.COLON,yytext()));}
					case -10:
						break;
					case 10:
						{ return (new Symbol(sym.EQUAL,yytext()));}
					case -11:
						break;
					case 11:
						{ return (new Symbol(sym.SEMI_COLON,yytext()));}
					case -12:
						break;
					case 12:
						{ return (new Symbol(sym.PERCENT,yytext()));}
					case -13:
						break;
					case 13:
						{ return (new Symbol(sym.COMMA,yytext()));}
					case -14:
						break;
					case 14:
						{
	String str =  yytext().substring(1,yytext().length());
return (new Symbol(sym.error, "Invalid input:  "+yytext()  +" in line "+(yyline+1)));
}
					case -15:
						break;
					case 15:
						{
	String str =  yytext().substring(1,yytext().length());
return (new Symbol(sym.error, "Invalid input: "+ yytext()  +" in line: "+yyline));
}
					case -16:
						break;
					case 16:
						{ return (new Symbol(sym.INT_LIT,yytext()));}
					case -17:
						break;
					case 17:
						{ stack_char.push('('); return (new Symbol(sym.OPEN_PARAN,yytext()));}
					case -18:
						break;
					case 18:
						{ stack_char.push('[');return (new Symbol(sym.OPEN_SQUARE,yytext()));}
					case -19:
						break;
					case 19:
						{ stack_char.push('{');return (new Symbol(sym.OPEN_CURLY,yytext()));}
					case -20:
						break;
					case 20:
						{ 
		Character temp = stack_char.pop();
		if(temp == '(') {
			return (new Symbol(sym.CLOSE_PARAN,yytext()));
		} else {
			if(temp =='[') {
				square = true;
			}
			if(temp == '{') {
				curly = true;
			}
			return (new Symbol(sym.error, "You have a missing bracket in line" + + (yyline+1)));
		}
	}
					case -21:
						break;
					case 21:
						{ 
		Character temp = stack_char.pop();
			if(temp == '[') {
				return (new Symbol(sym.CLOSE_SQUARE,yytext()));
			} else {
				if(temp == '(') {
					paran = true;
				}
				if(temp == '{') {
					curly = true;
				}
				return ( new Symbol(sym.error, "You have a missing bracket in line" + + (yyline+1)));
			}
		}
					case -22:
						break;
					case 22:
						{ 
		Character temp = stack_char.pop();
		if(temp == '{') {
			return (new Symbol(sym.CLOSE_CURLY,yytext()));
		} else {
			if(temp =='[') {
				square = true;
			}
			if(temp == '(') {
				paran = true;
			}
			return (new Symbol(sym.error, "You have a missing bracket in line " + (yyline+1) ));
		}
	}
					case -23:
						break;
					case 23:
						{ return (new Symbol(sym.DOT,yytext()));}
					case -24:
						break;
					case 24:
						{ return (new Symbol(sym.IF,yytext()));}
					case -25:
						break;
					case 25:
						{ return (new Symbol(sym.INCREMENT,yytext()));}
					case -26:
						break;
					case 26:
						{}
					case -27:
						break;
					case 27:
						{ return (new Symbol(sym.COLON_EQUAL,yytext()));}
					case -28:
						break;
					case 28:
						{ return (new Symbol(sym.AND_OP,yytext()));}
					case -29:
						break;
					case 29:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	if(str.length() == yytext().length() - 2)
	return (new Symbol(sym.STRING_TEXT, yytext()));
	else
return (new Symbol(sym.error, "Invalid input:  "+yytext()  +" in line "+(yyline+1)));
}
					case -30:
						break;
					case 30:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	if(str.length() == yytext().length() - 2)
	return (new Symbol(sym.STRING_TEXT, yytext()));
	else
	return (new Symbol(sym.error, "STRING ERROR"));
}
					case -31:
						break;
					case 31:
						{ return (new Symbol(sym.VAR,yytext()));}
					case -32:
						break;
					case 32:
						{ return (new Symbol(sym.CASE,yytext()));}
					case -33:
						break;
					case 33:
						{ return (new Symbol(sym.ELSE,yytext()));}
					case -34:
						break;
					case 34:
						{ return (new Symbol(sym.TYPE,yytext()));}
					case -35:
						break;
					case 35:
						{ return (new Symbol(sym.FUNC,yytext()));}
					case -36:
						break;
					case 36:
						{ return (new Symbol(sym.CONST,yytext()));}
					case -37:
						break;
					case 37:
						{ return (new Symbol(sym.STRUCT,yytext()));}
					case -38:
						break;
					case 38:
						{ return (new Symbol(sym.SWITCH,yytext()));}
					case -39:
						break;
					case 39:
						{ return (new Symbol(sym.RETURN,yytext()));}
					case -40:
						break;
					case 40:
						{ return (new Symbol(sym.IMPORT,yytext()));}
					case -41:
						break;
					case 41:
						{ return (new Symbol(sym.PACKAGE,yytext()));}
					case -42:
						break;
					case 42:
						{ return (new Symbol(sym.DEFAULT,yytext()));}
					case -43:
						break;
					case 43:
						{ return (new Symbol(sym.REL_OP,yytext()));}
					case -44:
						break;
					case 45:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -45:
						break;
					case 46:
						{
  return new Symbol(sym.error, "Invalid input: " + yytext());
}
					case -46:
						break;
					case 47:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	if(str.length() == yytext().length() - 2)
	return (new Symbol(sym.STRING_TEXT, yytext()));
	else
return (new Symbol(sym.error, "Invalid input:  "+yytext()  +" in line "+(yyline+1)));
}
					case -47:
						break;
					case 48:
						{
	String str =  yytext().substring(1,yytext().length() - 1);
	if(str.length() == yytext().length() - 2)
	return (new Symbol(sym.STRING_TEXT, yytext()));
	else
	return (new Symbol(sym.error, "STRING ERROR"));
}
					case -48:
						break;
					case 49:
						{ return (new Symbol(sym.REL_OP,yytext()));}
					case -49:
						break;
					case 51:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -50:
						break;
					case 52:
						{ return (new Symbol(sym.REL_OP,yytext()));}
					case -51:
						break;
					case 54:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -52:
						break;
					case 56:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -53:
						break;
					case 58:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -54:
						break;
					case 60:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -55:
						break;
					case 62:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -56:
						break;
					case 64:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -57:
						break;
					case 66:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -58:
						break;
					case 68:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -59:
						break;
					case 70:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -60:
						break;
					case 72:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -61:
						break;
					case 74:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -62:
						break;
					case 76:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -63:
						break;
					case 78:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -64:
						break;
					case 80:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -65:
						break;
					case 82:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -66:
						break;
					case 84:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -67:
						break;
					case 87:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -68:
						break;
					case 88:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -69:
						break;
					case 89:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -70:
						break;
					case 90:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -71:
						break;
					case 91:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -72:
						break;
					case 92:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -73:
						break;
					case 93:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -74:
						break;
					case 94:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -75:
						break;
					case 95:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -76:
						break;
					case 96:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -77:
						break;
					case 97:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -78:
						break;
					case 98:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -79:
						break;
					case 99:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -80:
						break;
					case 100:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -81:
						break;
					case 101:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -82:
						break;
					case 102:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -83:
						break;
					case 103:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -84:
						break;
					case 104:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -85:
						break;
					case 105:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -86:
						break;
					case 106:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -87:
						break;
					case 107:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -88:
						break;
					case 108:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -89:
						break;
					case 109:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -90:
						break;
					case 110:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -91:
						break;
					case 111:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -92:
						break;
					case 112:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -93:
						break;
					case 113:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -94:
						break;
					case 114:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -95:
						break;
					case 115:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -96:
						break;
					case 116:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -97:
						break;
					case 117:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -98:
						break;
					case 118:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -99:
						break;
					case 119:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -100:
						break;
					case 120:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -101:
						break;
					case 121:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -102:
						break;
					case 122:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -103:
						break;
					case 123:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -104:
						break;
					case 124:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -105:
						break;
					case 125:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -106:
						break;
					case 126:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -107:
						break;
					case 127:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -108:
						break;
					case 128:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -109:
						break;
					case 129:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -110:
						break;
					case 130:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -111:
						break;
					case 131:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -112:
						break;
					case 132:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -113:
						break;
					case 133:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -114:
						break;
					case 134:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -115:
						break;
					case 135:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -116:
						break;
					case 136:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -117:
						break;
					case 137:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -118:
						break;
					case 138:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -119:
						break;
					case 139:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -120:
						break;
					case 140:
						{ return (new Symbol(sym.IDENTIFIER,yytext()));}
					case -121:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
