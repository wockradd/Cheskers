#!/bin/bash
# Basic while loop
counter=1
while [ $counter -le 1000 ]
do
./Untitled.jar
((counter++))
echo game $counter
done
echo All done
