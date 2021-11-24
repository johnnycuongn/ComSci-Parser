import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

public class LexicalAnalyser {

	public static List<Token> analyse(String sourceCode) throws LexicalException {
		// Turn the input String into a list of Tokens!\
		List<Token> result = new ArrayList<Token>();

		try {
			String[] terminals = split(sourceCode);
			for (String terminal : terminals) {
				if (terminal.isBlank()) {
					continue;
				}
				Optional<Token> token = tokenFromTerminal(terminal);
				if (token.isPresent()) {
					result.add(token.get());
				} else {
					// Stringlit, Char
					result.addAll(tokenFromQuote(terminal));

				}
			}
		} catch (LexicalException e) {
			throw e;
		}
		return result;
	}

	private static String getRegex(String value) {
		return "(?=\\" + value + ")" + "|" + "(?<=\\" + value + ")";
	}

	private static String[] split(String code) {
		String regex = "\\s+";
		List<String> regexList = Arrays.asList(";", "(", ")", "{", "}", "+", "-", "*", "%", "=", "==", "!=", "<", ">",
				"<=", ">=", "&&", "|\\|");
		for (String r : regexList) {
			regex = regex.concat("|" + getRegex(r));
		}

		return code.split(regex);
	}


	private static List<Token> tokenFromQuote(String string) throws LexicalException {
		List<Token> result = new ArrayList<Token>();
		String regex = "";
		Token.TokenType type = Token.TokenType.STRINGLIT;

		if (string.charAt(0) == '\'') {
			regex = "(?<=\')|(?=\')";
			type = Token.TokenType.CHARLIT;
		}
		else if (string.charAt(0) == '"') {
			regex = "(?<=\")|(?=\")";
			type = Token.TokenType.STRINGLIT;
		} else {
			throw new LexicalException("Invalid Type");
		}
		
		String[] stringT = string.split(regex);

		for (String terminal: stringT) {
			if (terminal.matches("[\\d|\\w]+")) {
				result.add(new Token(type, terminal));
				continue;
			}
			Optional<Token> token = tokenFromTerminal(terminal);
			if (token.isPresent()) {
				result.add(token.get());
			}
		}

		return result;
	}

	private static Optional<Token> tokenFromTerminal(String t) {
		Optional<Token.TokenType> type = tokenTypeOf(t);
		if (type.isPresent())
			return Optional.of(new Token(type.get(), t));
		return Optional.empty();
	}

	private static Optional<Token.TokenType> tokenTypeOf(String t) {
		switch (t) {
		case "+":
			return Optional.of(Token.TokenType.PLUS);
		case "-":
			return Optional.of(Token.TokenType.MINUS);
		case "*":
			return Optional.of(Token.TokenType.TIMES);
		case "/":
			return Optional.of(Token.TokenType.DIVIDE);
		case "%":
			return Optional.of(Token.TokenType.MOD);
		case "=":
			return Optional.of(Token.TokenType.ASSIGN);
		case "==":
			return Optional.of(Token.TokenType.EQUAL);
		case "!=":
			return Optional.of(Token.TokenType.NEQUAL);
		case "<":
			return Optional.of(Token.TokenType.LT);
		case ">":
			return Optional.of(Token.TokenType.GT);
		case "<=":
			return Optional.of(Token.TokenType.LE);
		case ">=":
			return Optional.of(Token.TokenType.GE);
		case "(":
			return Optional.of(Token.TokenType.LPAREN);
		case ")":
			return Optional.of(Token.TokenType.RPAREN);
		case "{":
			return Optional.of(Token.TokenType.LBRACE);
		case "}":
			return Optional.of(Token.TokenType.RBRACE);
		case "&&":
			return Optional.of(Token.TokenType.AND);
		case "||":
			return Optional.of(Token.TokenType.OR);
		case ";":
			return Optional.of(Token.TokenType.SEMICOLON);
		case "public":
			return Optional.of(Token.TokenType.PUBLIC);
		case "class":
			return Optional.of(Token.TokenType.CLASS);
		case "static":
			return Optional.of(Token.TokenType.STATIC);
		case "void":
			return Optional.of(Token.TokenType.VOID);
		case "main":
			return Optional.of(Token.TokenType.MAIN);
		case "String[]":
			return Optional.of(Token.TokenType.STRINGARR);
		case "args":
			return Optional.of(Token.TokenType.ARGS);
		case "int":
		case "char":
		case "boolean":
			return Optional.of(Token.TokenType.TYPE);
		case "System.out.println":
			return Optional.of(Token.TokenType.PRINT);
		case "while":
			return Optional.of(Token.TokenType.WHILE);
		case "for":
			return Optional.of(Token.TokenType.FOR);
		case "if":
			return Optional.of(Token.TokenType.IF);
		case "else":
			return Optional.of(Token.TokenType.ELSE);
		case "\"":
			return Optional.of(Token.TokenType.DQUOTE);
		case "\'": 
			return Optional.of(Token.TokenType.SQUOTE);
		case "true":
			return Optional.of(Token.TokenType.TRUE);
		case "false":
			return Optional.of(Token.TokenType.FALSE);
		}
		

		if (t.matches("\\d+"))
			return Optional.of(Token.TokenType.NUM);
		if (Character.isAlphabetic(t.charAt(0)) && t.matches("[\\d|\\w]+")) {
			return Optional.of(Token.TokenType.ID);
		}
		return Optional.empty();
	}

}
