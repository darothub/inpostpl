# InPost Recruitment Task

## Intro
We travel back in time ⏱️. InPost Mobile app was just created and you join the team to improve its feature set and make it ready for the future.
User base is growing fast and every day more people start to use it daily.

You, as an experienced developer, were assigned to the project to improve its quality. The initial code is not perfect and is far from being.
Organize and refactor code the way you like to work (packages, modules, layers, data flow, names, methods order etc.).

## Rules
- You can change and move any part you like, install any open source library you want
- A static JSON file is returned in response, **consider this is a real production environment** returning your data
- JSON file cannot be changed
- Git history is also important
- Feel free to comment your choices

## Solutions
1. Added grouping to the list of Shipments by flag **ShipmentNetwork.operations.highlight**
2. Styled list items as in Figma (link: https://www.figma.com/file/MzPR3whRl6KB1fFnkyM6Or/recruitment-task)
3. Sorted list items in groups by (the closest date to current date should be at top of the list):
    * status - order is described in `ShipmentStatus.kt` file (first item, should be at the top of list)
    * pickupDate
    * expireDate
    * storedDate
    * number
4. Added pull to refresh and handle refresh progress
5. Added storing shipments locally (use Room)
6. Added local archiving of the shipment by long pressing on the shipment:
    * `Shipment` stays hidden after re-downloading data or relaunching the app
7. Created unit tests


## Links and resources
- Fonts folder: [/app/src/main/res/font](./app/src/main/res/font)

If for some reason Figma link stops working, here you can see the requested design:
![Design from Figma](./images/Figma.png)

# Hope to hear from the team soon 💪
