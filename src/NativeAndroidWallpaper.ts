import { TurboModuleRegistry, type TurboModule } from 'react-native';

type WallpaperScreenType = 'LOCK' | 'HOME' | 'BOTH';

export interface Spec extends TurboModule {
  multiply(a: number, b: number): number;

  isSetWallpaperAllowed(): boolean;

  setWallpaper(
    imageUrl: string,
    whichScreen: WallpaperScreenType
  ): Promise<string>;

  getCropSetWallpaper(imageUrl: string, whichScreen: WallpaperScreenType): void;
}

export default TurboModuleRegistry.getEnforcing<Spec>('AndroidWallpaper');
