-----------------------------------IMPROVISING THE IMPLEMENTATION-----------------------------------------------------------------------------------------------------------------------
Description File of the Source code for Shutter Fly Coding Challenge alternate solution using DATA ACCESS OBJECT LAYER
This is just an attempt to code challenge!!!!!

This is code is the testing phase but will sure complete after getting every thing right!!!




-----------------------------------REQUIREMENTS To EXECUTE THE CODING CHALLENGE----------------------------------------------------------------------------------------------------------
DERBY: I am Apache Derby 10.12.1.1 as my Inmemory Database. The DataBase is created using the sqlcommand file in the "src" folder.
 
ARGUMENT TO THE PROGRAM: An argument needs to be passed, that is the Topx Value needs to put in the java argument window. 

-----------------------------------IMPLEMENTATION OF THE CODING CHALLENGE----------------------------------------------------------------------------------------------------------------

DATA ACCESS OBJECT LAYER: Common DAO class for all database operations, Intentional singleton class to allow sharing of connection. This is done using the Prepared Statements 
						to access the DataBase

INGENT FUNCTION: The Injest function gets the DATA according to attribute "TYPE" mention in the input JSON file.

DATA : Data is Stored USING IN-MEMORY DERBY DataBase. IT IS ACCESSED USING THE JSON STRING. This s Done using the DAO file by the CustomerBean class

CALCULATE LTV: WHEN THE NEW CUSTOMER IS INTO THE SYSTEM IT WILL CALUCATE LTV VALUSE OF THE CUSTOMER AND PASS THAT VALUE AND STORE THAT VALUE IN HASH-MAP MAP FOR FURTHER PROSESSING.

TOP X FUNCTION: IT WILL RETURN THE REVENEW OF TOP "X" CUSTOMERS GIVEN THROUGH THE ARGUMENT

-----------------------------------PERFORMANCE OF THE IMPLEMENTATION--------------------------------------------------------------------------------------------------------------------- 

1: THE TOTAL NUMBER OF DAYS THE CUSTOMER WAS ACTIVE IS CALCULATE BY USING BOTH THE TABLES BECAUSE IT CAN BE POSSIBLE THAT CUSTOMER VISITED THE SITE FIRST AND PLACE THE ORDER LATER.

2: TO STORE THE CUSTOMERS REVENEW EFFICIENTLY I HAVE USED HASH MAP.

-----------------------------------IMPROVISING THE IMPLEMENTATION----------------------------------------------------------------------------------------------------------------------- 

