package rxn.io.classic.stream.filter;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScrambledInputStream extends FilterInputStream {
  private final int[] map;

  public ScrambledInputStream(InputStream in, int[] map) {
    super(in);
    if (map == null) {
      throw new NullPointerException("map is null");
    }
    if (map.length != 256) {
      throw new IllegalArgumentException("map.length != 256");
    }
    this.map = map;
  }

  @Override
  public int read() throws IOException {
    int value = in.read();
    return (value == -1) ? -1 : map[value];
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    int nBytes = in.read(b, off, len);
    if (nBytes <= 0) {
      return nBytes;
    }
    for (int i = 0; i < nBytes; i++) {
      b[off + i] = (byte) map[off + i];
    }
    return nBytes;
  }
}
