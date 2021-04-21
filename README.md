# Cheskers project

## Running

To run the program with different agents just do ./Checkers.jar in a terminal. This jar was uploaded using git lfs so make sure you clone the repo correctly to run it.

A file 'moves.txt' will be generated after running. To quickly collect data use 'runalot.sh' run it multiple times.

Unfortunately the jar cant load the images for the GUI for some reason? Only noticed this (somehow) while getting the repo ready for submission. Import into eclipse or compile manually to actually play against the agents.

## Known issues

If there are two or more moves that start and end in the same place human players cant choose which of these moves they take

Humans also cant pick which piece their pawn is promoted to

The king cant do multiple hops, this kept causing bugs. Having the king only move one space is more like chess anyway

The board display gets buggy if you have two humans playing against each other




