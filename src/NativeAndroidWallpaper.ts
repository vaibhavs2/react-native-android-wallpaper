import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
import type { WallpaperScreenType } from 'react-native-android-wallpaper';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): Promise<number>;

  isSetWallpaperAllowed(): Promise<boolean>;
  setWallpaper(
    imageUrl: string,
    whichScreen: WallpaperScreenType
  ): Promise<string>;

  getCropSetWallpaper(imageUrl: string, whichScreen: WallpaperScreenType): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('AndroidWallpaper');
