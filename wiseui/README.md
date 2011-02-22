WiseUI
========

The WiseUI is a [GWT][gwt] based web client for the [Testbed Runtime][testbedruntime].


Quick Install
---------------------------
Just follow the instruction provided in [our Wiki][quick].


Deployment on a Remote Tomact
-----------------------------
To use this profile, make sure you settings.xml (~.m2/settings.xml) looks like the example below. Replace the property values with your specific credentials, hostname, etc.

Also, it is highly recommended, that you SSH client is configured to log in on you remote server (deployment target) without password. This requires a special SSH setup. You have to create a private key with ssh-keygen and copy it into the file ~.ssh/authorized_keys on your remote system.

This is how your settings.xml should look like:

        <settings>
            <profiles>
                <profile>
                    <id>inject-deployment-details</id>
                    <properties>
                        <deployment.artifact.name>wiseui</deployment.artifact.name>
                        <deployment.user>root</deployment.user>
                        <deployment.host>141.83.0.100</deployment.host>
                        <deployment.directory>/usr/local/tomcat/webapps/</deployment.directory>
                    </properties>
                </profile>
            </profiles>
            <activeProfiles>
                <activeProfile>inject-deployment-details</activeProfile>
            </activeProfiles>
        </settings>

You can deploy the resulting war-file with:

$ pwd
$ [...]/testbed-runtime/wiseui
$ cd client
$ mvn install -Ddeploy=remote


More Documentation
==================
The [wiki][] is your friend.


[gwt]:http://code.google.com/webtoolkit/doc/2.1/DevGuide.html
[wiki]:https://www.itm.uni-luebeck.de/projects/testbed-runtime/wiki/WisebedWebUiDesign
[quick]:https://www.itm.uni-luebeck.de/projects/testbed-runtime/wiki/WisebedWebUiDevQuickStart
[testbedruntime]:https://github.com/itm/testbed-runtime
