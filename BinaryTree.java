import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class BinaryTree {
	public treeNode Root;
	public treeNode head;
	int[] charCountAry = new int[256];
	String[] charCode = new String[256];

	public BinaryTree()
	{
		Root = null;
		head = new treeNode("dummy", -99, "", null, null, null);
	}

	public treeNode findSpot(treeNode head, treeNode newNode)
	{
		treeNode spot = this.head;
		while (spot.next != null && spot.next.prob < newNode.prob)
			spot = spot.next;
		return spot;
	}

	public void insertNewNode(treeNode head, treeNode newNode)
	{
		treeNode spot = this.findSpot(head, newNode);
		newNode.next = spot.next;
		spot.next = newNode;
	}

	public String printList(treeNode temp)
	{
		String output = "";
		temp = head;
		while (temp.next != null)
		{
			output = output + "(\"" + temp.chStr + "\"," + Integer.toString(temp.prob) + "," + temp.next.chStr + ")-->";
			temp = temp.next;
			if (temp.next == null)
				output = output + "(\"" + temp.chStr + "\"," + Integer.toString(temp.prob) + ",NULL" + ")--> NULL";
		}
		return output + "\n";
	}
	
	public void constructHuffmanLList(int[] ary, BufferedWriter outFile) throws IOException
	{
		treeNode listHead = this.head;
		int index = 0;
		while(index < 256) {
			if(ary[index] > 0) {
				String chr;
				int prob;
				chr = Character.toString((char)index);
				prob = ary[index];
				treeNode new_Node = new treeNode(chr, prob, " ", null, null, null);
				this.insertNewNode(listHead, new_Node);
			}
			index++;
		}
		outFile.write(this.printList(listHead));
	}

	public void constructHuffmanBinTree(treeNode listhead, BufferedWriter outFile) throws IOException
	{
		listhead = this.head;
		while (listhead.next.next != null)
		{
			treeNode newNode = new treeNode(listhead.next.chStr + listhead.next.next.chStr, listhead.next.prob + listhead.next.next.prob, " ", listhead.next, listhead.next.next, null);
			this.insertNewNode(this.head, newNode);
			listhead.next = listhead.next.next.next;
		}
		outFile.write(this.printList(listhead));
		Root = listhead.next;
	}

	public boolean isLeaf(treeNode T)
	{
		if (T.left == null && T.right == null)
			return true;
		else
			return false;
	}

	public void constructCharCode(treeNode T, String code) throws IOException
	{
		if (isLeaf(T))
		{
			T.code = code;
			int index = (int)(T.chStr.charAt(0));
			charCode[index] = T.code;

		}
		else
		{
			constructCharCode(T.left, code + "0");
			constructCharCode(T.right, code + "1");
		}
	}

	public void computeCharCounts(Scanner inFile, int[] ary) { 
		while(inFile.hasNext()) { 
			char charIn;
			String str = inFile.next();
			for(int i = 0; i < str.length(); i++) {
				charIn = str.charAt(i);
				int index = (int)charIn;
				charCountAry[index]++;
			}
		}
	}
	public void printCountAry(int[] arryName, BufferedWriter debugFile) throws IOException {
		for(int i = 0; i < arryName.length; i++) {
			if (arryName[i] != 0)
				debugFile.write((char)i + " " + arryName[i] + "\n");
		}
	}
	
	public void preOrderTraversal(treeNode t, BufferedWriter outFile) throws IOException
	{
		if (isLeaf(t))
			outFile.write("{" + t.chStr + "," + t.prob + "}");
		else {
			outFile.write("(" + t.chStr + "," + t.prob + "," + t.left.chStr + "," + t.right.chStr + ")->");
			preOrderTraversal(t.left, outFile);
			preOrderTraversal(t.right, outFile);
		}
	}
	public void inOrderTraversal(treeNode t, BufferedWriter outFile)
	{
		if (t == null)
			return;
		inOrderTraversal(t.left, outFile);
		try {
			outFile.write("{" + t.chStr + "," + t.prob + "}->");
		} catch (IOException e) {
			e.printStackTrace();
		}
		inOrderTraversal(t.right, outFile);
	}
	public void postOrderTraversal(treeNode t, BufferedWriter outFile)
	{
		if (t == null)
			return;
		postOrderTraversal(t.left, outFile);
		postOrderTraversal(t.right, outFile);
		try {
			outFile.write("{" + t.chStr + "," + t.prob + "}->");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void userInterFace() throws IOException {
		String nameOrg = " ";
		String nameCompress, nameDeCompress;
		char yesNo = ' ';
		while(yesNo != 'N') {
			yesNo = JOptionPane.showInputDialog(null, "Do you want to encode a file?").charAt(0);
			if (yesNo == 'N') 
				System.exit(0);
			else if (yesNo == 'Y') {
				nameOrg = JOptionPane.showInputDialog(null, "What is the name of the file you want compressed?");
				nameCompress = nameOrg + "_Compressed";
				nameDeCompress = nameOrg + "_DeCompress";
				Scanner orgFile = new Scanner(new FileReader(nameOrg));
				BufferedWriter compFile = new BufferedWriter(new FileWriter(nameCompress));
				BufferedWriter deCompFile = new BufferedWriter(new FileWriter(nameDeCompress));
				Encode(orgFile, compFile);
				compFile.close();
				Scanner compFile1 = new Scanner(new FileReader(nameCompress));
				Decode(compFile1, deCompFile);
				orgFile.close();
				compFile1.close();
				deCompFile.close();
			}
		}
	}
	public void Encode(Scanner inFile, BufferedWriter outFile) throws IOException {
		while(inFile.hasNext()) {
			char charIn;
			String str = inFile.next();
			for(int i = 0; i < str.length(); i++) {
				charIn = str.charAt(i);
				int index = (int)charIn;
				String code = charCode[index];
				outFile.write(code);
			}
		}
	}
	public void Decode(Scanner inFile, BufferedWriter outFile) throws IOException{
		treeNode spot = this.Root;
		while(inFile.hasNext()) {
			if (isLeaf(spot)) {
				outFile.write(spot.chStr);
				spot = this.Root;
			} 
			char oneBit;
			String nxtBitStr = inFile.next();
			for(int i = 0 ; i < nxtBitStr.length(); i++) {
				oneBit = nxtBitStr.charAt(i);
				if (oneBit == '0')
					spot = spot.left;
				else if (oneBit == '1')
					spot = spot.right;
				else {
					outFile.write("Error! The compress file contains invalid character!");
					System.exit(0);
				}
			}
		}
		if(!inFile.hasNext() && !isLeaf(spot))
			outFile.write("Error: The compress file is corrupted!");		
	}
} //end of BinaryTree class