# Ski resort

### Statement

Write a program with an array of structures for a ski resort renting system. The program should read the data
from `input.txt`. The data comprises of one or more lease cases, each lease case must contain a name of a tenant, rental
day and time, rented item(s) in the following format:

* the first line is the name of the tenant
* the second line is the rental day and time
* all the following lines should contain the equipment title, size, amount and measurement unit.

If there are more than 1 lease case, then they are separated by an empty line between them in `input.txt`. The program
should output into `output.txt` all the lease cases in a human-readable format (for each new case use new line).

#### Assumptions:

1. Names of the tenants consist of only English letters and spaces \[2;30\]
2. Date & time (non-negative integer numbers in format dd/mm/yyyy hh.mm.ss, considering max time 23:59:59, do not forget
   about leap years)
3. Items (\[4;15\] only Englsh letters and spaces, at least 1 item)
4. Size (float value in range (0;200\])
5. Amount (integer value in range (0;30\])
6. Measurement unit (pcs or pair)
7. Paired equipment is considered as 1 pair. If more than one pair is rented, then multiple form **“pairs”** must be
   used.

**NOTE:** If `input.txt` does not follow formatting described above, the program should output an error message “Invalid
input!”.

* * *

#### Example 1

input.txt

```
Mike Vazovski
01/02/2021 08:55:12
Snowboard 90 1 pcs
Googles 49 1 pair Helmet 51 1 pcs
```

output.txt

```
Mike Vazovski has rented 1 pcs of Snowboard of size 90, 1 pair of Googles of size 49 and 1 pcs of Helmet of size 51 on 01/02/2021 at 08:55:12.   
```

* * *

#### Example 2

input.txt

```
Matt Shadows
32/11/2020 13:06:06
Skis 178 1 pair
```

output.txt

```
Invalid input!
```

**Note:**

* Invalid day 32

* * *

#### Example 3

input.txt

```
Mr. Churchill
06/12/2021 10:30:16
Helmet 59 2 pcs
```

output.txt

```
Invalid input!
```

**Note:**

* Invalid symbol ‘.’

* * *

#### Example 4

input.txt

```
Mike Vazovski
01/02/2021 08:55:12
Snowboard 90 1 pcs
Googles 49 1 pair
Helmet 51 1 pcs

Andrew
04/11/2020 03:06:12
178 1 pair
```

output.txt

```
Invalid input!
```

**Note:**

* Invalid item name containing number in the second lease case

* * *

#### Example 5

input.txt

```
Mike Vazovski
01/02/2021 08:55:12
Snowboard 90 1 pcs
Googles 49 1 pair
Helmet 51 1 pcs

Steve Tyler
04/11/2020 03:06:12
Skis 180 2 pair
```

output.txt

```
Mike Vazovski has rented 1 pcs of Snowboard of size 90, 1 pair of Googles of size 49 and 1 pcs of Helmet of size 51 on 01/02/2021 at 08:55:12.
Steve Tyler has rented 2 pairs of Skis of size 180 on 04/11/2020 at 03:06:12.
```

* * *