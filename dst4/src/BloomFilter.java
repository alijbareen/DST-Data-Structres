
public class BloomFilter {
    private boolean[] bloomArray;
    HashList hashList;

    public BloomFilter(String m1, String path) {
    	if(m1!=null) {
    		bloomArray = new boolean[Integer.parseInt(m1)];
    		hashList = new HashList();
    		FileReader reader = new FileReaderImplement(path);
    		while (reader.hasNextLine()) {
    			String line = reader.nextLine();
    			int a = Integer.parseInt(line.substring(0, line.indexOf('_')));
    			int b = Integer.parseInt(line.substring(line.indexOf('_') + 1));
    			FunctionParameters function = new FunctionParameters(a, b);
    			hashList.add(function);
    		}
        }
    	else {
    		throw new NullPointerException("illegal input");
    	}
    }

    //update the Bloom Filter's table with the bad passwords
    public void updateTable(String path) {
        FileReader reader = new FileReaderImplement(path);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            int hornerKey = (int) convertToHorner(line);
            HashListElement element = hashList.getFirst();
            while (element != null) {
                bloomArray[element.getData().DoFunction(hornerKey, bloomArray.length)] = true;
                element = element.getNext();
            }
        }
    }

    //convert string to number by Horner rule
    public long convertToHorner(String s) {
    	if(s!=null) {
    		long hornerKey = s.charAt(0);
    		long mod = 15486907;
    		for (int i = s.length() - 1; i > 0; i = i - 1) {
    			hornerKey = (hornerKey * 256 + s.charAt(s.length() - i)) % (mod);
    		}
        return hornerKey;
    	}
    	else {
    		throw new NullPointerException("illegal input");
    	}
    }

    //checks if key is in the filter bloom(all functions return true)
    public boolean isExist(int key) {
        boolean isExist = true;
        HashListElement element = hashList.getFirst();
        while (element != null & isExist) {
            if (bloomArray[element.getData().DoFunction(key, bloomArray.length)] == false)
                isExist = false;
            element = element.getNext();
        }
        return isExist;
    }

    //Find the percentage of false-positives
    public String getFalsePositivePercentage(HashTable hash, String path) {
        FileReader reader = new FileReaderImplement(path);
        int allPass = 0;
        int allPassInFilterBloom = 0;
        while (reader.hasNextLine()) {
            allPass = allPass +1;
            String line = reader.nextLine();
            int hornerKey =(int) convertToHorner(line);
            if (isExist(hornerKey) == true)
                allPassInFilterBloom = allPassInFilterBloom + 1;
        }
        return ""+ (double)(allPassInFilterBloom - hash.size())/(double)(allPass - hash.size());
        //wronglyDeclined = allPassInFilterBloom - hash.size();
        //allGoodPass = allPass - hash.size();
    }

    public String getRejectedPasswordsAmount(String path) {
        int rejected = 0;
        FileReader reader = new FileReaderImplement(path);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            int hornerKey = (int) convertToHorner(line);
            if (isExist(hornerKey) == true)
                rejected = rejected + 1;
        }
        return "" + rejected;
    }
}