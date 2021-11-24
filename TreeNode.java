import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TreeNode {

	public enum Label implements Symbol {
		prog, los, stat, whilestat, forstat, forstart, forarith, ifstat, elseifstat, elseorelseif, possif, assign, decl,
		possassign, print, type, expr, boolexpr, boolop, booleq, boollog, relexpr, relexprprime, relop, arithexpr,
		arithexprprime, term, termprime, factor, printexpr, charexpr, epsilon, terminal;

		@Override
		public boolean isVariable() {
			return true;
		}

	};

	private Label label;
	private Optional<Token> token;
	private TreeNode parent;
	private List<TreeNode> children;
	private boolean visited;
	private boolean isUpdated;

	public TreeNode(Label label, TreeNode parent, boolean visited, boolean isUpdated) {
		this.label = label;
		this.token = Optional.empty();
		this.parent = parent;
		children = new ArrayList<TreeNode>();
		this.visited = visited;
		this.isUpdated = isUpdated;
	}

	public TreeNode(Label label, Token token, TreeNode parent, boolean visited, boolean isUpdated) {
		this.label = label;
		this.token = Optional.of(token);
		this.parent = parent;
		children = new ArrayList<TreeNode>();
		this.visited = visited;
		this.isUpdated = isUpdated;
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}

	public Optional<Token> getToken() {
		return this.token;
	}

	public TreeNode getParent() {
		return this.parent;
	}

	public List<TreeNode> getChildren() {
		return this.children;
	}

	public Label getLabel() {
		return this.label;
	}

	public boolean getVisited() {
		return this.visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean getIsUpdated() {
		return this.isUpdated;
	}

	public void setIsUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public void updateToken(Token t) {
		if (!this.isUpdated) {
			this.token = Optional.of(t);
			this.isUpdated = true;
		}
	}

	@Override
	public String toString() {
		return "[" + this.label + ", " + this.token + "]";
	}

}

