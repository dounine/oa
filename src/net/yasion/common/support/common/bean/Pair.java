package net.yasion.common.support.common.bean;

/** 支持一对的对象存在而实现的类 */
public class Pair<A, B> implements Cloneable {

	private A first;

	private B second;

	public A getFirst() {
		return first;
	}

	public void setFirst(A first) {
		this.first = first;
	}

	public B getSecond() {
		return second;
	}

	public void setSecond(B second) {
		this.second = second;
	}

	public Pair() {
		super();
	}

	public Pair(Pair<A, B> pair) {
		this.first = pair.first;
		this.second = pair.second;
	}

	public Pair(A first, B second) {
		super();
		this.first = first;
		this.second = second;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 37 * hash + (this.first != null ? this.first.hashCode() : 0);
		hash = 37 * hash + (this.second != null ? this.second.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Pair<?, ?> other = (Pair<?, ?>) obj;
		if (this.first != other.first && (this.first == null || !this.first.equals(other.first))) {
			return false;
		}
		if (this.second != other.second && (this.second == null || !this.second.equals(other.second))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format(this.getClass().getName() + "[%s,%s]", first, second);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Pair<A, B>(this);
	}
}