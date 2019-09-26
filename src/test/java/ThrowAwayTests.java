import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class ThrowAwayTests {

    FolderSearch folderSearch = new FolderSearch();

    @Test
    void folderSearchTest() {
        //Optional<Node> structure = folderSearch.getWritableFolderStructure(new ArrayList<String>(), new ArrayList<String>());
        //assertEquals(Optional.empty(), structure);
    }

    @Test
    void treeConstructionTest() {
        String[] stringList = {"/A/B", "/A/C/D", "/A/C", "/A", "/A/B/D", "/A/F/H", "/A/F"};
        List<String> listOfPaths = Arrays.asList(stringList);
        Node root = Node.createRoot();
        listOfPaths.forEach((path) -> {
            String[] splitted = path.split("/");
            root.addChildren(new LinkedList<>(Arrays.asList(splitted).subList(1, splitted.length)), Permission.READ);
        });

        System.out.println("ASD");

    }

    /*
    @Test
    void asd() {
        String split = "A/B/C/D";
        int i = split.lastIndexOf("/");
        split.substring(i);

    }

    @Test
    void canBeUsedForRealTstLater() {
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D1 = new Node("D");
        Node D2 = new Node("D");
        Node E = new Node("E");
        Node Q = new Node("Q");
        Node Z = new Node("Z");
        A.addChild(B);
        A.addChild(C);
        B.addChild(D1);
        D1.addChild(Z);
        B.addChild(Q);
        C.addChild(D2);
        C.addChild(E);
        assertEquals(D2, A.searchChildByPath("A/C/D").get());
        assertEquals(B, A.searchChildByPath("A/B").get());
    }

    @Test
    void canBeUsedForRealTstLater2() {
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        A.addChild(B);
        A.addChild(C);
        C.addChild(D);
        assertEquals(D, A.searchChildByPath("A/C/D").get());
    }

    @Test
    void canBeUsedForRealTstLater3() {
        Node A = new Node("A");
        Node B = new Node("B");
        A.addChild(B);
        assertEquals(A, A.searchChildByPath("A").get());
    }

    @Test
    void constructTest() {
        List<String> list = new ArrayList<>();
        list.add("A");
        list.add("A/B");
        list.add("A/B/C");
        list.add("A/B/D");
        list.add("A/Q");
        Node node = folderSearch.constructReadableTree(list);
    }
    */
}