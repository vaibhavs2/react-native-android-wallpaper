import { Text, View, StyleSheet, Button } from 'react-native';
import { multiply, setWallpaper } from 'react-native-android-wallpaper';

const result = multiply(3, 7);
const IMAGE =
  'https://firebasestorage.googleapis.com/v0/b/wallpaper-975d0.appspot.com/o/images%2F1707767945658.webp?alt=media';
export default function App() {
  return (
    <View style={styles.container}>
      <Text>Result: {result}</Text>
      <Button
        onPress={() => {
          console.log('billu', multiply(1, 2), setWallpaper(IMAGE, 'BOTH'));
        }}
        title="billu"
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
