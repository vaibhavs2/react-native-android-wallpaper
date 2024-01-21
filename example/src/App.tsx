import * as React from 'react';

import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';
import {
  isSetWallpaperAllowed,
  setWallpaper,
} from 'react-native-android-wallpaper';

const IMAGE = 'https://picsum.photos/200';
export default function App() {
  const onPress = async () => {
    isSetWallpaperAllowed()
      .then(() => setWallpaper(IMAGE, 'BOTH'))
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
