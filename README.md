# Yahtzee - Android Game App
Yahtzee is  android application which allows you to play a familiar board game on your Android device as well. The development of this application is based on the latest technologies in Android development using the Kotlin programming language.

## About

Android project that allows you to create Yahtzee games with players nicknames and roll dice for each player in the same way like when you play a game live. The project is extensible which means that the methods presented in the project can be extended for more players or some other parameters with minor modifications.

**Android version 30.0.2**
## Features
The android app lets you: 

 - Play with default players nickname 
 - Play against a friend
 - Create new game with different nicknames 
 - Roll dice
 - Save the dice
 - Score for specific combination/set (ACES, FULL HOUSE, SMALL STRAIGHT, YAHTZEE etc.) 
 - See each player's score
 - Declare the winner
 - ...

## Rules

Yahtzee game have several rules: 
 - Score as many points as possible by rolling dice to reach the 13 combinations predefined in the game
 - Dice can be rolled up to three times in a turn 
 - Once a combination has been used in the game it cannot be used again 
 - After roll you can save/unsave the dice by clicking on them 
 - When you reach at least 63 points in the minor part of the scoreboard you unlock a 35 bonus points 
 - You have Yahtzee when you get 5 dice with the same side and it is worth 50 points
 - In the minor part of the scoreboard choosing set you score the sum of specific dice 
 - Three of a kind & Four of a kind -> it scores the sum of all the dice if you have three or four dice with the same side 
 - Full house -> Three of a kind & a pair - 25 points
 - Small Straight -> 4 consecutive dice - 30 points
 - Large Straight -> 1-2-3-4-5 or 2-3-4-5-6 - 40 points
 - Chance -> scores the sum of all dice 
 - The game ends when all categories have been scored 


## Tech-stack
The project seeks to use recommended practices and libraries in Android development. 
 - MVVM architecture (Viewmodel + LiveData)
 - Android KTX
 - RecyclerView
 - Hilt dependency injection
 - View Binding
 - ...

## Screenshots

![image](https://user-images.githubusercontent.com/75457058/113405376-0dca0980-93aa-11eb-9629-7df98ac6dcb1.png)
![image](https://user-images.githubusercontent.com/75457058/113405434-233f3380-93aa-11eb-9554-ee583a97fef6.png)
![image](https://user-images.githubusercontent.com/75457058/113405408-17ec0800-93aa-11eb-9a9d-ec4fc1a49b90.png)

## Setup
1. Clone the repository
```
git clone https://github.com/kovaccc/Yahtzee.git
```
2. Open the project with your IDE/Code Editor
3. Run it on simulator or real Android device 
4. To run Tests open test package in java directory 
5. Right click on test class or package that you want to run -> Run or Ctrl + Shift + F10














