import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FolderSearchTest {

    FolderSearch folderSearch = new FolderSearch();

    @Test
    void testEmptyReadShouldReturnOnlyRoot() {
        Node writableFolders = constructFolders(new String[0], new String[0]);

        assertEquals(Node.createRoot(), writableFolders);
    }

    @Test
    void testTreeShouldNotContainAnyNodesForOnlyReadableInput() {
        String[] read = {"/A/B", "/A", "/A/B/D"};
        Node writableFolders = constructFolders(read, new String[0]);

        assertFalse(writableFolders.containsNode("A"));
        assertFalse(writableFolders.containsNode("B"));
        assertFalse(writableFolders.containsNode("D"));
    }

    @Test
    void testTreeShouldNotContainNonReachableWritableNodes() {
        String[] read = {"/A"};
        String[] write = {"/A/B/C"};
        Node writableFolders = constructFolders(read, write);

        assertFalse(writableFolders.containsNode("C"));
    }

    @Test
    void testTreeShouldContainOnlyWritableAndReachableNodes() {
        String[] read = {"/A/B", "/A", "/A/B/D", "/A/C/D", "/A/F", "/A/F/H", "/A/F/H/R"};
        String[] write = {"/A/C/D", "/A/F/H"};
        Node writableFolders = constructFolders(read, write);

        assertTrue(writableFolders.containsNode("A"));
        assertTrue(writableFolders.containsNode("F"));
        assertTrue(writableFolders.containsNode("H"));
        assertFalse(writableFolders.containsNode("B"));
        assertFalse(writableFolders.containsNode("C"));
    }

    private Node constructFolders(String[] read, String[] write) {
        List<String> readable = Arrays.asList(read);
        List<String> writable = Arrays.asList(write);
        return folderSearch.getWritableFolderStructure(readable, writable);
    }
}