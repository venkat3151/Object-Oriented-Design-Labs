
import java.util.Random;
import java.util.concurrent.Semaphore;

// Readers-Writers with Writer Priority

public class RW_Semaphore { 
	
	public static void main(String[] args) {

		Database d = new Database();

		Writer w1 = new Writer(d,1);
		Writer w2 = new Writer(d,10);
		Writer w3 = new Writer(d,100);
		Writer w4 = new Writer(d,1000);
		Writer w5 = new Writer(d,10000);

		Reader r1 = new Reader(d);
		Reader r2 = new Reader(d);
		Reader r3 = new Reader(d);
		Reader r4 = new Reader(d);
		Reader r5 = new Reader(d);
		
		w1.start();
		r1.start();
		r2.start();
		r3.start();
		w2.start();
		w3.start();
		w4.start();
		w5.start();
		r4.start();
		r5.start();
	}
}

class Reader extends Thread {
	Database d;

	public Reader(Database d) {
		this.d = d;
	}

	public void run() {

		for (int i = 0; i < 5; i++){		
			d.request_read();
			System.out.println(d.read());
			d.done_read();
			
		}
	}
}

class Writer extends Thread {

	Database d;
	int x;

	public Writer(Database d, int x) {
		this.d = d;
		this.x = x;
	}

	public void run() {
		for (int i = 0; i < 5; i++) {			
			d.request_write();
			d.write(x);
			try { Thread.sleep(50); }
			catch (Exception e) {}
			d.done_write();
		}
	}
}



class Database{
	int data = 0;
	int r = 0;   // # active readers
	int w = 0;   // # active writers (0 or 1)
	int ww = 0;  // # waiting writers
	int wr = 0;  // # waiting readers
	Semaphore s = new Semaphore(1);
	Semaphore sWW = new Semaphore(0);  
	Semaphore sWR = new Semaphore(0);

	public void request_read() {
		try {
			s.acquire();
			while (w == 1 || ww > 0) {
				try {   wr++; s.release(); sWR.acquire();   s.acquire(); wr--;}   
			    catch (Exception e) {}
			}
			r++;
			s.release();
		} catch(Exception e) {}
	}

	public void done_read() {	
		try {
			s.acquire();
			r--;
			//notifyAll();
			if(r==0 && ww>0) {
				sWW.release();
			}else if(ww==0 && wr > 0) {
				sWR.release(sWR.getQueueLength());
			}
			s.release();
		} catch(Exception e) {}
		
		
	}

	public void request_write() {	
		try {
			s.acquire();
	 		while (r > 0 || w == 1) {			
				try {
					 s.release(); ww++;  sWW.acquire();  ww--; s.acquire();
				} catch (Exception e) {}
	 		}
			w = 1;
			s.release();
		} catch(Exception e) {}
	}

	public void done_write() {
		
		try {
			s.acquire();
			w=0;
			if(wr>0 && ww ==0) {
				sWR.release(sWR.getQueueLength());
			}else if(ww>0) {
				sWW.release();
			}
			s.release();
			
		} catch(Exception e) {}
		
		//notifyAll();
		
	} 

	int read() {
		return data;
	}

	void write(int x) {
		data = data + x;
	}
}

