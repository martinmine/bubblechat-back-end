# README #

### What is this repository for? ###

This repo holds the back-end code for a project in [IMT3662 Mobile Development Theory](http://hig.no/content/view/full/31141/language/nor-NO).
It is basically a server which receives messages from Google Cloud Messaging (GCM) through XMPP. 

### Module Dependencies ###

* MySQL J Connector: http://dev.mysql.com/downloads/connector/j/5.1.html
* Smack 4.0.4 library (for XMPP communication): http://www.igniterealtime.org/downloads/index.jsp 
* SimpleJSON: https://json-simple.googlecode.com/files/json_simple-1.1.jar
* XML PullParser for XML parsing: http://www.extreme.indiana.edu/dist/java-repository/xpp3/distributions/xpp3-1.1.3.4.C_all.tgz 

### Contribution guidelines ###

* Create a table in a MySQL database:

```
#!MySQL

CREATE TABLE `node` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gcmKey` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `lastPinged` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

```

* Change the settings in your server.properties for gcm, MySQL, etc.
* Ready for launch!