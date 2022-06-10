# Springboot Exercise Whitelist api

## Description
- this is an app that creates maintains a whitelist.txt file
  - the file holds a list whitelist details for apps and environment
  - each row is an instance of this
  - it follows the format ip,environment,application
- the application is generally split into layers and models used
- the `resources/application.yml` file contains default app settings
  - there is a default `whitelist.txt` added as a sample.
  - you can change the directory and file in the said `application.yml`

## limitations
- currently the app does not check the reliability of the contents of the file
  - it assumes that if follows expected rules
  - an improvement would be to check the file or to move to a database and just have a feature to produce the file
  - but for this exercise its just maintains and use a whitelist.txt file as a form of persistence


### Setup and running the app
- this app uses mvn so upon checking out the git repo, do the following:
  - `mvn clean install`
    - this installs all dependencies in the pom
  - `mvn clean package`
    - this creates a jar file to run
  - `java -jar target/whitelist-0.0.1-SNAPSHOT.jar`
    - this runs specified jar file
- if you have an idea, you can just open the idea; and it should have ways to run the java project based on conventions

## TODO/improvements
- add profiles to switch different services
  - the current service is a file based one
  - this works if you are actually maintaining a literal whitelist file used by a service
  - adding profiles would allow us to quickly switch configurations
- add service layer test
  - since we are not using a repo the bulk of the actions are in the service
    - I struggled if I should make a repo layer
    - but this File classes basically does the repo or persistence interaction which is the repository layer
    - however to add proper tests to this one needs to mock a lot but should be doable hence TODO