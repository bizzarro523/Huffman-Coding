import java.io.*;
import java.util.Scanner;
public class Project7_main {
	public static void main (String[] args) throws IOException {
		String nameInFile = args[0];
		Scanner inFile = new Scanner(new FileReader(nameInFile));
		String nameDebugFile = nameInFile + "_Debug";
		BufferedWriter debugFile = new BufferedWriter(new FileWriter(nameDebugFile));
		BinaryTree binTree = new BinaryTree();
		binTree.computeCharCounts(inFile, binTree.charCountAry);
		binTree.printCountAry(binTree.charCountAry, debugFile);
		debugFile.write("\nHuffman Linked List:\n");
		binTree.constructHuffmanLList(binTree.charCountAry, debugFile);
		debugFile.write("\n");
		binTree.constructHuffmanBinTree(binTree.head, debugFile);
		debugFile.write("\n");
		binTree.constructCharCode(binTree.Root, " ");
		debugFile.write("\n\nPre-Order Traversal:\n");
		binTree.preOrderTraversal(binTree.Root, debugFile);
		debugFile.write("\n\nIn-Order Traversal:\n");
		binTree.inOrderTraversal(binTree.Root, debugFile);
		debugFile.write("\n\nPost-Order Traversal:\n");
		binTree.postOrderTraversal(binTree.Root, debugFile);
		inFile.close();
		debugFile.close();
		binTree.userInterFace();
		}
}