import java.io.BufferedWriter;
import java.io.IOException;

public class treeNode {
	public String chStr;
    public int prob;
    public String code;
    public treeNode left;
    public treeNode right;
    public treeNode next;
    
    public treeNode(String chr, int data, String cde, treeNode lft, treeNode rght, treeNode nxt)
    {
        chStr = chr;
        prob = data;
        code = cde;
        left = lft;
        right = rght;
        next = nxt;
    }

    public void printNode(treeNode t) throws IOException
    {
        System.out.println("(" + t.chStr + "," + Integer.toString(t.prob) + "," + t.next.chStr + "," + t.left.chStr + "," + t.right.chStr + ")");
    }

} //end treeNode class

