mkdir -p src/main/java
PATH = find -name *.java | uniq --check-chars 4 | sed  's/src.*/src/' | sed 's/\/\w*.java$//'| sed 's/\.\/[0-9]*//'
echo $PATH
cp -a $PATH src/main/java
