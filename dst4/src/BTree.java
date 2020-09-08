
public class BTree {

    private int t;
    private BTreeNode root;

    //build new BTree with the parameter t
    public BTree (String tVal){
        this.t= Integer.parseInt(tVal);
        this.root = new BTreeNode(t);
    }

    public BTreeNode getRoot() {
        return root;
    }

    public void setRoot(BTreeNode sqrt) {
        this.root = sqrt;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    //insert string to the tree
    public void insert (String s){
        s=s.toLowerCase();
        if(root.getCurrentKeys()==2*t-1){
            BTreeNode newNode = new BTreeNode(t);
            BTreeNode temp = getRoot();
            this.setRoot(newNode);
            newNode.setIsLeaf(false);
            newNode.setCurrentKeys(0);
            newNode.setSon(temp,1);
            newNode.splitChild(1);
            newNode.insertNoFull(s);
        }
        else {
            root.insertNoFull(s);
        }
    }

    //reed the keys from the path file and insert them one by one
    public void createFullTree(String path){
        FileReader reader = new FileReaderImplement(path);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            insert(line);
        }
    }

    //return a string represents the tree in order in the format of value_depth
    public String inOrder (){
        //need to complete this function
        if(root!=null) {
            String output = root.toString(0, "");
            output = output.substring(0, output.length() - 1);
            return output;
        }
        else{
            return "";
        }
    }

    //check if the tree contains the key
    public boolean search (String key){
        if(root==null){
            return false;
        }
        else {
            return root.searchInTree(key);
        }
    }

    //delete all the keys in the tree from the path
    public void deleteKeysFromTree(String path){
        if(root!=null) {
            FileReader reader = new FileReaderImplement(path);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                root.deleteKeysFromTree(line);
                if (root != null && root.getCurrentKeys() == 0 & root.getSons()[1] != null) {
                    setRoot(root.getSons()[1]);
                }
            }
        }
    }

    //Get the time required to perform a search of all requested words when using the B tree.
    public String getSearchTime (String path){

        FileReader reader = new FileReaderImplement(path);
        long startTime = System.nanoTime();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            search(line);
        }
        long endTime   = System.nanoTime();
        double totalTime = (double)endTime/1000000 - (double)startTime/1000000;
        String time = Double.toString(totalTime);
        //just 4 digits stays after the dot
        return cutTo4Digits(time);
    }

    //just 4 digits stays after the dot
    public String cutTo4Digits (String toCut){
        if(toCut.contains(".")) {
            if (toCut.length() - toCut.substring(0,toCut.indexOf('.')).length() > 4) {
                toCut = toCut.substring(0, toCut.indexOf('.') + 5);
            }
        }
        return toCut;
    }
    @Override
    public String toString (){
        return inOrder();
    }
}

