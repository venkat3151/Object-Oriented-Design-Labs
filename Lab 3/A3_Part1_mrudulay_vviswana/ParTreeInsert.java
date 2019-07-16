import java.util.Random;

public class ParTreeInsert {

		public static void main(String[] args) {	
		
	         Tree tr = new Tree(5000);

	         int N = 5;       

	         InsertNums[] threads = {
					new InsertNums(tr), 
					new InsertNums(tr), 
					new InsertNums(tr), 
					new InsertNums(tr),
					new InsertNums(tr)
					};        

			try {
				System.out.println("Start Parallel Insert ...");
				for (int i = 0; i < N; i++) {
					threads[i].start();
				}
				for (int i = 0; i < N; i++) {
					threads[i].join();
				}
				System.out.println("Done Parallel Insert ...\nResults:");

				tr.print();

			} catch (Exception e) { }
		}
	}

class InsertNums extends Thread {

	    Tree tr;	  
  
	    public InsertNums(Tree tr) {
	      this.tr = tr;
	    }

	    public void run() {
	      Random r = new Random();
	      for (int i = 0; i < 5; i++) 
	    	  tr.insert_par(r.nextInt(10000));
	      tr = null;
	    }
}

class Tree {  	
	private boolean empty = false;

		public Tree(int n) {
			value = n;
			//left == right == null, by default
		}
		protected void insert(int n) {
			if (value == n) return;
			if (value < n) {
				if (right == null) {
					right = new Tree(n);
					unlock();
				}
					
				else 
					right.insert(n);
			}
				

			else if (left == null) {
				left = new Tree(n);
			unlock();
			}
			else 
				left.insert(n);
		}
		
		void print() {
			if (left != null) left.print();
			System.out.print(value + " ");
			if (right != null) right.print();
		}
		

	    void insert_par(int n) {
	    	if (value == n) return;
	    	lock();
	    	insert(n);
	    unlock();
}
	    
		synchronized void lock() {

			try {
				while(empty) {
					wait();
				}
				empty = true;
			}catch(InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		synchronized void unlock() {

		
		        empty = false;
		        notify();

		}
		
		protected int value;
		protected Tree left, right;
		
		// okay to add extra fields
		 
}


 
