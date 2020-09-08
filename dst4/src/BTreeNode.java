public class BTreeNode {
    private BTreeNode[] sons;
    private String [] keys;
    private int currentKeys;
    private boolean isLeaf;
    private int tParameter;

    //build new BTreeNode with 2t-1 keys and 2t sons
    public BTreeNode (int t){
        this.keys = new String [2*t];
        this.sons = new BTreeNode[2*t+1];
        this.currentKeys = 0;
        isLeaf = true;
        tParameter = t;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public BTreeNode[] getSons() {
        return sons;
    }

    public String[] getKeys() {
        return keys;
    }

    public int getCurrentKeys() {
        return currentKeys;
    }

    public void setCurrentKeys(int currentKeys) {
        this.currentKeys = currentKeys;
    }

    public void setKeysArray(String[] keys) {
        this.keys = keys;
    }

    public void setIsLeaf(boolean bool){
        this.isLeaf=bool;
    }

    public void setSon (BTreeNode node, int index){
        this.sons[index]=node;
    }

    public void setKeyInIndexI (String newKey, int index){
        this.keys[index]=newKey;
    }

    public void insertNoFull (String s){
        int i=getCurrentKeys();
        if(isLeaf()){
            //find the place to insert
            while (i>=1&&Comparator.compare(s,getKeys()[i])<0){
                setKeyInIndexI(getKeys()[i],i+1);
                i=i-1;
            }
            setKeyInIndexI(s,i+1);
            setCurrentKeys(getCurrentKeys()+1);
        }
        else{
            //find the place to insert
            while (i>=1&&Comparator.compare(s,getKeys()[i])<0){
                i=i-1;
            }
            i=i+1;
            BTreeNode child = getSons()[i];
            if (child.getCurrentKeys()==(2*tParameter)-1){
                splitChild(i);
                if(Comparator.compare(s,getKeys()[i])>0){
                    i=i+1;
                }
            }
            getSons()[i].insertNoFull(s);
        }
    }

    //delete key to the Sub tree of this node
    public boolean deleteKeysFromTree(String key){
        if(searchInTree(key)){
            int index = searchInNode(key);
            //key in node and leaf
            if(getKeys()[index].equals(key)&&isLeaf()){
                return removeKeyFromLeaf(index);
            }
            //key in node and not leaf and left son has at least t keys
            else if (getKeys()[index].equals(key)&&!isLeaf()&&getSons()[index].getCurrentKeys()>tParameter-1){
                return changeKeyWithLSonAndDelete(index);
            }
            //key in node and not leaf and right son has at least t keys
            else if (getKeys()[index].equals(key)&&!isLeaf()&&getSons()[index+1].getCurrentKeys()>tParameter-1){
                return changeKeyWithRSonAndDelete(index);
            }
            //key in node and not leaf and both sons with t-1 keys
            else if(getKeys()[index].equals(key)&&!isLeaf()) {
                return deleteAfterMergingSons(index,key);
            }
            //key isn't in the node
            else if(!getKeys()[index].equals(key)){
                return deleteKeyNotExistsInNode(index,key);
            }
        }
        return false;
    }

    // String represents the tree in - order
    public String toString (int depth, String s1){
        String s2 = "";

        if(isLeaf()){ //when the recurtion stops
            for (int i=1;i<getCurrentKeys()+1;i=i+1){
                s2=s2+keys[i]+"_"+depth+",";
            }
            s1=s1+s2;
        }
        else {
            for (int i=1;i<getCurrentKeys()+1;i=i+1) {
                //split to cases in order not to repeat the same son twice
                //if its the first key then print its 2 sons, otherwise print just the right son
                if (i==1) {
                    s1 = s1 + sons[i].toString(depth + 1, s2);
                    s1 = s1 + keys[i]+"_"+depth+",";
                    s1 = s1 + sons[i+1].toString(depth + 1, s2);
                }
                else {
                    s1 = s1 + keys[i]+"_"+depth+",";
                    s1 = s1 + sons[i+1].toString(depth + 1, s2);
                }
            }
        }
        return s1;
    }

    //Split child
    public void splitChild (int i){
        BTreeNode y = getSons()[i];
        BTreeNode z = new BTreeNode(tParameter);
        z.setIsLeaf(y.isLeaf());
        z.setCurrentKeys(tParameter-1);
        //copy the relevant keys
        for (int j=1; j<=tParameter-1;j=j+1) {
            z.setKeyInIndexI(y.getKeys()[j+tParameter],j);
        }
        //copy the relevant sons
        if(!y.isLeaf()){
            for (int j=1; j<=tParameter;j=j+1){
                z.setSon(y.getSons()[j+tParameter],j);
            }
        }
        //update sons
        for(int j=getCurrentKeys()+1;j>=i+1;j=j-1){
            setSon(getSons()[j],j+1);
        }
        setSon(z,i+1);
        //update keys
        for(int j=getCurrentKeys(); j>=i; j=j-1){
            String newKey = getKeys()[j];
            setKeyInIndexI(newKey,j+1);
        }
        setKeyInIndexI(y.getKeys()[tParameter],i);
        setCurrentKeys(getCurrentKeys()+1);
        y.setCurrentKeys(tParameter-1);
    }

    //find key in node
    public int searchInNode (String key){
        int index =1;
        if (Comparator.compare(getKeys()[index],key)>0){
            return index;
        }
        while (index<getCurrentKeys()&&Comparator.compare(getKeys()[index+1],key)<=0){
            index=index+1;
        }
        return index;
    }

    //returns if a key exists in the sub tree of this node
    public boolean searchInTree (String key){

        if (isLeaf()){
            int index = searchInNode(key);
            boolean output= keys[index].equals(key);
            return output;
        }
        else{
            //if exists in the node
            int index = searchInNode(key);
            if (keys[index].equals(key)){
                return true;
            }
            else {
                //search in the relevant son
                if (Comparator.compare(key,getKeys()[index])<0) {
                    BTreeNode son = getSons()[index];
                    return son.searchInTree(key);
                }
                else {
                    BTreeNode son = getSons()[index+1];
                    return son.searchInTree(key);
                }
            }
        }
    }

    //remove a key from a leaf
    public boolean removeKeyFromLeaf (int fromIndex) {
        for (int i=fromIndex;i<currentKeys;i=i+1){
            setKeyInIndexI(getKeys()[i+1],i);
        }
        setCurrentKeys(getCurrentKeys()-1);
        return true;
    }

    //return the Predecessor from specific index
    public String getPredecessor(int index){

        BTreeNode newNode = getSons()[index+1];
        String predecossor = getKeys()[index];
        while (newNode!=null){
            predecossor =newNode.getKeys()[1];
            newNode = newNode.getSons()[1];
        }
        return predecossor;
    }

    //return the Successor from specific index
    public String getSuccessor (int index){
        BTreeNode newNode = getSons()[index];
        String successor = getKeys()[index];
        while (newNode!=null){
            successor =newNode.getKeys()[newNode.getCurrentKeys()+1];
            newNode = newNode.getSons()[newNode.getCurrentKeys()+2];
        }
        return successor;
    }

    //merge two sons with the key between them
    public BTreeNode mergeTwoSons (String key, BTreeNode secondSon){
        BTreeNode newNode = new BTreeNode(tParameter);
        newNode.setIsLeaf(isLeaf());
        //copy all the relevant keys
        for (int i=1;i<=tParameter-1;i=i+1){
            newNode.setKeyInIndexI(getKeys()[i],i);
            newNode.setKeyInIndexI(secondSon.getKeys()[i],i+tParameter);
        }
        if(!isLeaf()){
            //copy all the relevant sons
            for (int i=1;i<=tParameter;i=i+1){
                newNode.setSon(getSons()[i],i);
                newNode.setSon(secondSon.getSons()[i],i+tParameter);
            }
        }
        newNode.setKeyInIndexI(key,tParameter);
        newNode.setCurrentKeys((2*tParameter)-1);
        return newNode;
    }


    //all the functions down here relevant just for deletion:

    //change the key with Predecessor and delete key recursivly
    public boolean changeKeyWithRSonAndDelete (int index){
        setKeyInIndexI(getPredecessor(index),index);
        return getSons()[index+1].deleteKeysFromTree(getPredecessor(index));
    }
    //change the key with Successor and delete key recursivly
    public boolean changeKeyWithLSonAndDelete (int index){
        setKeyInIndexI(getSuccessor(index),index);
        return getSons()[index].deleteKeysFromTree(getSuccessor(index));
    }
    //merge two relevant sons and delete the key recursivly
    public boolean deleteAfterMergingSons (int index,String key){
        BTreeNode newSon = getSons()[index].mergeTwoSons(getKeys()[index], getSons()[index + 1]);
        for (int i = index; i < getCurrentKeys(); i = i + 1) {
            setKeyInIndexI(getKeys()[i + 1], i);
        }
        for (int i = index; i <= getCurrentKeys(); i = i + 1) {
            setSon(getSons()[i+1], i);
        }
        setCurrentKeys(getCurrentKeys() - 1);
        setSon(newSon, index);
        return newSon.deleteKeysFromTree(key);
    }
    //rotate counterclockwise
    public boolean borrowFromRBrother(int index, String key, BTreeNode relevantNode){
        String temp = getSons()[index+2].getKeys()[1];
        relevantNode.setKeyInIndexI(getKeys()[index+1],relevantNode.getCurrentKeys()+1);
        relevantNode.setCurrentKeys(relevantNode.getCurrentKeys()+1);
        setKeyInIndexI(temp,index+1);
        for (int i=1;i<getSons()[index+2].currentKeys;i=i+1){
            getSons()[index+2].setKeyInIndexI(getSons()[index+2].getKeys()[i+1],i);
        }
        getSons()[index+2].setCurrentKeys(getSons()[index+2].getCurrentKeys()-1);
        return relevantNode.deleteKeysFromTree(key);
    }
    //rotate clockwise
    public boolean borrowFromLBrother(int index, String key, BTreeNode relevantNode){
        String temp = getSons()[index].getKeys()[getSons()[index].getCurrentKeys()];
        for (int i=relevantNode.getCurrentKeys();i>=1;i=i-1){
            relevantNode.setKeyInIndexI(relevantNode.getKeys()[i],i+1);
        }
        relevantNode.setKeyInIndexI(getKeys()[index],1);
        relevantNode.setCurrentKeys(relevantNode.getCurrentKeys()+1);
        setKeyInIndexI(temp,index);
        getSons()[index].setCurrentKeys(getSons()[index].getCurrentKeys()-1);
        return relevantNode.deleteKeysFromTree(key);
    }
    //merge the relevant son with his right brother and delete recursivly
    public boolean mergeWithRBrotherAndDelete (int index, String key, BTreeNode relevantNode){
        BTreeNode newSon = relevantNode.mergeTwoSons(getKeys()[index+1],getSons()[index+2]);
        for (int i=index+1;i<getCurrentKeys();i=i+1){
            setKeyInIndexI(getKeys()[i+1],i);
        }
        for (int i=index+1;i<=getCurrentKeys();i=i+1){
            setSon(getSons()[i+1],i);
        }
        setSon(newSon,index+1);
        setCurrentKeys(getCurrentKeys()-1);
        return newSon.deleteKeysFromTree(key);
    }
    //merge the relevant son with his left brother and delete recursivly
    public boolean mergeWithLBrotherAndDelete (int index, String key, BTreeNode relevantNode){
        BTreeNode newSon = getSons()[index].mergeTwoSons(getKeys()[index],relevantNode);
        for (int i=index;i<getCurrentKeys();i=i+1){
            setKeyInIndexI(getKeys()[i+1],i);
        }
        for (int i=index;i<=getCurrentKeys();i=i+1){
            setSon(getSons()[i+1],i);
        }
        setSon(newSon,index);
        setCurrentKeys(getCurrentKeys()-1);
        return newSon.deleteKeysFromTree(key);
    }
    //treat case when not exist in node and the key in node is smaller than key
    public boolean indexInNodeSmallerThanKey (int index, String key){
        BTreeNode relevantNode = getSons()[index+1];
        if (relevantNode.getCurrentKeys()<tParameter){
            //if it can borrow from one of its close brothers
            if(getSons()[index+2]!=null&&getSons()[index+2].getCurrentKeys()>=tParameter){
                return borrowFromRBrother(index,key,relevantNode);
            }
            else if(getSons()[index]!=null&&getSons()[index].getCurrentKeys()>=tParameter){
                return borrowFromLBrother(index,key,relevantNode);
            }
            //if it cant borrow from any close brother
            else{
                if (getSons()[index+2]!=null){
                    return mergeWithRBrotherAndDelete(index,key,relevantNode);
                }
                if (getSons()[index]!=null){
                    return mergeWithLBrotherAndDelete(index,key,relevantNode);
                }
            }
        }
        //enough keys in node
        else {
            return relevantNode.deleteKeysFromTree(key);
        }
        return false;
    }
    //treat case when not exist in node and the key in node is bigger than key
    public boolean indexInNodeBiggerThanKey (int index, String key){
        BTreeNode relevantNode = getSons()[index];
        if (relevantNode.getCurrentKeys()<tParameter){
            //if it can borrow from one of its close brothers
            if(getSons()[index+1]!=null&&getSons()[index+1].getCurrentKeys()>=tParameter){
                return borrowFromRBrother(index-1,key,relevantNode);
            }
            else if(getSons()[index-1]!=null&&getSons()[index-1].getCurrentKeys()>=tParameter){
                return borrowFromLBrother(index-1,key,relevantNode);
            }
            //if it cant borrow from any close brother
            else {
                if (getSons()[index+1]!=null){
                    return mergeWithRBrotherAndDelete(index-1,key,relevantNode);
                }
                if(getSons()[index-1]!=null){
                    return mergeWithLBrotherAndDelete(index-1,key,relevantNode);
                }
            }
        }
        //enough keys in node
        else {
            return relevantNode.deleteKeysFromTree(key);
        }
        return false;
    }
    //treat case when the key doesnt exist in node but do exists in the tree
    public boolean deleteKeyNotExistsInNode(int index, String key){

        //if the key in the node smaller than the key to delete
        if (Comparator.compare(key,getKeys()[index])>0){
            return indexInNodeSmallerThanKey(index,key);
        }
        //if the key in the node bigger than the key to delete
        if (Comparator.compare(key,getKeys()[index])<0){
            return indexInNodeBiggerThanKey(index,key);
        }
        return false;
    }
}

