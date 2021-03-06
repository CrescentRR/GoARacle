# GoARacle
Garden of Assemblage Randomizer Oracle.  Generates hints for runs.

## How to use

-Download the zip file found on the Releases page for the most recent version of GoAracle.  Extract the folder and launch "GoARacle.exe", which is the application with the blue crystal icon.

-Click on "Select Seed" and navigate to your pnach seed file and select it.

-When you find a report, click on the corresponding button.  Pass the anti-cheat test to replace the button's text with your hint.  

-Mark off items you find by using the "Mark Off" button and choosing what location you would like to check off.

-When you start your run, look at Jiminey's Journal to see if there are any Reports.  Any reports gotten from Critical Mode bonuses or the Garden of Remembrance have the location "Critical Rewards and Garden", located at the end of the location list.

## Understanding the hints

-Each hint has a similar format, barring "Path of Light": "[Person] recommends looking in [place]."

-Each pool has something known as a Priority Score, which are a number of points assigned to each item in a respective pools.

-In the About button, you can see the pools, their contents, and their priority score.

-Reports are ordered from highest priority to lowest priority, meaning Report 1 is the best world in the seed, while Report 13 is the 13th best world in the seed.

-The pool that appears in the hint is the highest priority pool in that world.  For example, a world with 1 "Path of Light" item and 5 "Roxas' Choice" items would say "Roxas recommends looking in [place]", as the comparison would be 100 Priority Score vs 125 Priority Score (Path of Light being worth 100 each, Roxas being worth 25 each)

-In the event of a tie, a random pool in that tie is selected.

-Use the Mark Off button to get a running count of how much priority score is needed in each world.

## Changelog

1/3/21 (v1.5.1): You can now disable pools when selecting a seed (Yen Sid disabled by default. If Path of Light is disabled, you will not be asked if you want to hide Path of Light).  You can now reset the application by selecting a new seed.  Hovering over the points in Mark Off now displays keyboard controls (if that's more your style).  

12/11/20 (v1.5.0):When selecting a world in Mark Off, the dropdown now shows all worlds without a scrollbar.
When opening Mark Off, it starts off on the STT Mark Off page instead of having a popup for the world you want.
Yen Sid's Choice added: 5 Priority Score. Contains: Growth abilities.
You can now hide Path of Light. Points are still counted, but will hint at another pool instead.

11/18/20 (v1.4.3): Box around text on buttons have been removed.  Popout window now hides behind the main window to discourage pushing it off screen or minimizing it (which breaks it).  Tied pools are randomized on a seed instead of purely randomized.  Popout window starts on the last updated thing instead of the version number.  Hover text has been added for the functional buttons at the top of the main window for clarity.

10/16/20 (v1.4.2): Using Mark Off to track items in worlds that do not have a report revealed for them now show the number of points you have in that world in the Popout window.  Fixed an issue where Trinity Limit was not being tracked in reports.  Changed the world name of "Disney Castle and Timeless River" to "Disney Castle and TR".

10/12/20 (v1.4.1): Fixed an issue where the popout window would not be able to be shared via window capture.  Added a label to the popout window's hints to say what hint it is.

10/8/20 (v1.4.0): Wow, a whole month!  Added the "Popout" button, where you can get an automatically updating report tab that shows the most recently updated report! Might work on making a header-less version of it so make it blend into stream layouts better.  Also fixed a bug where those pesky boxes popped up when changing your world in "Mark Off".

9/16/20 (v1.3.3): Changed About button to display the README.txt file.  Reduced the time needed for the pool tooltips to show up (should show up almost immediately, granted you are focused on that page [you clicked on the page]).

9/11/20 (v1.3.2): Removed the boxes that were supposed to be apostrophes.  Updated README.txt in the folder.  Removed clutter from the root of the folder.

9/7/20 2:20am EST (v1.3.1): Added tooltips in the checklist, hover over the labels for a few seconds to display what is in the pools and what their scores are.  Corrected the Reports pool to include the Garden of Assemblage Map.  

9/6/20 (v1.3): Added ability to switch between worlds within the checklist.  Checklist now shows amount of points found in the world.  Changed text to overall be shorter, along with window size update.  

9/4/20 (v1.2): Reports now show how max priority score as well as remaining.  Changed window sizes (sorry if it's worse, hard to get a good window size for all).  Added a button for the Garden of Assemblage Map, giving you the 14th best world, might wanna look out for maps now!  
 
 Pools:
   
    Mickey's Choice:
      
      REMOVED:
       
        Combo Plus
       
         Air Combo Plus
     
       ADDED:
      
         Fire Boost
      
         Blizzard Boost
       
         Thunder Boost
    
    Reports:
    
      ADDED: 
      
        Garden of Assemblage Map`

8/31/20 4pm EST: Fixed an issue where using the Mark Off feature on a revealed report would not update it.

8/31/20 2:49pm EST: Dark mode!  If you really liked the light mode, then tell me, I can always add in an option for it.  Made the icon of the window the same as the application.  Top buttons are now in a 2x2 grid.  When clicked, buttons now stay their original color.  Reports are now worth 15 points, from 13 for visibility of items in Donald, Goofy, and Merlin's pools.

8/31/20 1:28pm EST: Changed the Font to KH font.  Made the color of the report buttons an off-white instead of blue (Swing default color, hard to change it).  Now not a beta! (The font was the main thing I wanted to complete before making it the full version, so I'm happy with calling this a full version.  

8/31/20: Added the Mark Off button, which allows you to check off items you find as you play.  Fixed a bug where levels were not counted correctly.  Fixed a bug where you could input a seed multiple times and jack up the priority score.  

8/23/20 10:34pm EST: Selecting the pnach file now starts at the Desktop instead of within the file's directory.  Made it so pools with equal priority score would randomize which one would show up.  Changed text in the About page that incorrectly stated that Kairi's pool contained Negative Combo, when it actually contains Berserk Charge.  Changed the units from "PC" to "Priority Score" to more accurately represent what it means.  

8/23/20 9:45pm EST: Added 3 strikes for anti-cheat to discourage brute-forcing.  Fixed being able to interact with report buttons after passing the check OR failing the test multiple times.

8/23/20: Created a Google Sheets spreadsheet for recording your findings.  Makes it easier to track the current score of items you have, giving you a better idea of how much there still is to find.  You can find it here: https://docs.google.com/spreadsheets/d/1pRdCDJb4bGSOApkjolcLxP5RfyjV2Gn_DfR7Aoo56ZY/edit?usp=sharing and in the application folder, called "GoARacle Item Checklist" as a shortcut.

8/22/20: Added anti-cheat.  When clicking on a report button, brings up a box to check where that report was.  If answered correctly, replaces the button with the hint.  If answered incorrectly, nothing happens.  Trying to brute force it is not really worth it, though possible.  Might be something to look at, if people ask for it.

8/20/20 8:15pm EST: Fixed sorting.  Now counts reports in priority score count.  Visible priority score for a rough estimate of what is in each world/for testing.  About button now shows reports and priority score (PC) next to each pool.  

8/20/20: Completed usable demo of hint generator.  Not an executable, but it still should run without needing to fix code.  

## Plans

-Change the tiebreaker to be seeded in some way, either through seed name or something else.  Depends on how leagues prepare their seeds.

-Ability to input custom seeds for customization in races/leagues.

-General bugfixes 

-Balance changes with help of feedback

-HOLY SHIT I FOUND OUT HOW TO MAKE AN EXECUTABLE.  Well, expect a release with the installer then.  I love this.

## Special Thanks

Thanks to jsmartee for being such a great guy and amazing wall for me to bounce design decisions off of. I wish him the best.
If you haven't checked out his hint generator a la Spikevegeta's design, you can get it here: https://github.com/Jsmartee/kh2fm-hints-demo
And use it here: https://jsmartee.github.io/kh2fm-hints-demo/

Thanks to Valaxor for the input during initial thoughts, without it there would be quite a lot of backtracking, but now I have a better idea of what I want to do.
Val's the amazing creator of the seed generator site!  Check it out here: https://randomizer.valaxor.com/#/seed

Thanks to "Key (Shrug)" on Discord for his input and feedback on the generator during designing.  Always appreciated, and good luck on your races.

Thanks to "HPfunman" on Discord for his bug reports.  I'm really good at making spaghetti, and man is he good at gobbling it up and spitting it back out.
