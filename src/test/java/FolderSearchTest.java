import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FolderSearchTest {

    FolderSearch folderSearch = new FolderSearch();

    @Test
    void testEmptyReadShouldReturnOnlyRoot() {
        Node writableFolderStructure = folderSearch.getWritableFolderStructure(new ArrayList<>(), new ArrayList<>());
        assertEquals(Node.createRoot(), writableFolderStructure);
    }

    @Test
    void testGetWritableFolderStructure() {
        String[] read = {"/A/B", "/A", "/A/B/D", "/A/C/D", "/A/F", "/A/F/H", "/A/F/H/R"};
        List<String> readable = Arrays.asList(read);
        String[] write = {"/A/C/D", "/A/F/H"};
        List<String> writeable = Arrays.asList(write);

        Node writableFolderStructure = folderSearch.getWritableFolderStructure(readable, writeable);
        System.out.println(writableFolderStructure);

    }
}