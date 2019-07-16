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

	// used to insert nodes in the tree
	public void insert(int n) {
		if (value == n)
			count_duplicates();
		else if (value < n)
			if (right == null) {
				right = add_node(n);
		        right.parent = this;// to refer the parent
			}
			else
				right.insert(n);
		else if (left == null) {
			left = add_node(n);
			left.parent = this;// to refer the parent
		}
		else
			left.insert(n);
	}
	 
	public void delete(int n) {  		
		//
		// *** do not modify this method ***
		//
		AbsTree t = find(n);
		
		if (t == null) { // n is not in the tree
			System.out.println("Unable to delete " + n + " -- not in the tree!");
			return;
		} else if (t.left == null && t.right == null) {
			  // n is at a leaf position
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
	protected void case1(AbsTree t) {
		if(t.returnCount() == 1) {
			if(t.value == t.parent.left.value) {
				 t.parent.left = null;
				 t.parent = null;
			}else if(t.value == t.parent.right.value) {
				t.parent.right = null;
				t.parent = null;
			}
		}else {
			t.reduceCount();
		}
		
	}
	
	// deleting the node with one subtree only
	protected void case2(AbsTree t) { 
		if(t.returnCount() == 1) {
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
		}else {
			t.reduceCount();
		}
	}
	
	protected void case3L(AbsTree t) { 
		if(t.returnCount() == 1) {
			int parentCount = t.returnCount();
			AbsTree q =  t.left.max();
			t.value =  t.left.max().value;
        	int childCount =  t.left.max().returnCount();
			t.setCount(childCount);
		    t.left.max().setCount(parentCount);
			q.parent = null;
			t.left.right.right = null;
		}else {
			t.reduceCount();
		}
	 	}
	

	protected void case3R(AbsTree t) {  
		if(t.returnCount() == 1) {
			int parentCount = t.returnCount();
			AbsTree q =  t.right.min();
			t.value = t.right.min().value;
        	int childCount = t.right.min().returnCount();
			t.setCount(childCount);
			t.right.min().setCount(parentCount);
			t.right = t.right.min().right;
			t.right.parent = t;
			q.right = null;
			q.parent = null;
	}else {
		t.reduceCount();
	}
	}

	private AbsTree find(int n) {
		if(n == this.value ) {
			return this;
		}else if(this.value > n && (this.left!=null)) {
			AbsTree temp3 = this.left;
			return temp3.find(n);
		}else if (this.value < n && (this.right!=null)) {
			AbsTree temp3 = this.right;
			return temp3.find(n);
		}else {
			return null;
		}
	}
	
	public AbsTree min() {
		AbsTree temp = this;
		while(temp.left != null)
		 temp = temp.left;	
		return temp;
	}

	public AbsTree max() {
		AbsTree temp2 = this;
		while(temp2.right != null)
		 temp2 = temp2.right;	
		return temp2;
	}

	protected int value;
	protected AbsTree left;
	protected AbsTree right;
	protected AbsTree parent;

	// Protected Abstract Methods
	
	protected abstract AbsTree add_node(int n);
	protected abstract void count_duplicates();
	protected abstract int returnCount();
	protected abstract int reduceCount();
	protected abstract void setCount(int childcount);

	// Additional protected abstract methods, as needed
}


//abstract class Tree extends AbsTree {
//
//	public Tree(int n) {
//		super(n);
//	}
//
//	protected AbsTree add_node(int n) {
//		return new Tree(n);
//	}
//
//	protected void count_duplicates() {
//		;
//	}
//	
//	// define additional protected methods here, as needed
//
//}

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
	
	protected int returnCount() {
		return count;
	}
	
	protected int reduceCount() {
		return count = count - 1;
	}
	
	protected void setCount(int childcount) {
		count = childcount;
	}
	

	// define additional protected methods here, as needed

	protected int count;
}