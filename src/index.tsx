import AndroidWallpaper from './NativeAndroidWallpaper';

export function multiply(a: number, b: number): number {
  return AndroidWallpaper.multiply(a, b);
}

export type WallpaperScreenType = 'LOCK' | 'HOME' | 'BOTH';

export function isSetWallpaperAllowed() {
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
  whichScreen: WallpaperScreenType = 'BOTH'
) {
  AndroidWallpaper.getCropSetWallpaper(imageUrl, whichScreen);
}
