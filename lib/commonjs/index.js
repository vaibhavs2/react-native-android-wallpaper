"use strict";

Object.defineProperty(exports, "__esModule", {
  value: true
});
exports.getCropSetWallpaper = getCropSetWallpaper;
exports.isSetWallpaperAllowed = isSetWallpaperAllowed;
exports.setWallpaper = setWallpaper;
var _reactNative = require("react-native");
const LINKING_ERROR = `The package 'react-native-android-wallpaper' doesn't seem to be linked. Make sure: \n\n` + _reactNative.Platform.select({
  ios: "- You have run 'pod install'\n",
  default: ''
}) + '- You rebuilt the app after installing the package\n' + '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;
const AndroidWallpaperModule = isTurboModuleEnabled ? require('./NativeAndroidWallpaper').default : _reactNative.NativeModules.AndroidWallpaper;
const AndroidWallpaper = AndroidWallpaperModule ? AndroidWallpaperModule : new Proxy({}, {
  get() {
    throw new Error(LINKING_ERROR);
  }
});
function isSetWallpaperAllowed() {
  return AndroidWallpaper.isSetWallpaperAllowed();
}
function setWallpaper(imageUrl, whichScreen) {
  return AndroidWallpaper.setWallpaper(imageUrl, whichScreen);
}
function getCropSetWallpaper(imageUrl, whichScreen) {
  AndroidWallpaper.getCropSetWallpaper(imageUrl, whichScreen);
}
//# sourceMappingURL=index.js.map