package simulator.view;


public interface Observable<T> {
	
	void addObserver(T o );
	void removeObserver(T o );
	
}
