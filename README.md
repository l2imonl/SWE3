# SWE3 (JAMU - Java Music Player)
## Funktionen:
- Nur .mp3 Dateien sind erlaubt 

## Installation:
Um die hochgeladenen Musik Datein abspielen zu können muss die Wildfly Konfiguration angepasst werden.
In der Standalone.xml jweils die zweite Zeile einfügen, welcome-content dient als Beispiel.

    <location name="/" handler="welcome-content"/>
    <location name="/musik" handler="musik"/>


und unter handlers folgendes:

    <file name="welcome-content" path="${jboss.home.dir}/welcome-content"/>
    <file name="musik" path="${jboss.server.data.dir}/musikOrdner" directory-listing="true"/>