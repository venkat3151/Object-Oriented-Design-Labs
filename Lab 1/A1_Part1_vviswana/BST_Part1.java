
// CSE 522: Assignment 1, Part 1

class BST_Part1 {

	public static void main(String args[]) {
		 Tree tr;
		 tr = new Tree(100);
		 tr.insert(50);
		 tr.insert(125);
		 tr.insert(150);
		 tr.insert(20);
		 tr.insert(75);
		 tr.insert(20);	
		 tr.insert(90);
		 tr.delete(20);
		 tr.delete(20);
		 tr.delete(125);
		 tr.delete(150);
		 tr.delete(100); 
		 tr.delete(50);
		 tr.delete(75);
		 tr.delete(25);
		 tr.delete(90);
	}
}

class Tree { // Defines one node of a binary search tree
	
	// Constructor
	public Tree(int n) {
		value = n;
		left = null;
		right = null;
	}
	
	// used to insert nodes in the tree
	public void insert(int n) {
		if (value == n)
			return;
		if (value < n)
			if (right == null) {
				right = new Tree(n);
		        right.parent = this; // to refer the parent
			}
			else
				right.insert(n);
		else if (left == null) {
			left = new Tree(n);
			left.parent = this; // to refer the parent
		}
		else
			left.insert(n);
	}

	
	public Tree min() {
		Tree temp = this;
		while(temp.left != null)
		 temp = temp.left;	
		return temp;
	}
	
	// returns the maximum value
	public Tree max() {
		Tree temp2 = this;
		while(temp2.right != null)
		 temp2 = temp2.right;	
		return temp2;
	}
	
	// returns the n
	public Tree find(int n) {
		if(n == this.value ) {
			return this;
		}else if(this.value > n && (this.left!=null)) {
			Tree temp3 = this.left;
			return temp3.find(n);
		}else if (this.value < n && (this.right!=null)) {
			Tree temp3 = this.right;
			return temp3.find(n);
		}else {
			return null;
		}
	} 
	
	public void delete(int n) {  
		//
		// *** do not modify this method ***
		//
		Tree t = find(n);
		
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		} else if (t.left == null && t.right == null) { // n is at a leaf position
			if (t != this) // if t is not the root of the tree
				case1(t);
			else
				System.out.println("Unable to delete " + n + " -- tree will become empty!");
			return;
		} else if (t.left == null || t.right == null) {
			// t has one subtree only
			if (t != this) { // if t is not the root of the tree
				case2(t);
				return;
			} else { // t is the root of the tree with one subtree
				if (t.left != null)
					case3L(t);
				else
					case3R(t);
				return;
			}
		} else 
			// t has two subtrees; replace n with the smallest value in t's right subtree
			case3R(t);
	}
	
	
	// deleting the leaf node
	private void case1(Tree t) {  
		if(t.value == t.parent.left.value) {
			 t.parent.left = null;
			 t.parent = null;
		}else if(t.value == t.parent.right.value) {
			t.parent.right = null;
			t.parent = null;
		}
		
	}
	
	// deleting the node with one subtree only
	private void case2(Tree t) {  
		if(t.parent.right!= null && t.value == t.parent.right.value) {
			if(t.left != null) {
				t.parent.right = t.left;
				t.right.parent = t.parent;
				t.left = null;
				t.parent = null;
				t.right = null;
			}else if(t.right != null) {
				t.parent.right = t.right;
				t.right.parent = t.parent;
				t.left = null;
				t.parent = null;
				t.right = null;
				
			}
		}else if(t.parent.left!= null && t.value == t.parent.left.value) {
			if(t.left != null) {
				t.parent.left = t.left;
				t.left.parent = t.parent;
				t.right = null;
				t.parent = null;
				t.right = null;
			}else if(t.right != null) {
				t.parent.left = t.right;
				t.right.parent = t.parent;
				t.right = null;
				t.parent = null;
				t.right = null;
			}
		}
	}
	
	
	// 
	private void case3L(Tree t) {   
    //shuffling of the tree is just to find the second maxima
	t = t.left;
	Tree y = t.max();// y is the second maxima to replaced at the root
	System.out.println(y.value);
	// tree is again shifted to the same 100
	t = t.parent;
	t.value = y.value;
	Tree z = y.parent;
	z.right = null;
	y.parent = null;
 	}

	private void case3R(Tree t) {   
	t = t.right;
	Tree r = t.min();
	t = t.parent;
	t.value = r.value;
	Tree z = r.parent;
	z.left = null;
	r.parent = null;
	
 	}

	protected int value;
	protected Tree left;
	protected Tree right;
    protected Tree parent;
}

























