
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNAndroidWallpaperSpec.h"

@interface AndroidWallpaper : NSObject <NativeAndroidWallpaperSpec>
#else
#import <React/RCTBridgeModule.h>

@interface AndroidWallpaper : NSObject <RCTBridgeModule>
#endif

@end
