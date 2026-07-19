1class Solution {
2    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
3        Stack <Integer> st = new Stack <>();
4        HashMap <Integer, Integer> map = new HashMap<>();
5
6        for(int num: nums2){
7            while(!st.isEmpty() && num>st.peek()){
8                map.put(st.pop(),num);
9            }
10            st.push(num);
11        }
12
13        while(!st.isEmpty()){
14            map.put(st.pop(),-1);
15        }
16
17        int [] ans = new int[nums1.length];
18
19        for(int i=0; i<ans.length; i++){
20            ans[i] = map.get(nums1[i]);
21        }
22
23        return ans;
24    }
25}