### Description

Re-implement in Java the “Text Justification” problem of assignment 2 extending it to handle exceptional cases (when the
assumptions do not hold):

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
6. The words consist of only English letters and symbols `.,!?-:;()'"`. Punctuations are part of words

The final code should take into consideration the aforementioned assumptions using catch, except, and finally, and
output the appropriate error handling message.

#### Exception messages according to descending priority:

1. Exception, file not found!
2. Exception, file is empty!
3. Exception, input exceeds text max size!
4. Exception, intended line width is not specified!
5. Exception, line width cannot be negative or zero!
6. Exception, input contains an empty word!
7. Exception, input contains forbidden symbol `'x'`!
8. Exception, `'word_x'` exceeds the limit of 20 symbols!
9. Exception, `'word_x'` exceeds `maxWidth` symbols!

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

* * *

#### Example 3

input.txt

```
Assignment four is almost done
15
```

output.txt

```
Exception, input contains an empty word!
```

* * *

#### Example 4

input.txt

```
One of the longest words in English is flocci­nauci­nihili­pili­fication
30
```

output.txt

```
Exception, 'flocci­nauci­nihili­pili­fication' exceeds the limit of 20 symbols!
```
