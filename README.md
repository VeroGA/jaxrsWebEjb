jaxrsWebEjb: Example Using Multiple Java EE 7 Technologies Deployed as an EAR
==============================================================================================
Author: Jesus Aguilar
Level: Intermediate
Technologies: EAR, JPA
Summary: Based on kitchensink, but deployed as an EAR
Target Project: WildFly
Source: <https://github.com/wildfly/quickstart/>

Configuracion del entorno
---------------

Para configurar el entorno se debe empezar por la BD, esta aplicacion en particular utiliza [MyBatis](http://www.mybatis.org/mybatis-3/getting-started.html) con postgres.

1- [Configurar Postgres](http://developer-should-know.com/post/127065962382/installing-postgresql-and-adding-data-source-to) con Wildfly:

        $JBOSS_HOME/bin/jboss-cli.sh -c
        	module add --name=org.postgresql --slot=main --resources=/temp/postgresql-9.4.1208.jar --dependencies=javax.api,javax.transaction.api
        	/subsystem=datasources/jdbc-driver=postgres:add(driver-name="postgres",driver-module-name="org.postgresql",driver-class-name=org.postgresql.Driver)

2- Luego de esto se debe configurar el datasource en el Wildfly con la consola web, o en el proyecto en:

        /jaxrsWebEjb-ejb/src/main/resources/META-INF/persistence.xml
        <jta-data-source>java:jboss/datasources/DS</jta-data-source>

3- Se debe configurar el datasource del MyBatis con la misma anteriormente configurada:

        /jaxrsWebEjb-ejb/src/main/resources/mybatis.properties

4- Configurar las entradas del jmeter para los archivos csv a tu $ECLIPSE_WORKSPACE y las direcciones de puerto para los HTTP request.

5- Enjoy it..

Start JBoss WildFly with the Web Profile
-------------------------

1. Open a command line and navigate to the root of the JBoss server directory.
2. The following shows the command line to start the server with the web profile:

        ux:   JBOSS_HOME/bin/standalone.sh
        For Windows: JBOSS_HOME\bin\standalone.bat
 
Build and Deploy the Quickstart
-------------------------

_NOTE: The following build command assumes you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Build and Deploy the Quickstarts](https://github.com/jboss-developer/jboss-eap-quickstarts#build-and-deploy-the-quickstarts) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package wildfly:deploy

4. This will deploy `target/jaxrsWebEjb.ear` to the running instance of the server.


Access the application 
---------------------

The application will be running at the following URL: <http://localhost:8080/jaxrsWebEjb-web>.

1. Enter a name, email address, and Phone nubmer in the input field and click the _Register_ button.
2. If the data entered is valid, the new member will be registered and added to the _Members_ display list.
3. If the data is not valid, you must fix the validation errors and try again.
4. When the registration is successful, you will see a log message in the server console:

        Registering _the_name_you_entered_


Undeploy the Archive
--------------------

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. When you are finished testing, type this command to undeploy the archive:

        mvn wildfly:undeploy


Run the Arquillian Tests 
-------------------------

This quickstart provides Arquillian tests. By default, these tests are configured to be skipped as Arquillian tests require the use of a container. 

_NOTE: The following commands assume you have configured your Maven user settings. If you have not, you must include Maven setting arguments on the command line. See [Run the Arquillian Tests](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/RUN_ARQUILLIAN_TESTS.md) for complete instructions and additional options._

1. Make sure you have started the JBoss Server as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type the following command to run the test goal with the following profile activated:

        mvn clean test -Parq-wildfly-remote


Investigate the Console Output
---------------------
You should see the following console output when you run the tests:

    Results :
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0


Investigate the Server Console Output
---------------------
You should see messages similar to the following:

    INFO  [org.jboss.as.server] (management-handler-thread - 9) JBAS018559: Deployed "test.war"
    INFO  [JaxrsEjb.jaxrsWebEjb.controller.MemberRegistration] (http--127.0.0.1-8080-2) Registering Jane Doe
    INFO  [JaxrsEjb.jaxrsWebEjb.test.MemberRegistrationTest] (http--127.0.0.1-8080-2) Jane Doe was persisted with id 1
    INFO  [org.jboss.weld.deployer] (MSC service thread 1-6) JBAS016009: Stopping weld service for deployment test.war
    INFO  [org.jboss.as.jpa] (MSC service thread 1-1) JBAS011403: Stopping Persistence Unit Service 'test.war#primary'
    INFO  [org.hibernate.tool.hbm2ddl.SchemaExport] (MSC service thread 1-1) HHH000227: Running hbm2ddl schema export
    INFO  [org.hibernate.tool.hbm2ddl.SchemaExport] (MSC service thread 1-1) HHH000230: Schema export complete
    INFO  [org.jboss.as.connector.subsystems.datasources] (MSC service thread 1-5) JBAS010409: Unbound data source [jboss/datasources/jaxrsWebEjbTestDS]
    INFO  [org.jboss.as.server.deployment] (MSC service thread 1-6) JBAS015877: Stopped deployment test.war in 19ms
    INFO  [org.jboss.as.server] (management-handler-thread - 10) JBAS018558: Undeployed "test.war"


Run the Quickstart in JBoss Developer Studio or Eclipse
-------------------------------------
You can also start the server and deploy the quickstarts from Eclipse using JBoss tools. For more information, see [Use JBoss Developer Studio or Eclipse to Run the Quickstarts](https://github.com/jboss-developer/jboss-developer-shared-resources/blob/master/guides/USE_JBDS.md) 


Debug the Application
---------------------

If you want to debug the source code or look at the Javadocs of any library in the project, run either of the following commands to pull them into your local repository. The IDE should then detect them.

        mvn dependency:sources
        mvn dependency:resolve -Dclassifier=javadoc
