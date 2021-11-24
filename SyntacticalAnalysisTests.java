import java.util.*;

public class SyntacticAnalyser {

	public static Symbol[][] rules = {
			{TreeNode.Label.prog, Token.TokenType.PUBLIC, Token.TokenType.CLASS, Token.TokenType.ID, Token.TokenType.LBRACE, Token.TokenType.PUBLIC, Token.TokenType.STATIC, Token.TokenType.VOID, Token.TokenType.MAIN, Token.TokenType.LPAREN, Token.TokenType.STRINGARR, Token.TokenType.ARGS, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, Token.TokenType.RBRACE},
			{TreeNode.Label.los, TreeNode.Label.stat, TreeNode.Label.los},
			{TreeNode.Label.los, Token.TokenType.EPSILON},
			{TreeNode.Label.stat, TreeNode.Label.whilestat},
			{TreeNode.Label.stat, TreeNode.Label.forstat},
			{TreeNode.Label.stat, TreeNode.Label.ifstat},
			{TreeNode.Label.stat, TreeNode.Label.assign, Token.TokenType.SEMICOLON},
			{TreeNode.Label.stat, TreeNode.Label.decl, Token.TokenType.SEMICOLON },
			{TreeNode.Label.stat, TreeNode.Label.print, Token.TokenType.SEMICOLON },
			{TreeNode.Label.stat, Token.TokenType.SEMICOLON },
			{TreeNode.Label.whilestat, Token.TokenType.WHILE, Token.TokenType.LPAREN, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE},
			{TreeNode.Label.forstat, Token.TokenType.FOR, Token.TokenType.LPAREN, TreeNode.Label.forstart, Token.TokenType.SEMICOLON, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.SEMICOLON, TreeNode.Label.forarith, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE},
			{TreeNode.Label.forstart, TreeNode.Label.decl},
			{TreeNode.Label.forstart, TreeNode.Label.assign},
			{TreeNode.Label.forstart, Token.TokenType.EPSILON},
			{TreeNode.Label.forarith, TreeNode.Label.arithexpr},
			{TreeNode.Label.forarith, Token.TokenType.EPSILON},
			{TreeNode.Label.ifstat, Token.TokenType.IF, Token.TokenType.LPAREN, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.RPAREN, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, TreeNode.Label.elseifstat},
			{TreeNode.Label.elseifstat, TreeNode.Label.elseorelseif, Token.TokenType.LBRACE, TreeNode.Label.los, Token.TokenType.RBRACE, TreeNode.Label.elseifstat},
			{TreeNode.Label.elseifstat, Token.TokenType.EPSILON},
			{TreeNode.Label.elseorelseif, Token.TokenType.ELSE, TreeNode.Label.possif},
			{TreeNode.Label.possif, Token.TokenType.IF, Token.TokenType.LPAREN, TreeNode.Label.relexpr, TreeNode.Label.boolexpr, Token.TokenType.RPAREN},
			{TreeNode.Label.possif, Token.TokenType.EPSILON},
			{TreeNode.Label.assign, Token.TokenType.ID, Token.TokenType.ASSIGN, TreeNode.Label.expr},
			{TreeNode.Label.decl, TreeNode.Label.type, Token.TokenType.ID, TreeNode.Label.possassign},
			{TreeNode.Label.possassign, Token.TokenType.ASSIGN, TreeNode.Label.expr},
			{TreeNode.Label.possassign, Token.TokenType.EPSILON},
			{TreeNode.Label.print, Token.TokenType.PRINT, Token.TokenType.LPAREN, TreeNode.Label.printexpr, Token.TokenType.RPAREN},
			{TreeNode.Label.type, Token.TokenType.TYPE},
			//{TreeNode.Label.type, Token.TokenType.TYPE},
			//{TreeNode.Label.type, Token.TokenType.TYPE},
			{TreeNode.Label.expr, TreeNode.Label.relexpr, TreeNode.Label.boolexpr},
			{TreeNode.Label.expr, TreeNode.Label.charexpr},
			{TreeNode.Label.charexpr, Token.TokenType.SQUOTE, Token.TokenType.TYPE, Token.TokenType.SQUOTE},
			{TreeNode.Label.boolexpr, TreeNode.Label.boolop, TreeNode.Label.relexpr, TreeNode.Label.boolexpr},
			{TreeNode.Label.boolexpr, Token.TokenType.EPSILON},
			{TreeNode.Label.boolop, TreeNode.Label.booleq},
			{TreeNode.Label.boolop, TreeNode.Label.boollog},
			{TreeNode.Label.booleq, Token.TokenType.EQUAL},
			{TreeNode.Label.booleq, Token.TokenType.NEQUAL},
			{TreeNode.Label.boollog, Token.TokenType.AND},
			{TreeNode.Label.boollog, Token.TokenType.OR},
			{TreeNode.Label.relexpr, TreeNode.Label.arithexpr, TreeNode.Label.relexprprime},
			{TreeNode.Label.relexpr, Token.TokenType.TRUE},
			{TreeNode.Label.relexpr, Token.TokenType.FALSE},
			{TreeNode.Label.relexprprime, TreeNode.Label.relop, TreeNode.Label.arithexpr},
			{TreeNode.Label.relexprprime, Token.TokenType.EPSILON},
			{TreeNode.Label.relop, Token.TokenType.LT},
			{TreeNode.Label.relop, Token.TokenType.LE},
			{TreeNode.Label.relop, Token.TokenType.GT},
			{TreeNode.Label.relop, Token.TokenType.GE},
			{TreeNode.Label.arithexpr, TreeNode.Label.term, TreeNode.Label.arithexprprime},
			{TreeNode.Label.arithexprprime, Token.TokenType.PLUS, TreeNode.Label.term, TreeNode.Label.arithexprprime},
			{TreeNode.Label.arithexprprime, Token.TokenType.MINUS, TreeNode.Label.term, TreeNode.Label.arithexprprime},
			{TreeNode.Label.arithexprprime, Token.TokenType.EPSILON},
			{TreeNode.Label.term, TreeNode.Label.factor, TreeNode.Label.termprime},
			{TreeNode.Label.termprime, Token.TokenType.TIMES, TreeNode.Label.factor, TreeNode.Label.termprime},
			{TreeNode.Label.termprime, Token.TokenType.DIVIDE, TreeNode.Label.factor, TreeNode.Label.termprime},
			{TreeNode.Label.termprime, Token.TokenType.MOD, TreeNode.Label.factor, TreeNode.Label.termprime},
			{TreeNode.Label.termprime, Token.TokenType.EPSILON},
			{TreeNode.Label.factor, Token.TokenType.LPAREN, TreeNode.Label.arithexpr, Token.TokenType.RPAREN},
			{TreeNode.Label.factor, Token.TokenType.ID},
			{TreeNode.Label.factor, Token.TokenType.NUM},
			{TreeNode.Label.printexpr, TreeNode.Label.relexpr, TreeNode.Label.boolexpr},
			{TreeNode.Label.printexpr, Token.TokenType.DQUOTE, Token.TokenType.STRINGLIT, Token.TokenType.DQUOTE}
	};

	public static String[][] parseTable;
	public static ArrayList<Symbol> NoneTerminals;
	public static ArrayList<Symbol> Terminals;
	public static ArrayList<Symbol> nullableRules;
	public static HashMap<Symbol,ArrayList<Symbol>> firstMap;
	public static HashMap<Symbol,ArrayList<Symbol>> followMap;
	public static HashMap<Integer,ArrayList<Symbol>> predict;

	public static void fillNoneTerminal() {
		NoneTerminals = new ArrayList<>();
		nullableRules = new ArrayList<>();
		for (int i = 0; i < rules.length; i++) {
			if (!NoneTerminals.contains(rules[i][0]))
				NoneTerminals.add(rules[i][0]);

			for (int j = 0; j < rules[i].length; j++) {
				if (rules[i][j].equals(Token.TokenType.EPSILON))
					nullableRules.add(rules[i][0]);
			}
		}
	}

	public static boolean isNoneTerminal(Symbol s){
		for (int i = 0; i < NoneTerminals.size(); i++) {
			if (s.equals(NoneTerminals.get(i)))
				return true;
		}

		return false;
	}

	public static void fillTerminal(){
		Terminals = new ArrayList<>();
		for (int i = 0; i < rules.length; i++) {
			for (int j = 0; j < rules[i].length; j++) {
				if (!isNoneTerminal(rules[i][j]))
					if (!Terminals.contains(rules[i][j]))
						Terminals.add(rules[i][j]);
			}
		}
	}

	public static boolean isNullable(Symbol s) {
		for (int i = 0; i < nullableRules.size(); i++) {
			if (s.equals(nullableRules.get(i)))
				return true;
		}

		return false;
	}

	public static void findFirstSet() {
		firstMap = new HashMap<>();

		for (int i = 0; i < Terminals.size(); i++) {
			if (Terminals.get(i) != Token.TokenType.EPSILON) {
				firstMap.put(Terminals.get(i), new ArrayList<Symbol>());
				firstMap.get(Terminals.get(i)).add(Terminals.get(i));
			}
		}

		for (int i = 0; i < rules.length; i++) {
			if (!firstMap.containsKey(rules[i][0]))
				firstMap.put(rules[i][0], new ArrayList<Symbol>());
			for (int j = 1; j < rules[i].length; j++) {
				if (!isNullable(rules[i][j])) {
					firstMap.get(rules[i][0]).add(rules[i][j]);
					break;
				}
				else {
					firstMap.get(rules[i][0]).add(rules[i][j]);
					continue;
				}
			}
		}

		for (Map.Entry<Symbol, ArrayList<Symbol>> entry : firstMap.entrySet()){
			for (int j = 0; j < entry.getValue().size() ; j++) {
				//System.out.println(entry);
				Symbol noneTerminal = entry.getValue().get(j);
				if(isNoneTerminal(noneTerminal)) {
					//System.out.println(j + " " + noneTerminal);
					entry.getValue().remove(noneTerminal);
					if (firstMap.get(noneTerminal) != null) {
						entry.getValue().addAll(firstMap.get(noneTerminal));
					}
					j--;
				}
			}
		}

		for (Map.Entry<Symbol, ArrayList<Symbol>> entry : firstMap.entrySet()){
			Set<Symbol> set = new HashSet<>(entry.getValue());
			entry.getValue().clear();
			entry.getValue().addAll(set);
			entry.getValue().remove("#");
		}
	}
//
	public static void findFollowSet(){
		followMap = new HashMap<>();
		followMap.put(TreeNode.Label.prog,new ArrayList<>());
		followMap.get(TreeNode.Label.prog).add(Token.TokenType.DOLLAR);

		for (int i = 0; i < rules.length ; i++) {
			for (int j = 1; j < rules[i].length ; j++) {
				if(!followMap.containsKey(rules[i][j]) && isNoneTerminal(rules[i][j])){
					//System.out.println(rules[i][j]);
					followMap.put(rules[i][j],new ArrayList<>());
				}

				if (j < rules[i].length - 1 && isNoneTerminal(rules[i][j])) {
					if (firstMap.get(rules[i][j + 1]) != null) {
						followMap.get(rules[i][j]).addAll(firstMap.get(rules[i][j + 1]));
						if (isNullable(rules[i][j + 1]) && followMap.get(rules[i][0]) != null && !rules[i][j].equals(rules[i][0]))
							followMap.get(rules[i][j]).addAll(followMap.get(rules[i][0]));
					}
				}
				else if (j == rules[i].length - 1 && isNoneTerminal(rules[i][j]) && !rules[i][j].equals(rules[i][0])) {
					//System.out.println(rules[i][0] + "-----" + rules[i][j]);
					followMap.get(rules[i][j]).addAll(followMap.get(rules[i][0]));
				}
			}
		}

		//remove duplicated item and # from follow ArrayList
		for (Map.Entry<Symbol, ArrayList<Symbol>> entry : followMap.entrySet()){
			for (int j = 0; j <entry.getValue().size() ; j++) {
				Symbol noneTerminal = entry.getValue().get(j);
				if(isNoneTerminal(noneTerminal) || noneTerminal == Token.TokenType.EPSILON){
					entry.getValue().remove(noneTerminal);
					if (followMap.get(noneTerminal) != null) {
						entry.getValue().addAll(followMap.get(noneTerminal));
						j--;
					}
				}
			}
		}
//
		//remove duplicated item and # from follow ArrayList
		for (Map.Entry<Symbol, ArrayList<Symbol>> entry : followMap.entrySet()){
			Set<Symbol> set=new HashSet<>(entry.getValue());
			entry.getValue().clear();
			entry.getValue().addAll(set);
			entry.getValue().remove("#");
		}
	}
//
	public static void findPredictSet() {
		predict = new HashMap<>();

		for (int i = 0; i < rules.length ; i++) {
			predict.put(i,new ArrayList<>());
		}

		for (int i = 0; i < rules.length; i++) {

			//means a Terminal
			if (!isNoneTerminal(rules[i][1])){
				if (!predict.get(i).contains(rules[i][1]))
					predict.get(i).add(rules[i][1]);
			}

			//means being nullable and we should add follow set of A for this(A -> #)
			if (rules[i][1].equals(Token.TokenType.EPSILON)) {
				for (int j = 0; j < followMap.get(rules[i][0]).size(); j++)
					if (!predict.get(i).contains(followMap.get(rules[i][0]).get(j)))
						predict.get(i).add(followMap.get(rules[i][0]).get(j));
			}

			//for a Terminal we should add first set to predict ( for  1)A - > B D  first of B )
			if (firstMap.get(rules[i][1]) != null)
				for (int j = 0; j < firstMap.get(rules[i][1]).size(); j++)
					if (!predict.get(i).contains(firstMap.get(rules[i][1]).get(j)))
						predict.get(i).add(firstMap.get(rules[i][1]).get(j));

			//for 1) A - > B D  if B is nullable we should also add follow set of B to predict 1
			if (isNullable(rules[i][1]))
				for (int j = 0; j < followMap.get(rules[i][1]).size(); j++)
					if (!predict.get(i).contains(followMap.get(rules[i][1]).get(j)))
						predict.get(i).add(followMap.get(rules[i][1]).get(j));

		}
	}
//
	public static void makeParseTable() {
		parseTable = new String[NoneTerminals.size()+1][Terminals.size()+1];
		parseTable[0][0] = " ";

		//Rows of NoneTerminals
		for (int i = 1; i < NoneTerminals.size()+1; i++) {
			parseTable[i][0] = NoneTerminals.get(i-1).toString();
		}

		//Columns of Terminals
		for (int i = 1; i < Terminals.size()+1; i++) {
			parseTable[0][i] = Terminals.get(i-1).toString();
		}


		//search which Terminals are in the predict set of K's rule
		for (int k = 0; k < predict.size(); k++) {
			for (int i = 1; i < NoneTerminals.size() + 1; i++) {
				if (rules[k][0].toString().equals(parseTable[i][0])) {
					for (int j = 1; j < Terminals.size() + 1; j++) {
						if (predict.get(k).contains(Terminals.get(j-1))) {
							parseTable[i][j] = Integer.toString(k);
						}
					}
				}
			}
		}

		//empty blocks
		for (int i = 0; i < parseTable.length; i++) {
			for (int j = 0; j < parseTable[i].length; j++) {
				if (parseTable[i][j] == null) {
					parseTable[i][j] = "-1";
				}
			}
		}

	}
//
	public static int findRule(Symbol noneTerminal, Symbol terminal) {
		int result = -1;
		int pos_1 = 0;
		int pos_2 = 0;
		for (int i = 1; i < parseTable[0].length; i++) {
			if (parseTable[0][i].equals(terminal.toString())) {
				pos_1 = i;
				break;
			}
		}

		for (int i = 1; i < parseTable.length; i++) {
			if (parseTable[i][0].equals(noneTerminal.toString())) {
				pos_2 = i;
				break;
			}
		}
		if (pos_1 == 0 || pos_2 == 0)
			return result;

		return Integer.parseInt(parseTable[pos_2][pos_1]);
	}

	public static ParseTree parse(List<Token> tokens) throws SyntaxException {
		fillNoneTerminal();
		fillTerminal();
		findFirstSet();
		findFollowSet();
		findPredictSet();
		makeParseTable();

		Stack<Symbol> stack = new Stack<>();

		stack.add(Token.TokenType.DOLLAR);
		stack.add(TreeNode.Label.prog);

		TreeNode prog = new TreeNode(TreeNode.Label.prog, new Token(Token.TokenType.PUBLIC), null, true, false);
		TreeNode parent = prog;

		ParseTree smallTree = new ParseTree();
		smallTree.setRoot(prog);

		boolean isNull = false;
		boolean stop = false;
		boolean isUpdated = false;
		Symbol s = TreeNode.Label.prog;
		int l = 0;

		if (tokens.size() == 0)
			throw new SyntaxException("Syntax Error!");

		for (int i = 0; i < tokens.size(); i++) {
			l = i;
			if (stack.peek().isVariable()) {
				int pos = findRule(stack.peek(), tokens.get(i).getType());
				if (pos != -1) {
					parent.updateToken(tokens.get(i));
					parent.setIsUpdated(true);

					if (i > 0) {
						stop = false;
						System.out.println(stack.peek());
						while (parent != null) {
							for (int k = 0; k < parent.getChildren().size(); k++) {
								if (stack.peek().equals(parent.getChildren().get(k).getLabel()) && !parent.getChildren().get(k).getVisited()) {
									parent = parent.getChildren().get(k);
									stop = true;
									break;
								}
								if (k == parent.getChildren().size() - 1)
									isNull = true;
							}

							if (stop)
								break;

							if (isNull) {
								parent = parent.getParent();
								isNull = false;
							}
						}
					}

					stack.pop();

					for (int j = rules[pos].length - 1; j > 0; j--) {
						stack.add(rules[pos][j]);
						if (l > tokens.size()) {
							throw new SyntaxException("Syntax Error!");
						}
						if (rules[pos][rules[pos].length - j].isVariable()) {
							parent.addChild(new TreeNode((TreeNode.Label) rules[pos][rules[pos].length - j], tokens.get(l), parent, false, false));
							parent.setVisited(true);
							//l++;
						} else {
							if (stack.peek().equals(tokens.get(i).getType())) {
								if (rules[pos][rules[pos].length - j].equals(Token.TokenType.EPSILON)) {
									parent.updateToken(tokens.get(l));
									//System.out.println(rules[pos][rules[pos].length - j] + " " + tokens.get(l).getValue().get() + " " + i + " " + l + " " + parent);
									parent.addChild(new TreeNode(TreeNode.Label.epsilon, tokens.get(i), parent, false, false));
									parent.setVisited(true);
									parent.setIsUpdated(true);
									l++;
								}
								else {							
									parent.updateToken(tokens.get(i));
									parent.addChild(new TreeNode(TreeNode.Label.terminal, new Token((Token.TokenType) rules[pos][rules[pos].length - j], tokens.get(l).getValue().get()), parent, false, false));
									parent.setVisited(true);
									l++;
								}
							}
							else {
								throw new SyntaxException("Syntax Error!");
							}
						}
					}

					if (stack.peek().equals(Token.TokenType.EPSILON)) {
						stack.pop();
					}

					s = stack.peek();

					if (s.isVariable())
						i--;
				}
				else {
					throw new SyntaxException("Syntax Error!");
				}
			}
			else if (stack.peek().equals(Token.TokenType.ID)) {
				if (parent.getParent() != null)
					parent = parent.getParent();
			}


			if (stack.peek().equals(Token.TokenType.ID)) {
				if (tokens.get(i).getType().equals(Token.TokenType.ID)) {
					for (int k = 0; k < parent.getChildren().size(); k++) {
						if (parent.getChildren().get(k).getLabel().equals(TreeNode.Label.terminal) && parent.getChildren().get(k).getToken().get().getType().equals(Token.TokenType.ID)) {
							parent = parent.getChildren().get(k);
							parent.updateToken(tokens.get(i));
							break;
						}
					}
					//System.out.println(tokens.get(i) + " " + parent);
					parent = parent.getParent();
					stack.pop();
				}
				else
					throw new SyntaxException("Syntax Error!");
			}
//
			if (stack.peek().equals(tokens.get(i).getType())) {
				//parent.updateToken(tokens.get(i));
				stack.pop();
			}
		}

		if (!stack.peek().equals(Token.TokenType.DOLLAR))
			throw new SyntaxException("Syntax Error!");

		return smallTree;
	}
}

