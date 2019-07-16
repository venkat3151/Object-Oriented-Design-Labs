// --------- THIS IS THE TOP-LEVEL CLASS CONTAINING main

public class Delegation {
	
	public static void main(String args[]) {
		
		B b = new B();

		System.out.println(b.f()+b.g()+b.p(1)+b.q(2));
		
		B2 b2 = new B2();

		System.out.println(b2.f()+b2.g()+b2.p(1)+b2.q(2));
		
		D d = new D();

		System.out.println(d.f()+d.g()+d.h()+d.p(1)+d.q(2)+d.r());
		
		D2 d2 = new D2();

		System.out.println(d2.f()+d2.g()+d2.h()+d2.p(1)+d2.q(2)+d2.r());
		
		E e = new E();
		
		System.out.println(e.f()+e.g()+e.h()+e.p(1)+e.q(2)+e.r()+e.k(100));
		
		E2 e2 = new E2();
		System.out.println(e2.f()+e2.g()+e2.h()+e2.p(1)+e2.q(2)+e2.r()+e2.k(100));

		F f = new F();	
		System.out.println(f.f()+f.g()+f.h()+f.p(1)+f.q(2)+f.r()+f.j(10)+f.l(100));
		
		F2 f2 = new F2();
		System.out.println(f2.f()+f2.g()+f2.h()+f2.p(1)+f2.q(2)+f2.r()+f2.j(10)+f2.l(100));		
	}
}


// --------- CLASSES A, B, C, D, E AND F ARE GIVEN BELOW 

abstract class A {
	int a1 = 1;
	int a2 = 2;

	public int f() {
		return a1 + p(100) + q(100);
	}

	protected abstract int p(int m);

	protected abstract int q(int m);
}

class B extends A {
	int b1 = 10;
	int b2 = 20;

	public int g() {
		return f() + this.q(200);
	}

	public int p(int m) {
		return m + b1;
	}

	public int q(int m) {
		return m + b2;
	}
}

abstract class C extends B {
	int c1 = 100;
	int c2 = 200;

	public int r() {
		return f() + g() + h() + c1;
	}

	public int q(int m) {
		return m + a2 + b2 + c2;
	}

	protected abstract int h();
}

class D extends C {
	int d1 = 500;
	int d2 = 600;

	public int r() {
		return f() + g() + h() + c1;
	}

	public int p(int m) {
		return super.p(m) + d2;
	}

	public int h() {
		return a1 + b1 + d1;
	}
	
	public int j(int n) {
		return r() + super.r();
	}

}

class E extends C {
	int e1 = 700;
	int e2 = 800;

	public int q(int m) {
		return p(m) + c2;
	}

	public int h() {
		return a1 + b1 + e1;
	}
	
	public int k(int n) {
		return q(n) + super.q(n);
	}

}

class F extends D {
	int f1 = 900;
	int f2 = 1000;

	public int q(int m) {
		return p(m) + a1 + b1 + d1;
	}

	public int h() {
		return a1 + c2 + d2 + f2;
	}
	
	public int l(int n) {
		return q(n) + super.q(n);
	}

}


// ---------------YOUR SOLUTION BEGINS HERE --------------------


// FIRST DEFINE THE INTERFACE HIERARCHY: IA, IB, IC, ID, IE, IF

interface IA {
	public int f();
	public int p(int m);
	public int q(int m);
}

interface IB extends IA {
	public int g();
}

interface IC extends IB {
	public int r();
	public int h();
}

interface ID extends IC {
	public int j(int n) ;
}

interface IE extends IC {
	public int k(int n);
}

interface IF extends ID {
	public int l(int n);
	 
}


// -------- THEN IMPLEMENT THE CLASSES:  A2, B2, C2, D2, E2, F2


class A2 implements IA {
	
	IA this2;
	int a1 = 1;
	int a2 = 2;
	
	public A2(IA x) {
		this2 = x;
	}
	@Override
	public int f() {
		return a1 + p(100) + q(100);
	}
	
	@Override
	public int p(int m) {
		return this2.p(m);
	}

	@Override
	public int q(int m) {
		return this2.q(m);
	}

}

class B2 implements IB {
	
	int b1 = 10;
	int b2 = 20;
	A2 super2;
	IB this2;
	public B2(IB y) {
		this2 = y;
		super2 = new A2(this2);
	}
	public B2() {
		this2 = this;
		super2 = new A2(this2);
	}

	@Override
	public int f() {
		return super2.f();
	}

	@Override
	public int p(int m) {
		return m + b1;
	}

	@Override
	public int q(int m) {
		return m + b2;
	}

	@Override
	public int g() {
		return f() + this2.q(200);
	}
	 
}

class C2 implements IC {
	
	int c1 = 100;
	int c2 = 200;
	IC this2;
    B2 super2;
	public C2(IC y) {
		this2 = y;
		super2 = new B2(this2);
	}
	
	@Override
	public int r() {
		return f() + g() + h() + c1;
	}
	
	@Override
	public int q(int m) {
		return m + super2.super2.a2 + super2.b2 + c2;
	}
	
	// dummy methods
	@Override
	public int f() {
		return super2.f();
	}

	@Override
	public int p(int m) {
		return super2.p(m);
	}

	  @Override
	public int g() {
		return super2.g();
	}

	@Override
	public int h() {
		return this2.h();
	}

	
	 
}

class D2 implements ID {
	
	int d1 = 500;
	int d2 = 600;
	ID this2;
	C2 super2;
	public D2(ID y) {
		this2 = y;
		super2 = new C2(this2);
		
	}
	
	public D2() {
		this2 = this;
		super2 = new C2(this);
	}

	public int h() {
		return super2.super2.super2.a1 + super2.super2.b1 + d1;
	}
	
	@Override
	public int p(int m) {
		return super2.p(m) + d2;
	}
	
	@Override
	public int r() {
		return f() + g() + super2.h() + super2.c1;
	}
	
	public int j(int n) {
		return r() + super2.r();
	}

	// dummy method
	@Override
	public int f() {
		return super2.f();
	}
	
	@Override
	public int q(int m) {
		return super2.q(m);
	}

	@Override
	public int g() {
		return super2.g();
	}
}
	

class E2 implements IE {
	
	int e1 = 700;
	int e2 = 800;

	C2 super2;
	public  E2(){
		super2 = new C2(this);
		}
	@Override
	public int q(int m) {
		return p(m) + super2.c2;
	}
	
	public int h() {
		return super2.super2.super2.a1 + super2.super2.b1 + e1;
	}
	
	public int k(int n) {
		return q(n) + super2.q(n);
	}
	
	@Override
	public int r() {
		return f() + g() + h() + super2.c1;
	}
	// dummy method
	@Override
	public int f() {
		return super2.f();
	}

	@Override
	public int p(int m) {
		return super2.p(m);
	}

	@Override
	public int g() {
		return super2.g();
	}

	
	 
}

class F2 implements IF{
	
	int f1 = 900;
	int f2 = 1000;
	
	D2 super2;
	public F2() {
		super2 = new D2(this);
	}

	@Override
	public int q(int m) {
		return p(m) + super2.super2.super2.super2.a1 + super2.super2.super2.b1 + super2.d1;
	}
	
	@Override
	public int h() {
		return super2.super2.super2.super2.a1 + super2.super2.c2 + super2.d2 + f2;
	}
	
	public int l(int n) {
		return q(n) + super2.q(n);
	}
	// dummy method
	@Override
	public int f() {
		return super2.f();
	}

	@Override
	public int p(int m) {
		return super2.p(m);
	}

	@Override
	public int g() {
		return super2.g();
	}

	@Override
	public int r() {
		return super2.r() ;
	}

	@Override
	public int j(int n) {
		return r()  + super2.r();
	}

}
 