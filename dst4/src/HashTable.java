

public class HashTable {

    private  LinkedList [] hashArr;
    private int size = 0;

    public HashTable(String m2){
    	if(m2!=null) {
    		hashArr = new LinkedList [Integer.parseInt(m2)];
    	}
    	else {
    		throw new NullPointerException("illegal input");
    	}
    }

    //this function insert bad passwords to the hash array
    public void updateTable(String path){
        FileReader reader = new FileReaderImplement(path);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            int hornerKey=(int) convertToHorner(line);
            insert(hornerKey);
        }
    }

    //converts string to number by Horner key
    public static long convertToHorner(String s) {
        long hornerKey = s.charAt(0);
        long mod = 15486907;
        for (int i = s.length() - 1; i > 0; i = i - 1) {
            hornerKey = (hornerKey * 256 + s.charAt(s.length() - i)) % (mod);
        }
        return hornerKey;
    }

    //define the hash function used
    public int hashFunction (int key){
        return key%hashArr.length;
    }

    //insert to HashTable by key
    public void insert (int key){
        int order = hashFunction(key);
        if (hashArr[order]==null){
            hashArr[order]= new LinkedList();
            hashArr[order].add(key);
        }
        else {
            hashArr[order].add(key);
        }
        size = size +1;
    }

    //size of HashTable (number of elements)
    public int size(){
        return  size;
    }

    //return if a key exists in the hash table
    public boolean isExists (int key){
        int order = hashFunction(key);
        if(hashArr[order]!=null) {
        return hashArr[order].contains(key);
        }
        else {
        return false;
        }
    }

    //Get the time required to perform a search of all requested words when using the hash table.
    public String getSearchTime (String path){
        FileReader reader = new FileReaderImplement(path);
        long startTime = System.nanoTime();
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            int hornerKey=(int) convertToHorner(line);
           isExists(hornerKey);
        }
        long endTime   = System.nanoTime();
        double totalTime = (double)endTime/1000000 - (double)startTime/1000000;
        String time = Double.toString(totalTime);
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
}
