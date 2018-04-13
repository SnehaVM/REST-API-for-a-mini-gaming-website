# REST APIs for a mini gaming website

<b>REQUIREMENTS:</b>

Build a set of <b>REST APIs</b> to manage entities and relationships in a mini gaming website. The API needs to be hosted in either Amazon EC2, Google App Engine, Or Compute Engine. Use <b>JPA for persistence</b>, and each API method must be <b>transactional</b>.

There are <b>two primary types of entities: Players and Sponsors.</b> They have the following relationships and constraints:</br></br>
<b>Opponents:</b> </br>
• If two players play against each other, they are opponents. </br>
• The opponent relationship is symmetric in that is Alice is an opponent of Bob, then Bob is also an opponent of Alice.</br>
• A player can have zero or more opponent players.</br></br>
<b>Sponsors:</b>
• A player can optionally be sponsored by an external sponsor. Different players can have the same sponsor.</br>
•	The firstname, lastname, and email fields are required for any player. Emails have to be unique across players. </br>
•	The name field is required for any Sponsor, and does not need to be unique. </br>

You are <b>recommended to embed addresses </b>and not to store them as separate entities.

You need to persist the entities in a <b>non-volatile database</b>.

To manage these entities and their relationships, you are asked to provide the following REST APIs. The paths below are relative to the base URL of your app.

<b>Player APIs:</b></br>
(1) Create a player Path: player?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$... Method: POST </br> This API creates a player object. Only the firstname, lastname, and email are required. Anything else is optional. Opponents is not allowed to be passed in as a parameter. The sponsor parameter, if present, must be the ID of an existing sponsor. The request returns the newly created player object in JSON in its HTTP payload, including all attributes. If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.

(2) Get a player Path:player/{id} Method: GET </br> This returns a full player object in JSON in its HTTP payload. All existing fields, including the optional sponsor and list of opponents should be returned. The JSON should contain the full sponsor object, if present. The list of opponents can be either 
(a) list of player IDs, or 
(b) list of “shallow” player objects that do not have their opponents list populated. 
If you take option (b), you want to use techniques like lazy loading to avoid serializing the whole game network starting from the requested player in the JSON to be returned. If the player of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.

(3) Update a player Path: player/{id}?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$... Method: POST </br>

This API updates a player object. For simplicity, all the player fields (firstname, lastname, email, street, city, sponsor, etc), except opponents, should be passed in as query parameters. Required fields like email must be present. The object constructed from the parameters will completely replace the existing object in the server, except that it does not change the player’s list of opponents. Similar to the get method, the request returns the updated player object, including all attributes (first name, last name, email, opponents, sponsor, etc), in JSON. If the player ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.

(4) Delete a player URL: http://player/{id} Method: DELETE </br>

This deletes the player object with the given ID. If the player with the given ID does not exist, return 404. Otherwise, delete the player and remove any reference of this player from your persistence of opponent relations, and return HTTP status code 200 and the deleted player in JSON.

<b>Sponsor APIs:</b></br>
(5) Create an sponsor Path: sponsor?name=XX&description=YY&street=ZZ&... Method: POST </br>

This API creates an sponsor object. For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query parameters. Only name is required. The request returns the newly created sponsor object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.) If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.

(6) Get a sponsor Path:sponsor/{id} Method: GET </br>

This returns a full sponsor object in JSON in its HTTP payload. All existing fields, including name, description, street, and city, should be returned. If the sponsor of the given ID does not exist, the HTTP return code should be 404; otherwise, 200.

(7) Update a sponsor Path: sponsor/{id}?name=XX&description=YY&street=ZZ&... Method: POST </br>

This API updates a sponsor object. For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query parameters. Only name is required. Similar to the get method, the request returns the updated sponsor object, including all attributes in JSON. If the sponsor ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.

(8) Delete a sponsor URL: http://sponsor/{id} Method: DELETE </br>

This method deletes the sponsor object with the given ID. If there is still any player belonging to this sponsor, return 400. If the sponsor with the given ID does not exist, return 404. Return HTTP code 200 and the deleted object in JSON if the object is deleted;

<b>Opponent’s APIs:</b></br>
(9) Add an opponent Path:opponents/{id1}/{id2} Method: PUT </br>

This makes the two players with the given IDs opponents with each other. If either player does not exist, return 404. If the two players are already opponents, do nothing, just return 200. Otherwise, Record this opponent relation. If all is successful, return HTTP code 200 and any informative text message in the HTTP payload.

(10) Remove an opponent Path:opponents/{id1}/{id2} Method: DELETE </br>

This request removes the opponent relation between the two players. If either player does not exist, return 404. If the two players are not opponents, return 404. Otherwise, Remove this opponent relation. Return HTTP code 200 and a meaningful text message if all is successful.
