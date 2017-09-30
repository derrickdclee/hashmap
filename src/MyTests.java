import static org.junit.Assert.*;
import org.junit.Test;


public class MyTests {
	
	@Test
	public void testSetAndGet() {
		MyHashMap<String> map = new MyHashMap<>(4);
		map.set("k1", "v1");
		assertEquals("v1",  map.get("k1"));
	}
	
	@Test
	public void testDelete() {
		MyHashMap<String> map = new MyHashMap<>(4);
		map.set("k1", "v1");
		map.delete("k1");
		assertNull(map.get("k1"));
	}
	
	@Test
	public void testWithCustomObjectAsValue() {
		MyHashMap<MyObject> map = new MyHashMap<>(4);
		MyObject o1 = new MyObject();
		map.set("k1", o1);
		assertSame(o1, map.get("k1"));
	}
	
	@Test
	public void testOverridingWithSameKey() {
		MyHashMap<MyObject> map = new MyHashMap<>(4);
		MyObject o1 = new MyObject();
		MyObject o2 = new MyObject();
		map.set("k1", o1);
		map.set("k1", o2);
		assertSame(o2, map.get("k1"));
	}
	
	@Test
	public void testGoingOverCapacity() {
		MyHashMap<MyObject> map = new MyHashMap<>(4);
		map.set("k1", new MyObject());
		map.set("k2", new MyObject());
		map.set("k2", new MyObject()); // overriding
		map.set("k3", new MyObject());
		map.delete("k3"); // deleting
		map.set("k3", new MyObject());
		map.set("k4", new MyObject());
		assertFalse(map.set("k5", new MyObject()));
	}
}
