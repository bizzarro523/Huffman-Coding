
public class linkedList {
	public treeNode head;

    public linkedList()
    {
        head = new treeNode("dummy", 0, "", null, null, null);
    }

    treeNode findSpot(treeNode head, treeNode newNode)
    {
        treeNode spot = this.head;
        while (spot.next != null && spot.next.prob < newNode.prob)
            spot = spot.next;
        return spot;
    }

    void insertNewNode(treeNode head, treeNode newNode)
    {
        treeNode spot = this.findSpot(head, newNode);
        newNode.next = spot.next;
        spot.next = newNode;
    }

    String printList(treeNode temp)
    {
        String output = "";
        temp = head;
        while (temp.next != null)
        {
            output = output + "(\"" + temp.chStr + "\"," + String.valueOf(temp.prob) + "," + temp.next.chStr + ")-.";
            temp = temp.next;
            if (temp.next == null)
                output = output + "(\"" + temp.chStr + "\"," + String.valueOf(temp.prob) + ",NULL" + ")-. NULL";
        }
        return output;
    }
} //end of LinkedList class


