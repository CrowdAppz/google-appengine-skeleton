appengine-skeleton
=============================

This is a generated application from the appengine-skeleton archetype.
Tips and tricks for deploying:

<h1>Deploy on AppEngine</h1>
Before updating appengine, make sure the project can be built:
> mvn clean install

Update the backend on appengine with Maven.
Open shell in project directory and type
> mvn appengine:update

<h3>Important!</h3>
Even though maven states "Build Success" the update may not have been successful on appengine. Have a look at the logs of the backend and search it for any error logs, if you have a troublesome feeling that something could have went wrong.
The logs can be accessed under <b>https://console.developers.google.com -> Your application -> AppEngine -> Logs</b>
Errors could occur,for instance,  when native types are returned by the API, which is not allowed! (native types are int, String,...)

<h2>Application ID</h2>
The application Id of the appengine the code is pushed to is defined in the <b>appengine-web.xml</b>.
It has the following structure:
> &lt;?xml version="1.0" encoding="utf-8"?><br/>
    &lt;appengine-web-app xmlns="http://appengine.google.com/ns/1.0"><br/>
         &lt;application>%applicationIdPlaceholder%&lt;/application><br/>
        &lt;version>1&lt;/version><br/>
         ...<br/>
    &lt;/appengine-web-app>

%applicationIdPlaceholder% is for this project replaced by <b>crowdappz-drop</b>

<h2>Troubleshooting</h2> (https://developers.google.com/appengine/docs/java/tools/uploadinganapp)<br/>
Maybe you get following error: <b>404 Not Found This application does not exist (app_id=u'your-app-ID')</b><br/>
This error will occur if you have multiple Google accounts and are using the wrong account to perform the update.
To solve this issue, change directories to ~, locate a file named .appcfg_oauth2_tokens_java, and rename it. Then try updating again.

After you have performed the Troubleshooting steps, type in again 
> mvn appengine:update

You will get prompeted for a code, simultaneously a browser tab will open, requesting you to log in. Choose the account you used to create the application on appengine. After the login process, a code is displayed in the browser. Copy and paste that code into your shell and press Enter. The app should be deployed successfully now.

<h1>API</h1>
Access the API of the backend by visiting: https://yourProjectId.appspot.com/_ah/api/explorer

In general, the API of the backend looks like this: https://yourProjectId.appspot.com/_ah/api/%endPointName%/%version%/%methodPlaceholder%

%endPointName% and %version% are defined in the Endpoint classes of this project. <br/>
%methodPlaceholder% is the name of the requested method by its name.
Currently, the a link could look like this:
https://yourProjectId.appspot.com/_ah/api/dropTest/v0/testPing

Via this link, the backend can be accessed as a REST service from everywhere.

Visit the following link for usage and cost statistics of the deployed backend: https://your-project-id.appspot.com/appstats

If you want to access a non-default version of your app, modify the specific urls like this: <br/> version-dot-crowdappz-drop.appspot.com <br/>
i.e.: 1-2-dot-yourProjectId.appspot.com
