import type { TurboModule } from 'react-native';
import type { WallpaperScreenType } from 'react-native-android-wallpaper';
export interface Spec extends TurboModule {
    multiply(a: number, b: number): Promise<number>;
    isSetWallpaperAllowed(): Promise<boolean>;
    setWallpaper(imageUrl: string, whichScreen: WallpaperScreenType): Promise<string>;
    getCropSetWallpaper(imageUrl: string, whichScreen: WallpaperScreenType): void;
}
declare const _default: Spec;
export default _default;
//# sourceMappingURL=NativeAndroidWallpaper.d.ts.map