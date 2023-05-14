package com.company;

import java.util.HashMap;
import java.util.Map;

public class CopyNode {
    Map<Node,Node> cacheNode=new HashMap<>();

    public Node copyRandomList(Node head){
        if (head==null) return null;
        if (!cacheNode.containsKey(head)){
            Node headnew=new Node(head.val);
            cacheNode.put(head,headnew);
            headnew.next=copyRandomList(head.next);
            headnew.random=copyRandomList(head.random);
        }
        return cacheNode.get(head);
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
