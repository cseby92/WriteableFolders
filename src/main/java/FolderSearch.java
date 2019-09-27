import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FolderSearch {

    public Node getWritableFolderStructure(List<String> readableFolders, List<String> writableFolders) {
        Node root = Node.createRoot();
        readableFolders.forEach((path) -> constructFolders(root, path));
        writableFolders.forEach((path) -> setWritePermission(root, path));
        root.cutNonWritableNodes();
        return root;
    }

    private void constructFolders(Node root, String folderPath) {
        String[] splitted = folderPath.split("/");
        List<String> folders = Arrays.asList(splitted).subList(1, splitted.length);
        root.addChildren(folders, Permission.READ);

    }

    private void setWritePermission(Node root, String folderPath) {
        Optional<Node> node = root.getChildByPath(folderPath);
        node.ifPresent(n -> n.setPermission(Permission.WRITE));
    }
}
