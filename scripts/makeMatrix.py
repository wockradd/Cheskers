import numpy as np

data = open('../data/v2moves.csv', 'r')
mat = np.zeros([6,6],dtype=int)

while True:
    #read in data
    line1 = data.readline()
    line2 = data.readline()
    line3 = data.readline()

    #break if eof
    if not line1 or not line2 or not line3:
        break 


    #fill the matrix
    blackPlayer = int(line1[-2])-1
    whitePlayer = int(line2[-2])-1
    #dont care who was white or black, just who won, so edit in both slots in the matrix 
    if line3 == "w\n":
        mat[whitePlayer][blackPlayer] += 1
        mat[blackPlayer][whitePlayer] -= 1     
    elif line3 == "b\n":   
        mat[whitePlayer][blackPlayer] -= 1
        mat[blackPlayer][whitePlayer] += 1
    



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
#[[  0  42   0   8   7   9]
# [-42   0 -13   5 -14  -9]
# [  0  13   0  10   7   1]
# [ -8  -5 -10   0   1  10]
# [ -7  14  -7  -1   0  -2]
# [ -9   9  -1 -10   2   0]]


#Conclusions:
#mm1 really good against mm2
#mm2 sucks
#mm3 is good against mm2 and mm4
#mm4 is good against mm6
#mm5 is good against mm2
#mm6 kinda sucks, best against mm2 though


#To get good data:
#run 500 games mm1 vs mm2, record mm1
#run 250 games mm3 vs mm2, record mm3
#run 250 games mm3 vs mm4, record mm4
#run 250 games mm4 vs mm6, record mm4
#run 250 games mm5 vs mm2, record mm5
#run 100 games mm6 vs mm2, record mm6

    
