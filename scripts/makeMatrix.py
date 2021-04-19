#!/usr/bin/env python3
import numpy as np

data = open('./results.txt', 'r')
mat = np.zeros([9,9],dtype=float)
totalMat = np.zeros([9,9],dtype=float)

while True:
    #read in data
    line1 = data.readline()
    line2 = data.readline()
    line3 = data.readline()

    #break if eof
    if not line1 or not line2 or not line3:
        break 


    #fill matrix
    blackPlayer = int(line1[-2])
    whitePlayer = int(line2[-2])
  
    #dont care who was white or black, just who won, so edit in both slots in the matrix 
    if line3 == "w\n":
        mat[whitePlayer][blackPlayer] += 1    
    elif line3 == "b\n":   
        mat[blackPlayer][whitePlayer] += 1
    totalMat[whitePlayer][blackPlayer] += 1
    totalMat[blackPlayer][whitePlayer] += 1
    
print(mat)
print(totalMat)
    

for i in range(9):
    for j in range(9):
        mat[i][j] /= totalMat[i][j]

print(mat)


#raw output:
#[[ -3 -18   2   4  -1   2]
# [ 24  -5   2  -5   5   7]
# [  2 -11   3  -4  -2   4]
# [ 12   0   6  13  -3 -11]
# [  6  -9   5  -2   4   3]
# [ 11  -2   5  -1   1   2]]

#cleaned up output where it ignores who was black or white:
#for each line read across to see how many times that ai won overall
#eg on line 1: mm1 beat mm1 0 times overall, mm2 42 times overall, mm3 0 times overall, etc 

# mmSimple1	[  0  42   0   8   7   9]
# mmPositional1	[-42   0 -13   5 -14  -9]
# mmSimple2	[  0  13   0  10   7   1]
# mmPositional2	[ -8  -5 -10   0   1  10]
# mmSimple3	[ -7  14  -7  -1   0  -2]
# mmPositional3	[ -9   9  -1 -10   2   0]]


#Conclusions:
#mmSimple1 really good against mmPositional1
#mmPositional1 not good
#mmSimple2 is good against mmPositional1 and mmPositional2
#mmPositional1 is good against mmPositional1
#mmSimple3 is good against mmPositional1
#mmPositional3 pretty bad, best against mmPositional1 though


#To get good data:
#run 500 games mmSimple1 vs mmPositional1, record mmSimple2
#run 250 games mmSimple2 vs mmPositional1, record mmSimple2
#run 250 games mmSimple2 vs mmPositional2, record mmPositional2
#run 250 games mmPositional2 vs mmPositional3, record mmPositional2
#run 250 games mm5 vs mm2, record mm5
#run 100 games mmPositional1 vs mm2, record mmPositional3


#random		[0.5      0.       0.       0.       0.048    0.       0.       0.64    0.6  ]
#mmSimple1	[1.       0.5      0.619    0.391    0.563    0.6      0.433    1.      1.   ]
#mmPositional1 	[1.       0.333    0.462    0.207    0.5      0.273    0.286    1.      1.   ]
#mmSimple2 	[1.       0.609    0.655    0.5      0.478    0.476    0.706    1.      1.   ]
#mmPositional2 	[0.952    0.25     0.333    0.478    0.469    0.387    0.588    0.96    1.   ]
#mmSimple3 	[1.       0.32     0.545    0.476    0.548    0.5      0.517    1.      0.944]
#mmPositional3 	[1.       0.567    0.429    0.235    0.294    0.483    0.5      1.      0.917]
#nn1 		[0.36     0.       0.       0.       0.       0.       0.       0.      0.   ]
#nn2 		[0.4      0.       0.       0.       0.       0.056    0.       0.      0.   ]

#0.197
#0.678
#0.562
#0.714
#0.602
#0.65
#0.603
#0.04
#0.05




#rand [  0 -33 -21 -24 -19 -27 -29   7   5]   
# mm1 [ 33   0   6  -5   5   7  -4  27  29]
# mm2 [ 21  -6   0 -13   3  -9  -4  30  19]
# mm3 [ 24   5  13   0   0   0   8  28  26]
# mm4 [ 19  -5  -3   0   0  -5   5  24  26]
# mm5 [ 27  -7   9   0   5   0   1  19  16]
# mm6 [ 29   4   4  -8  -5  -1   0  20  22]
# nn1 [ -7 -27 -30 -28 -24 -19 -20   0   0]
# nn2 [ -5 -29 -19 -26 -26 -16 -22   0   0]


    
