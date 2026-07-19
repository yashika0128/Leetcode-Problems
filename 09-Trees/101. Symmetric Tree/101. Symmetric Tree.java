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
17    public boolean isSymmetric(TreeNode root) {
18        return isMirror(root.left, root.right);
19    }
20
21    public boolean isMirror (TreeNode left, TreeNode right){
22        if(left== null && right== null) return true;
23
24        if(left == null || right == null) return false;
25
26        if(left.val != right.val)return false;
27
28        return isMirror(left.left, right.right) &&
29            isMirror(left.right, right.left);
30    }
31}