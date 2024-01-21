import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import {
  getCropSetWallpaper,
  isSetWallpaperAllowed,
} from 'react-native-android-wallpaper';

const IMAGE =
  'https://firebasestorage.googleapis.com/v0/b/wallpaper-975d0.appspot.com/o/images%2Fviratkholi-1.jpeg?alt=media';
export default function App() {
  const onPress = async () => {
    isSetWallpaperAllowed()
      .then(() => getCropSetWallpaper(IMAGE, 'home'))
      .catch(() => console.log("Can't set wallpaper"));
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity
        onPress={onPress}
        style={{ padding: 40, backgroundColor: 'pink' }}
      >
        <Text>Set Wallpaper</Text>
      </TouchableOpacity>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
