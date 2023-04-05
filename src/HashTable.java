import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HashTable<T>{
    NGen<T>[] hashTable;

    //TODO: Create a constructor that takes in a length and initializes the hash table array
    public HashTable(int length) {
        hashTable = new NGen[length];
    }
    //TODO: Implement a simple hash function
    public int hash1(T item) { //slowest
        String s = item.toString();
        int x = s.charAt(0);
        return x % hashTable.length;
    }

    //TODO: Implement a second (different and improved) hash function
    public int hash2(T item) { //fastest
        String s = item.toString();
        int x = s.charAt(0) + s.charAt(s.length() - 1) + s.length(); //More unique numbers
        return x % hashTable.length;
    }

    //TODO: Implement the add method which adds an item to the hash table using your best performing hash function
    // Does NOT add duplicate items
    public void add(T item) {
        int x = hash2(item);
        NGen<T> temp = hashTable[x];
//        System.out.println(x);
        NGen<T> node = new NGen<T>(item, null);
        if (hashTable[x] == null) {
            hashTable[x] = node;
        }
        else {
            while (temp.getNext() != null) {
//                System.out.println(hashTable[x]);
                if (temp.getData() == item) {
                    return;
                }
                temp = temp.getNext();
            }
            temp.setNext(node);
        }
    }

    // ** Already implemented -- no need to change **
    // Adds all words from a given file to the hash table using the add(T item) method above
    @SuppressWarnings("unchecked")
    public void addWordsFromFile(String fileName) {
        Scanner fileScanner = null;
        String word;
        try {
            fileScanner = new Scanner(new File(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("File: " + fileName + " not found.");
            System.exit(1);
        }
        while (fileScanner.hasNext()) {
            word = fileScanner.next();
            word = word.replaceAll("\\p{Punct}", ""); // removes punctuation
            this.add((T) word);
        }
    }

    //TODO: Implement the display method which prints the indices of the hash table and the number of words "hashed"
    // to each index. Also prints:
    // - total number of unique words
    // - number of empty indices
    // - number of nonempty indices
    // - average collision length
    // - length of longest chain
    public void display() {
        int unique = 0;
        int empty = 0;
        int nonempty = 0;
        int longest = 0;
        for (int i = 0; i < hashTable.length; i++) { //prints indices of hash table and number of words hashed to each index
            int idx = 0; //for printing indices
            int temp = 0; //for getting the longest chain
            if (hashTable[i] == null) {
                empty++;
            }
            else {
                while (hashTable[i] != null) {
                    unique++;
                    idx++;
                    hashTable[i] = hashTable[i].getNext();
                    temp++;
                }
                nonempty++;
                if (longest < temp) {
                    longest = temp;
                }
            }
            System.out.println(i + ": " + idx);
        }
        System.out.println("unique words: " + unique);
        System.out.println("empty indices: " + empty);
        System.out.println("nonempty indices: " + nonempty);
        System.out.println("average collision length: " + ((float)unique/nonempty));
        System.out.println("longest chain: " + longest);
    }

    // TODO: Create a hash table, store all words from "canterbury.txt", and display the table
    //  Create another hash table, store all words from "keywords.txt", and display the table
    public static void main(String args[]) {
        HashTable Table1 = new HashTable(150);
        HashTable Table2 = new HashTable(50);
//        Table1.addWordsFromFile("/Users/ethanyang/Desktop/Csci1933/Project 5/src/canterbury.txt");
//        Table1.display();
        Table2.addWordsFromFile("/Users/ethanyang/Desktop/Csci1933/Project 5/src/keywords.txt");
        Table2.display();

    }
}
