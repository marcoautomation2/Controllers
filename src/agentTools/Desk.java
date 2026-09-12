package agentTools;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/// The screen with its pointer and keyboard, as a person would see and use them.
public interface Desk extends AutoCloseable{
  enum Button{
    left(0x110,InputEvent.BUTTON1_DOWN_MASK), middle(0x112,InputEvent.BUTTON2_DOWN_MASK), right(0x111,InputEvent.BUTTON3_DOWN_MASK);
    final int evdev; final int mask;
    Button(int evdev, int mask){ this.evdev= evdev; this.mask= mask; }
  }
  final class Key{
    static final Key control= new Key(0xffe3,KeyEvent.VK_CONTROL);
    static final Key alt= new Key(0xffe9,KeyEvent.VK_ALT);
    static final Key f4= new Key(0xffc1,KeyEvent.VK_F4);
    static final Key enter= new Key(0xff0d,KeyEvent.VK_ENTER);
    static final Key backspace= new Key(0xff08,KeyEvent.VK_BACK_SPACE);
    static final Key tab= new Key(0xff09,KeyEvent.VK_TAB);
    final int keysym; final int code;
    private Key(int keysym, int code){ this.keysym= keysym; this.code= code; }
    /// A key that types the given printable ASCII character: X11 keysyms for 0x20-0x7e are the character's own code, unshifted.
    static Key of(char c){ return new Key(c,KeyEvent.getExtendedKeyCodeForChar(c)); }
  }
  void move(int x, int y);
  void button(Button b, boolean down);
  void key(Key k, boolean down);
  BufferedImage shot();
  @Override void close();
  static Desk open(){ return System.getenv("WAYLAND_DISPLAY")==null ? new Awt() : new Mutter(); }
}
