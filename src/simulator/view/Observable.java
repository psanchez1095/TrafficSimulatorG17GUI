package simulator.view;


public interface Observable<T> {
	
	void removeObserver(T o );
	void addObserver(T o );
	
	
}
