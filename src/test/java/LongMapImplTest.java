import de.comparus.opensource.longmap.LongMap;
import de.comparus.opensource.longmap.LongMapImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongMapImplTest {
    private LongMap<String> map;

    @BeforeEach
    public void setUp() {
        map = new LongMapImpl<>();
    }

    @Test
    public void testPutAndGet() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.put(3L, "Three");

        assertEquals("One", map.get(1L));
        assertEquals("Two", map.get(2L));
        assertEquals("Three", map.get(3L));
    }

    @Test
    public void testPutWithCollision() {
        map.put(1L, "One");
        map.put(17L, "Seventeen");

        assertEquals("One", map.get(1L));
        assertEquals("Seventeen", map.get(17L));
    }

    @Test
    public void testPutAndGetSpecialCase() {
        map.put(Long.MIN_VALUE, "MinValue");

        assertEquals("MinValue", map.get(Long.MIN_VALUE));
    }

    @Test
    public void testPutAndGetNullValue() {
        map.put(1L, null);
        assertNull(map.get(1L));
    }

    @Test
    public void testRemove() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.put(3L, "Three");

        assertEquals("Two", map.remove(2L));
        assertNull(map.get(2L));
    }

    @Test
    public void testRemoveNonExistentKey() {
        assertNull(map.remove(100L));
    }

    @Test
    public void testSize() {
        assertEquals(0, map.size());
        map.put(1L, "One");
        map.put(2L, "Two");
        assertEquals(2, map.size());
        map.remove(1L);
        assertEquals(1, map.size());
    }

    @Test
    public void testClear() {
        map.put(1L, "One");
        map.put(2L, "Two");
        map.clear();
        assertEquals(0, map.size());
        assertNull(map.get(1L));
        assertNull(map.get(2L));
    }
}
