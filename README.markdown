# bnetchecker #

This little Java App checks your Battle.net account to see if you have access to Mists of Pandaria Beta. 

# How to use it #

Checkout git repo and run maven to build the package:

mvn package

Maven will fetch the required dependencies and build the JAR file. Afterwards you can run the tool like this:

java -jar target/bnetchecker-0.0.1-SNAPSHOT.jar ACCOUNT PASSWORD

e.g.:

java -jar target/bnetchecker-0.0.1-SNAPSHOT.jar you@me.com 12345

You can check more than one account at once:

java -jar target/bnetchecker-0.0.1-SNAPSHOT.jar ACCOUNT1 PASSWORD1 ACCOUNT2 PASSWORD2 ...