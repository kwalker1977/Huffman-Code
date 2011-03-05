//Kathy Walker
//EECS 233
//Programming Assignment #2
//March 4, 2010
//Purpose: To encode a text file using the Huffman Algorithm
//Class: HuffNode
//*************************************************************************************************

//Define and intialize the Huffman nodes
public class  HuffNode {
  char character;
  int frequency;
  static int length;
  String path;
  
  HuffNode next, last, left, right;
  
  HuffNode() {
    character = 0;
    frequency = 0;
    path = "";
  }
  
  HuffNode(int value) {
    character = (char) value;
    frequency = 0;
    path = "";
  }
  
  HuffNode(HuffNode previous){
    frequency = 0;
    last = previous;
    path = "";
  }
}