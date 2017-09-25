class TestClass {
	int age;
	String name;
	
	TestClass(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public String toString() {
		return String.format("%s is %d years old.", name, age);
	}
}

public class MyHashMap<V> {
	private MyHashEntry[] hashtable;
	private int capacity;
	private int size = 0;
	
	/*
	 * static inner class for elements to be inserted into the hash map
	 */
	public static class MyHashEntry {
		private MyHashEntry next;
		private String key;
		private Object value;
		
		MyHashEntry(String key, Object value) {
			this.key = key;
			this.value = value;
			this.next = null;
		}
	}
	
	/*
	 * Constructor for MyHashMap
	 */
	public MyHashMap(int capacity) {
		if (capacity <= 0 ) throw new IllegalArgumentException();
		this.capacity = capacity;
		this.hashtable = new MyHashEntry[capacity];
	}
	
	private int findInd(String key) {
		return mod(key.hashCode(), capacity);
	}
	
	private int mod(int n, int m) {
		return ((n % m) + m) % m; // to deal with negative hash codes
	} 
	
	public boolean set(String key, V value) {
		// if the hash map is full, reject
		if (size == capacity) return false;
		
		int ind = findInd(key);
		
		// no element in the bucket
		if (hashtable[ind] == null) {
			hashtable[ind] = new MyHashEntry(key, (Object) value);
			size++;
			return true;
		} else { 
			// if there is an element in the bucket
			MyHashEntry curr = hashtable[ind];
			while (curr.next != null) {
				if (!curr.key.equals(key)) {
					curr = curr.next;
				} else {
					// there is an element with the same key
					// so override it
					curr.value = (Object) value;
					return true;
				}
			}
			// reached the end of the linked list
			if (!curr.key.equals(key)) {
				curr.next = new MyHashEntry(key, (Object) value);
				size++;
			} else {
				curr.value = (Object) value;
			}
			return true;
		}
	};
	
	public V get(String key) {
		int ind = findInd(key);
		if (hashtable[ind] == null) return null;
		MyHashEntry curr = hashtable[ind];
		while (curr.next != null) {
			if (!curr.key.equals(key)) {
				// if the keys don't match, keep moving down the linked list
				curr = curr.next;
			} else {
				// if the match is found, return the value
				return (V) curr.value;
			}
		}
		// reached the end of the linked list
		return curr.key.equals(key)? (V) curr.value : null;
	}
	
	public V delete(String key) {
		int ind = findInd(key);
		
		if (hashtable[ind] == null) return null;
		
		MyHashEntry curr = hashtable[ind];
		MyHashEntry prev = null;
		
		while (curr.next != null) {
			if (!curr.key.equals(key)) {
				// if the keys don't match, keep moving down the linked list
				curr = curr.next;
				prev = curr;
			} else {
				// if the match is found, delete the entry from the linked list and return the value
				prev.next = curr.next;
				curr.next = null;
				return (V) curr.value;
			}
		}
		
		// reached the end of the linked list
		if (!curr.key.equals(key)) {
			// if it's not a match
			return null;
		} else {
			if (prev == null) {
				// if this was a single-element linked list
				hashtable[ind] = null;
				return (V) curr.value;
			} else {
				prev.next = null;
				return (V) curr.value;
			}
		}
	}
	
	public double load() {
		return (1.0 * size) / capacity;
	}
	
	public static void main(String[] args) {
		MyHashMap<TestClass> map = new MyHashMap<TestClass>(16);
		System.out.println(map.set("thisismykey", new TestClass(22, "Derrick")));
		System.out.println(map.set("thisismykey", new TestClass(21, "Chloe")));
		System.out.println(map.set("thisisanotherkey", new TestClass(21, "Chloe")));
		System.out.println(map.load());
		System.out.println(map.set("thisisyetanotherkey", new TestClass(100, "OldMan")));
		System.out.println(map.set("whathappens?", new TestClass(1, "baby")));
		System.out.println(map.load());
	}

}
