1/**
2 * Definition for singly-linked list.
3 * public class ListNode {
4 *     int val;
5 *     ListNode next;
6 *     ListNode() {}
7 *     ListNode(int val) { this.val = val; }
8 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
9 * }
10 */
11class Solution {
12    public ListNode reverseList(ListNode head) {
13        
14        // ListNode prev = null;
15        // ListNode curr = head;
16
17        // while(curr != null){
18        //     ListNode next = curr.next;
19
20        //     curr.next = prev;
21        //     prev = curr;
22        //     curr = next;
23        // }
24        // return prev;
25
26        if(head == null || head.next == null){
27            return head;
28        }
29
30        ListNode newHead = reverseList(head.next);
31
32        head.next.next = head;
33        head.next= null;
34
35        return newHead;
36    }
37}