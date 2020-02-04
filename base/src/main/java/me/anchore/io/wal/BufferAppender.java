package me.anchore.io.wal;

import me.anchore.io.util.IoUtil;

import java.io.Closeable;
import java.nio.BufferOverflowException;
import java.nio.charset.StandardCharsets;

abstract class BufferAppender<V> implements Closeable {
    BufferRoller bufferRoller;

    protected BufferAppender(BufferRoller bufferRoller) {
        this.bufferRoller = bufferRoller;
    }

    public static BufferAppender<Byte> ofByte(BufferRoller bufferRoller) {
        return new BufferAppender<Byte>(bufferRoller) {
            @Override
            void append(Byte v) {
                try {
                    bufferRoller.getCurrentBuffer().put(v == null ? 0 : v);
                } catch (NullPointerException | BufferOverflowException e) {
                    bufferRoller.rollToNextBuffer().put(v == null ? 0 : v);
                }
            }
        };
    }

    public static BufferAppender<Integer> ofInt(BufferRoller bufferRoller) {
        return new BufferAppender<Integer>(bufferRoller) {
            @Override
            void append(Integer v) {
                try {
                    bufferRoller.getCurrentBuffer().putInt(v == null ? 0 : v);
                } catch (NullPointerException e) {
                    bufferRoller.rollToNextBuffer().putInt(v == null ? 0 : v);
                } catch (BufferOverflowException e) {
                    int val = v == null ? 0 : v;
                    for (int shift = Integer.SIZE - Byte.SIZE;
                         shift >= 0; shift -= Byte.SIZE) {
                        byte b = (byte) ((val >> shift) & 0xFF);
                        try {
                            bufferRoller.getCurrentBuffer().put(b);
                        } catch (BufferOverflowException ex) {
                            bufferRoller.rollToNextBuffer().put(b);
                        }
                    }
                }
            }
        };
    }

    public static BufferAppender<Long> ofLong(BufferRoller bufferRoller) {
        return new BufferAppender<Long>(bufferRoller) {
            @Override
            void append(Long v) {
                try {
                    bufferRoller.getCurrentBuffer().putLong(v == null ? 0 : v);
                } catch (NullPointerException e) {
                    bufferRoller.rollToNextBuffer().putLong(v == null ? 0 : v);
                } catch (BufferOverflowException e) {
                    long val = v == null ? 0 : v;
                    for (int shift = Long.SIZE - Byte.SIZE;
                         shift >= 0; shift -= Byte.SIZE) {
                        byte b = (byte) ((val >> shift) & 0xFF);
                        try {
                            bufferRoller.getCurrentBuffer().put(b);
                        } catch (BufferOverflowException ex) {
                            bufferRoller.rollToNextBuffer().put(b);
                        }
                    }
                }
            }
        };
    }

    public static BufferAppender<Double> ofDouble(BufferRoller bufferRoller) {
        return new BufferAppender<Double>(bufferRoller) {
            @Override
            void append(Double v) {
                try {
                    bufferRoller.getCurrentBuffer().putDouble(v == null ? 0 : v);
                } catch (NullPointerException e) {
                    bufferRoller.rollToNextBuffer().putDouble(v == null ? 0 : v);
                } catch (BufferOverflowException e) {
                    long val = v == null ? 0 : Double.doubleToLongBits(v);
                    for (int shift = Long.SIZE - Byte.SIZE;
                         shift >= 0; shift -= Byte.SIZE) {
                        byte b = (byte) ((val >> shift) & 0xFF);
                        try {
                            bufferRoller.getCurrentBuffer().put(b);
                        } catch (BufferOverflowException ex) {
                            bufferRoller.rollToNextBuffer().put(b);
                        }
                    }
                }
            }
        };
    }

    public static BufferAppender<String> ofString(BufferRoller bufferRoller) {
        return new BufferAppender<String>(bufferRoller) {
            @Override
            void append(String v) {
                try {
                    bufferRoller.getCurrentBuffer().putInt(v == null ? 0 : v.length());
                } catch (NullPointerException e) {
                    bufferRoller.getCurrentBuffer().putInt(v == null ? 0 : v.length());
                } catch (BufferOverflowException e) {
                    int val = v == null ? 0 : v.length();
                    for (int shift = Integer.SIZE - Byte.SIZE;
                         shift >= 0; shift -= Byte.SIZE) {
                        byte b = (byte) ((val >> shift) & 0xFF);
                        try {
                            bufferRoller.getCurrentBuffer().put(b);
                        } catch (BufferOverflowException ex) {
                            bufferRoller.rollToNextBuffer().put(b);
                        }
                    }
                }
                if (v != null) {
                    for (byte b : v.getBytes(StandardCharsets.UTF_8)) {
                        try {
                            bufferRoller.getCurrentBuffer().put(b);
                        } catch (NullPointerException | BufferOverflowException e) {
                            bufferRoller.rollToNextBuffer().put(b);
                        }
                    }
                }
            }
        };
    }

    abstract void append(V v);

    @Override
    public void close() {
        IoUtil.close(bufferRoller);
    }
}
