//Kathy Walker
//EECS 233
//Programming Assignment #2
//March 4, 2010
//Purpose: To encode a text file using the Huffman Algorithm
//Class: Huffman_Coder
//*************************************************************************************************

import java.io.*;
import java.util.*;

public class Huffman_Coder {
  Hashtable coding;
  int charCoded, bitsUsed;
  
  Huffman_Coder (String input_file, String output_file) {
    try {
      
      //This will parse the file and stores it into a list; it uses helper methods that count the frequency of apperence of each
      //character in the order that it appears and then put it into a Huffman tree. Then create a hashtable
      //to store the encoding
      HuffNode charFrequency = parseText(input_file);
      charFrequency = tree(charFrequency);
      compute(charFrequency, "");
      coding = new Hashtable();
      store(charFrequency, coding);
      reparseText(input_file, output_file, coding);
    } 
    catch(Exception e) {
    }
  }
  
  //This is the method that takes the characters, puts them into the tree, calculates the occurences of each
  //letter and assigns it a node
  private static HuffNode parseText (String fileName) throws Exception {
    HuffNode nodeRoot = new HuffNode();
    HuffNode.length = 0;
    HuffNode current = nodeRoot;
    int [] checkList = new int[128];
    for (int i = 0; i < checkList.length; i++)
      checkList[i] = 0; 
  FileReader file = new FileReader(fileName);
  char tempChar = (char) file.read();
  while (tempChar != -1 && tempChar != 65535){
    if (tempChar < 128){
    if (checkList[tempChar] == 0){
      current = nodeRoot;
      while (current.next != null)
        current = current.next;
      current.character = tempChar;
      current.next = new HuffNode(current);
      HuffNode.length++;
      checkList[tempChar] = 1;
    }
    else {
      current = nodeRoot;
      while (current.next != null){
        if (current.character == tempChar)
          break;
        current = current.next;
      }
    }
    current.frequency++;
  }
  tempChar = (char) file.read();
  }
  return nodeRoot;
}

  //create the tree
private static HuffNode tree (HuffNode nodeRoot) {
  HuffNode move = nodeRoot;
  while (HuffNode.length >= 1) {
    move = combineSmall (move);
    HuffNode.length--;
  }
  return move;
}

private static HuffNode combineSmall(HuffNode nodeRoot){
  HuffNode append = new HuffNode(0);
  HuffNode rarest = nodeRoot;
  HuffNode secondRarest = nodeRoot.next;
  HuffNode current = nodeRoot;
  
  while (current.next != null) {
    if (current.frequency <= rarest.frequency && current != rarest && current != secondRarest){
      secondRarest = rarest;
      rarest = current;
    }
    else if (current.frequency <= secondRarest.frequency && current != rarest && current != secondRarest){
      secondRarest = current;
    }
    current = current.next;
  }
  
  append.left = rarest;
  append.right = secondRarest;
  append.frequency = rarest.frequency + secondRarest.frequency;
  append.last = current.last.next;
  current.next = append;
  
  if(rarest.last != null){
    rarest.last.next = rarest.next;
  }
  else {
    nodeRoot = nodeRoot.next;
  }
  if (rarest.next != null)
    rarest.next.last = rarest.last;
  if (secondRarest.last != null) {
    secondRarest.last.next = secondRarest.next;
  }
  else {
    nodeRoot = nodeRoot.next;
  }
  if (secondRarest.next != null)
    secondRarest.next.last = secondRarest.last;
  rarest.last = null;
  rarest.next = null;
  secondRarest.last = null;
  secondRarest.next = null;
  
  return nodeRoot;
}

//grow the tree
private static void compute (HuffNode nodeRoot, String path) {
  nodeRoot.path = path;
  if (nodeRoot.left != null)
    compute(nodeRoot.left, path + "0");
  if (nodeRoot.right != null)
    compute(nodeRoot.right, path + "1");
}

private static void store (HuffNode nodeRoot, Hashtable coding){
  if (nodeRoot.character != 0){
    coding.put(nodeRoot.character, nodeRoot.path);
  }
  if (nodeRoot.left != null)
    store(nodeRoot.left, coding);
  if (nodeRoot.right != null)
    store(nodeRoot.right, coding);
}
//create the new Huffman-encoded text file
 private void reparseText(String inputFile, String outputFile, Hashtable coding) throws Exception {
        File newFile = new File(outputFile);
        if (newFile.exists()) {
          System.out.print("Existing encoded file found, replaced with new file\n");
            newFile.delete();
        }
        if (newFile.createNewFile()) {
            FileReader inputFileReader = new FileReader(inputFile);
            FileWriter outputFileWriter = new FileWriter(newFile);
            char tempChar = (char) inputFileReader.read();
            while(tempChar != -1 && tempChar != 65535) {
                if (tempChar < 128) { 
                    String coded = (String) coding.get(tempChar);
                    outputFileWriter.write(coded);
                    bitsUsed += coded.length();
                    charCoded++;
                }
                tempChar = (char) inputFileReader.read();
            }
            outputFileWriter.close();
        }
    }

 //create the file that has the character used and its corresponding code
  public void dumpCoding(String file) throws Exception {
        File newFile = new File(file);
        if (newFile.exists())
          System.out.print("Existing coding file found, replaced with new file\n");
            newFile.delete();
        newFile.createNewFile();
        FileWriter outputFileWriter = new FileWriter(newFile);
        for (int i=0; i<128; i++) {
          if (coding.get((char)i) != null)
            outputFileWriter.write((String)"Character: " + (char)i + " Code: " + (String) coding.get((char)i) + "\r\n");
        }
        outputFileWriter.close();
    }

  //calculates the number of characters coded, how many bits they used, and then the percentage of space the compression saved 
  //and pouts it into a stats file
 public void saveStats(String file) throws Exception {
        File newFile = new File(file);
        if (newFile.exists()){
          System.out.print("Existing stats file found, replaced with new file\n");
            newFile.delete();
        }
        newFile.createNewFile();
        FileWriter outputFileWriter = new FileWriter(newFile);
        outputFileWriter.write("Number of characters coded: " + this.charCoded + "\r\n");
        outputFileWriter.write("Number of bits used: " + this.bitsUsed + "\r\n"); 
        outputFileWriter.write("Space reduction percentage: " + Math.round(100-(double)this.bitsUsed/this.charCoded/8*100) + "%");
        outputFileWriter.close();
    }
}
