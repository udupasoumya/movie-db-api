# movie-db-api

Dependencies:
1. Maven to handle dependencies
2. Rest Assured and Test NG for testing framework
3. Extent reports for reporting

Framework:
1. Configurations: env.properties: This is where the key for the API testing needs to be updated. This also has different end point URL's
2. Reports: test-output: ResultsExtentReport.html : This is the report generated based on TestNG Listener and the logger using extent-reports
Utils==>
3. src/main/java/utils : HelperMethods.java-->has some helper methods or pre requesite methods(for ex getting the movie ID which is required for all tests has not been hardcode but got in a helper method using search for a movie)
4. src/main/java/utils : ListenerTestNG.java --> implements the interface ITestListener and overrides methods like onStart, onSuccess, onFailure etc
5. src/main/java/utils : RequestSetUpMethods.java --> this has all methods related to setting up the request details for the API like, setting up the content type, queryparams, requestparams. Also has the request Calls like GET, DELETE,POST
6. src/main/java/utils : ResponseDetailsMethod.java --> has methods to Validate details in the response from API
Test==>
7. src/test/java/tests : BaseTest --> starting point for the test like setting up the reporting and the API request parameters
8. src/test/java/tests : VerifyGetCreditsDetails.java  and VerifyRatingUpdate.java actual tests
9. TestNG.xml: can be used to run the TestNG tests

How to Run:
1. get the code on to local
2. update the api key you want to use in the env.properties file within Configurations
3. You should be able to run the test and see the updated results in the Reports: test-output: ResultsExtentReport.html 
