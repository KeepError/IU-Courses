### Numbers

#### Task

You are expected to implement methods that do basic mathematical operations on a given sequence of numbers of different
types. For the given list of number in `output.txt` your program should output the result: a `double` value for
addition, multiplication and average for absolute values; a sequence of `double` values for division and square root;
the original type for deletion of negative values. Casting to `double` has to happen before calculations. Assume that
modifications in the list of numbers (if any) are saved and the updated list will be given to the next operations, this
belongs to division, square root and deletion of negative values.

#### Inputs

Input file may contain 2 or 3 lines:

1. The first line has a random sequence of operation numbers separated by a single space:

1. addition
2. multiplication
3. division
4. getting average
5. square root
6. negative value deletion

3. The second line has a sequence of numbers of different types separated by a single space
4. The third line comprises of one `double` divisor value required for division operation.

As input types you may expect any of the following subtypes of `Number`:

1. Integer
2. Long (the number followed by L or l)
3. Float (the number followed by F or f)
4. Double
5. BigInteger (the number followed by BI or bi)
6. BigDecimal (the number followed by BD or bd)

#### Assumptions

* The length of the first line is \[1; 10\] numbers
* The length of the second line is \[1; 20\] numbers
* The third line is optional and should be provided only if division operation appears in the first line.

#### Outputs

The `output.txt` should contain the number of strings equivalent to the number of operations provided in the `input.txt`
. The order of strings is equivalent to the order of operations in the `input.txt`. The Float, Long, BigInteger and
BigDecimal numbers in the output file should NOT contain letters in the end (F, L, BI, BD, ...). However, it is ok to
have E in power for some numbers (like 4.471420576731144E14). For more than one number in string use “, ” as a
delimiter. Your solution should handle exceptional cases, such as:

1. Exception: Non-existing operation
2. Exception: The list of operations has an invalid length
3. Exception: The list of numbers has an invalid length
4. Exception: Invalid data

After successful reading of inputs and while execution next exceptions may occur:

5. Exception: Square root cannot be calculated for negative value
6. Exception: Division by 0

Consider only the first exception for each operation according to the given priority. Catching exceptions 1-4 after
printing the message to the `output.txt` should terminate the program. However, catching exceptions 5 and 6 should not
lead to termination of the program, next operations (if any) also have to be performed. Each operation should be
performed fully, do NOT return partial answers, instead use the exception and do not modify the list.

**Hint**: use generics and lambda expressions to reduce the number of lines of code

* * *

#### Example 1

input.txt

```
1 2 4 3 6 5
1 10 -1 5 -34 0.5 2.5F 2147483649L 399872039480293478029384092384BI 8.59283429183429384795098329580194801294080192809810BD
2
```

output.txt

```
3.9987203948029345E29
1.5680007809211907E43
3.9987203948029345E28
0.5, 5.0, -0.5, 2.5, -17.0, 0.25, 1.25, 1.0737418245E9, 1.9993601974014673E29, 4.296417145917147
0.5, 5.0, 2.5, 0.25, 1.25, 1.0737418245E9, 1.9993601974014673E29, 4.296417145917147
0.7071067811865476, 2.23606797749979, 1.5811388300841898, 0.5, 1.118033988749895, 32768.000007629395, 4.471420576731144E14, 2.072780052469906
```

* * *

#### Example 2

input.txt

```
3 5 6
1 10 -1 5 -34 0.5 2.5F 2147483649L 399872039480293478029384092384BI 8.59283429183429384795098329580194801294080192809810BD
2
```

output.txt

```
0.5, 5.0, -0.5, 2.5, -17.0, 0.25, 1.25, 1.0737418245E9, 1.9993601974014673E29, 4.296417145917147
Exception: Square root cannot be calculated for negative value
0.5, 5.0, 2.5, 0.25, 1.25, 1.0737418245E9, 1.9993601974014673E29, 4.296417145917147
```

* * *

#### Example 3

input.txt

```
3 5 7      // operation 7 does not exist
1 10 -1 5 -34 0.5 2.5F 2147483649L 399872039480293478029384092384BI 8.59283429183429384795098329580194801294080192809810BD
2
```

output.txt

```
Exception: Non-existing operation
```
