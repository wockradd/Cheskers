#!/usr/bin/env python3
file = open('./bm5p.txt','r')
list = []
while True:
    # read a single line
    line = file.readline()
    if not line:
        break
    try:
        list.append(int(line))
    except:
        pass
print(list)
print(sum(list)/len(list))

# close the pointer to that file
file.close()
