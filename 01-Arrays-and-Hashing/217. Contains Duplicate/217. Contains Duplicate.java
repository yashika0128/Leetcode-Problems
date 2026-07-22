1class Solution {
2    public boolean containsDuplicate(int[] nums) {
3        Set<Integer>s= new HashSet<>();
4        for(int i=0; i<nums.length; i++){
5            s.add(nums[i]);
6        }
7        return s.size() != nums.length;
8    }
9}