G.A.T.S.B.I.
======

Gabe Andrew and Tyler's System of Basic Intelligence.

GATSBI is a Chat bot created by Gabriel Driver-Wilson, Andrew Jansen, and Tyler Higley as a final project for Professor Levenick's CS-448: Machine Learning Class at Willamette University.

The Chat bot runs with three levels of understanding, attempting the highest level first and decreasing if unsuccessful.  
	3. Pattern Match:
		- Try to match a sentence pattern:
		- Pick out the subject, predicate, key features, etc, and respond appropriately.  
	2. Keyword Match:
		- Look for a keyword within the input.
		- Respond using the keyword.  
	1. Predetermined Response.
	
In addition, GATSBI runs on a server which collects information from the local application.

Primary Responsibilities:   
+ Tyler Higley-- Server/Client Side Socket, keyword match, and predetermined responses.  
+ Andrew Jansen-- File Structure, general IO, application structure.  
+ Gabriel Driver-Wilson-- Sentence pattern analysis and response, and version control management
	
The project is written in Java and will be hosted on Willamette University's shell server.