import java.util.*;


// ============  THE MAIN METHOD WITH TWO TESTS.  ==============
// ============  DON'T MODIFY THE TEST METHODS   ===============
// ============  COMPLETE ONLY THE containedIn METHOD ==========

public class GenericIterators {

public static void main(String[] args) {
	
	test1();
	System.out.println();
	test2();
}

static void test1() {
	
	AbsTree<Integer> set1 = new Tree<Integer>(100);
	set1.insert(50);
	set1.insert(50);
	set1.insert(25);
	set1.insert(75);
	set1.insert(75);
	set1.insert(150);
	set1.insert(125);
	set1.insert(200);
	set1.insert(100);
	
	AbsTree<Integer> set2 = new Tree<Integer>(100);
	set2.insert(150);
	set2.insert(125);
	set2.insert(50);
	set2.insert(50);
	set2.insert(26);
	set2.insert(25);
	set2.insert(27);
	set2.insert(75);
	set2.insert(75);
	set2.insert(76);
	set2.insert(150);
	set2.insert(125);
	set2.insert(200);
	
	System.out.print("set1 = "); print(set1);
	System.out.print("set2 = "); print(set2);
	
	if (containedIn(set1, set2))
		System.out.println("set1 is contained in set2.");
	else
		System.out.println("set1 is not contained in set2.");
}


static void test2() {
	
	AbsTree<Integer> bag1 = new DupTree<Integer>(100);
	bag1.insert(50);
	bag1.insert(50);
	bag1.insert(25);
	bag1.insert(75);
	bag1.insert(75);
	bag1.insert(150);
	bag1.insert(125);
	bag1.insert(200);
	bag1.insert(100);
	
	AbsTree<Integer> bag2 = new DupTree<Integer>(100);
	bag2.insert(150);
	bag2.insert(125);
	bag2.insert(50);
	bag2.insert(50);
	bag2.insert(26);
	bag2.insert(25);
	bag2.insert(27);
	bag2.insert(75);
	bag2.insert(75);
	bag2.insert(76);
	bag2.insert(150);
	bag2.insert(125);
	bag2.insert(200);
	
	System.out.print("bag1 = "); print(bag1);
	System.out.print("bag2 = "); print(bag2);
	
	if (containedIn(bag1, bag2))
		System.out.println("bag1 is contained in bag2.");
	else
		System.out.println("bag1 is not contained in bag2.");
}


static void print(AbsTree<Integer> bs) {
	System.out.print("{ ");
	for (int x : bs) 
		System.out.print(x + " ");
	System.out.println("}");
}


static <T extends Comparable<T>> boolean containedIn(AbsTree<T> tr1, AbsTree<T> tr2) {
	Iterator<T> iter1 = tr1.iterator(); // for set1 or bag1 iteration
	Iterator<T> iter2 = tr2.iterator(); // for set2 or bag2 iteration
	 
	// fill in rest of the code here
	int flag = 0;
	while(iter1.hasNext() ) {
		flag = 0;
		T a = iter1.next();
		while (iter2.hasNext()) {
			flag  = 1;			
			T b = iter2.next();
			if(a.compareTo(b) == 0) {
				System.out.println(a + " = " + b);
				break;
			}
			else {
				if(a.compareTo(b) < 0) {
					System.out.println(a + " < " + b);
					return false;
				}
				else{
					System.out.println(a + " > " + b);
				}
			}
		}
		if(flag == 0) {
			break;
		}
	}
	return true;
}


}


//========= GENERIC ABSTREE, TREE, AND DUPTREE (DON'T MODIFY THESE CLASSES)


abstract class AbsTree<T extends Comparable<T>> implements Iterable<T> {

	public AbsTree(T v) {
		value = v;
		left = null;
		right = null;
	}

	public void insert(T v) {
		if (value.compareTo(v) == 0)
			count_duplicates();
		if (value.compareTo(v) > 0)
			if (left == null)
				left = add_node(v);
			else
				left.insert(v);
		else if (value.compareTo(v) < 0)
			if (right == null)
				right = add_node(v);
			else
				right.insert(v);
	}

	public Iterator<T> iterator() {
		return create_iterator();
	}

	protected abstract AbsTree<T> add_node(T n);
	protected abstract void count_duplicates();
	protected abstract int get_count();
	protected abstract Iterator<T> create_iterator();
	
	protected T value;
	protected AbsTree<T> left;
	protected AbsTree<T> right;
}


class Tree<T extends Comparable<T>> extends AbsTree<T> {
	public Tree(T n) {
		super(n);
	}
	
	public Iterator<T> create_iterator() {
		return new AbsTreeIterator<T>(this);
	}

	protected AbsTree<T> add_node(T n) {
		return new Tree<T>(n);
	}

	protected void count_duplicates() {
		;
	}
	
	protected int get_count() {
		return 1;
	}
}


class DupTree<T extends Comparable<T>> extends AbsTree<T> {
	public DupTree(T n) {
		super(n);
		count = 1;
	};

	public Iterator<T> create_iterator() {
		return new AbsTreeIterator<T>(this);   // to do
	}
	
	protected AbsTree<T> add_node(T n) {
		return new DupTree<T>(n);
	}

	protected void count_duplicates() {
		count++;
	}
	
	protected int get_count() {
		return count;
	}

	protected int count;
}




// ========  GENERIC TREE ITERATORS (COMPLETE THE OUTLINES) =========

 

class AbsTreeIterator<T extends Comparable<T>> implements Iterator<T> {

public AbsTreeIterator(AbsTree<T> root) {
		
	// fill in code here
	stack_left_spine(root);
}

public boolean hasNext() {

	// fill in code here
	return !(stack.isEmpty());
	
}

public T next() {
	
	// fill in code here
	
	Pair<T> peek_value_instack = stack.peek();
	Pair<T> popped_node ;
	if(peek_value_instack.get_count_node_instack()==1) {
		popped_node = stack.pop();
		T val = popped_node.tree_node.value;
		if(popped_node.tree_node.right!=null) {
			stack_left_spine(popped_node.tree_node.right);
		}
		return val;
	}
	else {
		peek_value_instack.decrement_count_node_instack();
	}
	
	return peek_value_instack.tree_node.value;
	
}

private void stack_left_spine(AbsTree<T> node) {
	
	// fill in code here
	Pair<T> temp;
	while(node.left!=null) {
		temp = new Pair<T>(node);
		stack.push(temp);
		node = node.left;
	}
	temp = new Pair<T>(node);
	stack.push(temp);
	/**if(node.left!=null) { //if the Tree node has a left child
		stack.push(node);
		stack_left_spine(node.left);
		
	}else {
		stack.push(node);
	}**/

}

private Stack<Pair<T>> stack = new Stack<Pair<T>>();

}

class TreeIterator<T extends Comparable<T>> extends AbsTreeIterator<T> {
	
	// fill in code here
	public TreeIterator(Tree<T> node){
		super(node);
	}

}

class DupTreeIterator<T extends Comparable<T>> extends AbsTreeIterator<T> {
	
	// fill in code here
	public DupTreeIterator(DupTree<T> node){
		super(node);
	}
}

class Pair<T extends Comparable<T>>{
	
	public Pair(AbsTree<T> node) {
		tree_node = node;
		count_node = node.get_count();
	}
	AbsTree<T> tree_node;
	private int count_node;
	
	public int get_count_node_instack() {
		return count_node;
	}
	
	public void decrement_count_node_instack() {
		if(count_node > 1)
			count_node = count_node -1;
	}
	
}

