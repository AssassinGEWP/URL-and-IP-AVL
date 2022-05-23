import java.util.*;

public class AVLTree {

    private AVLNode root = null;
    private int size = 0;

    public AVLTree (){
        root = null;
        size = 0;
    }

    public void insert (String key, String ip){
        root = insert(key.toLowerCase(), ip, root);
    }

    private AVLNode insert (String key, String originalData, AVLNode node){
        if (node == null){
            node = new AVLNode (key, originalData);
            size++;
            return node;
        }
        else{
            if (key.compareTo (node.getKey()) < 0){
                node.setLeftChild (insert (key, originalData, node.getLeftChild ()));
                if (height (node.getLeftChild ()) - height (node.getRightChild ()) == 2){
                    if (key.compareTo ((node.getLeftChild ()).getKey ()) < 0)
                        node = rotateWithLeft (node);
                    else
                        node = doubleWithLeft (node);
                }
            }
            else{
                if (key.compareTo (node.getKey ()) > 0){
                    node.setRightChild (insert (key, originalData, node.getRightChild ()));
                    if (height (node.getRightChild ()) - height (node.getLeftChild ()) == 2){
                        if (key.compareTo ((node.getRightChild ()).getKey ()) > 0)
                            node = rotateWithRight (node);
                        else
                            node = doubleWithRight (node);
                    }
                }
            }
        }
        node.setHeight (max (height (node.getLeftChild ()), height (node.getRightChild ())) + 1);
        return node;
    }

    public AVLNode root(){
        return root;
    }

    public int size (){
        return size;
    }

    public int height (){
        return height (root);
    }

    private int height (AVLNode node){
        if (node == null)
            return -1;
        return node.getHeight ();
    }

    public boolean isEmpty (){
        return (size == 0);
    }

    private AVLNode rotateWithLeft (AVLNode node){
        AVLNode newNode = node.getLeftChild ();
        node.setLeftChild (newNode.getRightChild ());
        newNode.setRightChild (node);
        node.setHeight (max (height (node.getLeftChild ()), height (node.getRightChild ())) + 1);
        newNode.setHeight (max (height (newNode.getLeftChild ()), node.getHeight ()) + 1);
        return newNode;
    }


    private AVLNode rotateWithRight (AVLNode node){
        AVLNode newNode = node.getRightChild ();
        node.setRightChild (newNode.getLeftChild ());
        newNode.setLeftChild (node);
        node.setHeight (max (height (node.getLeftChild ()), height (node.getRightChild ())) + 1);
        newNode.setHeight (max (height (newNode.getRightChild ()), node.getHeight ()) + 1);
        return newNode;
    }

    private AVLNode doubleWithLeft (AVLNode node){
        node.setLeftChild (rotateWithRight (node.getLeftChild ()));
        return rotateWithLeft (node);
    }


    private AVLNode doubleWithRight (AVLNode node){
        node.setRightChild (rotateWithLeft (node.getRightChild ()));
        return rotateWithRight (node);
    }

    public static int max (int first, int second){
        if (first >= second)
            return first;
        else
            return second;
    }
    public boolean Disply (){
        return Disply (root);
    }

    private boolean Disply (AVLNode node){

        if (node == null)
            return false;

            System.out.println(node.getKey() + node.getOriginalData());
            Disply ( node.getLeftChild ());
             Disply ( node.getRightChild ());
        return true;
    }

    public boolean isInTree (String key){
        if (isInTree (key, root))
        return true;
        System.out.println("URL not found!");
        return false;
    }

    private boolean isInTree (String key, AVLNode node){

        if (node == null)
            return false;
        if (node.getKey().contains(key)){
            System.out.println("IP address for " + key +" : " + node.getOriginalData());
            return true;}

        if (key.compareTo (node.getKey ()) < 0)
            return isInTree (key, node.getLeftChild ());

        if (key.compareTo (node.getKey ()) > 0)
            return isInTree (key, node.getRightChild ());
        return true;
    }
    public boolean isInTreeip (String key){
        if (isInTreeip (key, root) == true)
            return true;
        System.out.println("Please Enter a vaild IP, IP has to be in this format xxx.xxx.xxx.xxx");
        return false;
    }
    private boolean isInTreeip (String key, AVLNode node){

        if (node == null)
            return false;
        if (node.getOriginalData().equalsIgnoreCase(key)){
            System.out.println("IP " + key +" belongs to : " + node.getKey());
            return true;}
        if (key.compareTo (node.getOriginalData ()) < 0)
            return isInTreeip (key, node.getLeftChild ());

        if (key.compareTo (node.getOriginalData ()) > 0)
            return isInTreeip (key, node.getRightChild ());
        return true;
    }

    public String[] getBestMatches(String word, int limit) {
        if (isInTree(word.toLowerCase(), root))
            return new String[] { word };
        if (word.length() <= 1)
            return new String[0];
        // strip off last letter and search for first node that "starts with" the term
        String searchTerm = word;
        AVLNode bestPartialMatchedNode = null;
        do {
            searchTerm = searchTerm.substring(0, word.length() - 1).toLowerCase();
            bestPartialMatchedNode = getBestPartialMatch(searchTerm, root);
        }
        while (searchTerm.length() > 1 && bestPartialMatchedNode == null);
        if (bestPartialMatchedNode == null)
            return new String[0];            // nothing to suggest
        LinkedList<String> bestMatches = new LinkedList<String>();
        LinkedList<AVLNode> currentLevel = new LinkedList<AVLNode>();
        LinkedList<AVLNode> nextLevel = new LinkedList<AVLNode>();
        currentLevel.push(bestPartialMatchedNode);
        while(!currentLevel.isEmpty() && bestMatches.size() < limit){
            AVLNode node = currentLevel.pollLast();
            if (node != null) {
                if (node.getKey().startsWith(searchTerm))
                    bestMatches.add(node.getOriginalData());
                if(node.getLeftChild() != null)
                    nextLevel.push(node.getLeftChild());
                if(node.getRightChild() != null)
                    nextLevel.push(node.getRightChild());
                if (currentLevel.isEmpty()) {
                    LinkedList<AVLNode> temp = nextLevel;
                    nextLevel = currentLevel;
                    currentLevel = temp;
                }
            }
        }
        Collections.sort(bestMatches);
        return bestMatches.toArray(new String[bestMatches.size()]);
    }

    private AVLNode getBestPartialMatch(String key, AVLNode node) {
        if (node == null)
            return null;
        if (node.getKey().startsWith(key))
            return node;
        if (key.compareTo (node.getKey ()) < 0)
            return getBestPartialMatch (key, node.getLeftChild ());
        if (key.compareTo (node.getKey ()) > 0)
            return getBestPartialMatch (key, node.getRightChild ());
        return null;
    }

    public void remove (String key) {
        AVLNode parent = null;
        AVLNode target = null;
        AVLNode node = root;

        // need a non-recusrive search alogorithm, because we have to
        // not only find the target node, but it's parent as well
        while (target == null && node != null){
            if (key.compareTo(node.getKey()) < 0){
                parent = node;
                node = node.getLeftChild ();
            }
            else if (key.compareTo(node.getKey()) > 0){
                parent = node;
                node = node.getRightChild ();
            }
            else{
                // ok, we've found the node we want to delete
                target = node;
            }
        }
        if (target == null) // target not found, nothing to delete
            return;
        // we need to find a replcement node to our target node
        // in the tree.
        AVLNode replacement = null;
        // seek a replacement node if there are two children
        if (target.getLeftChild() != null || target.getRightChild() != null){
            replacement = getReplacementRecursive(target.getLeftChild(), target, target);
        }
        else{
            // the replacement node is the node that exists
            if (target.getLeftChild() != null)
                replacement = target.getLeftChild();
            else
                replacement = target.getRightChild();
        }
        if (replacement != null){    // the replacement was a leaf node, so we can safely
            // dock the target's children on to the replacement
            replacement.setLeftChild(target.getLeftChild());
            replacement.setRightChild(target.getRightChild());
            updateHeight(replacement);
        }
        if (root == target){
            root = replacement;
        }
        else{
            if (parent.getLeftChild() == target)
                parent.setLeftChild(replacement);
            else
                parent.setRightChild(replacement);
        }
        size--;
    }

    private AVLNode getReplacementRecursive(AVLNode node, AVLNode parent, AVLNode target){
        if (node == null)
            return parent;
        AVLNode replacement = getReplacementRecursive(node.getRightChild(), node, target);
        if (parent.getRightChild() == replacement){
            parent.setRightChild(null);
            if (parent.getLeftChild() == null && parent != target)
                parent.setHeight(parent.getHeight() -1);
        }
        else{
            updateHeight(parent);
        }
        return replacement;
    }

    private void updateHeight(AVLNode node){
        int iLeftHeight = -1;
        int iRightHeight = -1;
        if (node.getLeftChild() != null)
            iLeftHeight = node.getLeftChild().getHeight();
        if (node.getRightChild() != null)
            iRightHeight = node.getRightChild().getHeight();
        node.setHeight(max(iLeftHeight, iRightHeight) + 1);
    }
}