find -name *.java -not -path "./*\_\_MACOSX/*"| sed  's/src.*/src/' | sed 's/\/\w*.java$//'| sed 's/\.\///' | sort -u > output.txt
while read -r line
do
	PP=$(echo "$line" | cut -c -2)
	cd $PP
	mkdir -p src/main/java
	CAM=$(find -name *.java -not -path "./*\_\_MACOSX/*"| sed  's/src.*/src/' | sed 's/\/\w*.java$//'| sed 's/\.\///' | sort -u | xargs -0) 
 	cp -a "$CAM"/. src/main/java
	PAS=$(basename "$PWD")
	find src/main/java/ -type f ! -name '*.java' -delete
	echo '<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>groupId</groupId>
	<artifactId>'$PAS'</artifactId>
	<version>1.0-SNAPSHOT</version>
	<properties>
	<maven.compiler.source>1.8</maven.compiler.source>
	<maven.compiler.target>1.8</maven.compiler.target>
	</properties>
	</project>
	<!-- business data  -->' > pom.xml
	mvn compile
	mvn package
	mvn sonar:sonar 
	cd ..
done < output.txt

