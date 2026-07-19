1/**
2 * Definition for a binary tree node.
3 * public class TreeNode {
4 *     int val;
5 *     TreeNode left;
6 *     TreeNode right;
7 *     TreeNode() {}
8 *     TreeNode(int val) { this.val = val; }
9 *     TreeNode(int val, TreeNode left, TreeNode right) {
10 *         this.val = val;
11 *         this.left = left;
12 *         this.right = right;
13 *     }
14 * }
15 */
16class Solution {
17
18    public void inorder(TreeNode root, List <Integer> l){
19        // base case
20        if(root == null){
21            return ;
22        }
23
24        //recursive call
25        inorder(root.left, l);
26        l.add(root.val);
27        inorder(root.right, l);
28    }
29
30    public List<Integer> inorderTraversal(TreeNode root) {
31        List<Integer> l = new ArrayList <>();
32        inorder(root, l);
33        return l;
34    }
35}