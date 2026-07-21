1class Solution {
2    public int firstUniqChar(String s) {
3        // int n= s.length();
4        // for(int i=0; i<n; i++){
5        //     boolean unique= true;
6        //     for(int j=0; j<n;j++){
7        //         if(i!=j && s.charAt(i) ==s.charAt(j)){
8        //             unique= false;
9        //             break;
10        //         }
11        //     }
12        //     if(unique == true){
13        //         return i;    
14        //     }
15        // }
16        // return (-1);
17        HashMap <Character,Integer> Map= new HashMap <>();
18        for(int i=0; i<s.length(); i++){
19            char ch= s.charAt(i);
20            if(Map.containsKey(ch)==true){
21                Map.put(ch,Map.get(ch)+1);
22            }else{
23                Map.put(ch,1);
24            }
25        }
26        for(int i=0; i<s.length(); i++){
27            char ch= s.charAt(i);
28            if(Map.get(ch)==1)return i;
29        }
30        return -1;
31    }
32}