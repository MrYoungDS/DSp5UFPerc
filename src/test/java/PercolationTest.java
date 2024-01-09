import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.File;

import edu.princeton.cs.algs4.In;

public class PercolationTest {

    // input file to visualize
    private static final String INPUT_PATH =
            "./percolation-test-files";
    private Percolation grid10;

    @BeforeEach
    public void setup() {
        grid10 = new Percolation(10);
    }

    @Test
    public void testCloseSites() {
        for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                assertFalse(grid10.isOpen(i, j),
                        "Site " + i + "," + j + " should start closed");
            }
        }
    }

    @Test
    public void testOpen() {
        grid10.open(1, 1);
        assertTrue(grid10.isOpen(1, 1));
    }

    @Test
    public void testOpenAdjacentClosed() {
        grid10.open(1, 1);
        assertFalse(grid10.isOpen(1, 2),
                "You should not open an adjacent site, you should union them");
    }

    @Test
    public void testNumberOfOpenSites() {
        grid10.open(1, 1);
        assertEquals(1, grid10.numberOfOpenSites(),
                "Should have 1 site open");
    }

    @Test
    public void testIsFull() {
        grid10.open(1, 1);
        assertTrue(grid10.isFull(1, 1),
                "1,1 should translate to the top left corner (traditionally 0,0) and be full if it is opened");
    }

    @Test
    public void testIsFullFalse() {
        grid10.open(1, 1);
        grid10.open(3, 1);
        assertFalse(grid10.isFull(3, 1),
                "3,1 should not be full as it is not connected to top");
    }

    @Test
    public void testIsFullConnection() {
        grid10.open(1, 1);
        grid10.open(2, 1);
        grid10.open(3, 1);
        assertTrue(grid10.isFull(3, 1),
                "3, 1 is connected to the top and thus should be full");
    }

    private Percolation generatePercolation(String filename) {
        In in = new In(INPUT_PATH + "/" + filename);      // input file
        int n = in.readInt();         // n-by-n percolation system
        // repeatedly read in sites to open
        Percolation perc = new Percolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        return perc;
    }

    @Test
    public void testPercolatesFalse() {
        File folder = new File(INPUT_PATH);

        for (File file : folder.listFiles()) {
            // check that it's a valid txt file
            if (file.isFile() &&
                    (file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("txt")))
            {
                // check to verify that it is a system that does not percolate
                if (file.getName().contains("no") || file.getName().equals("greeting57.txt")
                        || file.getName().equals("heart25.txt"))
                {
                    Percolation perc = generatePercolation(file.getName());
                    assertFalse(perc.percolates(), file.getName() + " should not percolate");
                }
            }
        }
    }

    @Test
    public void testPercolates() {
        File folder = new File(INPUT_PATH);
        for (File file : folder.listFiles()) {
            // check that it's a valid txt file
            if (file.isFile() &&
                    (file.getName().substring(file.getName().lastIndexOf('.') + 1).equals("txt")))
            {
                // check to verify that it is a system that percolates
                if (!file.getName().contains("no") && !file.getName().equals("greeting57.txt") &&
                        !file.getName().equals("heart25.txt"))
                {
                    Percolation perc = generatePercolation(file.getName());
                    assertTrue(perc.percolates(), file.getName() + " should percolate");
                }
            }
        }
    }

    @Test
    public void testBackwash() {
        Percolation input10 = generatePercolation("input10.txt");
        assertFalse(input10.isFull(10, 1),
                "Bottom left site is not connected to the top so should not be full");
    }
}
