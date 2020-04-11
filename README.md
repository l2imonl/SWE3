# SWE3 (JAMU - Java Music Player)
## Funktionen:
- Nur .mp3 Dateien sind erlaubt 

## Installation:
Um die hochgeladenen Musik Datein abspielen zu können muss die Wildfly Konfiguration angepasst werden.
In der wildfly/standalone/configuration/standalone.xml jeweils die zweite Zeile einfügen, welcome-content dient als Beispiel.

    <location name="/" handler="welcome-content"/>
    <location name="/musik" handler="musik"/>


und unter handlers folgendes:

    <file name="welcome-content" path="${jboss.home.dir}/welcome-content"/>
    <file name="musik" path="${jboss.server.data.dir}/musikOrdner" directory-listing="true"/>
    
Der Upload ist außerdem durch max-post-size limitiert, ohne Änderung in der standalone.xml schlagen Uploads >= 10MB fehl.

## Verwendete Bibliotheken:
	https://github.com/mpatric/mp3agic