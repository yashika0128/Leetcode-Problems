<h2><a href="https://leetcode.com/problems/first-unique-character-in-a-string">387. First Unique Character in a String</a></h2>

<p>Given a string <code>s</code>, find the <strong>first</strong> non-repeating character in it and return its index. If it <strong>does not</strong> exist, return <code>-1</code>.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "leetcode"</span></p>

<p><strong>Output:</strong> <span class="example-io">0</span></p>

<p><strong>Explanation:</strong></p>

<p>The character <code>'l'</code> at index 0 is the first character that does not occur at any other index.</p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "loveleetcode"</span></p>

<p><strong>Output:</strong> <span class="example-io">2</span></p>
</div>

<p><strong class="example">Example 3:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "aabb"</span></p>

<p><strong>Output:</strong> <span class="example-io">-1</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length &lt;= 10<sup>5</sup></code></li>
	<li><code>s</code> consists of only lowercase English letters.</li>
</ul>


---

# 🛍️ First-Unique-Character-in-a-String | Explained

## Approach 1: Nested Loops (Brute-Force)
### Intuition
The simplest way to solve this problem is to inspect every character in the string one by one and compare it against all other characters in the string. If we find a character that does not appear anywhere else, we can immediately return its index as the first unique character.

Think of this like standing in a queue of people. To find the first person wearing a unique-colored shirt, you start with the first person, then walk down the line to see if anyone else is wearing that same color. If you find a match, you go back to the front, select the second person, and scan the entire line again. You repeat this until you find someone with a truly unique shirt.

### Algorithm Visualized
```mermaid
flowchart TD
    Start([Start]) --> LoopI[Outer Loop: i = 0 to N-1]
    LoopI --> InitUnique[Set unique = true]
    InitUnique --> LoopJ[Inner Loop: j = 0 to N-1]
    
    LoopJ --> CheckSelf{i != j?}
    CheckSelf -- Yes --> CheckMatch{s[i] == s[j]?}
    CheckSelf -- No --> NextJ[Increment j]
    
    CheckMatch -- Yes --> SetFalse[Set unique = false]
    SetFalse --> BreakInner[Break Inner Loop]
    CheckMatch -- No --> NextJ
    
    NextJ --> LoopJEnd{j reached N?}
    LoopJEnd -- No --> LoopJ
    LoopJEnd -- Yes --> CheckUnique{unique == true?}
    
    BreakInner --> CheckUnique
    
    CheckUnique -- Yes --> ReturnI([Return Index i])
    CheckUnique -- No --> LoopIEnd{i reached N?}
    
    LoopIEnd -- No --> LoopI
    LoopIEnd -- Yes --> ReturnNeg1([Return -1])
```

### Approach
1. **Outer loop** iterates through each character of the string at index `i`.
2. For each character `s.charAt(i)`, initialize a boolean flag `unique = true`.
3. **Inner loop** iterates through every character of the string at index `j`.
4. If `i != j` (we are not comparing the character to itself) and `s.charAt(i) == s.charAt(j)`, we have found a duplicate. Set `unique = false` and break out of the inner loop early.
5. If the inner loop finishes and `unique` remains `true`, it means no duplicate was found. Return the current outer loop index `i`.
6. If both loops finish without returning, return `-1`.

### Detailed Code Analysis
- **Lines 3-4**: We capture the length of the string `n` and start our outer loop with index `i`.
- **Line 5**: `boolean unique = true;` reset this flag on every pass. This assumes the character at index `i` is unique until proven otherwise.
- **Lines 6-7**: The inner loop starts. We compare the element at `i` with the element at `j`. The condition `i != j` prevents self-comparison, which would otherwise falsely flag every character as a duplicate.
- **Lines 8-10**: If a duplicate is discovered, we change `unique` to `false` and execute `break`. This optimization stops unnecessary comparisons for the current character.
- **Lines 12-14**: If `unique` is still `true` after checking the entire string, we immediately return `i`. Because we traverse from left to right (`i = 0` to `n - 1`), this guarantees we return the *first* unique character.
- **Line 16**: If no unique character exists, we return `-1`.

### Code
```java
class Solution {
    public int firstUniqChar(String s) {
        int n = s.length();
        for(int i = 0; i < n; i++){
            boolean unique = true;
            for(int j = 0; j < n; j++){
                if(i != j && s.charAt(i) == s.charAt(j)){
                    unique = false;
                    break;
                }
            }
            if(unique == true){
                return i;    
            }
        }
        return -1;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N^2)$ where $N$ is the length of the string. In the worst-case scenario (e.g., all characters are identical or the unique character is at the very end), we perform $N \times N$ operations.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. No additional data structures are allocated; we only use a few primitive variables (`i`, `j`, `n`, and `unique`).

---

## Approach 2: HashMap Frequency Counter (Active Code)
### Intuition
To avoid checking every character against every other character, we can trade space for speed. Instead of re-scanning the string repeatedly, we can perform a single pass to count how often each character appears.

Imagine walking down the line of people once and writing down their shirt colors on a notepad, tallying how many times you see each color. Once you finish writing the tally, you walk down the line a second time. The moment you see someone wearing a color that has exactly "1" on your notepad, you stop and point them out.

### Algorithm Visualized
```
Input String: "leetcode"

Pass 1: Populate Map (Character -> Frequency)
l -> 1
e -> 3
t -> 1
c -> 1
o -> 1
d -> 1

Pass 2: Scan String Left-to-Right and Query Map
Index 0: 'l' -> Map.get('l') is 1. (Match! Return 0)
```

### Approach
1. **Pass 1 (Frequency Collection):** Initialize a `HashMap` to store characters as keys and their occurrences as values. Iterate through the string, building this frequency lookup table.
2. **Pass 2 (First Unique Search):** Iterate through the string a second time from left to right. For each character, query the `HashMap`. The first character that has a frequency of exactly `1` is our answer. Return its index.
3. If no character has a frequency of `1`, return `-1`.

### Detailed Code Analysis
- **Line 17**: `HashMap<Character, Integer> Map = new HashMap<>();` instantiates our frequency map. Note that `Map` is capitalized here, which deviates from standard Java camelCase naming conventions (`map`).
- **Lines 18-25**: This is our first loop.
  - **Line 20**: `Map.containsKey(ch) == true` checks if the character has already been encountered. (The explicit `== true` comparison is redundant but functionally correct).
  - **Line 21**: If the character exists, we fetch its current count with `Map.get(ch)`, increment it by `1`, and write it back.
  - **Line 23**: If it does not exist, we initialize its entry in the map with a count of `1`.
- **Lines 26-29**: This is our second loop.
  - We scan the string from left to right starting at index `0`.
  - **Line 28**: `if(Map.get(ch) == 1) return i;` checks the frequency of the current character. Because we process the indices sequentially from $0$ to $N-1$, the first character we encounter with a value of `1` is guaranteed to be the first unique character in the string.
- **Line 30**: Returns `-1` if we make it through the entire string without finding any unique characters.

### Code
```java
import java.util.HashMap;

class Solution {
    public int firstUniqChar(String s) {
        HashMap <Character,Integer> Map = new HashMap <>();
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if(Map.containsKey(ch) == true){
                Map.put(ch, Map.get(ch) + 1);
            } else {
                Map.put(ch, 1);
            }
        }
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if(Map.get(ch) == 1) return i;
        }
        return -1;
    }
}
```

### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the length of the string. We perform two sequential, non-nested passes over the string. HashMap operations (`put`, `get`, `containsKey`) have an average time complexity of $\mathcal{O}(1)$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space. While we are using a map, the number of unique keys is bound by the size of the alphabet. For lowercase English letters, the map will store at most 26 key-value pairs, which is constant space. Even with the extended ASCII or Unicode set, the maximum size of the map is bound by a constant limit.

---

## 🕵️‍♂️ Follow-up Questions

### 1. How would you optimize the performance of the Active Approach further?
While the `HashMap` approach is $\mathcal{O}(N)$, it incurs overhead due to **autoboxing** (converting `char` to `Character` and `int` to `Integer`) and hash calculation/collision handling. 

Since the problem constraints usually limit the input to lowercase English letters, we can replace the `HashMap` with a fixed-size integer array of size 26. This achieves a major speedup by utilizing direct array index mapping:

```java
class Solution {
    public int firstUniqChar(String s) {
        int[] freq = new int[26];
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            freq[s.charAt(i) - 'a']++;
        }
        
        for (int i = 0; i < n; i++) {
            if (freq[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
```
This optimization eliminates memory allocation overhead, garbage collection pressure, and object creation, running in near-native speed.

### 2. What if the input is a massive, real-time data stream and you cannot store the entire string?
If we are dealing with a data stream where we cannot read the string twice, we can use a **LinkedHashMap** or a combination of a **HashMap** and a **Doubly Linked List** (similar to an LRU cache architecture). 

We can track:
1. Characters that are currently unique (stored in a Doubly Linked List in their order of arrival).
2. All seen characters (stored in a Set/Map).

As symbols arrive:
- If a symbol has never been seen, we add it to our seen set and append it to the end of our Doubly Linked List.
- If it has already been seen, we remove it from our Doubly Linked List (since it is no longer unique).
- The first unique element will always be the element at the head of our Doubly Linked List, giving us $\mathcal{O}(1)$ retrieval time at any point during stream processing.