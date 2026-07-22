1class Solution {
2    public boolean isAnagram(String s, String t) {
3        char [] arr= s.toCharArray();
4        char [] arr1= t.toCharArray();
5
6        Arrays.sort(arr);
7        Arrays.sort(arr1);
8        boolean flag= true;
9        if(arr.length!=arr1.length) return false;
10        for(int i=0;i<arr.length;i++){
11            if(arr[i]!=arr1[i]) flag = false;
12        }
13        return flag;
14    }
15}