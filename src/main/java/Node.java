import java.util.*;

public class Node {
    private String name;
    private List<Node> children = new LinkedList<>();
    private Permission permission = Permission.NONE;

    public Node(String name) {
        this.name = name;
    }

    public static Node createRoot() {
        Node node = new Node("/");
        node.setPermission(Permission.WRITE);
        return node;
    }

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

    public Node searchChildByPath(String childPath) {
        List<String> nodeNames = new LinkedList<>(Arrays.asList(childPath.split("/")));
        nodeNames.remove(0);
        nodeNames.add(0, "/");
        Node node = searchChildRecursive(nodeNames);
        return node;
    }

    private Node searchChildRecursive(final List<String> childPath) {
        List<String> modifiableChildPath = new ArrayList<>(childPath);
        String nodeName = modifiableChildPath.get(0);
        modifiableChildPath.remove(0);

        if (name.equals(nodeName) && modifiableChildPath.isEmpty()) {
            return this;
        }
        if (!name.equals(nodeName)) {
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

    public void cutNonWritablePermissions() {
        Iterator<Node> childrenIterator = children.iterator();
        while (childrenIterator.hasNext()) {
            Node child = childrenIterator.next();
            if (child.permission == Permission.NONE) {
                childrenIterator.remove();
                continue;
            } else {
                child.cutNonWritablePermissions();
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
        if (!children.equals(node.children)) return false;
        return permission == node.permission;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + children.hashCode();
        result = 31 * result + permission.hashCode();
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
