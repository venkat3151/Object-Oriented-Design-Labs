//CSE 522: Assignment 1, Part 2

  class BST_Part2 {

	  public static void main(String[] args) {
		AbsTree tr = new DupTree(100);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(20);
		tr.insert(75);
		tr.insert(20);
		tr.insert(90);
		tr.insert(50);
		tr.insert(125);
		tr.insert(150);
		tr.insert(75);
		tr.insert(90);
		
		tr.delete(20);
		tr.delete(20);
		tr.delete(20);
		tr.delete(150);
		tr.delete(100);
		tr.delete(150);
		tr.delete(125);
		tr.delete(125);
		tr.delete(50);
		tr.delete(50);
		tr.delete(50);
		tr.delete(75);
		tr.delete(90);
		tr.delete(75);
		tr.delete(90);
		}
  }
  
  abstract class AbsTree {

	public AbsTree(int n) {
		value = n;
		left = null;
		right = null;
	}

	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null)
				right = add_node(n);
			else
				right.insert(n);
		else if (left == null)
			left = add_node(n);
		else
			left.insert(n);
	}
	
	public void delete(int n) {  		
	// adapt Part 1 solution and use here
	}
	
	protected void case1(AbsTree t) {  
	// adapt Part 1 solution and use here
	}
	
	protected void case2(AbsTree t) { 
	// adapt Part 1 solution and use here
	}
	
	protected void case3L(AbsTree t) { 
	// adapt Part 1 solution and use here
	}

	protected void case3R(AbsTree t) {  
	// adapt Part 1 solution and use here
	}

	private AbsTree find(int n) {
	// adapt Part 1 solution and use here
	}
	
	public AbsTree min() {
	// adapt Part 1 solution and use here
	}

	public AbsTree max() {
	// adapt Part 1 solution and use here
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	// Protected Abstract Methods
	
	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();

	// Additional protected abstract methods, as needed
}


class Tree extends AbsTree {

	public Tree(int n) {
		super(n);
	}

	protected AbsTree add_node(int n) {
		return new Tree(n);
	}

	protected void count_duplicates() {
		;
	}
	
	// define additional protected methods here, as needed

}

class DupTree extends AbsTree {

	public DupTree(int n) {
		super(n);
		count = 1;
	};

	protected AbsTree add_node(int n) {
		return new DupTree(n);
	}

	protected void count_duplicates() {
		count++;
	}

	// define additional protected methods here, as needed

	protected int count;
}