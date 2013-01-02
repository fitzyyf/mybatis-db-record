# mybatis-db-record
A simplified version of the code entities Tools

1. clone repository
    
        git clone git@github.com:yfyang/mybatis-db-record.git
    
2. make&build

        mvn clean package
        
3. find tool file

    open the file `myrecord-jar-with-dependencies.jar` in the `target` directory,the replacement for the name of thire favorite,such as `dodomain.jar`
    
4. run the file

        # help
        jar -jar dodomain.jar -h
 
5. Parameter information

        
        -d <arg>   Database instance name
        -h         mybatis mapper generation tools to help
        -o <arg>   Code file output path to the current working directory if it
            is empty, then the output
        -p <arg>   Database login user password
        -package   Package names generated in the specified output directory is
            empty
        -port      The port number of the database instance
        -project   Project Name
        -s <arg>   Ip address of the database server, default 127.0.0.1
        -t <arg>   Database type, currently only supports mysql and oracle
        -u <arg>   Database user account login
