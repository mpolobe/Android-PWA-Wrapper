# Android-PWA-Wrapper
The detailed tutorial can be found here for the Forked App https://drive.google.com/file/d/1y7yPqE2wEkxr983168RGaBjVH5kC5Jmz/view?usp=sharing
An Android Wrapper application to create native Android Apps from an offline-capable Progressive Web App.

Drafted for the [Android App](https://play.google.com/store/apps/details?id=at.xtools.leasingrechner&utm_source=github.com&utm_medium=link&utm_campaign=store_visit) of my [Leasing Calculator](https://www.leasingrechnen.at) Web App using [React](https://github.com/facebook/react), [Redux](https://github.com/reactjs/redux), [Materialize.css](https://github.com/Dogfalo/materialize) and a lot of Offline-First love over at [leasingrechnen.at](https://www.leasingrechnen.at).

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

### I don't accept Feature Requests, only Pull Requests :)

## License
[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html) - if you use it, we wanna see it!
Other licensing options are available on inquiry.
