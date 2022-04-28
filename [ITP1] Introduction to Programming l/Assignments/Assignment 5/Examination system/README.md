### Description

Implement a small system for importing, reordering and printing (outputting preprint version) examination questions. The system should support 4 types of questions:

1.  Multiple choice - Allows selection of single or multiple responses from a pre-defined list.
2.  True/False - A simple form of multiple choice question with just the two choices 'True' and 'False'.
3.  Short text answer - Allows a response of one or a few words that is graded by comparing against various model answers, which may contain wildcards.
4.  Essay - Allows a response of online text. This must then be graded manually.

Given a file `input.xml` containing questions of different type in XML format the program should reorder questions based on difficulty and write into `output.txt` camera-ready version of the exam followed by version with correct answers.  
Partial implementation (`Main.java` and `XMLParser.java`) is being provided. The code intentionally misses comments, your job is to understand what these parts do, understand missing parts and complete the program. You are not allowed to modify provided files.

#### Note:

1.  Your solution should be modular and extendible

#### Structure of input file:

*   question type="multichoice"

*   difficulty
*   questiontext
*   single
*   options

*   answer

*   solution

*   question type="truefalse"

*   difficulty
*   questiontext
*   answer

*   question type="short"

*   difficulty
*   questiontext
*   answers

*   question type="essay"

*   difficulty
*   questiontext
*   answer

#### Assumptions:

*   There are at most 50 questions overall in the input file
*   Difficulty of a question is a number between 1 and 100

* * *

### Example 1

input.txt
```
<questions>
<question type="short">
<difficulty>3</difficulty>
<questiontext>What is the main function of the computer?</questiontext>
<answers>compute,execute</answers>
</question>
<question type="multichoice">
<difficulty>2</difficulty>
<questiontext>What is the main function of the computer?</questiontext>
<single>true</single>
<options>
<answer>Compute Pi with required precision</answer>
<answer>Execute computer games</answer>
<answer>Execute a set of machine instructions</answer>
<answer>Control the traffic light system</answer>
</options>
<solution>3</solution>
</question>
<question type="multichoice">
<difficulty>2</difficulty>
<questiontext>A reference type U conforms to a reference type T if either:</questiontext>
<single>false</single>
<options>
<answer>They have no generic parameters, and U is a descendant of T;</answer>
<answer>U has generic parameter(s), and T is a descendant of U;</answer>
<answer>They are both generic derivations with the same number of actual generic parameters</answer>
<answer>They are both generic derivations where the number of actual generic parameters does not matter</answer>
</options>
<solution>1,3</solution>
</question>
<question type="truefalse">
<difficulty>1</difficulty>
<questiontext>There is no difference between concurrency and parallelism, they are just synonyms.</questiontext>
<answer>false</answer>
</question>
<question type="truefalse">
<difficulty>1</difficulty>
<questiontext>It is possible to start a thread twice.</questiontext>
<answer>false</answer>
</question>
<question type="essay">
<difficulty>5</difficulty>
<questiontext>Explain how JVM works:</questiontext>
<answer>It works will</answer>
</question>
</questions>
```

output.xml
```
==============Exam==============
1) There is no difference between concurrency and parallelism, they are just synonyms.
   Answer: true false (circle the right answer)

2) It is possible to start a thread twice.
   Answer: true false (circle the right answer)

3) What is the main function of the computer?
    1) Compute Pi with required precision
    2) Execute computer games
    3) Execute a set of machine instructions
    4) Control the traffic light system

4) A reference type U conforms to a reference type T if either:
    1) They have no generic parameters, and U is a descendant of T;
    2) U has generic parameter(s), and T is a descendant of U;
    3) They are both generic derivations with the same number of actual generic parameters
    4) They are both generic derivations where the number of actual generic parameters does not matter

5) What is the main function of the computer?
   Answer: ____________________

6) Explain how JVM works:






==========Exam answers==========
1) There is no difference between concurrency and parallelism, they are just synonyms.
   Answer: false

2) It is possible to start a thread twice.
   Answer: false

3) What is the main function of the computer?
    1) Compute Pi with required precision
    2) Execute computer games
       -> 3) Execute a set of machine instructions
    4) Control the traffic light system

4) A reference type U conforms to a reference type T if either:
   -> 1) They have no generic parameters, and U is a descendant of T;
    2) U has generic parameter(s), and T is a descendant of U;
       -> 3) They are both generic derivations with the same number of actual generic parameters
    4) They are both generic derivations where the number of actual generic parameters does not matter

5) What is the main function of the computer?
   Accepted answers: [compute, execute]

6) Explain how JVM works:
   It works will
   Note: To be checked manually
```