package com.projecttwin.utils;
/**
 * Class use to return 2 value
 * @author NewWy
 *
 */
public class Pair<F, S> {
	private F first;
	private S second;
	public Pair(F first, S second){
		setFirst(first);
		setSecond(second);
	}
	
	public F getFirst(){
		return first;
	}
	
	public void setFirst(F first){
		this.first = first;
	}

	public S getSecond() {
		return second;
	}

	public void setSecond(S second) {
		this.second = second;
	}
}
