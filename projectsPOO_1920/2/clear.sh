mkdir -p src/main/java
CAM=$(find -name *.java | uniq --check-chars 4 | sed  's/src.*/src/' | sed '^(?!_)s/\/\w*.java$//'| sed 's/\.\/[0-9]*//')
echo $CAM
cp -a $CAM/. src/main/java
find src/main/java/ -type f ! -name '*.java' -delete
