# TysonFoster-CodingAssignment1-IdleTapper

A basic incremental game where the player gains "taps" by by repeatedly tapping on a button,
and by spending taps to increase the amount of taps gained both by the button and passively each second

app uses a single view, so key files are just MainActivity.kt and activity_main.xml

Project architecture is OnClickListers for each of the 3 buttons, functions to save and load
the instance state (to prevent rotating the screen from resetting the game), and a handler
set to call a function once per second, to implement the passive "tap" income
