# Android-PWA-Wrapper
The detailed tutorial can be found here for the Forked App https://drive.google.com/file/d/1y7yPqE2wEkxr983168RGaBjVH5kC5Jmz/view?usp=sharing
An Android Wrapper application to create native Android Apps from an offline-capable Progressive Web App.

Drafted for the [Android App](https://appdistribution.firebase.google.com/pub/i/88e2d08dfbd01e93) of my [Afriteach App](http://www.afriteach.com/) Web App using [React](https://github.com/facebook/react), [Redux](https://github.com/reactjs/redux), [Materialize.css](https://github.com/Dogfalo/materialize) and a lot of Offline-First love over at [afriteach.com/](http://www.afriteach.com/).

## Looking for iOS or Desktop?
Check out my other projects:
- [iOS-PWA-Wrapper](https://github.com/xtools-at/iOS-PWA-Wrapper) for iOS
- [Electron-PWA-Wrapper](https://github.com/xtools-at/Electron-PWA-Wrapper) for macOS, Windows and Linux

## Why would I use a wrapper?
Progressive web apps (PWAs) are simply awesome. They enable us to build an app that serves all devices and form factors once, using web technologies. A PWA can be accessible over the web but also surface on the home screen of your Android/iOS device. That app can work offline, display a splash screen when it launches, and have notifications, too. 
PWAs can help us save money as well. The alternative is building the same application using three different technologies (one for web, one for Android, and one for iOS).
When we take this path, it’s hard to avoid a multiplication of cost and complexity. It often leads to dividing up the team as each works on a different stack. It is common to lose a certain amount of focus. PWAs can help here. They are a compelling alternative not just from a developer standpoint, but from a resourcing one, too.
This tutorial will highlight one of these — how we can go from our very own React app to an android app using a “wrapper” in the quickest possible time.  Note that this tutorial presumes knowledge of:
This tutorial will highlight one of these — how we can go from our very own React app to an android app using a “wrapper” in the quickest possible time.  Note that this tutorial presumes knowledge of:

•	React 
•	Node (https://www.freecodecamp.org/news/create-a-react-frontend-a-node-express-backend-and-connect-them-together-c5798926047c/)
•	Android Studio basics including building with Gradle
•	Basics of hosting with Google Firebase (Firebase Tutorial https://www.youtube.com/watch?v=mmmaeHBCTOw)
•	Basics of Creating a signed APK in Android Studio
•	Hosting the APK in an android emulator


## What it does
- Sets up a WebView just the way PWAs/SPAs like it (e.g. enables App cache and DOM storage, ...).
- Shows a loading spinner while fetching the Web App.
- Provided your Web App is Offline-capable, it only needs an Internet connection on the first startup. If this fails, it shows a native refresh widget.
- Opens all external URLs in the device's Browser instead.
- Checks for Internet connection and fetches Updates for your Web App accordingly.
- Is compatible down to JellyBean, although it's recommended to build for SDK Version >= 19 (KitKat). Building for SDK Version >= 21 (Lollipop) puts you on the safe side without having to worry too much about Browser support.
- APK-size < 1.4 MB. The latest cat video from WhatsApp weighs heavier ;)

## How to build your own
- Get Android Studio 3.4+
- Clone/fork repository
- Put your Web App's URL in _WEBAPP_URL_ in `Constants.java`
- Replace *app_name* in `strings.xml` with the name of your App
- Add your own primary colors to `colors.xml` (*colorPrimary, colorPrimaryDark, colorPrimaryLight*)
- Put your own icons in place:
    - Add your own _ic_launcher.png_ and _ic_launcher_round.png_ in the `mipmap` folders
    - Add your own _ic_appbar.png_ in the `drawables` folders. This is displayed in Android's _Recent Apps_ View on your app bar, so it should look nicely when placed on top of your primary color.
    - I recommend using [Android Asset Studio](https://romannurik.github.io/AndroidAssetStudio) to get the icons ready in no time
- Change the package name in `app/build.gradle`, *applicationId*
- Change `AndroidManifest.xml` -> `aplication` -> `activity` -> `intent-filter` to your own URLs/schemes/patterns/etc. or remove the `intent-filter` for `android.intent.action.VIEW` altogether
- Check `Constants.java` for more options
- Build App in Android Studio

## License
[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html) - if you use it, we wanna see it!
Other licensing options are available on inquiry.

### Detailed Tutorial Steps - Part 1
•	First deploy your react app to Firebase. To Deploy the app to Firebase use this tutorial https://www.youtube.com/watch?v=IDHfvpsYShs . You will need your Firebase URL in the android app in the app\google-services.json file. For example in my app the Firebase URL was  
"firebase_url": https://afriteach-aa319.firebaseio.com. 

•	You will also need the application ID from Firebase, which you will enter into the app/build.gradle file in Android studio wrapper project we will set up. In my case the application ID was applicationId "com.afriteach.afriteach"
•	Download and install Android Studio 4.1https://developer.android.com/studio
•	After downloading and installing Android Studio with all its latest updates,  go to File>New>Project from Version Control
•	Clone/fork repository https://github.com/xtools-at/Android-PWA-Wrapper 
•	In the Repository URL Enter https://github.com/xtools-at/Android-PWA-Wrapper.git and click Clone. Once the project has finished cloning make changes to the following files:
•	Put your Web App's URL in WEBAPP_URL in Constants.java
•	Replace app_name in strings.xml with the name of your App
•	Add your own primary colors to colors.xml (colorPrimary, colorPrimaryDark, colorPrimaryLight)
•	Put your own icons in place:
o	Add your own ic_launcher.png and ic_launcher_round.png in the mipmap folders
o	Add your own ic_appbar.png in the drawables folders. This is displayed in Android's Recent Apps View on your app bar, so it should look nicely when placed on top of your primary color.
o	I recommend using Android Asset Studio to get the icons ready in no time
•	Change the package name in app/build.gradle, applicationId
•	Change AndroidManifest.xml -> aplication -> activity -> intent-filter to your own URLs/schemes/patterns/etc. or remove the intent-filter for android.intent.action.VIEW altogether
•	Check Constants.java for more options
•	Build App in Android Studio by going to Build>Generate Signed APK


### Detailed Tutorial Steps - Part 2
Follow the Signed APK Wizard in Android Studio to create a signed APK that can be deployed on Google Firebase for Testing. 
To connect Firebase to your app in Android Studio, go to  Tools>Firebase>Test Lab
Rebuild the project in order to update the firebase dependencies in your project, and create a new signed APK. There a number of tutorials on how to setup Firebase dependencies in the app/build.gradle file. The best instructions are actually from Firebase tutorials https://firebase.google.com/docs/android/setup . Your app/build.grade file should look like the one below once the firebase dependencies are updated.

Go to developer.google.com for deployment to the Google play Store with the Google play console https://developer.android.com/distribute.
Follow the steps to create a new app and deploy on the Google play store.
Here is a link to the Github Repository https://github.com/mpolobe/Android-PWA-Wrapper.
You can test your APK wrapper by uploading the Apk to https://appetize.io/. Here is the link to the Reactjs app on Appetize.io after our android wrapped app was loaded.
https://appetize.io/app/txkecypjfgw7ennh5twm7wr87g?device=galaxytabs7&scale=75&orientation=portrait&osVersion=10.0
You can also distribute your app on Google Firebase before deploying to the Play store by going to https://firebase.google.com/ and signing up with your Gmail account.

### Detailed Tutorial Steps - Part 3 - Testing the Deployed APK

Here is the App Distribution link to download the APK I wrapped. You can download and  and install the app on an android phone https://appdistribution.firebase.google.com/pub/i/88e2d08dfbd01e93

When you sign up on the app distribution link from Firebase, you will receive an email with instructions on how to  to download the APK to an andorid device, install the app and test it.




