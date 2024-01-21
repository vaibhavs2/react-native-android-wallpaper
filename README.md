# react-native-android-wallpaper

You can crop and set a new wallpaper to your android device using a image URL.

## Installation

```sh
npm install https://github.com/vaibhavs2/react-native-android-wallpaper.git
```

## Usage

### Check if setting wallpaper allowed
```js

import { isSetWallpaperAllowed } from 'react-native-android-wallpaper';

isSetWallpaperAllowed().then(allowed=> console.log(allowed))

```

### Set a new wallpaper with a specified image URL
```js
import { setWallpaper } from 'react-native-android-wallpaper';

/**
 * 
 * @param  url  -> image string url
 * 
 * @param whereToSetWallpaper -> one of "lock"/ "home" / "both"
 * home -> home screen
 * lock -> lock screen
 * both -> both home and lock screen
 * 
 * */

const result = await setWallpaper("https://someurls.png", 'lock');

```

### Crop and set a new wallpaper with a specified image URL
```js
import { getCropSetWallpaper } from 'react-native-android-wallpaper';

/**
 * 
 * @param  url  -> image string url
 * 
 * @param whereToSetWallpaper -> one of "lock"/ "home" / "both"
 * home -> home screen
 * lock -> lock screen
 * both -> both home and lock screen
 * 
 * */

// opens a new activity where you can crop image and set cropped image as  wallpaper
const result = await getCropSetWallpaper("https://someurls.png", 'lock');
 
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
