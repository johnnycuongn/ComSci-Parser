import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner {

	public static void main(String[] args) {
		// SyntacticAnalyser.fillNoneTerminal();
		// SyntacticAnalyser.fillTerminal();
		// SyntacticAnalyser.findFirstSet();
		// SyntacticAnalyser.findFollowSet();
		// SyntacticAnalyser.findPredictSet();
		// SyntacticAnalyser.makeParseTable();

		// ArrayList<Symbol> testNoneTerminal = SyntacticAnalyser.NoneTerminals;
		// ArrayList<Symbol> testTerminal = SyntacticAnalyser.Terminals;
		// HashMap<Symbol,ArrayList<Symbol>> testFirstMap = SyntacticAnalyser.firstMap;
		// HashMap<Symbol,ArrayList<Symbol>> testFollowMap = SyntacticAnalyser.followMap;
		// ArrayList<Symbol> testNullableRules = SyntacticAnalyser.nullableRules;
		// String[][] parseTable = SyntacticAnalyser.parseTable;

//		for (int i = 0; i < testTerminal.size(); i++) {
//			System.out.println(testTerminal.get(i));
//		}

		//System.out.println(SyntacticAnalyser.isNoneTerminal("ID"));

//		for (Map.Entry<Symbol, ArrayList<Symbol>> entry : testFirstMap.entrySet()){
//			if (SyntacticAnalyser.isNoneTerminal(entry.getKey())) {
//				System.out.print(entry.getKey() + " : ");
//				for (int j = 0; j < entry.getValue().size() - 1; j++) {
//					System.out.print(entry.getValue().get(j) + ",");
//				}
//				if (entry.getValue().size() > 0)
//					System.out.println(entry.getValue().get(entry.getValue().size() - 1));
//			}
//		}
//
//		System.out.println("-----------------------------------------------------------------------------------------");
//
//		System.out.println("follow");
//		for (Map.Entry<Symbol, ArrayList<Symbol>> entry : testFollowMap.entrySet()){
//			if(SyntacticAnalyser.isNoneTerminal(entry.getKey())) {
//				System.out.print(entry.getKey() + " : ");
//				for (int j = 0; j < entry.getValue().size()-1; j++) {
//					System.out.print(entry.getValue().get(j) + ",");
//				}
//				if (entry.getValue().size() > 0)
//					System.out.println(entry.getValue().get(entry.getValue().size()-1));
//			}
//		}
//
//		System.out.println("Table");
//		for (int i = 0; i < parseTable.length; i++) {
//			for (int j = 0; j < parseTable[i].length; j++) {
//				String s = String.format("%15s", parseTable[i][j]);
//				System.out.print(s +" ");
//			}
//			System.out.println();
//		}

		try {
			List<Token> results = LexicalAnalyser.analyse("public class Test { public static void main(String[] args){ int i = 'c'; }}");
			System.out.println(results);
			ParseTree tree = SyntacticAnalyser.parse(results);
			System.out.println(tree);
		} catch (LexicalException e) {
			e.printStackTrace();
		} catch (SyntaxException e) {
			e.printStackTrace();
		}

	}
}

