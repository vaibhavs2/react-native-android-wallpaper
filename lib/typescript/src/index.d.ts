export type WallpaperScreenType = 'LOCK' | 'HOME' | 'BOTH';
export declare function isSetWallpaperAllowed(): Promise<boolean>;
export declare function setWallpaper(imageUrl: string, whichScreen: WallpaperScreenType): Promise<string>;
export declare function getCropSetWallpaper(imageUrl: string, whichScreen: WallpaperScreenType): void;
//# sourceMappingURL=index.d.ts.map