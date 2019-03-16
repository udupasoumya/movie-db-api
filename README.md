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

How to Run(on local with an IDE by running TestNG.xml)
1. get the code on to local and open it in IDE
2. update the api key you want to use in the env.properties file within Configurations
3. Run the TestNG.xml
4. You should be able to run the test and see the updated results in the Reports: test-output: ResultsExtentReport.html 

How to Run(on local with Maven)
1. The pom.xml has the details on the TestNG suite
2. On local make sure you clone the repository
3. Navigate to the directory for the repo on local on cmd line
4. Make sure maven is installed: **_mvn --version_** should give u the details on the maven version installed as well as the java jdk version. Make note of the jdk version(example below)
mvn --version
Apache Maven 3.6.0 (97c98ec64a1fdfee7767ce5ffb20918da4f719f3; 2018-10-24T11:41:47-07:00)
Maven home: /Users/soumya/Documents/apache-maven-3.6.0
Java version: **_11.0.2_**, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-11.0.2.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.13.6", arch: "x86_64", family: "mac"
5. For the above the jdk is 11
6. Update the pom.xml within the project to show the correct jdk version for the maven compiler plugin
`<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
               **<version>3.7.0</version>**
                <configuration>
                    <release>11</release>
                </configuration>
            </plugin>`
7. Make sure that release above matches with the jdk on your system(as in the above case its 11)
8. Save and close pom.xml
9. Now from cmd line within the project folder type >> **_mvn clean test_**
10. This will run the test on local

