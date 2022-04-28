### Description

Given a file `input.txt` containing a text (sequence of words separated by a space) in the first line and a positive
integer `maxWidth` in the second, format the text splitting it into lines such that each line has exactly `maxWidth`
characters and is fully (**left and right**) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra
spaces `' '` when necessary so that each line has exactly `maxWidth` characters.

Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line does not
divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right. If
the line can accommodate only one word, it should be **left-justified**. For the last line of text, it should be **
left-justified** and no extra space is inserted between words.

#### Assumtions:

1. A word is defined as a character sequence consisting of non-space characters only.
2. The text contains at least one word
3. The text is no longer than 300 symbols in total.
4. Each word has at least one symbol.
5. Each word is no longer than 20 symbols and do not exceed `maxWidth`.
6. The words consist of only English letters and symbols. Punctuations are part of words

* * *

#### Example 1

input.txt

```
This is an example of text justification.
16
```

output.txt

```
This    is    an
example  of text
justification.
```

* * *

#### Example 2

input.txt

```
What must be acknowledgment shall be.
16
```

output.txt

```
What   must   be
acknowledgment  
shall be.
```

**Note:**

* the second line is left-justified because it contains only one word.
* the last line is "shall be." instead of "shall be.", because the last line must be left-justified instead of
  fully-justified.
