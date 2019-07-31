package com.joebase.sortMethods;

public interface Sortable {

	/**
	 * Sort the given array which consists of child class of {@link Comparable}<br/>
	 * 
	 * @param array
	 * @param ascend
	 */
	<T extends Comparable<T>> void sort(T[] array, boolean ascend);
}
