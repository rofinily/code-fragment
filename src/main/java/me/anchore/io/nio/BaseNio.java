package me.anchore.io.nio;


import me.anchore.io.Io;

/**
 * @author anchore
 * @date 2018/7/20
 */
abstract class BaseNio implements Io {
    NioConf conf;

    BaseNio(NioConf conf) {
        this.conf = conf;
    }
}