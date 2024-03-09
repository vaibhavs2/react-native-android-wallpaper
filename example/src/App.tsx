import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import {
  isSetWallpaperAllowed,
  getCropSetWallpaper,
} from 'react-native-android-wallpaper';

const IMAGE =
  'https://firebasestorage.googleapis.com/v0/b/wallpaper-975d0.appspot.com/o/images%2F1707767945658.webp?alt=media';
export default function App() {
  const onPress = async () => {
    isSetWallpaperAllowed()
      .then(() => getCropSetWallpaper(IMAGE, 'BOTH'))
      .catch(() => console.log("Can't set wallpaper"));
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity onPress={onPress} style={styles.button}>
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
  button: { padding: 40, backgroundColor: 'pink' },
});
