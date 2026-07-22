<h2><a href="https://leetcode.com/problems/valid-anagram">242. Valid Anagram</a></h2>

<p>Given two strings <code>s</code> and <code>t</code>, return <code>true</code> if <code>t</code> is an <span data-keyword="anagram" class=" cursor-pointer relative text-dark-blue-s text-sm"><button type="button" aria-haspopup="dialog" aria-expanded="false" aria-controls="radix-_r_1l_" data-state="closed" class="">anagram</button></span> of <code>s</code>, and <code>false</code> otherwise.</p>

<p>&nbsp;</p>
<p><strong class="example">Example 1:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "anagram", t = "nagaram"</span></p>

<p><strong>Output:</strong> <span class="example-io">true</span></p>
</div>

<p><strong class="example">Example 2:</strong></p>

<div class="example-block">
<p><strong>Input:</strong> <span class="example-io">s = "rat", t = "car"</span></p>

<p><strong>Output:</strong> <span class="example-io">false</span></p>
</div>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= s.length, t.length &lt;= 5 * 10<sup>4</sup></code></li>
	<li><code>s</code> and <code>t</code> consist of lowercase English letters.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Follow up:</strong> What if the inputs contain Unicode characters? How would you adapt your solution to such a case?</p>


---

# 🛍️ Valid-Anagram | Explained

## Approach 1: Sorting and Index Comparison
### Intuition
An anagram is a word formed by rearranging the letters of another word, using all the original letters exactly once. A real-world analogy is sorting two decks of cards alphabetically: if two decks contain the exact same count of every card, sorting both decks from A to Z will result in two identical sequences. If any position differs or the lengths don't match, they cannot be anagrams.

### Algorithm Visualized
```mermaid
flowchart TD
    A[Start: Strings s and t] --> B[Convert s and t to Char Arrays: arr, arr1]
    B --> C[Sort arr and arr1 in-place]
    C --> D{Are lengths equal?}
    D -- No --> E[Return false]
    D -- Yes --> F[Initialize flag = true]
    F --> G[Loop i from 0 to length - 1]
    G --> H{arr[i] != arr1[i]?}
    H -- Yes --> I[Set flag = false]
    H -- No --> J[Continue loop]
    I --> J
    J --> K{End of loop?}
    K -- No --> G
    K -- Yes --> L[Return flag]
```

### Approach
1. Convert input strings `s` and `t` into primitive character arrays `arr` and `arr1`.
2. Sort both character arrays in ascending ASCII order using dual-pivot Quicksort (`Arrays.sort`).
3. Check if the array lengths differ. If they do, early return `false`.
4. Initialize a boolean flag `flag` to `true`.
5. Iterate through the array indices from `0` to `arr.length - 1`.
6. Compare character by character: if `arr[i]` does not match `arr1[i]`, update `flag` to `false`.
7. Return `flag` after inspecting all characters.

### Detailed Code Analysis
- **Lines 3–4 (`char [] arr= s.toCharArray(); char [] arr1= t.toCharArray();`):** Extracts character primitives from Java String objects into heap-allocated arrays to allow in-place sorting operations.
- **Lines 6–7 (`Arrays.sort(arr); Arrays.sort(arr1);`):** Sorts both arrays in-place using Java's built-in `Arrays.sort()`, which uses Dual-Pivot Quicksort for primitive arrays (performing $O(N \log N)$ time operations).
- **Line 8 (`boolean flag= true;`):** Initializes a state tracking variable to record whether a mismatch is encountered.
- **Line 9 (`if(arr.length!=arr1.length) return false;`):** Length guard condition. Note: Executing this *after* sorting is functional, though performing this guard before sorting line 6–7 would save unnecessary $O(N \log N)$ sorting work if lengths differ.
- **Lines 10–12 (`for(int i=0;i<arr.length;i++){ if(arr[i]!=arr1[i]) flag = false; }`):** Sequentially compares elements. Note: Without a `break` statement upon finding `arr[i] != arr1[i]`, the loop continues evaluating remaining indices even after finding a mismatch.
- **Line 13 (`return flag;`):** Returns the final Boolean result.

### Code
```java
class Solution {
    public boolean isAnagram(String s, String t) {
        char [] arr= s.toCharArray();
        char [] arr1= t.toCharArray();

        Arrays.sort(arr);
        Arrays.sort(arr1);
        boolean flag= true;
        if(arr.length!=arr1.length) return false;
        for(int i=0;i<arr.length;i++){
            if(arr[i]!=arr1[i]) flag = false;
        }
        return flag;
    }
}
```

### Complexity
- **Time:** $\mathcal{O}(N \log N)$, where $N$ is the length of string `s` (or `t`). Converting strings to char arrays takes $\mathcal{O}(N)$ time, sorting takes $\mathcal{O}(N \log N)$ time, and linear comparison takes $\mathcal{O}(N)$ time.
- **Space:** $\mathcal{O}(N)$ auxiliary memory required to store the extracted character arrays `arr` and `arr1`.

---

## 🕵️‍♂️ Follow-up Questions (Optional)

1. **How can you optimize this solution to run in $\mathcal{O}(N)$ linear time and $\mathcal{O}(1)$ space?**
   - **Answer:** Instead of sorting, use a fixed-size frequency counter array of size 26 (for lowercase English letters). Increment counts for characters in `s` and decrement counts for characters in `t`. If all final counts are zero, the strings are valid anagrams.

2. **What if the inputs contain Unicode characters (beyond basic ASCII)?**
   - **Answer:** A fixed-size array of 26 integers won't suffice. You should use a dynamic Hash Table (`HashMap<Character, Integer>` in Java) to map unicode characters to their frequencies.