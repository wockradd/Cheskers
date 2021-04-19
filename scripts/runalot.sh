#!/bin/bash
# Just runs a jar 1000 times
counter=1
while [ $counter -le 1000 ]
do
../Cheskers.jar
((counter++))
done
