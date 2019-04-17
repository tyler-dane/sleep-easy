## About
Rest API build in Java that creates local, compressed backups for your cloud accounts (Google, Dropbox, OneDrive, etc).

The following scenarios demonstrate why this could be useful.

- Someone gets the credentials to your cloud account (Google, Dropbox, OneDrive), deletes all your documents, and empties the trash.
- You permanently delete an email, certain that you'll never need it again. A few days later, you realize that you do, in fact, need the email.
- You need to access a document saved in the cloud, but your cloud provider's website is down and you have no local copy of the document.

What now?

The best option might be to recover from a physical backup that is stored locally or on a separate cloud account. 
Unfortunately, many cloud providers do not natively support this sort of backup, and creating them manually can be 
time-consuming and tedious. This program intends to solve this problem by automating backups for many popular cloud services, 
allowing users to sleep easy again.

## Features [WIP]
- Secure Authorization to cloud accounts 
- Compressed backups
- Incremental and full backups
- Option to configure backup schedule
- Summary and alerts emails 
 
### Requirements:
Java 1.8
- Path to Java binary should be `/usr/bin/java`. 
    - Edit `com.sleep.plist` if it's not
Gradle

### Running the program:
See the 'Usage' Wiki

### File types:
Below are the default file types that Google files are converted to.
These can be changed in `GFile.java`
- Google Docs --> `.docx`
- Google Sheets --> `.csv`
- Google Slides --> `.pptx`
//TODO file in the whole list

### Logs
- Launchd logs are sent to `/tmp/sleep-easy-backups/logs/`
- App logs are saved in `$DRIVE_HOME/logs`

### Known limitations:
- Filename cannot include `/`s. 
- Google Sheets can be saved as CSVs or XLSX files, but not both
- exported content is limited to 10MB ([DriveApi](https://developers.google.com/drive/api/v3/reference/files/export))
- By default, the compressed backups are saved to $HOME/sleep-easy-backups/backups. To change this, 
edit the `DRIVE_HOME` and `BACKUP_DIR` valeus in DownloaderSettings.java.

### Third-Party Documentation
- [Launchd](http://www.launchd.info/) (runs program as daemon on OSX)

### References
Other develeoper's related implementations that I found helpful

#### Auth:
https://github.com/smarx/othw

#### HTTP server:
https://www.logicbig.com/tutorials/core-java-tutorial/http-server/http-server-basic.html

#### oAuth
- Explain difference between HTTP **Server** (`com.sun.net.httpserver.` or `jetty`) and HTTP **Servlet** (`javax.servlet.http`)
    - [com.sun.net.httpserver](https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html)
    - [servlet](tbd)
