#! /bin/sh
java="$JAVA_HOME/bin/java"
echo "Enter spring and mybatis's file's path:"
read PATH
exec $java -jar nut_genterate.jar $PATH
