import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-android-wallpaper' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

const AndroidWallpaperModule = isTurboModuleEnabled
  ? require('./NativeAndroidWallpaper').default
  : NativeModules.AndroidWallpaper;

const AndroidWallpaper = AndroidWallpaperModule
  ? AndroidWallpaperModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export type WallpaperScreenType = 'lock' | 'home' | 'both';

export function isSetWallpaperAllowed(): Promise<boolean> {
  return AndroidWallpaper.isSetWallpaperAllowed();
}

export function setWallpaper(
  imageUrl: string,
  whichScreen: WallpaperScreenType
): Promise<string> {
  return AndroidWallpaper.setWallpaper(imageUrl, whichScreen);
}

export function getCropSetWallpaper(
  imageUrl: string,
  whichScreen: WallpaperScreenType
) {
  AndroidWallpaper.getCropSetWallpaper(imageUrl, whichScreen);
}
