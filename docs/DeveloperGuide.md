﻿---
layout: page
title: Developer Guide
---
<p align="center">
  <img src="https://raw.githubusercontent.com/AY2021S2-CS2103T-T12-1/tp/master/docs/images/NuFash-logo.png" alt="logo" width="600">
</p>

---
* [Setting up, getting started](#setting-up-getting-started)
* [Design](#design)
  * [Architecture](#architecture)
    * [UI Component](#ui-component)
    * [Logic Component](#logic-component)
    * [Model Component](#model-component)
    * [Storage Component](#storage-component)
* [Implementation](#implementation)
    * [Default Sorting Order](#completed-default-sorting-order-of-garments-in-wardrobe)
    * [Find feature](#find-feature)
    * [Match feature](#match-feature)
    * [Select feature](#select-feature)
    * [View feature](#view)
* [Appendix: Requirements](#appendix-requirements)
    * [Product Scope](#product-scope)
        * [Target User Profile](#target-user-profile)
        * [Value Proposition](#value-proposition)
    * [User stories](#user-stories)
    * [Use cases](#use-cases)
    * [Non-Funtional Requirements](#non-functional-requirements)
    * [Glossary](#glossary)



--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="https://raw.githubusercontent.com/AY2021S2-CS2103T-T12-1/tp/master/docs/images/ArchitectureDiagram.png" alt="alt text" width="400">

<div style="page-break-after: always;"></div>

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

<div style="page-break-after: always;"></div>

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

![Structure of the UI Component](images/ArchitectureSequenceDiagram.png)

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2021S2-CS2103T-T12-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `GarmentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2021S2-CS2103T-T12-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2021S2-CS2103T-T12-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

<div style="page-break-after: always;"></div>

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S2-CS2103T-T12-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

1. `Logic` uses the `WardrobeParser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. deleting a garment).
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
5. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

<div style="page-break-after: always;"></div>

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source:  **Note:**  The lifeline for `DeleteCommandParser` 
should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

<div style="page-break-after: always;"></div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S2-CS2103T-T12-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the wardrobe data.
* exposes an unmodifiable `ObservableList<Garment>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.<br><br>


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Description` list in the `Wardrobe`, which `Garment` references. This allows `Wardrobe` to only require one `Description` object per unique `Description`, instead of each `Garment` needing their own `Description` object.<br>

![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>
<div style="page-break-after: always;"></div>

### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S2-CS2103T-T12-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the wardrobe data in json format and read it back.

<div style="page-break-after: always;"></div>

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## Implementation
This section describes some noteworthy details on how certain features are implemented.

### Default Sorting Order of Garments in Wardrobe

#### Implementation

The default sorting order of Garments in the Wardrobe, which lists out the Garments based on when they have been last used. The new default sorting order sorts the list of garments
based on the date that they were last used, and outputs the entries from the earliest used date to the latest used date.

This is achieved through the creation of the `LastUse` attribute that is tied to the `Garment` object, that gives a date as to when it was last used.

The Wardrobe is sorted at the `UniqueGarmentList` level with the help of `GarmentComparator`.

The following diagram shows where sorting occurs in the Model component (higher level elements omitted).

![Structure of the Model Component for sorting](images/SortingModelDiagram.png)

#### Design Consideration:

##### Aspect: Sorting the garments
* **Alternative 1 (current choice)**: <br>
  Sorts the Garments based on chronological ordering of the `LastUse` attribute.
  * Pros: Garments that have not been used for a longer period of time come up earlier in the list, which would 
    encourage and remind users to wear all their garments.
  * Cons: Garments may not have been used for a reason, which may result in user taking a longer time to sieve 
    through the list.
* **Alternative 2**: <br>
  Sorts Garments based on reverse chronological ordering of the `LastUse` attribute, from the latest used date to the earliest used date.
  * Pros: Garments that are often used could be better suited for the users' needs, and making them come 
    earlier in the list allows users sieving through the list quicker.
  * Cons: Users may forget about the Garments that they have not worn in some time.
* **Alternative 3**: <br>
    Sorts the Garments based on chronological or reverse chronological ordering of the `LastUse` attribute based on user input.
  * Pros: Allows users to choose which ordering they want, and they can get the respective benefits as above.
  * Cons: Requires more time to implement.
 
<div style="page-break-after: always;"></div>

### Find feature

#### Implementation

The proposed `find` mechanism extends the `find` mechanism of `AddressBook`, which only allows users to find entries 
based on the `Name` attribute. This extended find mechanism allows users to find their garments using 1 or more of 
the garment attributes, namely:
* `Name`
* `Size`
* `Colour`
* `DressCode`
* `Type`
* `Description`

The command is parsed from `WardrobeParser` to the `FindCommandParser`, where the inputs are tokenized using 
`ArgumentTokenizer` into an object of `ArgumentMultiMap`, `argMultiMap`. An `AttributesContainsKeywordsPredicate` 
object of the input is then created with `argMultiMap` as the argument for the constructor. 

Finding the correct garment is achieved through the creation of new `Predicate` classes (in addition to the existing 
`NameContainsKeywordsPredicate`):
* `ContainsKeywordsPredicate` parent class
* `SizeContainsKeywordsPredicate` child class
* `ColourContainsKeywordsPredicate` child class
* `TypeContainsKeywordsPredicate` child class
* `DressCodeContainsKeywordsPredicate` child class
* `DescriptionContainsKeywordsPredicate` child class

`AttributesContainsKeywordsPredicate` class is used which looks through each of the other `ContainsKeywordsPredicate` 
child classes to return true for garments that pass each predicate, and false otherwise. 

The object of `AttributesContainsKeywordsPredicate` is passed to an object of `FindCommand`. The `execute` method of 
`FindCommand` object is then called with `model`, which then calls the `updateFilteredGarmentList` method of `model`.
This then displays all matching garments in the data to the front end of the NuFash application.

<div style="page-break-after: always;"></div>

Given below is an example usage scenario of how the `find` mechanism works.

1. The user launches the NuFash application for the first time and is presented with a list of all garments 
   retrieved from local storage `wardrobe.json` (if applicable)

2. The user executes `find n/Shirt shorts s/23 22 c/blue` command to find the garments that has name containing 
   'Shirt' or 'shorts', has size of 23 or 22, and has colour blue. The `find` command makes use of a 
   `AttributesContainsKeywordsPredicate` to call the `updateFilteredGarmentList` display all the matching garments, 
   without modifying the original garments list.
   * Note: If no garments match the keywords given by the user, an empty list will be shown.


The sequence diagram below shows how the find command works:
![Find Sequence Diagram](images/FindSequenceDiagram.png)

**Note** ALL_PREFIXES in the above diagram refers to input the arguments as PREFIX_NAME, PREFIX_SIZE, PREFIX_COLOUR, 
PREFIX_TYPE, PREFIX_DRESSCODE, PREFIX_DESCRIPTION
The activity diagram below shows the flow of what happens when a user executes the find command:
![Find Activity Diagram](images/FindActivityDiagram.png)

<div style="page-break-after: always;"></div>

#### Design Consideration:

##### Aspect: How many attributes Find can account for at a time
* **Alternative 1**: <br>
  Finds with only one attribute at a time. <br>
  E.g. `find n/jeans c/blue` will only find entries whose Name attribute contains the keyword "jeans".
  * Pros: Easier to implement.
  * Cons: Limited functionality.
* **Alternative 2(Implemented)**: <br>
  Finds with multiple given attributes. <br>
  E.g. `find n/jeans c/blue` will find entries whose Name attribute contains the keyword "jeans" **and** whose Colour attribute contains the keyword "blue".
  * Pros: More precise results.
  * Cons: Requires a single predicate to account for all combinations of user input.

### Match feature

#### Implementation

The `match` mechanism extends the `find` mechanism of `NuFash`. It 
finds garments that match the colour and dress code of a specified garment, and 
also complement the type of the specified garment.

This is achieved through the creation of four new Predicates (in addition to the existing NameContainsKeywordsPredicate).
These are shown below, and the first predicate is dependent on the second, third and fourth.
* AttributesContainsKeywordsPredicate
* ColourContainsKeywordsPredicate
* DressCodeContainsKeywordsPredicate
* TypeContainsKeywordsPredicate

MatchCommand is updated to use a find command
with single or multiple attributes (i.e. `c/` for Colour, `r/` for dressCode and `t/` for type) and the respective 
predicate is subsequently used to create a FindCommand Object. This is then executed.

<div style="page-break-after: always;"></div>

The following sequence diagram shows how the match operation works:
![Match Sequence Diagram](images/MatchSequenceDiagram.png)  

The following activity diagram summarizes what happens when a user
executes a match command:
![Match Activity Diagram](images/MatchActivityDiagram.png)

<div style="page-break-after: always;"></div>

#### Design Consideration:

##### Aspect: Matching based on multiple attributes
* **Alternative 1 (Current implementation)**: <br>
  Matches based on a single garment. <br>
  E.g. `match 1` will find entries that match the colour and dress code of garment at index 1 in the wardrobe,
  and complement its type.
    * Pros: Easier to implement.
    * Cons: Requires multiple match commands to generate a full outfit.
* **Alternative 2**: <br>
  Matches based on multiple garments <br>
  E.g. `match 1 2` will find entries that match the colours and dress codes of the garments at indices 1 and 2
  in the wardrobe, and complements their types.
    * Pros: Can generate a full outfit with one match command.
    * Cons: Difficult to implement.

### Select feature

#### Implementation
The proposed `select` mechanism is facilitated by the `SelectCommand` class
The mechanism allows for the `LastUse` attribute to be updated in the specified Garment.
`LastUse` is an attribute of `Garment` which implements the `Model` interface.
It implements the following operations:
* `addGarment()` - Adds a duplicate garment with the updated `LastUse` attribute.
* `deleteGarment()` - delete the original object with the outdated `LastUse` attribute.

Given below is an example usage scenario and how the Select mechanism behaves at each step.

1. The user launches the application for the first time. The Wardrobe will be initialized with the stored garments.
Each garment has the distinct attributes: Colour, DressCode, LastUse, Name, Size, Type.
   

2. The user executes add n/NAME s/SIZE c/COLOUR r/DRESSCODE t/TYPE to add a new garment to the existing list.
The LastUse attribute of this garment is instantiated with a null value: `Never`, indicating the garment is newly added.
   

3. The user decides that they would like to indicate that a particular garment was worn. They can do this
by viewing the garments, following which they can use the Select Command by specifying the garment's index.
The selected garment is duplicated, with the `LastUse` attribute being updated to the current local date.
The original selected garment with now obsolete `LastUse` attribute is deleted. 
This signifies to the wardrobe that the user has checked out the garment to be worn today.

<div style="page-break-after: always;"></div>

The following sequence diagram shows how the select operation works:

![SelectSequenceDiagram](images/SelectSequenceDiagram.png)

The following activity diagram summarizes what happens when a user executes a new command:

![SelectActivityDiagram](images/SelectActivityDiagram.png)

<div style="page-break-after: always;"></div>

#### Design Consideration

* **Alternative 1 (current choice):** Select garment via `INDEX`
    * Pros: Easy to implement.
    * Cons: The garment indexing is not fixed. When three garments are picked for `view`, the `view` listing puts the
      garments under different indexes as compared their regular indexes from the command `list`. Therefore, although
      selectable from the `view` command garment list, the garments should be selected from the list displayed after 
      the `list` command.

* **Alternative 2:** Select garment via unique attributes
    * Pros: User only requires a unique attribute, e.g. `Name` to be entered.
    * Cons: Harder to implement as the `Name` attribute has its own drawbacks, e.g `Name` could be
      a extremely long phrase, which could be hard for the user to remember or input into the application.


### <a name="view"></a> View feature

#### Implementation
The `view` mechanism extends the `list` mechanism from `AddressBook`. It is facilitated by the `ViewCommand` class.

The mechanism allows for the list of Garments to be filtered to show an outfit based on user input.

Users must select 3 Garments to view, with the conditions being:
* Garments cannot be of the same type <br>
  (i.e. must have one of upper, one of lower and one of footwear)
* Garments must be of the same DressCode.

Given below is an example usage scenario and how the View mechanism behaves at each step.

1. The user launches the application for the first time. The Wardrobe will be initialized with the stored garments.
   Each garment has the attributes: Colour, DressCode, LastUse, Name, Size, Type.
   
2. The user executes `view 1 2 3` to view an outfit consisting of Garments indexed at 1, 2, and 3. We assume that the 3 Garments follow the conditions as stated above.

3. The `view` command indirectly calls updateFilteredGarmentList method of Model with a predicate that filters the selected Garments to be viewed as an outfit.

<div style="page-break-after: always;"></div>

The following sequence diagram shows how the view operation works:

![ViewSequenceDiagram](images/ViewSequenceDiagram.png)

The following activity diagram summarizes what happens when a user executes a new command:

![ViewActivityDiagram](images/ViewActivityDiagram.png)

<div style="page-break-after: always;"></div>

#### Design consideration:

##### Aspect: Allow viewing of outfit consisting of less than or more than 3 Garments
* **Alternative 1: Allow variable number of Garments to be viewed as an outfit<br>**
  * Pros: Flexible user input
  * Cons: Viewing of certain outfits missing a certain Type may result in an incomplete looking outfit.<br>
    e.g. viewing an outfit consisting of upper and footwear only.
* **Alternative 2 (current choice): Allow only exactly 3 Garments to be viewed as an outfit**
  * Pros: Ensures that outfits always look complete.
  * Cons: Limited user choice.

## Appendix: Requirements

### Product Scope

<a name="target-user-profile"></a>**Target User Profile:**

* Students currently in tertiary educational institutions

* Users that prefer desktop applications as opposed to other types of applications

* Users that are reasonably comfortable with command line interfaces

* Users who are able to type fast and hence prefer typing to mouse interaction

* Users who have a hard time organising their wardrobe
<br><br>


<a name="value-proposition"></a>**Value Proposition:**

* Ability to organise garments based on attributes such as colour, size, material, type of garment

* Maintain outfit schedules to prevent repetitive dressing

* Receive garment suggestions based on factors such as weather, temperature, or the nature of events the user may be attending

<div style="page-break-after: always;"></div>

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a ...                                           | I want to ...                                             | So that I ...                                                                            |
| -------- | -------------------------------------------------- | --------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| `* * *`  | new user                                           | have a tutorial to teach me how to use the app            | will be able to learn how to use the app quicker                                         |
| `* * *`  | user                                               | add a garment                                             | can keep a record of garments                                                            |
| `* * *`  | user                                               | edit details of garment logged in                         | can correct incorrect details pertaining to items                                        |
| `* * *`  | user                                               | remove a garment from my wardrobe                         | can reflect that a garment has been discarded                                            |
| `* * *`  | user with many garments in my wardrobe             | find a garment by name                                    | can locate details of a garment without having to go through the entire list             |
| `* *`    | user that cannot decide what to wear               | easily plan a schedule for what garments to wear          | do not have to waste time deciding at the last minute                                    |
| `* *`    | user that always wears the same few garments       | be reminded on the garments I have not worn               | would not be wearing the same garments all the time                                      |
| `*`      | user that does not know how to match garments      | to see whether certain colours and types of garment match | will be able to ascertain if the garments match without having to try them on physically |
| `*`      | user that cannot visualise how an outfit will look | to be able to preview how an outfit will look             | will be able to better visualise how an outfit will look                                 |


### Use cases
(For all use cases below, the System is the `NuFash` and the Actor is the `user`, unless specified otherwise)

**Use case: Add a garment**

**MSS**

1. User requests to add a garment with specified attributes
2. NuFash adds the specified garment to list of existing garments

   Use case ends.

**Extensions**
* 1a. The specified attributes are in an invalid format.
    * 1a1. NuFash shows an error message with the valid format.

    * 1a2. User enters new data.
    
        Steps 1a1-1a2 are repeated until data entered is in a valid format.
        
        Use case resumes at step 2.

      
**Use case: Delete a garment**

**MSS**

1. User requests to list all garments
2. NuFash shows a list of garments
3. User requests to delete a specific garment in the list
4. NuFash deletes the specified garment 
   
    Use case ends.

**Extensions**
* 2a. The list is empty.
  
  Use case ends.

* 3a. The given index is invalid.
    * 3a1. NuFash shows an error message.
      
        Use case resumes at step 2.

**Use case: Select a garment**

**MSS**

1. User requests to list all garments
2. NuFash shows a list of garments
3. User requests to select a specific garment in the list
4. NuFash selects the specified garment

   Use case ends.

**Extensions**
* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.
    * 3a1. NuFash shows an error message.

      Use case resumes at step 2.
<br><br>

**Use case: Finding garments**

**MSS**

1. User specifies keywords of the attributes for the garments they would like to find.
2. NuFash shows a list of all the garments that contain the specified keywords, which is a list of 0 or more garments.  
   Use case ends.
   
**Extensions**
* 1a. The specified keywords are invalid.
    * 1a1. NuFash shows an error message.   
    Use case resumes at step 1.
<br><br>    

**Use case: Matching a garment**

**MSS**

1. User requests to list all garments
2. NuFash shows a list of garments
3. User specifies a garment they would like to match
4. NuFash shows a list of garments that match the specified garments    
   Use case ends.

**Extensions**
* 2a. The list is empty.  
  Use case ends.

* 3a. The given index is invalid.
    * 3a1. NuFash shows an error message.   
      Use case resumes at step 2.
<br><br>

**Use case: Viewing an outfit consisting of 3 garments (upper, lower, footwear)**

**MSS**

1. User requests to list all garments
2. NuFash shows a list of garments
3. User specifies a 3 garments they would like to view as an outfit.
4. NuFash shows a list of garments with the specified garments    
   Use case ends.

**Extensions**
* 2a. The list is empty.  
  Use case ends.

* 3a. The given indexes are invalid.
    * 3a1. NuFash shows an error message.   
      Use case resumes at step 2.
      
<br><br>
      
<a name="non-functional-requirements"></a>**Non-Functional Requirements:**
1. Should work on any mainstream OS as long as it has Java 11 or above installed.
2.  Should be able to hold up to 1000 garments without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
<br><br>

<a name="glossary"></a>**Glossary:**
* **Mainstream OS**: Windows, Linux, Unix, OS-X
