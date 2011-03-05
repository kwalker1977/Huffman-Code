//Kathy Walker
//EECS 233
//Programming Assignment #2
//March 4, 2010
//Purpose: To encode a text file using the Huffman Algorithm
//Class: Main
//*************************************************************************************************

//This will create a Huffman Coder that will read in the text file "BraveNewWorld.txt", encode it
//then place it in the result folder as "Huffman_brave.txt"
public class main {
  public static void main(String[] args) {
    Huffman_Coder hCode = new Huffman_Coder("txt/BraveNewWorld.txt", "result/Huffman_brave.txt");
    try {
      
      //Once the coding is finished, it will create "HuffmanKey.txt" that contains the characters 
      //in the text file and the corresponding Huffman code. Then a message prints letting the 
      //user knows it's complete.
      hCode.dumpCoding("result/HuffmanKey.txt");
      System.out.print("Encoding complete\n");
      
      //Once the statics are calculated will place the results in "Statistics.txt" in the result folder
      //and print a message letting the user know that it's complete
      hCode.saveStats("result/Statistics.txt");
      System.out.print("Statistics complete\n");
      
      //Once everything is done, prints a message letting the user know it's ready
      System.out.print("Program completed\n");
    } catch(Exception e) {};
  }
}