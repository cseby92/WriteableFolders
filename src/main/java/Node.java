import java.util.*;

public class Node {
    private String name;
    private List<Node> children = new LinkedList<>();
    private Permission permission = Permission.NONE;

    public Node(String name) {
        this.name = name;
    }

    /**
     * Method for creating root node.
     */
    public static Node createRoot() {
        Node node = new Node("/");
        node.setPermission(Permission.WRITE);
        return node;
    }

    /**
     * Takes a path and permission, and adds it's last element on the path with the permission.
     * Any non existing nodes on the path also gets created with default NONE permission.
     */
    public void addChildren(List<String> path, Permission permission) {
        path = new LinkedList<>(path);
        String nodeName = path.get(0);
        path.remove(0);
        Node child = getChild(nodeName);
        if (child == null) {
            child = new Node(nodeName);
            addChild(child);
        }
        if (!path.isEmpty()) {
            child.addChildren(path, permission);
        } else {
            child.setPermission(permission);
        }
        return;
    }

    private Node getChild(String name) {
        for (Node child : children) {
            if (child.name.equals(name)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Takes a nodeName and returns ture if the node or any of it's children's name is equal
     * to the nodeName.
     */
    public Boolean containsNode(String nodeName) {
        if (name.equals(nodeName)) {
            return true;
        }
        for (Node child : children) {
            if (child.containsNode(nodeName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes a childPath and returns the last node on the path if it exists, else return Optional empty.
     */
    public Optional<Node> getChildByPath(String childPath) {
        List<String> nodeNames = new LinkedList<>(Arrays.asList(childPath.split("/")));
        nodeNames.remove(0);
        nodeNames.add(0, "/");
        Node node = searchChildRecursive(nodeNames);
        return Optional.ofNullable(node);
    }

    private Node searchChildRecursive(final List<String> childPath) {
        List<String> modifiableChildPath = new ArrayList<>(childPath);
        String nodeName = modifiableChildPath.get(0);
        modifiableChildPath.remove(0);
        boolean nameMatches = name.equals(nodeName);
        boolean nodeHasNoChildren = modifiableChildPath.isEmpty();
        if (nameMatches && nodeHasNoChildren) {
            return this;
        }
        if (!nameMatches) {
            return null;
        }
        for (Node node : children) {
            Node tempNode;
            tempNode = node.searchChildRecursive(modifiableChildPath);
            if (tempNode != null) {
                return tempNode;
            }
        }
        return null;
    }

    /**
     * Removes child nodes that are non writable, or has no direct or indirect writable child nodes
     * and also writable nodes that are not accessible by readable or writable nodes.
     */
    public void cutNonWritableNodes() {
        Iterator<Node> childrenIterator = children.iterator();
        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            if (child.permission == Permission.NONE) {
                childrenIterator.remove();
            } else {
                child.cutNonWritableNodes();
            }
            if (child.children.isEmpty() && child.permission != Permission.WRITE) {
                childrenIterator.remove();
            }
        }
    }

    private void addChild(Node child) {
        children.add(child);
    }

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (!getName().equals(node.getName())) return false;
        if (!getChildren().equals(node.getChildren())) return false;
        return getPermission() == node.getPermission();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getChildren().hashCode();
        result = 31 * result + getPermission().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", permission=" + permission +
                ", children=" + children +
                '}';
    }
}
